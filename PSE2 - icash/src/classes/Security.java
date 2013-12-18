package classes;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * class for Security methods
 * 
 */
public class Security {
	
	/**
	 * hashes the given password
	 * @param password String
	 * @return hashed password
	 */
	public static String createPasswordHash(String password) {
        	return DigestUtils.sha1Hex(password);
    }
}
