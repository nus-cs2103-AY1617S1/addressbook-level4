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
	
	public static boolean isValidEditArgumentFormat(String trimmedArgs){
			String onlyIndex = trimmedArgs.substring(0,trimmedArgs.indexOf(' '));
			if(!trimmedArgs.contains(" ")){
				return false;
			}
			if(!(onlyIndex.matches("^-?\\d+$"))){
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
	
	
	public static boolean isValidDeleteFormat(String trimmedArgs){
		if(trimmedArgs.equals("complete")){
			return true;
		}
		if(!(trimmedArgs.matches("^-?\\d+$"))){
			return false;
		}
		return true;
	
	}
	
	
	public static boolean isValidPinOrCompleteFormat(String trimmedArgs){
		if(trimmedArgs.equals("complete")){
			return false;
		}
		if(!(trimmedArgs.matches("^-?\\d+$"))){
			return false;
		}
		return true;
	}
	
	
	public static boolean isValidShowFormat(String trimmedArgs){
		if(trimmedArgs.equals("complete")){
			return true;
		}
		if(!(trimmedArgs.matches("^-?\\d+$"))){
			return false;
		}
		return true;
	}

}
