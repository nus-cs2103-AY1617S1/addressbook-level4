package seedu.todo.controllers;

import seedu.todo.commons.core.CommandDefinition;
import seedu.todo.commons.exceptions.ParseException;

// @@author A0093907W
/**
 * Contains the logic required to appropriately interpret and process user input
 * from the views.
 */
public abstract class Controller {
    
    public abstract CommandDefinition getCommandDefinition();
    
    /**
     * Given a command keyword, performs a case-insensitive match.
     * 
     * @param keyword       Keyword to match
     * @return confidence   True if the command keyword matches.
     */
    public boolean matchCommandKeyword(String keyword) {
        return getCommandDefinition().getCommandKeyword().equalsIgnoreCase(keyword);
    }
    
    /**
     * Processes the user input.
     * 
     * @param input
     *            User input
     */
    public abstract void process(String input) throws ParseException;

}
