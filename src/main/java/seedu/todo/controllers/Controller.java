package seedu.todo.controllers;

import seedu.todo.commons.exceptions.ParseException;

/**
 * @@author A0093907W
 * 
 * Contains the logic required to appropriately interpret and process user input
 * from the views.
 */
public interface Controller {
    
    public static CommandDefinition getCommandDefinition() {
        // Override with CommandDefinition.
        return null;
    }
    
    /**
     * Given a command keyword, performs a case-insensitive match.
     * 
     * @param keyword       Keyword to match
     * @return confidence   True if the command keyword matches.
     */
    public default boolean matchCommandKeyword(String keyword) {
        return getCommandDefinition().getCommandKeyword().toLowerCase().equals(keyword.toLowerCase());
    }
    
    /**
     * Processes the user input.
     * 
     * @param input
     *            User input
     */
    public void process(String input) throws ParseException;

}
