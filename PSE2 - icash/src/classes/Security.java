package classes;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

/**
 * class for Security methods
 * 
 */
public class Security {

	/**
	 * main program
	 * @param args
	 */
	public static void main(String[] args) {
		String password = "geheim";
		password = createPasswordHash(password);
		System.out.println(password);
	}
	
	/**
	 * hashes the given password
	 * @param password String
	 * @return hashed password
	 */
	public static String createPasswordHash(String password) {
        	return DigestUtils.sha1Hex(password);
    }
}
