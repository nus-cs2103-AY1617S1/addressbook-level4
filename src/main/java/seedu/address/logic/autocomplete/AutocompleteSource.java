package seedu.address.logic.autocomplete;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.CommandParserList;

/*
 * Returns a set of words for autocomplete purposes based on the requested
 * source
 */
public class AutocompleteSource {

	public static Set<String> getCommands() {
		// Iterate through all parsers and get the command words, fail with assertion errors on any problems
		// because we don't expect any non-programming related issues in this method
		
		Class<?>[] parserTypes = CommandParserList.getList();
		Logger logger = LogsCenter.getLogger(AutocompleteSource.class);
		Set<String> commandWords = new HashSet<String>();
		
		for (Class<?> parser : parserTypes) {
			try {
				String commandWord = (String) parser.getField("COMMAND_WORD").get(null);
				commandWords.add(commandWord);
				
			} catch (NoSuchFieldException ex) {
				logger.severe(ex.getMessage());
				assert false;
			} catch (IllegalArgumentException ex) {
				logger.severe(ex.getMessage());
				assert false;
			} catch (IllegalAccessException ex) {
				logger.severe(ex.getMessage());
				assert false;
			} catch (SecurityException ex) {
				logger.severe(ex.getMessage());
				assert false;
			} catch (Exception e) {
				logger.severe("Generic exception: " + e.getMessage());
				assert false;
			}
		}
		
		return commandWords;
	}
}
