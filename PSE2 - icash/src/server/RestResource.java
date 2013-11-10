package server;

import java.sql.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

import org.json.*;

import classes.*;

@Path("/")
@Singleton
public class RestResource {
	@GET
	@Path("/getAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getAccount(@QueryParam("number")int number) {
		
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
			transaction.put("amount", t[i].getAmount());
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
		return Response.ok(jo.toString(), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/transferMoneyapplication")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response transferMoneyapplication(@FormParam("senderNumber")int sender, 
			                                 @FormParam("receiverNumber")int receiver, 
			                                 @FormParam("amount")int amount, 
			                                 @FormParam("reference")String reference) {
		
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
}
