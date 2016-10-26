package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class ActivityCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String REMINDER_FIELD_ID = "#reminder";
    private static final String DUEDATE_FIELD_ID = "#due date";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String STARTTIME_FIELD_ID = "#start time";
    private static final String ENDTIME_FIELD_ID = "#end time";
    

    private Node node;

    public ActivityCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getReminder() {
        return getTextFromLabel(REMINDER_FIELD_ID);
    }
    
    public String getDueDate() {
    	return getTextFromLabel(DUEDATE_FIELD_ID);
    }
    
    public String getPriority() {
    	return getTextFromLabel(PRIORITY_FIELD_ID);
    }
    
    public String getStartTime() {
    	return getTextFromLabel(STARTTIME_FIELD_ID);
    }
    
    public String getEndTime() {
    	return getTextFromLabel(ENDTIME_FIELD_ID);
    }

    public boolean isSameActivity(ReadOnlyActivity person){
    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().value);
    	
    	return (isSameName && isSameReminder);
    }
    
    public boolean isSameTask(ReadOnlyTask person){
    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().getCalendarValue());
    	boolean isSameDueDate = getDueDate().equals(person.getDueDate().getCalendarValue());
    	boolean isSamePriority = getPriority().equals(person.getPriority().value);
    	
    	return (isSameName && isSameReminder && isSameDueDate && isSamePriority);
    }
    
    public boolean isSameEvent(ReadOnlyEvent person){
    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().value);
    	boolean isSameStartTime = getStartTime().equals(person.getStartTime().getCalendarValue());
    	boolean isSameEndTime = getEndTime().equals(person.getEndTime().getCalendarValue());

    	return (isSameName && isSameReminder && isSameStartTime && isSameEndTime);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ActivityCardHandle) {
            ActivityCardHandle handle = (ActivityCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getReminder().equals(handle.getReminder()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getReminder();
    }
}
