package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDateTimeFormatter;

/**
 * Provides a handle to a task card in the task list panel.
 */
//@@author A0142184L
public class DeadlineTaskCardHandle extends GuiHandle {
    private static final String TASK_NAME_FIELD_ID = "#taskName";
    private static final String TASK_TYPE_FIELD_ID = "#taskType";
    private static final String DUE_DATE_FIELD_ID = "#dueDateAndTime";

    private Node node;

    public DeadlineTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
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

    public String getDueDate() {
        return getTextFromLabel(DUE_DATE_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
    	return getTaskName().equals(task.getName().value)
               && getTaskType().equals(task.getTaskType().toString())
               && getDueDate().equals(TaskDateTimeFormatter.formatToShowDateAndTime(task.getEndDate().get()));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DeadlineTaskCardHandle) {
            DeadlineTaskCardHandle handle = (DeadlineTaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getTaskType().equals(handle.getTaskType())
                    && getDueDate().equals(handle.getDueDate());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getTaskType() + " " + getDueDate();
    }
}
