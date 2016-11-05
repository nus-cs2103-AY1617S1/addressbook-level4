package seedu.todo.controllers.concerns;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.todo.commons.exceptions.InvalidNaturalDateException;
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

}
