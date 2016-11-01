//@@author A0146130W

package seedu.gtd.logic.parser;

import seedu.gtd.commons.exceptions.DataConversionException;

public interface NaturalLanguageProcessor {
	
	/** Takes in a string written in natural language and formats it.*/
	String formatString(String s);
	
	public static class NaturalLanguageException extends DataConversionException {
        protected NaturalLanguageException() {
            super("Natural Language Processor was unable to convert input");
        }
    }
}