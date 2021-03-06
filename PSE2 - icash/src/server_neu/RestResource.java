package server_neu;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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

/**
 * this is the RestResource
 * interface between clients and server
 * 
 */
@Path("/")
@Singleton
public class RestResource {
	
	private static Lock lock = new ReentrantLock();
	
	/**
	 * gets Account
	 * @param numberString String account ID
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Account Data
	 */
	@GET
	@Path("/getAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getAccount(@QueryParam("number")String numberString,
			                   @Context HttpServletRequest req) {
		
		if (JettyServer.securityMode)
			return Response.status(403).build();			// Not Allowed in Security Mode
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getAccount");
		logger.info(new java.util.Date() + ": Account: " + numberString);
		
		int number = 0;
		if (numberString != null)
			number = Convert.toInt(numberString);
		
		if (number == 0) 
			return Response.status(400).build();
		try {
		Account a = new Account(number);
		if (a.getId() == 0)
			return Response.status(404).build();
		return getAccount(number, a);
		}
		catch (SQLException e) {
			return Response.status(500).build();
		}		
	}
	
	/**
	 * gets Account
	 * @param numberString String account ID
	 * @param customerString String customer ID
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Account Data
	 */
	@GET
	@Path("/s/getAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getAccount(@QueryParam("number")String numberString,
			                   @QueryParam("kundenID")String customerString,
			                   @QueryParam("passwortHash")String password,
			                   @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getAccount");
		logger.info(new java.util.Date() + ": Account: " + numberString);
		
		int number = Convert.toInt(numberString);
		int customer = Convert.toInt(customerString);
		
		if (number == 0 || customer == 0 || password == null) 
			return Response.status(400).build();
		try {
			Account a = new Account(number);
			if (a.getId() == 0)
				return Response.status(404).build();		// Account does not exist
		    Customer c = new Customer(customer);                     
		    c.login(password, a);								
		    if (!c.isLoggedIn())
		    	return Response.status(403).build();		// Wrong Customer Number or Password
		    if (a.getCustomer().getId() != c.getId())
		    	return Response.status(400).build();		// Customer Number and Account Number do not match
		    return getAccount(number, a);
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}
		
	}
	
	/**
	 * gets Account
	 * @param number int account ID
	 * @param a Account
	 * @return Response HTTP-Response-Code / Account Data
	 */
	public Response getAccount(int number, Account a) {
		
		JSONObject jo = new JSONObject();
		try {
		
		// Build up header data
		jo.put("number", "" + a.getId());
		jo.put("owner", a.getCustomer().getFirstName() + " " + a.getCustomer().getLastName());
        
		JSONArray ja = new JSONArray();
		Transaction[] t = new Transaction[a.getTransactions().size()];
		a.getTransactions().toArray(t);
		
		// Build up transaction data
		for (int i=0; i<t.length; i++) {
			JSONObject transaction = new JSONObject();
			transaction.put("amount", Convert.toEuro(t[i].getAmount()));
			//transaction.put("id", t[i].getId());
			
			// Receiver Data
			JSONObject receiver = new JSONObject();
			receiver.put("number", "" + t[i].getIncomingAccount().getId());
			receiver.put("owner", t[i].getIncomingAccount().getCustomer().getFirstName() + " " 
			                    + t[i].getIncomingAccount().getCustomer().getLastName());
			transaction.put("receiver", receiver);
			transaction.put("reference", t[i].getDescription());
			
			// Sender Data
			JSONObject sender = new JSONObject();
			sender.put("number", "" + t[i].getOutgoingAccount().getId());
			sender.put("owner", t[i].getOutgoingAccount().getCustomer().getFirstName() + " " 
			                    + t[i].getOutgoingAccount().getCustomer().getLastName());
			transaction.put("sender", sender);
			transaction.put("transactionDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+01:00'").format(t[i].getDate()));
			
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
	
	/**
	 * transfer money from sender to receiver
	 * @param senderString String sender
	 * @param receiverString String receiver
	 * @param amountString String amount
	 * @param reference String optional reason
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/transferMoney")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response transferMoney(@FormParam("senderNumber")String senderString, 
			                      @FormParam("receiverNumber")String receiverString, 
			                      @FormParam("amount")String amountString, 
			                      @FormParam("reference")String reference,
			                      @Context HttpServletRequest req) {
    	
		if (JettyServer.securityMode)
			return Response.status(403).build();			// Not Allowed in Security Mode
		int sender = Convert.toInt(senderString);
		int receiver = Convert.toInt(receiverString);
		int amount = Convert.toCent(amountString);
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: transferMoney");
		logger.info(new java.util.Date() + ": Sender: " + sender + "/ Receiver: " + receiver
				                         + "/ Amount: " + Convert.toEuro(amount) + "/ Reference: " + reference);

		if (sender == 0 || receiver == 0 || amount == 0)
			return Response.status(400).build();
		try {
			Account outgoingAccount = new Account(sender);
			if (outgoingAccount.getId() == 0)
				return Response.status(404).build();		// Account does not exist
			return transferMoney(sender, receiver, amount, reference, outgoingAccount);
		}
		catch(SQLException e) {
			return Response.status(500).build();
		}
	}
	
	/**
	 * transfer money in security mode
	 * @param senderString String
	 * @param receiverString String
	 * @param amountString String
	 * @param reference String optional reason
	 * @param customerString String
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/transferMoney")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response transferMoney(@FormParam("senderNumber")String senderString, 
			                      @FormParam("receiverNumber")String receiverString, 
			                      @FormParam("amount")String amountString, 
			                      @FormParam("reference")String reference,
			                      @FormParam("kundenID")String customerString,
				                  @FormParam("passwortHash")String password,
				                  @Context HttpServletRequest req) {
    	
		int sender = Convert.toInt(senderString);
		int receiver = Convert.toInt(receiverString);
		int amount = Convert.toCent(amountString);
		int customer = Convert.toInt(customerString);
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: transferMoney");
		logger.info(new java.util.Date() + ": Sender: " + sender + "/ Receiver: " + receiver
				                         + "/ Amount: " + Convert.toEuro(amount) + "/ Reference: " + reference);
		
		try {
		    Account outgoingAccount = new Account(sender);
			if (outgoingAccount.getId() == 0)
				return Response.status(404).build();		// Account does not exist
			Customer c = new Customer(customer);                     
		    c.login(password, outgoingAccount);								
		    if (!c.isLoggedIn())
		    	return Response.status(403).build();		// Wrong Customer Number or Password
		    if (outgoingAccount.getCustomer().getId() != c.getId()
		    &&  outgoingAccount.getAccountType().getId() != 1)
		    	return Response.status(400).build();		// Customer Number and Account Number do not match
			if (sender == 0 || receiver == 0 || amount == 0)
				return Response.status(400).build();			// Client Error 
			return transferMoney(sender, receiver, amount, reference, outgoingAccount);
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}

	}
	
	/**
	 * transfer money
	 * @param sender int
	 * @param receiver int
	 * @param amount int
	 * @param reference String optional reason
	 * @param outgoingAccount Account
	 * @return Response HTTP-Response-Code
	 */
	public Response transferMoney(int sender, int receiver, int amount, String reference, Account outgoingAccount){
		Transaction t;
		Response r;
		
		try {
			if (amount < 0)
				return Response.status(400).build();         	// Negative Amounts not allowed
			Account incomingAccount = new Account(receiver);
			if (incomingAccount.getId() == 0)
				return Response.status(404).build();			// Account does not exist
				
				synchronized (lock) {
					int balance = outgoingAccount.getBalance();
			
					if (balance >= amount || outgoingAccount.getId() 
					    == incomingAccount.getBank().getBank_account().getId()) {
						t = new Transaction(amount, reference, Convert.currentDate(), incomingAccount, outgoingAccount);
						r = Response.ok().build();						// Transaction was created successfully
					}
					else
						r = Response.status(412).build();			// Insufficient Money
				}
		}
		catch(SQLException e) {
			r = Response.status(500).build();			// Internal Server Error
		}
	return r;
	}	
	
	/**
	 * gets bank account
	 * @param account int account ID
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Bank Account (for deposites and withdraws)
	 */
	@GET
	@Path("/getBankAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getBankAccount(@QueryParam("account")int account,
			                       @Context HttpServletRequest req) {
		
		if (JettyServer.securityMode)
			return Response.status(403).build();			// Not Allowed in Security Mode
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getBankAccount");
		logger.info(new java.util.Date() + ": Account: " + account);
		
		JSONObject jo = new JSONObject();
		
		try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();			// Account does not exist
		
			jo.put("bankAccount", a.getBank().getBank_account().getId());
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
	}
	
	/**
	 * gets current balance
	 * @param account int account ID
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Balance
	 */
	@GET
	@Path("/getBalance")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getBalance(@QueryParam("account")int account,
			                   @Context HttpServletRequest req) {
		
		if (JettyServer.securityMode)
			return Response.status(403).build();			// Not Allowed in Security Mode
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getBalance");
		logger.info(new java.util.Date() + ": Account: " + account);
		
		JSONObject jo = new JSONObject();
		
		try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();			// Account does not exist
		    
			String balance = Convert.toEuro(a.getBalance());
			jo.put("balance", balance);
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
	}	
	
	/**
	 * gets bank account in security mode
	 * @param account int 
	 * @param customer int 
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Bank Account (for deposits and withdraws)
	 */
	@GET
	@Path("/s/getBankAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getBankAccount(@QueryParam("account")int account,
			   					   @QueryParam("kundenID")int customer,
			   					   @QueryParam("passwortHash")String password,
			                       @Context HttpServletRequest req) {
		
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getBankAccount");
		logger.info(new java.util.Date() + ": Account: " + account);
		
		JSONObject jo = new JSONObject();
		
		try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();		// Account does not exist
			Customer c = new Customer(customer);                     
		    c.login(password, a);								
		    if (!c.isLoggedIn())
		    	return Response.status(403).build();		// Wrong Customer Number or Password
		    if (a.getCustomer().getId() != c.getId())
		    	return Response.status(400).build();		// Customer Number and Account Number do not match
		
			jo.put("bankAccount", a.getBank().getBank_account().getId());
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
	}
	
	/**
	 * gets current balance in security mode
	 * @param account int
	 * @param customer int 
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Balance
	 */
	@GET
	@Path("/s/getBalance")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getBalance(@QueryParam("account")int account,
							   @QueryParam("kundenID")int customer,
							   @QueryParam("passwortHash")String password,
			                   @Context HttpServletRequest req) {
		
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: getBalance");
		logger.info(new java.util.Date() + ": Account: " + account);
		
		JSONObject jo = new JSONObject();
		
		try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();		// Account does not exist
			Customer c = new Customer(customer);                     
		    c.login(password, a);								
		    if (!c.isLoggedIn())
		    	return Response.status(403).build();		// Wrong Customer Number or Password
		    if (a.getCustomer().getId() != c.getId())
		    	return Response.status(400).build();		// Customer Number and Account Number do not match
			String balance = Convert.toEuro(a.getBalance());
			jo.put("balance", balance);
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
	}
	
	/**
	 * get customer of given account
	 * @param account int account ID
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Customer Data
	 */
	@GET
	@Path("/getCustomer")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getCustomer(@QueryParam("account")int account,
                                @Context HttpServletRequest req) {
		
		if (JettyServer.securityMode)
			return Response.status(403).build();			// Not Allowed in Security Mode
		
        Logger logger = Logger.getRootLogger();
        logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
        logger.info(new java.util.Date() + ": Method: getCustomer");
        logger.info(new java.util.Date() + ": Account: " + account);
                        		
        JSONObject jo = new JSONObject();
        
        try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();			// Account does not exist
		    
			jo.put("id", a.getCustomer().getId());
			jo.put("firstName", a.getCustomer().getFirstName());
			jo.put("lastName", a.getCustomer().getLastName());
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
    }
	
	/**
	 * get customer of given account in security mode
	 * @param account int account ID
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Customer Data
	 */
	@GET
	@Path("/s/getCustomer")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getCustomer(@QueryParam("account")int account,
								@QueryParam("passwortHash")String password,
                                @Context HttpServletRequest req) {
        Logger logger = Logger.getRootLogger();
        logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
        logger.info(new java.util.Date() + ": Method: getCustomer");
        logger.info(new java.util.Date() + ": Account: " + account);
                        		
        JSONObject jo = new JSONObject();
        
        try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();			// Account does not exist
		    Customer c = a.getCustomer();
		    c.login(password, a);								
		    if (!c.isLoggedIn())
				return Response.status(403).build();
			jo.put("id", a.getCustomer().getId());
			jo.put("firstName", a.getCustomer().getFirstName());
			jo.put("lastName", a.getCustomer().getLastName());
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
    }
	
	/**
	 * get Bank of given account in security mode
	 * @param account int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Bank Data
	 */
	@GET
	@Path("/s/getBank")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getBank(@QueryParam("account")int account,
							@QueryParam("passwortHash")String password,
                            @Context HttpServletRequest req) {
        Logger logger = Logger.getRootLogger();
        logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
        logger.info(new java.util.Date() + ": Method: /s/getBank");
        logger.info(new java.util.Date() + ": Account: " + account);
                        		
        JSONObject jo = new JSONObject();
        
        try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();			// Account does not exist
		    Customer c = a.getCustomer();
		    c.login(password, a);								
		    if (!c.isLoggedIn())
				return Response.status(403).build();
			
			jo.put("id", a.getBank().getId());
			jo.put("blz", a.getBank().getBlz());
			jo.put("description", a.getBank().getDescription());
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
    }
	
	/**
	 * get account type of given account
	 * @param account int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Account Type Data
	 */
	@GET
	@Path("/s/getAccountType")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getAccountType(@QueryParam("account")int account,
								   @QueryParam("passwortHash")String password,
								   @Context HttpServletRequest req) {
        Logger logger = Logger.getRootLogger();
        logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
        logger.info(new java.util.Date() + ": Method: /s/getAccountType");
        logger.info(new java.util.Date() + ": Account: " + account);
                        		
        JSONObject jo = new JSONObject();
        
        try {
			Account a = new Account(account);
			if (a.getId() == 0)
				return Response.status(404).build();			// Account does not exist
		    
			Customer c = a.getCustomer();
		    c.login(password, a);								
		    if (!c.isLoggedIn())
				return Response.status(403).build();
			
			jo.put("id", a.getAccountType().getId());
			jo.put("interestRate", a.getAccountType().getInterestRate());
			jo.put("description", a.getAccountType().getDescription());
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
    }
	
	/**
	 * change customer
	 * @param firstName String
	 * @param lastName String
	 * @param passwordNew String
	 * @param customerId int
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/changeAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response changeAccount(@FormParam("firstName")String firstName, 
			                      @FormParam("lastName")String lastName, 
			                      @FormParam("passwordNew")String passwordNew,
			                      @FormParam("customerId")int customerId,
			                      @Context HttpServletRequest req) {
		
		if (JettyServer.securityMode)
			return Response.status(403).build();			// Not Allowed in Security Mode
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/changeAccount");
		logger.info(new java.util.Date() + ": Admin: " + customerId);
		
		try {
			Customer c = new Customer(customerId);
			if (c.getId() == 0)
				return Response.status(404).build();			// Customer does not exist
			
			c.setFirstName(firstName);
			c.setLastName(lastName);
			c.setPassword(passwordNew);
			c.updateDB();
			// Customer Data was changed successfully
			return Response.ok().build();
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}							
	}
	
	/**
	 * change customer in security mode
	 * @param firstName String 
	 * @param lastName String
	 * @param passwordNew String
	 * @param customerId int
	 * @param password String 
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/changeAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response changeAccount(@FormParam("firstName")String firstName, 
			                      @FormParam("lastName")String lastName, 
			                      @FormParam("passwordNew")String passwordNew,
			                      @FormParam("customerId")int customerId,
						          @FormParam("passwortHash")String password,
			                      @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/changeAccount");
		logger.info(new java.util.Date() + ": Admin: " + customerId);
		
		try {
			Customer c = new Customer(customerId);
			if (c.getId() == 0)
				return Response.status(404).build();			// Customer does not exist
			if (c.getPassword().equals(password)) {
				c.setFirstName(firstName);
				c.setLastName(lastName);
				c.setPassword(passwordNew);
				c.updateDB();
				// Customer Data was changed successfully
				return Response.ok().build();
			}
			else
				return Response.status(403).build();			// Wrong Password
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}							
	}
	
// Administrator Methods
	
	/**
	 * get admin in security mode
	 * @param adminID int 
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Admin Data
	 */
	@GET
	@Path("/s/getAdmin")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getAdmin(@QueryParam("adminID")int adminID,
							 @QueryParam("passwortHash")String password,
                             @Context HttpServletRequest req) {
        Logger logger = Logger.getRootLogger();
        logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
        logger.info(new java.util.Date() + ": Method: /s/getAdmin");
        logger.info(new java.util.Date() + ": Admin: " + adminID);
                        		
        JSONObject jo = new JSONObject();
        
        try {
			Administrator a = new Administrator(adminID);
			if (a.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
		    a.login(password);								
		    if (!a.isLoggedIn())
		    	return Response.status(403).build();			// Wrong password
		    else {
				jo.put("id", a.getId());
				jo.put("firstName", a.getFirstName());
				jo.put("lastName", a.getLastName());
				
				JSONArray ja = new JSONArray();
				Account[] acc = new Account[a.getAccounts().size()];
				a.getAccounts().toArray(acc);
				
				for (int i=0; i<acc.length; i++) {
					JSONObject currentAccount = new JSONObject();
					currentAccount.put("id", acc[i].getId());
					
					JSONObject bank = new JSONObject();
					bank.put("id", acc[i].getBank().getId());
					bank.put("description", acc[i].getBank().getDescription());
					currentAccount.put("bank", bank);
					
					JSONObject customer = new JSONObject();
					customer.put("id", acc[i].getCustomer().getId());
					customer.put("firstName", acc[i].getCustomer().getFirstName());
					customer.put("lastName", acc[i].getCustomer().getLastName());
					currentAccount.put("customer", customer);
					
					JSONObject accountType = new JSONObject();
					accountType.put("id", acc[i].getAccountType().getId());
					accountType.put("description", acc[i].getAccountType().getDescription());
					currentAccount.put("accountType", accountType);
					currentAccount.put("active", acc[i].isFlagActive());
					
					ja.put(currentAccount);
				}
				
				jo.put("accounts", ja);
				return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
			} 
        
			
		}
		catch(SQLException e) {
			logger.info(new java.util.Date() + ": SQL-Exception during select for adminID: " + adminID);
			return Response.status(500).build();            // Internal Server Error
		}
    }
	
	/**
	 * gets data for given admin ID in security mode
	 * @param adminID int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / Data for administrators, banks, customers, account types
	 */
	@GET
	@Path("/s/getData")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getData(@QueryParam("adminID")int adminID,
							@QueryParam("passwortHash")String password,
							@Context HttpServletRequest req) {

		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/getData");
		logger.info(new java.util.Date() + ": Admin: " + adminID);
		
		JSONObject jo = new JSONObject();
        
        try {
			Administrator a = new Administrator(adminID);
			if (a.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (a.getPassword().equals(password)) {
				
				JSONArray banks = new JSONArray();
				JSONArray customers = new JSONArray();
				JSONArray admins = new JSONArray();
				JSONArray accountTypes = new JSONArray();
				
				// Banks
				String[] columnBank = {"idBank", "descriptionBank"};
				String table = "Bank";
				String[] condition = {};
				String connector = "and";
				
				String[][] value = SQL.select(columnBank, table, condition, connector);
				
				for (int i=0; i<value.length; i++) {
					JSONObject bank = new JSONObject();
					bank.put("id", Convert.toInt(value[i][0]));
					bank.put("description", value[i][1]);
					banks.put(bank);
				}
				
				jo.put("banks", banks);
				
				// Customers
				String[] columnCustomer = {"idCustomer", "firstNameCustomer", "lastNameCustomer"};
				table = "Customer";
				
				value = SQL.select(columnCustomer, table, condition, connector);
				
				for (int i=0; i<value.length; i++) {
					JSONObject customer = new JSONObject();
					customer.put("id", Convert.toInt(value[i][0]));
					customer.put("firstName", value[i][1]);
					customer.put("lastName", value[i][2]);
					customers.put(customer);
				}
				
				jo.put("customers", customers);
				
				// Admins
				String[] columnAdmin = {"idAdministrator", "firstNameAdministrator", "lastNameAdministrator"};
				table = "Administrator";
				
				value = SQL.select(columnAdmin, table, condition, connector);
				
				for (int i=0; i<value.length; i++) {
					JSONObject admin = new JSONObject();
					admin.put("id", Convert.toInt(value[i][0]));
					admin.put("firstName", value[i][1]);
					admin.put("lastName", value[i][2]);
					admins.put(admin);
				}
				
				jo.put("admins", admins);
				
				// AccountTypes
				String[] columnAccountType = {"idAccountTyp", "descriptionAccountTyp", "interestRateAccountTyp"};
				table = "AccountTyp";
				
				value = SQL.select(columnAccountType, table, condition, connector);
				
				for (int i=0; i<value.length; i++) {
					JSONObject accountType = new JSONObject();
					accountType.put("id", Convert.toInt(value[i][0]));
					accountType.put("description", value[i][1]);
					accountType.put("interestRate", value[i][2]);
					accountTypes.put(accountType);
				}
				
				jo.put("accountTypes", accountTypes);
				
				return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
			}
			else 
			{
				logger.info(new java.util.Date() + ": wrong password!");
				return Response.status(404).build();
			}
        }
        catch(SQLException e) {
        	return Response.status(500).build();
        }
	}
	
	/**
	 * set active flag of given account to given boolean
	 * @param active boolean 
	 * @param idAccount int
	 * @param idLogin int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/setActive")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response setActive(@FormParam("active")boolean active,
			                  @FormParam("idAccount")int idAccount,
			                  @FormParam("idLogin")int idLogin,
			                  @FormParam("passwortHash")String password,
			                  @Context HttpServletRequest req) {

		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/setActive");
		logger.info(new java.util.Date() + ": Admin: " + idLogin);
        
        try {
			Administrator a = new Administrator(idLogin);
			if (a.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (a.getPassword().equals(password)) {				
				Account acc = new Account(idAccount);
				if (acc != null) {
					acc.setFlagActive(active);
					acc.updateDB();
					return Response.ok().build();
				}
				else
					return Response.status(404).build();		// Account does not exist
			}
			else
				return Response.status(403).build();			// Wrong password
        }
        catch(SQLException e) {
        	return Response.status(500).build();				// Internal Server Error
        }
	}
	
	/**
	 * pay interst for accounts of given bank
	 * @param bankId int
	 * @param idLogin int admin ID
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/payInterests")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response payInterests(@FormParam("idBank")int bankId,
			                     @FormParam("idLogin")int idLogin,
			                     @FormParam("passwortHash")String password,
			                     @Context HttpServletRequest req) {

		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/payInterests");
		logger.info(new java.util.Date() + ": Admin: " + idLogin);
        
        try {
			Administrator a = new Administrator(idLogin);
			if (a.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (a.getPassword().equals(password)) {		
				Bank b = new Bank(bankId);
				if (b != null) 
					b.payInterests();
				return Response.ok().build();					// Interests paid successfully
			}
			else
				return Response.status(403).build();			// Wrong password
        }
		catch (SQLException e) {
			return Response.status(500).build();				// Internal Server Error
		}
	}
	
	/**
	 * create new Customer
	 * @param firstName String
	 * @param lastName String
	 * @param password String
	 * @param adminIdLogin int 
	 * @param passwordLogin String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/createCustomer")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response createCustomer(@FormParam("firstName")String firstName, 
			                       @FormParam("lastName") String lastName, 
			                       @FormParam("password") String password,
			                       @FormParam("adminIdLogin")int adminIdLogin,
						           @FormParam("passwortHash")String passwordLogin,
			                       @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/createCustomer");
		logger.info(new java.util.Date() + ": first name: " + firstName + "/ last name: " + lastName);
		
		try {
			Administrator adminLogin = new Administrator(adminIdLogin);
			if (adminLogin.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (adminLogin.getPassword().equals(passwordLogin)) {
				Customer c = new Customer(firstName, lastName, password);
				JSONObject jo = new JSONObject();
				jo.put("id", c.getId());
				// Customer was created successfully
				return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
			}
			else {
				return Response.status(403).build();			// Wrong Password
			}
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}						
	}
	
	/**
	 * create new Administrator
	 * @param firstName String
	 * @param lastName String
	 * @param password String
	 * @param adminIdLogin int
	 * @param passwordLogin String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/createAdministrator")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response createAdministrator(@FormParam("firstName")String firstName, 
			                       		@FormParam("lastName") String lastName, 
			                       		@FormParam("password") String password,
			                       		@FormParam("adminIdLogin")int adminIdLogin,
			                       		@FormParam("passwortHash")String passwordLogin,
			                       		@Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/createAdministrator");
		logger.info(new java.util.Date() + ": first name: " + firstName + "/ last name: " + lastName);
		
		try {
			Administrator adminLogin = new Administrator(adminIdLogin);
			if (adminLogin.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (adminLogin.getPassword().equals(passwordLogin)) {
				Administrator a = new Administrator(firstName, lastName, password);
				JSONObject jo = new JSONObject();
				jo.put("id", a.getId());
				// Administrator was created successfully
				return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
			}
			else {
				return Response.status(403).build();			// Wrong Password
			}
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}						
	}
		
	/**
	 * create new Account
	 * @param bankId int
	 * @param customerId int
	 * @param adminId int
	 * @param accountTypeId int
	 * @param adminIdLogin int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/createAccount")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response createAccount(@FormParam("bankId")int bankId, 
			                      @FormParam("customerId") int customerId, 
			                      @FormParam("adminId") int adminId,
			                      @FormParam("accountTypeId") int accountTypeId,
			                      @FormParam("adminIdLogin")int adminIdLogin,
					              @FormParam("passwortHash")String password,
			                      @Context HttpServletRequest req) {
		
		// Declarations
		Customer customer = new Customer();
		Administrator admin = new Administrator();
		Bank bank = new Bank();
		AccountType accountType = new AccountType();
		Account account = new Account();
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/createAccount");
		logger.info(new java.util.Date() + ": Account type: " + accountTypeId); 
		logger.info(new java.util.Date() + ": Customer: " + customerId);
		logger.info(new java.util.Date() + ": Admin: " + adminId);
		
		try {
			Administrator adminLogin = new Administrator(adminIdLogin);
			if (adminLogin.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (adminLogin.getPassword().equals(password)) {
				customer = new Customer(customerId);
				admin = new Administrator(adminId);
				bank = new Bank(bankId);
				accountType = new AccountType(accountTypeId);
				account = new Account(true, customer, admin, bank, accountType);
				JSONObject jo = new JSONObject();
				jo.put("id", account.getId());
				return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
			}
			else {
				return Response.status(403).build();			// Wrong Password
			}
		} 
		catch(SQLException e) 
		{
			if (customer.getId() <= 0) {
				logger.info(new java.util.Date() + ": the customerId " + customerId + " does not exist.");
				return Response.status(404).build();
			}
			
			if (admin.getId() <= 0) {
				logger.info(new java.util.Date() + ": the adminId " + adminId + " does not exist.");
				return Response.status(404).build();
			}
			
			if (bank.getId() <= 0) {
				logger.info(new java.util.Date() + ": the bankId " + bankId + " does not exist.");
				return Response.status(404).build();
			}
			
			if (accountType.getId() <= 0) {
				logger.info(new java.util.Date() + ": the accountType " + accountTypeId + " does not exist.");
				return Response.status(404).build();
			}
			
			if (account.getId() <= 0) {
				logger.info(new java.util.Date() + ": the account creation failed.");
			}
			
			return Response.status(500).build();			
		}				
	}
	
	/**
	 * create new AccountType
	 * @param interestRate double 
	 * @param description String name of type
	 * @param adminIdLogin int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/createAccountType")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response createAccountType(@FormParam("interestRate")double interestRate, 
			                       	  @FormParam("description")String description, 
			                       	  @FormParam("adminIdLogin")int adminIdLogin,
						              @FormParam("passwortHash")String password,
			                       	  @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/createAccountType");
		logger.info(new java.util.Date() + ": Admin: " + adminIdLogin);
		
		try {
			Administrator adminLogin = new Administrator(adminIdLogin);
			if (adminLogin.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (adminLogin.getPassword().equals(password)) {
				AccountType at = new AccountType(description, interestRate);
				JSONObject jo = new JSONObject();
				jo.put("id", at.getId());
				// Account Type was created successfully
				return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
			}
			else
				return Response.status(403).build();			// Wrong Password
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}							
	}
	
	/**
	 * create new Bank
	 * @param blz int
	 * @param description String name of bank
	 * @param adminIdLogin int 
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/createBank")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response createBank(@FormParam("blz")int blz, 
			                   @FormParam("description")String description, 
			                   @FormParam("adminIdLogin")int adminIdLogin,
						       @FormParam("passwortHash")String password,
			                   @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/createBank");
		logger.info(new java.util.Date() + ": Admin: " + adminIdLogin);
		
		try {
			Administrator adminLogin = new Administrator(adminIdLogin);
			if (adminLogin.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (adminLogin.getPassword().equals(password)) {
				Bank b = new Bank(blz, description);
				Customer c = new Customer("Customer", description, description);
				Administrator a = new Administrator(adminIdLogin);
				Account acc = new Account(true, c, a, b, new AccountType(1));
				b.setBank_account(acc);
				b.updateDB();
				JSONObject jo = new JSONObject();
				jo.put("id", b.getId());
				// Bank was created successfully
				return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
			}
			else
				return Response.status(403).build();			// Wrong Password
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}							
	}
	
	/**
	 * 
	 * @param idLogin int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code / whether security mode is active or not
	 */
	@GET
	@Path("/s/getSecurityMode")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response getSecurityMode(@QueryParam("idLogin")int idLogin,
									@QueryParam("passwortHash")String password,
									@Context HttpServletRequest req) {
		
        Logger logger = Logger.getRootLogger();
        logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
        logger.info(new java.util.Date() + ": Method: /s/getSecurityMode");
        logger.info(new java.util.Date() + ": Admin: " + idLogin);
                        		
        JSONObject jo = new JSONObject();
        
        try {
			Administrator a = new Administrator(idLogin);
			if (a.getId() == 0)
				return Response.status(404).build();			// Account does not exist
		    if (!a.getPassword().equals(password))
				return Response.status(403).build();
			
			jo.put("securityMode", JettyServer.securityMode);
			return Response.ok(jo.toString(4), MediaType.APPLICATION_JSON).build();
		}
		catch(SQLException e) {
			return Response.status(500).build();            // Internal Server Error
		}
    }
	
	/**
	 * sets securty mode
	 * @param securityMode boolean new mode
	 * @param idLogin int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/setSecurityMode")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response setSecurityMode(@FormParam("securityMode")boolean securityMode,
			                  		@FormParam("idLogin")int idLogin,
			                  		@FormParam("passwortHash")String password,
			                  		@Context HttpServletRequest req) {

		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/setSecurityMode");
		logger.info(new java.util.Date() + ": Admin: " + idLogin);
        
        try {
			Administrator a = new Administrator(idLogin);
			if (a.getId() == 0)
				return Response.status(404).build();			// Admin does not exist
			if (a.getPassword().equals(password)) {				
				JettyServer.securityMode = securityMode;
				return Response.ok().build();
			}
			else
				return Response.status(403).build();			// Wrong password
        }
        catch(SQLException e) {
        	return Response.status(500).build();				// Internal Server Error
        }
	}
	
	/**
	 * change administrator
	 * @param firstName String new first name
	 * @param lastName String new last name
	 * @param passwordNew String new password
	 * @param administratorId int 
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/changeAdmin")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response changeAdmin(@FormParam("firstName")String firstName, 
			                    @FormParam("lastName")String lastName, 
			                    @FormParam("passwordNew")String passwordNew,
			                    @FormParam("administratorId")int administratorId,
						        @FormParam("passwortHash")String password,
			                    @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/changeAdmin");
		logger.info(new java.util.Date() + ": Admin: " + administratorId);
		
		try {
			Administrator a = new Administrator(administratorId);
			if (a.getId() == 0)
				return Response.status(404).build();			// Administrator does not exist
			if (a.getPassword().equals(password)) {
				a.setFirstName(firstName);
				a.setLastName(lastName);
				a.setPassword(passwordNew);
				a.updateDB();
				// Administrator Data was changed successfully
				return Response.ok().build();
			}
			else
				return Response.status(403).build();			// Wrong Password
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}							
	}
	
	/**
	 * change account tpye
	 * @param idAccountType int
	 * @param description String name
	 * @param interestRate double 
	 * @param idLogin int
	 * @param password String
	 * @param req HttpServletRequest
	 * @return Response HTTP-Response-Code
	 */
	@POST
	@Path("/s/changeAccountType")
	@Produces({ MediaType.TEXT_PLAIN + "; charset=utf-8" })
	public Response changeAccountType(@FormParam("idAccountType")int idAccountType, 
			                    	  @FormParam("description")String description, 
			                    	  @FormParam("interestRate")double interestRate,
			                    	  @FormParam("idLogin")int idLogin,
			                    	  @FormParam("passwortHash")String password,
			                    	  @Context HttpServletRequest req) {
		
		// Logging
		Logger logger = Logger.getRootLogger();
		logger.info(new java.util.Date() + ": IP: " + req.getRemoteAddr());
		logger.info(new java.util.Date() + ": Method: /s/changeAccountType");
		logger.info(new java.util.Date() + ": Admin: " + idLogin);
		
		try {
			Administrator a = new Administrator(idLogin);
			if (a.getId() == 0)
				return Response.status(404).build();			// Administrator does not exist
			if (a.getPassword().equals(password)) {
				AccountType at = new AccountType(idAccountType);
				if (at == null)
					return Response.status(400).build();		// Account Type not found
				else {
					at.setDescription(description);
					at.setInterestRate(interestRate);
					at.updateDB();
					// Account Type Data was changed successfully
					return Response.ok().build();
				}
			}
			else
				return Response.status(403).build();			// Wrong Password
		}
		catch(SQLException e) {
			return Response.status(500).build();			// Internal Server Error
		}							
	}
}
