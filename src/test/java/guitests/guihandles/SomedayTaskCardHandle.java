package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class SomedayTaskCardHandle extends GuiHandle {
    private static final String TASK_NAME_FIELD_ID = "#taskName";
    private static final String TASK_TYPE_FIELD_ID = "#taskType";

    private Node node;

    public SomedayTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromLabel(TASK_NAME_FIELD_ID);
    }

    public String getTaskType() {
        return getTextFromLabel(TASK_TYPE_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getTaskName().equals(task.getName().fullName)
                && getTaskType().equals(task.getTaskType().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SomedayTaskCardHandle) {
            SomedayTaskCardHandle handle = (SomedayTaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getTaskType().equals(handle.getTaskType());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getTaskType();
    }
}
