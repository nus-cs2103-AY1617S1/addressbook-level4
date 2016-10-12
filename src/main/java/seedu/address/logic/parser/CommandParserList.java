package seedu.address.logic.parser;

/*
 * List of all available parsers to respond to command input
 */
public class CommandParserList {
	public static Class<?>[] getList(){
	    
		return new Class[]{AddCommandParser.class, DeleteCommandParser.class, FindCommandParser.class, 
		        AddAliasCommandParser.class,
		        DeleteAliasCommandParser.class,
		        IncorrectCommandParser.class,
		        ListCommandParser.class};

	}
}
