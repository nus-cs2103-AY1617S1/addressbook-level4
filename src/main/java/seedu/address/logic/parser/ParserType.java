package seedu.address.logic.parser;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

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
				logger.warning("Error: Non-parser class placed into list");
				System.exit(0);
			} catch (Exception e) {
				logger.warning("Exception encountered");
				System.exit(0);			
			}
		}
		return new IncorrectCommandParser();
		
	}
}
