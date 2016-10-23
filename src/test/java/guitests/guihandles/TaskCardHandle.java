package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#date";
    private static final String DONE_FIELD_ID = "#done";
    private static final String RECURRING_FIELD_ID="#recurring";
    private static final String RECURRING_FREQUENCY_FIELD_ID="#frequency";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }
    
    public String getFrequency(){
        return getTextFromLabel(RECURRING_FREQUENCY_FIELD_ID);
    }
    
    // Temporary design
    public boolean isDone() {
        return getTextFromLabel(DONE_FIELD_ID).equals("done");
    }
    
    public boolean isRecurring(){
        return getTextFromLabel(RECURRING_FIELD_ID).equals("recurring");
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getName().equals(task.getName().taskName) && getDate().equals(task.getDate().getValue())
                && isDone() == task.isDone()&&isRecurring()==task.isRecurring()
                &&getFrequency().equals(task.getRecurring().recurringFrequency);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getDate().equals(handle.getDate())
                    && isDone() == handle.isDone()
                    && isRecurring()==handle.isRecurring()
                    && getFrequency()==handle.getFrequency(); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getDate() + " " + isDone()+" "+isRecurring()+" "+getFrequency();
    }
}
