package seedu.address.logic.parser;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/*
 * Iterates through all parsers to find the appropriate one for the given command input
 */
public class ParserType {
		
	private static final Class<?>[] parserTypes = CommandParserList.getList();
    private static final Logger logger = LogsCenter.getLogger(ParserType.class);

	
	public static CommandParser get(String commandWord){
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
