package classes;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class Security {

	public static void main(String[] args) {
		String password = "myPassword";
		createPasswordHash(password);
		System.out.println(password);
	}
	
	public static String createPasswordHash(String password) {
        try {
        	  MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        	  String hash = new String (sha1.digest(password.getBytes()));
        	  hash.replace("\'", "!");
        	  hash.replace("\"", "!");
        	  password = hash;
        	 
        	} catch (NoSuchAlgorithmException ex) {
        		Logger logger = Logger.getRootLogger();
        		logger.info(new java.util.Date() + "An error occured during the creation of the password-hash");
        	}
    	return password;
    }
}
