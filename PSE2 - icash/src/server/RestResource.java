package server;

import java.sql.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;

import com.sun.jersey.spi.resource.Singleton;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.json.*;

import classes.*;

@Path("/")
@Singleton
public class RestResource {
	@GET
	@Path("/getAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getAccount(@QueryParam("number")int number,
			                   @Context HttpServletRequest req) {
		
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getAccount");
		logger.info(new java.util.Date() + ": Account: " + number);
		
		if (number == 0) 
			return Response.status(400).build();
		JSONObject jo = new JSONObject();
		try {
		Account a = new Account(number);
		if (a.getId() == 0)
			return Response.status(404).build();
		
		jo.put("id", a.getBank().getId());
		jo.put("number", a.getId());
		jo.put("owner", a.getCustomer().getFirstName() + " " + a.getCustomer().getSecondName());
        
		JSONArray ja = new JSONArray();
		Transaction[] t = new Transaction[a.getTransactions().size()];
		a.getTransactions().toArray(t);
		
		for (int i=0; i<t.length; i++) {
			JSONObject transaction = new JSONObject();
			transaction.put("amount", Convert.toEuro(t[i].getAmount()));
			transaction.put("id", t[i].getId());
			
			JSONObject receiver = new JSONObject();
			receiver.put("id", t[i].getIncomingAccount().getBank().getId());
			receiver.put("number", t[i].getIncomingAccount().getId());
			receiver.put("owner", t[i].getIncomingAccount().getCustomer().getFirstName() + " " 
			                    + t[i].getIncomingAccount().getCustomer().getSecondName());
			transaction.put("receiver", receiver);
			transaction.put("reference", t[i].getDescription());
			
			JSONObject sender = new JSONObject();
			sender.put("id", t[i].getOutgoingAccount().getBank().getId());
			sender.put("number", t[i].getOutgoingAccount().getId());
			sender.put("owner", t[i].getOutgoingAccount().getCustomer().getFirstName() + " " 
			                    + t[i].getOutgoingAccount().getCustomer().getSecondName());
			transaction.put("sender", sender);
			transaction.put("transactionDate", t[i].getDate());
			
			ja.put(transaction);
		}
		jo.put("transactions", ja);
		}
		catch (SQLException e) {
			return Response.status(500).build();
		}
		
		//return Response.status(400).build();
		//return Response.serverError().build(); //Code 500
		return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/s/getAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getAccount(@QueryParam("number")int number,
			                   @QueryParam("kundenId")int customer,
			                   @QueryParam("passwortHash")String password,
			                   @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getAccount");
		logger.info(new java.util.Date() + ": Account: " + number);
		
		// Declarations
		Customer c;
		JSONObject jo;
		Account a;
		JSONArray ja;
		
		try {
		    c = new Customer(customer);                     
		    c.login(password);								
		    if (!c.isLoggedIn())
		    	return Response.status(403).build();		// Wrong Customer Number or Password
		    a = new Account(number);
			if (a.getId() == 0)
				return Response.status(404).build();		// Account does not exist
		    if (a.getCustomer().getId() != c.getId())
		    	return Response.status(400).build();		// Customer Number and Account Number do not match
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}
		
		jo = new JSONObject();
		try {
		
		// Build up header data
		jo.put("id", a.getBank().getId());
		jo.put("number", a.getId());
		jo.put("owner", a.getCustomer().getFirstName() + " " + a.getCustomer().getSecondName());
        
		ja = new JSONArray();
		Transaction[] t = new Transaction[a.getTransactions().size()];
		a.getTransactions().toArray(t);
		
		// Build up transaction data
		for (int i=0; i<t.length; i++) {
			JSONObject transaction = new JSONObject();
			transaction.put("amount", Convert.toEuro(t[i].getAmount()));
			transaction.put("id", t[i].getId());
			
			// Receiver Data
			JSONObject receiver = new JSONObject();
			receiver.put("id", t[i].getIncomingAccount().getBank().getId());
			receiver.put("number", t[i].getIncomingAccount().getId());
			receiver.put("owner", t[i].getIncomingAccount().getCustomer().getFirstName() + " " 
			                    + t[i].getIncomingAccount().getCustomer().getSecondName());
			transaction.put("receiver", receiver);
			transaction.put("reference", t[i].getDescription());
			
			// Sender Data
			JSONObject sender = new JSONObject();
			sender.put("id", t[i].getOutgoingAccount().getBank().getId());
			sender.put("number", t[i].getOutgoingAccount().getId());
			sender.put("owner", t[i].getOutgoingAccount().getCustomer().getFirstName() + " " 
			                    + t[i].getOutgoingAccount().getCustomer().getSecondName());
			transaction.put("sender", sender);
			transaction.put("transactionDate", t[i].getDate());
			
			ja.put(transaction);
		}
		jo.put("transactions", ja);
		}
		catch (SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}
		// No Errors, JSONObject is returned
		return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/transferMoney")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response transferMoney(@FormParam("senderNumber")int sender, 
			                      @FormParam("receiverNumber")int receiver, 
			                      @FormParam("amount")String amountEuro, 
			                      @FormParam("reference")String reference,
			                      @Context HttpServletRequest req) {
    		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: transferMoney");
		logger.info(new java.util.Date() + ": Sender: " + sender + "/ Receiver: " + receiver
				                         + "/ Amount: " + amountEuro + "/ Reference: " + reference);

		int amount = Convert.toCent(amountEuro);
		if (sender == 0 || receiver == 0 || amount == 0)
			return Response.status(400).build();
		try {
		Account outgoingAccount = new Account(sender);
		if (outgoingAccount.getId() == 0)
			return Response.status(404).build();
		Account incomingAccount = new Account(receiver);
		if (incomingAccount.getId() == 0)
			return Response.status(404).build();
		Transaction t;
		int balance = outgoingAccount.getBalance();
		
		if (balance >= amount)
		    t = new Transaction(amount, reference, Convert.currentDate(), incomingAccount, outgoingAccount);
		else
			return Response.status(412).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();
		}
		return Response.ok().build();
	}
	
	@POST
	@Path("/s/transferMoney")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response transferMoney(@FormParam("senderNumber")int sender, 
			                      @FormParam("receiverNumber")int receiver, 
			                      @FormParam("amount")String amountEuro, 
			                      @FormParam("reference")String reference,
			                      @FormParam("kundenId")int customer,
				                  @FormParam("passwortHash")String password,
				                  @Context HttpServletRequest req) {
    		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: transferMoney");
		logger.info(new java.util.Date() + ": Sender: " + sender + "/ Receiver: " + receiver
				                         + "/ Amount: " + amountEuro + "/ Reference: " + reference);
		
		int amount = Convert.toCent(amountEuro);
		Account outgoingAccount;
		Account incomingAccount;
		Transaction t;
		Customer c;
		
		try {
		    c = new Customer(customer);                     
		    c.login(password);								
		    if (!c.isLoggedIn())
		    	return Response.status(403).build();		// Wrong Customer Number or Password
		    outgoingAccount = new Account(sender);
			if (outgoingAccount.getId() == 0)
				return Response.status(404).build();		// Account does not exist
		    if (outgoingAccount.getCustomer().getId() != c.getId())
		    	return Response.status(400).build();		// Customer Number and Account Number do not match
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}
		if (sender == 0 || receiver == 0 || amount == 0)
			return Response.status(400).build();			// Client Error 
		try {
		
		incomingAccount = new Account(receiver);
		if (incomingAccount.getId() == 0)
			return Response.status(404).build();			// Account does not exist
		
		int balance = outgoingAccount.getBalance();
		
		if (balance >= amount)
		    t = new Transaction(amount, reference, Convert.currentDate(), incomingAccount, outgoingAccount);
		else
			return Response.status(412).build();			// Insufficient Money
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}
		return Response.ok().build();						// Transaction was created successfully
	}
}
