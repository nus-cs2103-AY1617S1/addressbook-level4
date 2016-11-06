package seedu.todo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.CalendarItemFilter;
import seedu.todo.controllers.concerns.DateParser;
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
    private static final String NAME_FIELD = "<name>";
    private static final String TASK_STATUS_FIELD = "<task status>";
    private static final String EVENT_STATUS_FIELD = "<event status>";
    private static final String START_TIME_FIELD = "<start>";
    private static final String END_TIME_FIELD = "<end>";
    private static final String TAG_FIELD = "<tag>";
    

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
        String name = (parsedResult.get("name") == null) ? null : parsedResult.get("name")[1];
        name = StringUtil.replaceEmpty(name, NAME_FIELD);
        
        String taskStatus = (parsedResult.get("taskStatus") == null) ? null : parsedResult.get("taskStatus")[0];
        taskStatus = StringUtil.replaceEmpty(taskStatus, TASK_STATUS_FIELD);
        
        String eventStatus = (parsedResult.get("eventStatus") == null) ? null : parsedResult.get("eventStatus")[0];
        eventStatus = StringUtil.replaceEmpty(eventStatus, EVENT_STATUS_FIELD);
        
        String[] datePair = DateParser.extractDatePair(parsedResult);
        String timeStartNatural = datePair[0];
        String timeEndNatural = datePair[1];
        timeStartNatural = StringUtil.replaceEmpty(timeStartNatural, START_TIME_FIELD);
        timeEndNatural = StringUtil.replaceEmpty(timeEndNatural, END_TIME_FIELD);
        
        String tag = (parsedResult.get("tag") == null) ? null : parsedResult.get("tag")[1];
        tag = StringUtil.replaceEmpty(tag, TAG_FIELD);
        
        String consoleCommand = null;
        if ((filterTask && filterEvent) || (!filterTask && !filterEvent)) {
            consoleCommand = String.format(CLEAR_TEMPLATE, name, timeStartNatural, timeEndNatural, tag);
        } else if (filterTask) {
            consoleCommand = String.format(CLEAR_TASKS_TEMPLATE, name, taskStatus, timeStartNatural, timeEndNatural, tag);
        } else {
            consoleCommand = String.format(CLEAR_EVENTS_TEMPLATE, name, eventStatus, timeStartNatural, timeEndNatural, tag);
        }
        
        Renderer.renderDisambiguation(consoleCommand, "");
    }
}
