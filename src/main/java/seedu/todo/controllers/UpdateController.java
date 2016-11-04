package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.DateParser;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
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
    private static final String STRING_WHITESPACE = "";
    private static final String UPDATE_EVENT_TEMPLATE = "update \"%s\" [name \"%s\"] [from \"%s\" to \"%s\"]";
    private static final String UPDATE_TASK_TEMPLATE = "update \"%s\" [name \"%s\"] [by \"%s\"]";
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
    public void process(String input) throws ParseException {
        // TODO: Example of last minute work
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        // Record index
        Integer recordIndex = parseIndex(parsedResult);
        
        // Retrieve record and check if task or event
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = null;
        try {
            calendarItem = edb.getCalendarItemsByDisplayedId(recordIndex);
        } catch (NullPointerException e) {
            System.out.println("Wrong index!");
        }
        boolean isTask = calendarItem.getClass() == Task.class;
        
        // Name
        String name = parseName(parsedResult);
        
        // Time
        String[] naturalDates = DateParser.extractDatePair(parsedResult);
        String naturalFrom = naturalDates[0];
        String naturalTo = naturalDates[1];

        // Validate isTask, name and times.
        if (!validateParams(isTask, name, naturalFrom, naturalTo)) {
            renderDisambiguation(isTask, recordIndex, name, naturalFrom, naturalTo);
            return;
        }
        
        // Parse natural date using Natty.
        LocalDateTime dateFrom;
        LocalDateTime dateTo;
        try {
            dateFrom = naturalFrom == null ? null : DateParser.parseNatural(naturalFrom);
            dateTo = naturalTo == null ? null : DateParser.parseNatural(naturalTo);
        } catch (InvalidNaturalDateException e) {
            System.out.println("Disambiguate!");
            return;
        }
        
        // Update and persist task / event.
        TodoListDB db = TodoListDB.getInstance();
        updateCalendarItem(db, calendarItem, isTask, name, dateFrom, dateTo);
        
        // Re-render
        Renderer.renderIndex(db, MESSAGE_UPDATE_SUCCESS);
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
    
    /**
     * Updates and persists a CalendarItem to the DB.
     * 
     * @param db
     *            TodoListDB instance
     * @param record
     *            Record to update
     * @param isTask
     *            true if CalendarItem is a Task, false if Event
     * @param name
     *            Display name of CalendarItem object
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event
     */
    private void updateCalendarItem(TodoListDB db, CalendarItem record,
            boolean isTask, String name, LocalDateTime dateFrom, LocalDateTime dateTo) {
        // Update name if not null
        if (name != null) {
            record.setName(name);
        }
        
        // Update time
        if (isTask) {
            Task task = (Task) record;
            if (dateFrom != null) {
                task.setDueDate(dateFrom);
            }
        } else {
            Event event = (Event) record;
            if (dateFrom != null) {
                event.setStartDate(dateFrom);
            }
            if (dateTo != null) {
                event.setEndDate(dateTo);
            }
        }
        
        // Persist
        db.save();
    }
    
    /**
     * Validate that applying the update changes to the record will not result in an inconsistency.
     * 
     * <li>
     * <ul>Fail if name is invalid</ul>
     * </li>
     * 
     * Tasks:
     * <li>
     * <ul>Fail if task has a dateTo</ul>
     * </li>
     * 
     * Events:
     * <li>
     * <ul>Fail if event does not have both dateFrom and dateTo</ul>
     * <ul>Fail if event has a dateTo that is before dateFrom</ul>
     * </li>
     * 
     * @param isTask
     * @param name
     * @param dateFrom
     * @param dateTo
     * @return
     */
    private boolean validateParams(boolean isTask, CalendarItem record, String name,
            LocalDateTime dateFrom, LocalDateTime dateTo) {
        // TODO: Not enough sleep
        // We really need proper ActiveRecord validation and rollback, sigh...
        
        if (isTask) {
            // Fail if task has a dateTo
            if (dateTo != null) {
                return false;
            }
        } else {
            Event event = (Event) record;
            
            // Take union of existing fields and update params
            LocalDateTime newDateFrom = (dateFrom == null) ? event.getStartDate() : dateFrom;
            LocalDateTime newDateTo = (dateTo == null) ? event.getEndDate() : dateTo;
            
            if (newDateFrom == null || newDateTo == null) {
                return false;
            }
            
            if (newDateTo.isBefore(newDateFrom)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Renders disambiguation with best-effort input matching to template.
     * 
     * @param isTask
     * @param name
     * @param naturalFrom
     * @param naturalTo
     */
    private void renderDisambiguation(boolean isTask, int recordIndex, String name, String naturalFrom, String naturalTo) {
        name = StringUtil.replaceEmpty(name, NAME_FIELD);

        String disambiguationString;
        String errorMessage = STRING_WHITESPACE; // TODO
        
        if (isTask) {
            naturalFrom = StringUtil.replaceEmpty(naturalFrom, DEADLINE_FIELD);
            disambiguationString = String.format(UPDATE_TASK_TEMPLATE, recordIndex, name, naturalFrom);
        } else {
            naturalFrom = StringUtil.replaceEmpty(naturalFrom, START_TIME_FIELD);
            naturalTo = StringUtil.replaceEmpty(naturalTo, END_TIME_FIELD);
            disambiguationString = String.format(UPDATE_EVENT_TEMPLATE, recordIndex, name, naturalFrom, naturalTo);
        }
        
        // Show an error in the console
        Renderer.renderDisambiguation(disambiguationString, errorMessage);
    }
}
