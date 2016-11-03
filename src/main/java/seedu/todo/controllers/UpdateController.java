package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.TodoListDB;

/**
 * @@author A0093907W
 * 
 * Controller to update a CalendarItem.
 */
public class UpdateController implements Controller {
    
    private static final String NAME = "Update";
    private static final String DESCRIPTION = "Updates a task by listed index.";
    private static final String COMMAND_SYNTAX = "update <index> <task> by <deadline>";
    
    private static final String MESSAGE_UPDATE_SUCCESS = "Item successfully updated!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.toLowerCase().startsWith("update")) ? 1 : 0;
    }
    
    /**
     * Get the token definitions for use with <code>tokenizer</code>.<br>
     * This method exists primarily because Java does not support HashMap
     * literals...
     * 
     * @return tokenDefinitions
     */
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"update"});
        tokenDefinitions.put("name", new String[] {"name"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to" });
        return tokenDefinitions;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        
    }
    
    /**
     * Extracts the record index from parsedResult.
     * 
     * @param parsedResult
     * @return  Integer index if parse was successful, null otherwise.
     */
    private Integer parseIndex(Map<String, String[]> parsedResult) {
        String indexStr = null;
        if (parsedResult.get("default") != null && parsedResult.get("default")[1] != null) {
            indexStr = parsedResult.get("default")[1].trim();
            return Integer.decode(indexStr);
        } else {
            return null;
        }
    }
    
    /**
     * Extracts the name to be updated from parsedResult.
     * 
     * @param parsedResult
     * @return  String name if found, null otherwise.
     */
    private String parseName(Map<String, String[]> parsedResult) {
        if (parsedResult.get("name") != null && parsedResult.get("name")[1] != null) {
            return parsedResult.get("name")[1];
        }
        return null;
    }
}
