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
    private static final String DETAILS_FIELD_ID = "#cardDetails";

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
        Node detailsNode = getNode(DETAILS_FIELD_ID, node);
        return detailsNode.isVisible() && detailsNode.isManaged();
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getTaskName().equals(task.getName().taskName)
                && getOpenTime().equals(task.getOpenTime().toPrettyString())
                && getCloseTime().equals(task.getCloseTime().toPrettyString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getOpenTime().equals(handle.getOpenTime())
                    && getCloseTime().equals(handle.getCloseTime());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getOpenTime() + " " + getCloseTime();
    }
}
