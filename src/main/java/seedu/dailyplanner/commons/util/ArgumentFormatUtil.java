package seedu.dailyplanner.commons.util;

import org.apache.commons.lang.StringUtils;
//@@author A0139102U
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
	    if(noIndexInArgs(args)) {
	        return false;
	    }
	    
	    //Return false if string is only a number
	    if (StringUtils.isNumeric(args)) {
	        return false;
	    }
	    
	    //Return false if no space before s/
	    if(args.contains("s/")) {
	        if (ifNoWhiteSpaceBeforeParameter(args, "s/")) {
	            return false;
	        }
	    }
	    
	  //Return false if no space before e/
	    if(args.contains("e/")) {
	        if (ifNoWhiteSpaceBeforeParameter(args, "e/")) {
                return false;
            }
        }
	  //Return false if no space before c/
	    if(args.contains("c/")) {
	        if (ifNoWhiteSpaceBeforeParameter(args, "c/")) {
                return false;
            }
        }    
	    return true;  
	}

    private static boolean ifNoWhiteSpaceBeforeParameter(String args, String parameter) {
        return args.charAt(args.indexOf(parameter)-1) != ' ';
    }

    private static boolean noIndexInArgs(String args) {
        return !Character.isDigit(args.charAt(0));
    }
}
