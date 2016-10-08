package seedu.taskman.model.event;

import seedu.taskman.model.tag.UniqueTagList;

import java.util.Optional;

/**
 * Wrapper for both ReadOnlyEvent and ReadOnlyTask
 */
public class Activity implements ReadOnlyEvent{

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

    @Override
    public Title getTitle() {
        return activity.getTitle();
    }

    @Override
    public Optional<Frequency> getFrequency() {
        return activity.getFrequency();
    }

    @Override
    public Optional<Schedule> getSchedule() {
        return activity.getSchedule();
    }

    @Override
    public UniqueTagList getTags() {
        return activity.getTags();
    }

    @Override
    public boolean isSameStateAs(ReadOnlyEvent other) {
        return activity.isSameStateAs(other);
    }

    public boolean isSameStateAs(ReadOnlyTask task) {
        return task.isSameStateAs(activity);
    }

    @Override
    public String getAsText() {
        return activity.getAsText();
    }

}
