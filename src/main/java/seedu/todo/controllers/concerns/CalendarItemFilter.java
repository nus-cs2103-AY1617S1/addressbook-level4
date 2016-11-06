package seedu.todo.controllers.concerns;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.todo.commons.exceptions.AmbiguousEventTypeException;
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
        tokenDefinitions.put("default", new String[] { "clear", "list" });
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks" });
        tokenDefinitions.put("name", new String[] { "name" });
        tokenDefinitions.put("taskStatus", new String[] { "complete" , "completed", "incomplete", "incompleted" });
        tokenDefinitions.put("eventStatus", new String[] { "over" , "past", "future" });
        tokenDefinitions.put("timeFrom", new String[] { "from", "after" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before", "until", "by" });
        tokenDefinitions.put("tag", new String[] { "tag" });
        return tokenDefinitions;
    }
    
    /**
     * Returns a boolean array of {isTask, isEvent} which specifies if we should
     * filter tasks, events or both.
     * 
     * If there is no eventType specified, we will filter both.
     * 
     * <ol>
     * <li>If no "task"/"event" token, and no eventStatus/taskStatus token, then filter both</li>
     * <li>If no "task"/"event" token, and exactly one of eventStatus/taskStatus present, then use it to guess</li>
     * <li>If "task" token found, then assert no eventStatus token</li>
     * <li>If "event" token found, then assert no taskStatus token</li>
     * <li>Assert that eventStatus and taskStatus tokens cannot both be present</li>
     * </ol>
     * 
     * @param parsedResult
     * @return {isTask, isEvent}
     */
    public static boolean[] parseIsTaskEvent(Map<String, String[]> parsedResult) throws AmbiguousEventTypeException {
        // Extract relevant params
        String eventType = null;
        if (parsedResult.get("eventType") != null) {
            eventType = parsedResult.get("eventType")[0];
            // Singularize
            eventType = "events".equals(eventType) ? "event" : eventType;
            eventType = "tasks".equals(eventType) ? "task" : eventType;
        }
        boolean taskStatusPresent = parsedResult.get("taskStatus") != null;
        boolean eventStatusPresent = parsedResult.get("eventStatus") != null;

        if (eventType == null) {
            if (!taskStatusPresent && !eventStatusPresent) {
                // Condition 1
                return new boolean[] { true, true };
            } else if (eventStatusPresent && !taskStatusPresent) {
                // Condition 2 - Task
                return new boolean[] { false, true };
            } else if (taskStatusPresent && !eventStatusPresent) {
                // Condition 2 - Event
                return new boolean[] { true, false };
            }
        } else {
            if ("task".equals(eventType) && !eventStatusPresent) {
                // Condition 3
                return new boolean[] { true, false };
            } else if (eventType.equals("event") && !taskStatusPresent) {
                // Condition 4
                return new boolean[] { false, true };
            }
        }
        
        // If we made it here, then at least one assertion was violated.
        throw new AmbiguousEventTypeException("Couldn't determine if task or event!");
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
        
        // Filter by tag
        if (parsedResult.get("tag") != null && parsedResult.get("tag")[1] != null) {
            taskPredicates.add(Task.predTag(parsedResult.get("tag")[1]));
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
        if (parsedResult.get("eventStatus") != null && parsedResult.get("eventStatus")[0] != null) {
            String eventStatus = parsedResult.get("eventStatus")[0];
            LocalDateTime now = LocalDateTime.now();
            if ("over".equals(eventStatus) || "past".equals(eventStatus)) {
                eventPredicates.add(Event.predEndBefore(now));
            } else if ("future".equals(eventStatus)) {
                eventPredicates.add(Event.predStartAfter(now));
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
        
        // Filter by tag
        if (parsedResult.get("tag") != null && parsedResult.get("tag")[1] != null) {
            eventPredicates.add(Event.predTag(parsedResult.get("tag")[1]));
        }
        
        return Event.where(eventPredicates);
    }

}
