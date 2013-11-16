package client;

import java.sql.Date;
import java.util.ArrayList;

import javax.ws.rs.*;

import classes.*;

import org.json.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class TestClient {

	public Account getAccount(int number) {
		ClientResponse cr = Client.create().resource( "http://localhost:9998/rest/getAccount?number=" + number ).get( ClientResponse.class );
        if (cr.hasEntity()) {
		JSONObject jo = new JSONObject(cr.getEntity(String.class));
        Account a = new Account();
	    a.setId(jo.getInt("number"));
	    Customer c = new Customer();
	    c.setFirstName(jo.getString("owner").split(" ")[0]);
	    c.setSecondName(jo.getString("owner").split(" ")[1]);
	    a.setCustomer(c);
	    
	    JSONArray ja = jo.getJSONArray("transactions");
	    
	    for (int i=0; i<ja.length(); i++) {
	    	Transaction t = new Transaction();
	    	JSONObject transaction = ja.getJSONObject(i);
	    	t.setAmount(transaction.getInt("amount"));
	    	t.setDate(Date.valueOf(transaction.getString("transactionDate").substring(0, 10)));
	    	t.setDescription(transaction.getString("reference"));
	    	
	    	Account incomingAccount = new Account();
	    	JSONObject receiver = transaction.getJSONObject("receiver");
	    	incomingAccount.setId(receiver.getInt("number"));
	    	Customer c_in = new Customer();
	    	c_in.setFirstName(receiver.getString("owner").split(" ")[0]);
	    	c_in.setSecondName(receiver.getString("owner").split(" ")[1]);
	    	incomingAccount.setCustomer(c_in);
	    	t.setIncomingAccount(incomingAccount);
	    	
	    	Account outgoingAccount = new Account();
	    	JSONObject sender = transaction.getJSONObject("sender");
	    	outgoingAccount.setId(sender.getInt("number"));
	    	Customer c_out = new Customer();
	    	c_out.setFirstName(sender.getString("owner").split(" ")[0]);
	    	c_out.setSecondName(sender.getString("owner").split(" ")[1]);
	    	outgoingAccount.setCustomer(c_out);
	    	t.setOutgoingAccount(outgoingAccount);
	    	
	    	a.add(t);
	    }
	    return a;
        }
        else
        	return null;
	}
	
	public void transferMoneyapplication(int senderNumber, int receiverNumber, int amount, String reference) {
		ClientResponse cr = Client.create().resource( "http://localhost:9998/rest/transferMoneyapplication"
				                                    + "?senderNumber=" + senderNumber + "&receiverNumber=" + receiverNumber
				                                    + "&amount=" + amount + "&reference=" + reference).get( ClientResponse.class );
		
	}
	
	public void createCustomer(String firstName, String lastName, String password) {
		ClientResponse cr = Client.create().resource( "http://localhost:9998/rest/CreateCustomer"
                									+ "&firstName=" + firstName + "&lastName=" + lastName
                									+ "&password=" + password).get( ClientResponse.class );
	}
}
