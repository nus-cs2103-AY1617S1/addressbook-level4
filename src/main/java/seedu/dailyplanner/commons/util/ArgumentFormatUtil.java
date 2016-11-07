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

	public static boolean isValidEditArgumentFormat(String args) {
		String trimmedArgs = args.trim();

		if (!(trimmedArgs.contains(" "))) { //check for empty edits with index
			return false;
		}
		String[] checkFormat = trimmedArgs.split(" ");
		// String onlyIndex = trimmedArgs.substring(0,trimmedArgs.indexOf(' '));
		if(!checkFormat[0].matches("\\d+")){ //check if it is an index
			return false;
		}
		for (int k = 0; k < trimmedArgs.length(); k++) {
			if ((checkFormat[k].length() != 2) && (checkFormat[k].substring(0, 2).equals("s/"))) {
				if ((checkFormat[k].charAt(2) == 'e') || !(checkFormat[k].charAt(2) == 'c')) {
					return false;
				}
			}
			if (checkFormat[k].substring(0, 2).equals("e/")) {
				if ((checkFormat[k].length() != 2) && (checkFormat[k].charAt(2) == 'e')
						|| !(checkFormat[k].charAt(2) == 'c')) {
					return false;
				}
			}
			if (checkFormat[k].substring(0, 2).equals("c/")) {
				if ((checkFormat[k].length() != 2) && (checkFormat[k].charAt(2) == 's')
						|| !(checkFormat[k].charAt(2) == 'e')) {
					return false;
				}
			}

		}

		return true;
	}
}
