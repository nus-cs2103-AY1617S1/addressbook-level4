package seedu.dailyplanner.commons.util;

import org.apache.commons.lang.StringUtils;

public class ArgumentFormatUtil {

	public static boolean isValidAddArgumentFormat(String trimmedArgs) {
		if (trimmedArgs.length() != 1 && trimmedArgs.charAt(1) == '/') {
			return false;
		}
		for (int k = 0; k < trimmedArgs.length(); k++) {
			if (trimmedArgs.charAt(k) == '/') {
				if (!(k + 1 == trimmedArgs.length())) {
					if (trimmedArgs.charAt(k + 1) == ' ') {
						return false;
					}
				} else {
					if (trimmedArgs.charAt(k) == '/')
						return false;
				}

			}
		}
		return true;
	}

	public static boolean isValidEditArgumentFormat(String args) {
		args = args.trim();
	    //If no index, return invalid
	    if(!Character.isDigit(args.charAt(0))) {
	        return false;
	    }
	    
	    //Return false if string is only a number
	    if (StringUtils.isNumeric(args)) {
	        return false;
	    }
	    
	    //Return false if no space before s/
	    if(args.contains("s/")) {
	        int indexOfStart = args.indexOf("s/");
	        if (args.charAt(indexOfStart-1) != ' ') {
	            return false;
	        }
	    }
	    
	  //Return false if no space before e/
	    if(args.contains("e/")) {
            int indexOfStart = args.indexOf("e/");
            if (args.charAt(indexOfStart-1) != ' ') {
                return false;
            }
        }
	  //Return false if no space before c/
	    if(args.contains("c/")) {
            int indexOfStart = args.indexOf("c/");
            if (args.charAt(indexOfStart-1) != ' ') {
                return false;
            }
        }
	    
	    return true;
	    
	}
}
