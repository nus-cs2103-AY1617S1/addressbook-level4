package seedu.address.logic.parser;

import guitests.FindCommandTest;

/*
 * List of all available parsers to respond to command input
 */
public class CommandParserList {
	public static Class<?>[] getList(){
		return new Class[]{AddCommandParser.class, DeleteCommandParser.class};
	}
}
