package harmony.mastermind.model.task;

import java.time.Duration;
import java.util.Date;

import harmony.mastermind.model.tag.UniqueTagList;

//@@author A0124797R
public interface ReadOnlyTask {
    //provide safe read, unmodifiable task object
    final int INDEX_DAY = 0;
    final int INDEX_MONTH = 1;
    final int INDEX_DATE = 2;
    final int INDEX_TIME = 3;
    final int INDEX_YEAR = 5;
    final int INDEX_HOUR = 0;
    final int INDEX_MINUTE = 1;

    public String getName();
    public Date getStartDate();
    public Date getEndDate();
    public Date getCreatedDate();
    public UniqueTagList getTags();
    public String getRecur();
    
    public boolean isMarked();
    public boolean isFloating();
    public boolean isDeadline();
    public boolean isEvent();
    public boolean isRecur();
    
    public boolean isDue();
    public boolean isHappening();
    public Duration getDueDuration();
    public Duration getEventDuration();
    
    default boolean isSameTask(ReadOnlyTask task) {
        return task == this // short circuit if same object
                || (this.toString().equals(task.toString())); // state check  
    }
    
    /**
     * Formats the task as text, showing all task details.
     */
    //@@author A0124797R
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        
        if (getStartDate() != null) {
            builder.append(" start:" + parseForConsole(getStartDate()));
        }
        
        if (getEndDate() != null) {
            builder.append(" end:" + parseForConsole(getEndDate()));
        }
        
        if (!getTags().toString().isEmpty()) {
            builder.append(" Tags: ")
            .append(getTags().toString());
        }
                
        return builder.toString();
    }

    //@@author A0124797R
    /**
     * Formats the Date as text, showing Task's date.
     */
    default String parse(Date date) {
        String[] dateArr = date.toString().split(" ");
        String[] timeArr = dateArr[INDEX_TIME].split(":");
        final StringBuilder builder = new StringBuilder();
        
        builder.append(dateArr[INDEX_DAY] + " ").append(timeArr[INDEX_HOUR] + ":" + timeArr[INDEX_MINUTE] + " ")
            .append(dateArr[INDEX_DATE] + " ").append(dateArr[INDEX_MONTH] + " ").append(dateArr[INDEX_YEAR]);
        
        return builder.toString();
        
    }

    //@@author A0124797R
    /**
     * Formats the Date as text, showing Task's date.
     */
    default String parseForConsole(Date date) {
        String[] dateArr = date.toString().split(" ");
        String[] timeArr = dateArr[INDEX_TIME].split(":");
        String year = dateArr[INDEX_YEAR].substring(2, 4);
        final StringBuilder builder = new StringBuilder();
        
        builder.append(timeArr[INDEX_HOUR] + ":" + timeArr[INDEX_MINUTE] + "|")
            .append(dateArr[INDEX_DATE]).append(dateArr[INDEX_MONTH]).append(year);
        
        return builder.toString();
        
    }
    
}
