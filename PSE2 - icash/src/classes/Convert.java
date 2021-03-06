package classes;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * class to convert different data types
 */
public class Convert {
	
	/**
	 * converts String to int
	 */
	public static int toInt(String s) {
		
		int result = 0;
		boolean negative = false;
		char[] c = s.toCharArray();
		
		for (int i=0; i<c.length; i++) {
			result *= 10;
			switch(c[i]) {
			case '1': result += 1; break;
			case '2': result += 2; break;
			case '3': result += 3; break;
			case '4': result += 4; break;
			case '5': result += 5; break;
			case '6': result += 6; break;
			case '7': result += 7; break;
			case '8': result += 8; break;
			case '9': result += 9; break;
			case '0': break;
			case '-': negative = true; result /= 10; break;
			default : result /= 10;
			}
		}
		if (negative)
			result = -result;
		return result;
	}

	/**
	 * converts String to double
	 */
	public static double toDouble(String s) {
		
		double result = 0;
		boolean negative = false;
		char[] c = s.toCharArray();
		boolean decimalPlace = false;
		int noDecimalPlaces = 0;
		
		for (int i=0; i<c.length; i++) {
			result *= 10;
			if (decimalPlace)
				noDecimalPlaces++;
			switch(c[i]) {
			case '1': result += 1; break;
			case '2': result += 2; break;
			case '3': result += 3; break;
			case '4': result += 4; break;
			case '5': result += 5; break;
			case '6': result += 6; break;
			case '7': result += 7; break;
			case '8': result += 8; break;
			case '9': result += 9; break;
			case '0': break;
			case '-': negative = true; result /= 10; break;
			case ',':
			case '.': if (!decimalPlace) {
						  decimalPlace = true; 
						  result /= 10; 
						  break;
					  }
			default : result /= 10; 
					  if (decimalPlace) 
						  noDecimalPlaces--;
			}
		}	
		for (int i=0; i<noDecimalPlaces; i++)
			result /= 10;
		if (negative)
			result = -result;
		return result;
	}
	
	/**
	 * converts String (euro) to Cent
	 */
	public static int toCent(String euro) {
		
		double amount = toDouble(euro);
		return (int)Math.round(amount * 100);
	}

	/**
	 * converts int (cent) to euro (String)
	 */
	public static String toEuro(int cent) {
		String sign;
		if (cent < 0) {
			sign = "-";
			cent = -cent;
		}
		else
			sign = "";
		String euro = "" + cent;
		if (cent >= 100)
			return sign + euro.substring(0, euro.length() - 2) + "." + euro.substring(euro.length() - 2, euro.length());
		else if (cent >= 10)
			return sign + "0." + euro.substring(euro.length() - 2, euro.length());
		else
			return sign + "0.0" + euro.substring(euro.length() - 1, euro.length());
	}
	
	/**
	 * returns current DateTime
	 */
	public static Date currentDate() {
        java.util.Date currentTime = new java.util.Date();
        Date d = new Date(currentTime.getTime());
	    return d;
	}
}
