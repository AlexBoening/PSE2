
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

	// Die Klasse Security muss teil der Desktop Software sein, damit wir das Passwort nicht im Klartext übermitteln
	// Man könnte überlegen die Klassen zu trennen und eine Klasse SecurityAdmin und eine Klasse SecurityCustomer draus zu machen
	
    public static String createPasswordHash(String password) {
        try {
        	  MessageDigest md5 = MessageDigest.getInstance("MD5");
        	  String hash = new String (md5.digest(password.getBytes()));
        	  hash.replace("\'", "!");
        	  hash.replace("\"", "!");
        	  password = hash;
        	 
        	} catch (NoSuchAlgorithmException ex) {
        		// Fehlerhandling hängt davon ab, ob wir mit einem HTTP Request arbeiten, oder mit etwas anderem
        	}
    	return password;
    }
    
    public static boolean sendAdminLoginRequest(String login, String password) {
    	
    	String pw = createPasswordHash(password);
    	// GET Session/User...
    	// Fehlerhandling
    	return true;
    
    }
    
    public static boolean sendCustomerLoginRequest(String login, String password) {
    
    	String pw = createPasswordHash(password);
    	// Get Session/User...
    	// Fehlerhandling
    	return true;
    
    }
    
    public static boolean logout() {
    	// Kill Session/Userobject.....
    	return true;
    }
}
