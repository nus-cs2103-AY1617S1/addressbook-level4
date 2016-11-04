package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.*;

import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.controllers.concerns.DateParser;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

/**
 * @@author A0093907W
 * 
 * Controller to add an event or task.
 */
public class AddController implements Controller {
    
    private static final String NAME = "Add";
    private static final String DESCRIPTION = "Adds a task / event to the to-do list.\n"
                                            + "Accepts natural date formats (e.g. \"Today 5pm\" is allowed).";
    private static final String COMMAND_SYNTAX = "add <task> by <deadline> || add <event> from <start_date> to <end_date>";

    private static final String MESSAGE_ADD_SUCCESS = "Item successfully added!";
    private static final String STRING_WHITESPACE = "";
    private static final String ADD_EVENT_TEMPLATE = "add event \"%s\" from \"%s\" to \"%s\"";
    private static final String ADD_TASK_TEMPLATE = "add task \"%s\" by \"%s\"";
    private static final String START_TIME_FIELD = "<start time>";
    private static final String END_TIME_FIELD = "<end time>";
    private static final String DEADLINE_FIELD = "<deadline>";
    private static final String NAME_FIELD = "<name>";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.toLowerCase().startsWith("add")) ? 1 : 0;
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
        tokenDefinitions.put("default", new String[] {"add"});
        tokenDefinitions.put("eventType", new String[] { "event", "task" });
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to" });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        // Task or event?
        boolean isTask = parseIsTask(parsedResult);
        
        // Name
        String name = parseName(parsedResult);
        
        // Time
        String[] naturalDates = DateParser.extractDatePair(parsedResult);
        String naturalFrom = naturalDates[0];
        String naturalTo = naturalDates[1];
        
        // Validate isTask, name and times.
        if (validateParams(isTask, name, naturalFrom, naturalTo)) {
            renderDisambiguation(isTask, name, naturalFrom, naturalTo);
            return;
        }
        
        // Parse natural date using Natty.
        LocalDateTime dateFrom;
        LocalDateTime dateTo;
        try {
            dateFrom = naturalFrom == null ? null : DateParser.parseNatural(naturalFrom);
            dateTo = naturalTo == null ? null : DateParser.parseNatural(naturalTo);
        } catch (InvalidNaturalDateException e) {
            renderDisambiguation(isTask, name, naturalFrom, naturalTo);
            return;
        }
        
        // Create and persist task / event.
        TodoListDB db = TodoListDB.getInstance();
        createCalendarItem(db, isTask, name, dateFrom, dateTo);
        
        // Re-render
        Renderer.renderIndex(db, MESSAGE_ADD_SUCCESS);
    }

    /**
     * Creates and persists a CalendarItem to the DB.
     * 
     * @param db
     *            TodoListDB object
     * @param isTask
     *            true if CalendarItem should be a Task, false if Event
     * @param name
     *            Display name of CalendarItem object
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event
     */
    private void createCalendarItem(TodoListDB db, 
            boolean isTask, String name, LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (isTask) {
            Task newTask = db.createTask();
            newTask.setName(name);
            newTask.setDueDate(dateFrom);
        } else {
            Event newEvent = db.createEvent();
            newEvent.setName(name);
            newEvent.setStartDate(dateFrom);
            newEvent.setEndDate(dateTo);
        }
        db.save();
    }

    
    /**
     * Validates the parsed parameters.
     * 
     * <ul>
     * <li>Fail if name is null.</li>
     * <li>Fail if "to" exists without "from"</li>
     * <li>Fail if task, but "from" and "to" exist</li>
     * </ul>
     * 
     * @param isTask
     *            true if CalendarItem should be a Task, false if Event
     * @param name
     *            Display name of CalendarItem object
     * @param naturalFrom
     *            Raw input for due date for Task or start date for Event
     * @param naturalTo
     *            Raw input for end date for Event
     * @return true if validation passed, false otherwise
     */
    private boolean validateParams(boolean isTask, String name, String naturalFrom, String naturalTo) {
        return (name == null ||
                (naturalFrom == null && naturalTo != null) || (isTask && naturalTo != null));
    }

    /**
     * Extracts the display name of the CalendarItem from parsedResult.
     * 
     * @param parsedResult
     * @return name
     */
    private String parseName(Map<String, String[]> parsedResult) {
        String name = null;
        if (parsedResult.get("default") != null && parsedResult.get("default")[1] != null) {
            name = parsedResult.get("default")[1];
        }
        if (parsedResult.get("eventType") != null && parsedResult.get("eventType")[1] != null) {
            name = parsedResult.get("eventType")[1];
        }
        return name;
    }

    /**
     * Extracts the intended CalendarItem type from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task, false if Event
     */
    private boolean parseIsTask(Map<String, String[]> parsedResult) {
        boolean isTask = true;
        if (parsedResult.get("eventType") != null && parsedResult.get("eventType")[0].equals("event")) {
            isTask = false;
        }
        return isTask;
    }
    
    private void renderDisambiguation(boolean isTask, String name, String naturalFrom, String naturalTo) {
        name = StringUtil.replaceEmpty(name, NAME_FIELD);

        String disambiguationString;
        String errorMessage = STRING_WHITESPACE; // TODO
        
        if (isTask) {
            naturalFrom = StringUtil.replaceEmpty(naturalFrom, DEADLINE_FIELD);
            disambiguationString = String.format(ADD_TASK_TEMPLATE, name, naturalFrom);
        } else {
            naturalFrom = StringUtil.replaceEmpty(naturalFrom, START_TIME_FIELD);
            naturalTo = StringUtil.replaceEmpty(naturalTo, END_TIME_FIELD);
            disambiguationString = String.format(ADD_EVENT_TEMPLATE, name, naturalFrom, naturalTo);
        }
        
        // Show an error in the console
        Renderer.renderDisambiguation(disambiguationString, errorMessage);
    }
    
}
