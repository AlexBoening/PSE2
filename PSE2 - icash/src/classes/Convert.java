package classes;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Convert {
	
	public static int toInt(String s) {
		
		int result = 0;
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
			}
		}
		
		return result;
	}

	public static double toDouble(String s) {
		
		double result = 0;
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
			case ',':
			case '.': decimalPlace = true; result /= 10;
			}
		}	
		for (int i=0; i<noDecimalPlaces; i++)
			result /= 10;
		return result;
	}
	
	public static Date currentDate() {
	    //SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        java.util.Date currentTime = new java.util.Date();
        //String s = formatter.format(currentTime);
        //Date d = new Date(toInt(s.substring(0,4))-1900, toInt(s.substring(4,6))-1, toInt(s.substring(6,8)));*/
        Date d = new Date(currentTime.getTime());
	    return d;
	}
}
