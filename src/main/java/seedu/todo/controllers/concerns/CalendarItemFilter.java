package seedu.todo.controllers.concerns;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

/**
 * @@author A0093907W
 * 
 * Class to store CalendarItem filtering methods to be shared across controllers.
 *
 */
public class CalendarItemFilter {
    
    /**
     * Get the token definitions for use with <code>tokenizer</code>.<br>
     * This method exists primarily because Java does not support HashMap
     * literals...
     * 
     * @return tokenDefinitions
     */
    public static Map<String, String[]> getFilterTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks" });
        tokenDefinitions.put("name", new String[] { "name" });
        tokenDefinitions.put("taskStatus", new String[] { "complete" , "completed", "incomplete", "incompleted" });
        tokenDefinitions.put("eventStatus", new String[] { "over" , "past", "future" });
        tokenDefinitions.put("timeFrom", new String[] { "from", "after" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before", "until", "by" });
        return tokenDefinitions;
    }
    
    /**
     * Returns a boolean array of {isTask, isEvent} which specifies if we should
     * filter tasks, events or both.
     * 
     * If there is no eventType specified, we will filter both.
     * 
     * @param parsedResult
     * @return {isTask, isEvent}
     */
    public static boolean[] parseIsTaskEvent(Map<String, String[]> parsedResult) {
        if (parsedResult.get("eventType") != null) {
            return new boolean[] { true, true };
        } else if (parsedResult.get("eventType")[0].equals("task")
                || parsedResult.get("eventType")[0].equals("tasks")) {
            return new boolean[] { true, false };
        } else {
            return new boolean[] { false, true };
        }
    }
    
    
    public static List<Task> filterTasks(Map<String, String[]> parsedResult) throws InvalidNaturalDateException {
        List<Predicate<Task>> taskPredicates = new ArrayList<Predicate<Task>>();
        
        // Filter by name
        if (parsedResult.get("name") != null) {
            taskPredicates.add(Task.predByName(parsedResult.get("name")[1]));
        }
        
        // Filter by taskStatus
        if (parsedResult.get("taskStatus") != null) {
            String taskStatus = parsedResult.get("taskStatus")[0];
            if (taskStatus.equals("complete") || taskStatus.equals("completed")) {
                taskPredicates.add(Task.predCompleted(true));
            } else {
                taskPredicates.add(Task.predCompleted(false));
            }
        }
        
        // Filter by dueDate
        String[] datePair = DateParser.extractDatePair(parsedResult);
        String timeStartNatural = datePair[0];
        String timeEndNatural = datePair[1];
        if (timeStartNatural != null) {
            LocalDateTime timeStart = DateParser.parseNatural(timeStartNatural);
            taskPredicates.add(Task.predAfterDueDate(timeStart));
        }
        if (timeEndNatural != null) {
            LocalDateTime timeEnd = DateParser.parseNatural(timeEndNatural);
            taskPredicates.add(Task.predBeforeDueDate(timeEnd));
        }
        
        return Task.where(taskPredicates);
    }
    
    public static List<Event> filterEvents(Map<String, String[]> parsedResult) throws InvalidNaturalDateException {
        List<Predicate<Event>> eventPredicates = new ArrayList<Predicate<Event>>();
        
        // Filter by name
        if (parsedResult.get("name") != null) {
            eventPredicates.add(Event.predByName(parsedResult.get("name")[1]));
        }
        
        // Filter by eventStatus
        if (parsedResult.get("eventStatus") != null && parsedResult.get("eventStatus")[1] != null) {
            String eventStatus = parsedResult.get("eventStatus")[1];
            LocalDateTime now = LocalDateTime.now();
            if (eventStatus.equals("over") || eventStatus.equals("past")) {
                eventPredicates.add(Event.predEndBefore(now));
            } else if (eventStatus.equals("future")) {
                eventPredicates.add(Event.predStartBefore(now));
            }
        }
        
        // Filter by time
        String[] datePair = DateParser.extractDatePair(parsedResult);
        String timeStartNatural = datePair[0];
        String timeEndNatural = datePair[1];
        if (timeStartNatural != null) {
            LocalDateTime timeStart = DateParser.parseNatural(timeStartNatural);
            eventPredicates.add(Event.predStartAfter(timeStart));
        }
        if (timeEndNatural != null) {
            LocalDateTime timeEnd = DateParser.parseNatural(timeEndNatural);
            eventPredicates.add(Event.predEndBefore(timeEnd));
        }
        
        return Event.where(eventPredicates);
    }

}
