package seedu.address.logic.parser;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/*
 * Responsible for selecting an appropriate parser from a pre-specified list
 */
//@@author A0143107U
public class ParserSelector {
		
	private static final Class<?>[] parserTypes = CommandParserList.getList();
    private static final Logger logger = LogsCenter.getLogger(ParserSelector.class);

	/*
	 * Returns an appropriate CommandParser based on the input command word.
	 * Iterates through the list of available parsers to select the first one with a matching
	 * command word
	 */
	public static CommandParser getByCommandWord(String commandWord){
		for(int i=0; i<parserTypes.length; i++){
			try {
				Field type = parserTypes[i].getField("COMMAND_WORD");
				if(type.get(null).equals(commandWord)){
					return (CommandParser)parserTypes[i].newInstance();
				}
			} 
			catch (NoSuchFieldException e) {
				logger.severe("Error: Non-parser class placed into list");
				e.printStackTrace();
				System.exit(0);
			} catch (Exception e) {
				logger.severe("Exception encountered");
				e.printStackTrace();
				System.exit(0);			
			}
		}
		return new IncorrectCommandParser();
		
	}
}
