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
    private static final String DUEDATE_FIELD_ID = "#line1";
    private static final String PRIORITY_FIELD_ID = "#line2";
    private static final String STARTTIME_FIELD_ID = "#line1";
    private static final String ENDTIME_FIELD_ID = "#line2";
    

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
    	boolean isSameReminder = getReminder().equals(person.getReminder().forDisplay());
    	return (isSameName && isSameReminder);
    }
    
    public boolean isSameTask(ReadOnlyTask person){
    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().forDisplay());
    	boolean isSameDueDate = getDueDate().toString().equals(person.getDueDate().forDisplay());
    	String name = getPriority();
String fromperson = person.getPriority().toString();
    	boolean isSamePriority = getPriority().equals(person.getPriority().forDisplay());
    	
    	return (isSameName && isSameReminder && isSameDueDate && isSamePriority);
    }
    
    public boolean isSameEvent(ReadOnlyEvent person){
    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().forDisplay());
    	boolean isSameStartTime = getStartTime().equals(person.getStartTime().forDisplay());
    	boolean isSameEndTime = getEndTime().equals(person.getEndTime().forDisplay());

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
