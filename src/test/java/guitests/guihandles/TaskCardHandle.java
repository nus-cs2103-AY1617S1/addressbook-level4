package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String OPEN_TIME_FIELD_ID = "#openTime";
    private static final String CLOSE_TIME_FIELD_ID = "#closeTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    
    protected String getTextFromText(String fieldId) {
        return getTextFromText(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromText(NAME_FIELD_ID);
    }
    
    public String getOpenTime() {
        return getTextFromLabel(OPEN_TIME_FIELD_ID);
    }
    
    public String getCloseTime() {
        return getTextFromLabel(CLOSE_TIME_FIELD_ID);
    }
    
    public boolean isDetailsShown() {
        Node openTimeNode = getNode(OPEN_TIME_FIELD_ID, node);
        Node closeTimeNode = getNode(CLOSE_TIME_FIELD_ID, node);
        return openTimeNode.isVisible() && openTimeNode.isManaged() && closeTimeNode.isVisible() && closeTimeNode.isManaged();
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getTaskName().equals(task.getName().taskName);
//                && getPhone().equals(person.getPhone().value)
//                && getEmail().equals(person.getEmail().value) && getAddress().equals(person.getAddress().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName());
                    //&& getAddress().equals(handle.getAddress()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getOpenTime() + " " + getCloseTime();
    }
}
