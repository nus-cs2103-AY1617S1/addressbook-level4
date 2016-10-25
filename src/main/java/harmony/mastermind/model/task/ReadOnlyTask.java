package harmony.mastermind.model.task;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

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
    
    //@@author A0124797R
    default boolean isSameTask(ReadOnlyTask task) {
        return this.getName().equals(task.getName());
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
    
    /**
     * Formats the Date as text, showing Task's date.
     */
    //@@author A0124797R
    default String parse(Date date) {
        String[] dateArr = date.toString().split(" ");
        String[] timeArr = dateArr[INDEX_TIME].split(":");
        final StringBuilder builder = new StringBuilder();
        
        builder.append(dateArr[INDEX_DAY] + " ").append(timeArr[INDEX_HOUR] + ":" + timeArr[INDEX_MINUTE] + " ")
            .append(dateArr[INDEX_DATE] + " ").append(dateArr[INDEX_MONTH] + " ").append(dateArr[INDEX_YEAR]);
        
        return builder.toString();
        
    }
    
    /**
     * Formats the Date as text, showing Task's date.
     */
    //@@author A0124797R
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
