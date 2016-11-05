package seedu.todo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.controllers.concerns.CalendarItemFilter;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

/**
 * Controller to clear task/event by type or status
 * 
 * @@author A0093907W
 *
 */
public class ClearController implements Controller {
    
    private static final String NAME = "Clear";
    private static final String DESCRIPTION = "Clear all tasks/events or by specify date.";
    private static final String COMMAND_SYNTAX = "clear [task/event] [on date]";
    private static final String COMMAND_WORD = "clear";
    private static final String MESSAGE_CLEAR_NO_ITEMS_FOUND = "No items found!";
    private static final String MESSAGE_CLEAR_SUCCESS = "A total of %s deleted!\n" + "To undo, type \"undo\".";

    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith(COMMAND_WORD)) ? 1 : 0;
    }
    
    @Override
    public void process(String input) throws ParseException {
        // Tokenize input
        Map<String, String[]> parsedResult =
                Tokenizer.tokenize(CalendarItemFilter.getFilterTokenDefinitions(), input);
        
        // Decide if task/event/both
        boolean[] isTaskEvent = CalendarItemFilter.parseIsTaskEvent(parsedResult);
        boolean filterTask = isTaskEvent[0];
        boolean filterEvent = isTaskEvent[1];
        
        List<Task> clearTasks = new ArrayList<Task>();
        List<Event> clearEvents = new ArrayList<Event>();
        try {
            if (filterTask) {
                clearTasks = CalendarItemFilter.filterTasks(parsedResult);
            }
            if (filterEvent) {
                clearEvents = CalendarItemFilter.filterEvents(parsedResult);
            }
        } catch (InvalidNaturalDateException e) {
            renderDisambiguation(parsedResult);
            return;
        }
        
        // Clear them all!
        TodoListDB db = TodoListDB.getInstance();
        db.destroyTasks(clearTasks);
        db.destroyEvents(clearEvents);
        db.save();
        
        Renderer.renderIndex(db, "Done!");
    }
    
    private void renderDisambiguation(Map<String, String[]> parsedResult) {
        return;
    }
}
