package seedu.dailyplanner.commons.util;


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
	
	
	public static boolean isValidEditArgumentFormat(String args){
			String trimmedArgs = args.trim();
		
			if(!(trimmedArgs.contains(" "))){
				return false;
			}			
			else {
				String onlyIndex = trimmedArgs.substring(0,trimmedArgs.indexOf(' '));						
				if(!(onlyIndex.matches("^-?\\d+$"))){
					return false;
				}
			}
			
			
			if (args.charAt(args.length() -1) == '/')
				return false;
			
			for (int k = 0; k < args.length(); k++) {
				if (args.charAt(k) == '/') {
					if (!(k + 3 == trimmedArgs.length())) {
						if (args.charAt(k + 2) == '/') {
								return false;
						}
					} 
					
				}
			}							
			
		
		return true;
	}
	
	
	

}
