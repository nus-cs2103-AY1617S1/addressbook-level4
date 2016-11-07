package w15c2.tusk.logic.parser;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import w15c2.tusk.commons.core.LogsCenter;

//@@author A0143107U
/**
 * Responsible for selecting an appropriate parser from a pre-specified list
 */
public class ParserSelector {
		
	private static final Class<?>[] parserTypes = CommandParserList.getList();
    private static final Logger logger = LogsCenter.getLogger(ParserSelector.class);
    private static boolean isCommandWord;


    /**
     * Returns an appropriate CommandParser based on the input command word.
     * Iterates through the list of available parsers to select the first one with a matching
     * command word, or selects one that has a matching alternate command word if no matches are found
     * @param commandWord
     * @return command parser type
     */
	public static CommandParser getByCommandWord(String commandWord){
		for(int i=0; i<parserTypes.length; i++){
			try {
				Field type = parserTypes[i].getField("COMMAND_WORD");
				String command = (String)type.get(null);
				if(command.equals(commandWord)){
					isCommandWord = true;
					return (CommandParser)parserTypes[i].newInstance();
				}
				else{
					type = parserTypes[i].getField("ALTERNATE_COMMAND_WORD");
					command = (String)type.get(null);
					if(command!=null && command.equals(commandWord)){
						isCommandWord = true;
						return (CommandParser)parserTypes[i].newInstance();
					}
				}
			} 
			catch (NoSuchFieldException e) {
				logger.severe("Error: Non-parser class placed into list");
	        	assert false : "Non-parser class should not have been placed into list";
			} catch (Exception e) {
				logger.severe("Exception encountered");
			}
		}
		return new IncorrectCommandParser();
	}
	
	/**
     * Checks if the command is a commandWord
     * 
     * @return true if command is a commandWord
     */
	public static boolean getIsCommandWord(String command){
		isCommandWord = false;
		getByCommandWord(command);
		return isCommandWord;
	}
}
