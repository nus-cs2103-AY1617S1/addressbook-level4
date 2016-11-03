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

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        
    }
}
