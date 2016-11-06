package seedu.todo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seedu.todo.commons.exceptions.AmbiguousEventTypeException;
import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.CalendarItemFilter;
import seedu.todo.controllers.concerns.Disambiguator;
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
public class ClearController extends Controller {
    
    private static final String NAME = "Clear";
    private static final String DESCRIPTION = "Clear all tasks/events or by specify date.";
    private static final String COMMAND_SYNTAX = "clear [task/event] [on date]";
    private static final String COMMAND_KEYWORD = "clear";
    
    private static final String MESSAGE_CLEAR_NO_ITEMS_FOUND = "No items found!";
    private static final String MESSAGE_CLEAR_SUCCESS = "A total of %s %s and %s %s deleted!\n" + "To undo, type \"undo\".";
    
    private static final String CLEAR_TEMPLATE = "clear [name \"%s\"] [from \"%s\"] [to \"%s\"] [tag \"%s\"]";
    private static final String CLEAR_TASKS_TEMPLATE = "clear tasks [name \"%s\"] [\"%s\"] [from \"%s\"] [to \"%s\"] [tag \"%s\"]";
    private static final String CLEAR_EVENTS_TEMPLATE = "clear events [name \"%s\"] [\"%s\"] [from \"%s\"] [to \"%s\"] [tag \"%s\"]";
    

    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX, COMMAND_KEYWORD); 

    @Override
    public CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }
    
    @Override
    public void process(String input) throws ParseException {
        // Tokenize input
        Map<String, String[]> parsedResult =
                Tokenizer.tokenize(CalendarItemFilter.getFilterTokenDefinitions(), input);
        
        // Decide if task/event/both
        boolean[] isTaskEvent = null;
        try {
            isTaskEvent = CalendarItemFilter.parseIsTaskEvent(parsedResult);
        } catch (AmbiguousEventTypeException e) {
            renderDisambiguation(parsedResult, true, true);
            return;
        }
        
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
            renderDisambiguation(parsedResult, filterTask, filterEvent);
            return;
        }
        
        // Clear them all!
        TodoListDB db = TodoListDB.getInstance();
        
        if (clearTasks.size() == 0 && clearEvents.size() == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEMS_FOUND);
            return;
        }
        
        db.destroyTasks(clearTasks);
        db.destroyEvents(clearEvents);
        db.save();
        
        String consoleMessage = String.format(MESSAGE_CLEAR_SUCCESS,
                clearTasks.size(), StringUtil.pluralizer(clearTasks.size(), "task", "tasks"),
                clearEvents.size(), StringUtil.pluralizer(clearEvents.size(), "event", "events"));
        
        Renderer.renderIndex(db, consoleMessage);
    }
    
    private void renderDisambiguation(Map<String, String[]> parsedResult, boolean filterTask, boolean filterEvent) {
        Map<String, String> extractedTokens = Disambiguator.extractParsedTokens(parsedResult);
        
        String consoleCommand = null;
        if ((filterTask && filterEvent) || (!filterTask && !filterEvent)) {
            consoleCommand = String.format(CLEAR_TEMPLATE, extractedTokens.get("name"), extractedTokens.get("startTime"), 
                    extractedTokens.get("endTime"), extractedTokens.get("tag"));
        } else if (filterTask) {
            consoleCommand = String.format(CLEAR_TASKS_TEMPLATE, extractedTokens.get("name"), extractedTokens.get("taskStatus"), 
                    extractedTokens.get("startTime"), extractedTokens.get("endTime"), extractedTokens.get("tag"));
        } else {
            consoleCommand = String.format(CLEAR_EVENTS_TEMPLATE, extractedTokens.get("name"), extractedTokens.get("eventStatus"), 
                    extractedTokens.get("startTime"), extractedTokens.get("endTime"), extractedTokens.get("tag"));
        }
        
        Renderer.renderDisambiguation(consoleCommand, "");
    }
}
