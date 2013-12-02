package classes;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class Security {

	public static void main(String[] args) {
		String password = "geheim";
		password = createPasswordHash(password);
		System.out.println(password);
	}
	
	public static String createPasswordHash(String password) {
       // try {
        	  /*MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        	  String hash = new String (sha1.digest(password.getBytes()));
        	  if (URLEncode)
        		  password = URLEncoder.encode(hash, "UTF-8");
        	  else
        		  password = hash;*/
        	return DigestUtils.sha1Hex(password);
        	/*} 
        catch (NoSuchAlgorithmException ex) {
        	Logger logger = Logger.getRootLogger();
        	logger.info(new java.util.Date() + ": An error occured during the creation of the password-hash");
        	}
        catch (UnsupportedEncodingException e) {
        	Logger logger = Logger.getRootLogger();
    		logger.info(new java.util.Date() + ": An error occured during the creation of the password-hash");
        }
    	return password;*/
    }
}
