package seedu.taskman.model.event;

import seedu.taskman.model.tag.UniqueTagList;

import java.util.Optional;

/**
 * Wrapper for both ReadOnlyEvent and ReadOnlyTask
 *
 * May i
 */
public class Activity{

    public enum ActivityType {EVENT, TASK};

    private ReadOnlyEvent activity;
    private ActivityType type;

    public Activity(ReadOnlyEvent event){
        activity = event;
        type = ActivityType.EVENT;
    }

    public Activity(ReadOnlyTask task){
        activity = task;
        type = ActivityType.TASK;
    }

    public ActivityType getType(){
        return type;
    }

    public Optional<Status> getStatus() {
        switch(type){
            case TASK:
                return Optional.ofNullable(((ReadOnlyTask)activity).getStatus());
            case EVENT:
            default:
                return null;
        }
    }

    public Optional<Deadline> getDeadline() {
        switch(type){
            case TASK:
                return ((ReadOnlyTask)activity).getDeadline();
            case EVENT:
            default:
                return Optional.empty();
        }
    }


    public Title getTitle() {
        return activity.getTitle();
    }

    public Optional<Frequency> getFrequency() {
        return activity.getFrequency();
    }

    public Optional<Schedule> getSchedule() {
        return activity.getSchedule();
    }

    public UniqueTagList getTags() {
        return activity.getTags();
    }

}
