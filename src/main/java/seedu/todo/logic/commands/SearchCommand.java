//@@author A0093896H
package seedu.todo.logic.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.TaskDate;

/**
 * Finds and lists all the task in DoDoBird based on the search option and arguments
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public enum SearchIndex {
        ON,
        BEFORE,
        AFTER,
        KEYWORD,
        TAG,
        DONE,
        UNDONE,
        FT,
        PRIORITY,
    }
    
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Search all tasks whose names contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " birthday homework friday";

    private final String data;
    private final SearchIndex whichSearch;
    
    public SearchCommand(String data, SearchIndex whichSearch) {
        this.data = data;
        this.whichSearch = whichSearch;
    }

    @Override
    public CommandResult execute() {
        switch (this.whichSearch) {
        case ON : 
            return searchOn();
            
        case BEFORE : 
            return searchBefore();
            
        case AFTER : 
            return searchAfter();
            
        case FT : 
            return searchFT();
            
        case KEYWORD : 
            return searchKeyword();
            
        case TAG : 
            return searchTag();
            
        case DONE :
            return searchDone();
                    
        case UNDONE : 
            return searchUndone();
    
        case PRIORITY : 
            return searchPriority();
                    
        default :
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }        
    }

    /**
     * Search tasks that falls on a certain date
     */
    private CommandResult searchOn() {
        try {
            LocalDateTime datetime = DateTimeUtil.parseDateTimeString(data, TaskDate.TASK_DATE_ON);
            model.updateFilteredTaskListOnDate(datetime);
            
            int size = model.getFilteredTaskList().size();
            return new CommandResult(getMessageForTaskListShownSummary(size));
            
        } catch (DateTimeParseException e) {
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
    
    /**
     * Search tasks that falls before a certain date
     */
    private CommandResult searchBefore() {
        try {
            LocalDateTime datetime = DateTimeUtil.parseDateTimeString(data, TaskDate.TASK_DATE_BY);
            model.updateFilteredTaskListBeforeDate(datetime);
                
            int size = model.getFilteredTaskList().size();
            return new CommandResult(getMessageForTaskListShownSummary(size));
                
        } catch (DateTimeParseException e) {
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
    
    /**
     * Search tasks that falls after a certain date
     */
    private CommandResult searchAfter() {
        try {
            LocalDateTime datetime = DateTimeUtil.parseDateTimeString(data, TaskDate.TASK_DATE_ON);
            model.updateFilteredTaskListAfterDate(datetime);
                
            int size = model.getFilteredTaskList().size();
            return new CommandResult(getMessageForTaskListShownSummary(size));
                
        } catch (DateTimeParseException e) {
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
    
    /**
     * Search tasks that falls from a certain date to another date
     */
    private CommandResult searchFT() {
        try {
            String fromDateString = data.split("@")[0].trim();
            LocalDateTime fromDateTime = DateTimeUtil.parseDateTimeString(fromDateString, TaskDate.TASK_DATE_ON);
                
            String tillDateString = data.split("@")[1].trim();
            LocalDateTime tillDateTime = DateTimeUtil.parseDateTimeString(tillDateString, TaskDate.TASK_DATE_BY);
                
            model.updateFilteredTaskListFromTillDate(fromDateTime, tillDateTime);
            
            int size = model.getFilteredTaskList().size();
            return new CommandResult(getMessageForTaskListShownSummary(size));
                
        } catch (DateTimeParseException e) {
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
    
    /**
     * Search tasks based on keywords
     * Search is case insensitive
     * Will match the keywords in the tasks names and details.
     */
    private CommandResult searchKeyword() {
        final String[] keywords = data.split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        model.updateFilteredTaskListByKeywords(keywordSet);
        
        int size = model.getFilteredTaskList().size();
        return new CommandResult(getMessageForTaskListShownSummary(size));
    }
    
    /**
     * Search tasks that have a certain tag
     */
    private CommandResult searchTag() {
        String tag = data.split("tag")[1].trim();
        model.updateFilteredTaskListByTag(tag);
        
        int size = model.getFilteredTaskList().size();
        return new CommandResult(getMessageForTaskListShownSummary(size));
    }
    
    
    /**
     * Search tasks that based on their priority level
     */
    private CommandResult searchPriority() {
        try {
            String priority = data.trim();                   
            model.updateFilteredTaskListByPriority(new Priority(priority));
           
            int size = model.getFilteredTaskList().size();
            return new CommandResult(getMessageForTaskListShownSummary(size));
               
        } catch (IllegalValueException e) {
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
    
    /**
     * Search tasks that are completed
     */
    private CommandResult searchDone() {
        model.updateFilteredListToShowAllCompleted();

        int size = model.getFilteredTaskList().size();
        return new CommandResult(getMessageForTaskListShownSummary(size));
    }
    
    /**
     * Search tasks that are not completed
     */
    private CommandResult searchUndone() {
        model.updateFilteredListToShowAllNotCompleted();
        
        int size = model.getFilteredTaskList().size();
        return new CommandResult(getMessageForTaskListShownSummary(size));
    }
    
    
    
    
    
}
