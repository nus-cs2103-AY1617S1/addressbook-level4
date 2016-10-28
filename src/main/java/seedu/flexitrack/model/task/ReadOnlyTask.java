package seedu.flexitrack.model.task;

import java.util.Date;

/**
 * A read-only immutable interface for a Task in FlexiTrack. Implementations
 * should guarantee: details are present and not null, field values are
 * validated.
 */
public interface ReadOnlyTask extends Comparable<ReadOnlyTask>{

    Name getName();

    DateTimeInfo getDueDate();
    DateTimeInfo getStartTime();
    DateTimeInfo getEndTime();
    
    boolean getIsTask();
    boolean getIsEvent();
    boolean getIsDone();
    
    
    /**
     * Returns true if both have the same state. (interfaces cannot override
     * .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName()) // state
                                                                  // checks here
                                                                  // onwards
                        && other.getDueDate().equals(this.getDueDate())
                        && other.getStartTime().equals(this.getStartTime())
                        && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        String text = (getIsTask() || getIsEvent())
                ? getIsTask() ? " by/" + getDueDate() : " from/" + getStartTime() + " to/" + getEndTime() : "";
        builder.append(getIsDone() ? "(Done)" : "" + getName()).append(text);
        return builder.toString();
    }
    
    //@@author A0127855W
    /**
     * Comparator for ReadOnlyTask and its children classes
     * Sorts by whether the task is a floating task, then by whether the task is done, then by start time/due date, then by name
     */
    default public int compareTo(ReadOnlyTask task) {
        int c = compareByDone(task);
        if(c != 0){
            return c;
        }
        c = compareByType(task);
        if(c != 0){
            return c;
        }
        if(this.getIsNotFloatingTask() && task.getIsNotFloatingTask()){
            c = compareByDate(task);
            if(c != 0){
                return c;
            }
        }
       c = compareByName(task);
       return c;
    }

    default int compareByDone(ReadOnlyTask task) {
        if(this.getIsDone() && !task.getIsDone()){
            return 1;
        }else if(!this.getIsDone() && task.getIsDone()){
            return -1;
        }else{
            return 0;
        }
    }
    
    default int compareByType(ReadOnlyTask task) {
        if(this.getIsNotFloatingTask() && !task.getIsNotFloatingTask()){
            return 1;
        }else if(!this.getIsNotFloatingTask() && task.getIsNotFloatingTask()){
            return -1;
        }else{
            return 0;
        }
    }
    default int compareByDate(ReadOnlyTask task) {
        Date date1 = this.getStartingTimeOrDueDate().getTimeInfo().getTimingInfo().getDates().get(0);
        Date date2 = task.getStartingTimeOrDueDate().getTimeInfo().getTimingInfo().getDates().get(0);
        return date1.compareTo(date2);
    }
    default int compareByName(ReadOnlyTask task) {
        String name1 = this.getName().getNameOnly();
        String name2 = task.getName().getNameOnly();
        return name1.compareTo(name2);
    }
    
  //@@author A0127686R
    default public boolean getIsNotFloatingTask(){
        return (this.getIsEvent() || this.getIsTask());
    }
    
    default public DateTimeInfo getStartingTimeOrDueDate(){
        if (this.getIsTask()){
            return this.getDueDate();
        } else {
            return  this.getStartTime();
        }
    }
    
    default public DateTimeInfo getEndingTimeOrDueDate(){
        if (this.getIsTask()){
            return this.getDueDate();
        } else {
            return  this.getEndTime();
        }
    }
    
}
