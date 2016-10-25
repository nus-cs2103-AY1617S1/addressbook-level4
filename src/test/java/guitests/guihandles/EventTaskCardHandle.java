package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDateTimeFormatter;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class EventTaskCardHandle extends GuiHandle {
    private static final String TASK_NAME_FIELD_ID = "#taskName";
    private static final String TASK_TYPE_FIELD_ID = "#taskType";
    private static final String START_DATE_FIELD_ID = "#startDateAndTime";
    private static final String END_DATE_FIELD_ID = "#endDateAndTime";

    private Node node;

    public EventTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
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

    public String getStartDate() {
        return getTextFromLabel(START_DATE_FIELD_ID);
    }

    public String getEndDate() {
        return getTextFromLabel(END_DATE_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getTaskName().equals(task.getName().fullName)
               && getTaskType().equals(task.getTaskType().toString())
               && getStartDate().equals(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()))
               && getEndDate().equals(TaskDateTimeFormatter.formatToShowDateAndTime(task.getEndDate().get()));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EventTaskCardHandle) {
            EventTaskCardHandle handle = (EventTaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getTaskType().equals(handle.getTaskType())
                    && getStartDate().equals(handle.getStartDate())
                    && getEndDate().equals(handle.getEndDate());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getTaskType() + " " + getStartDate() + " " + getEndDate();
    }
}
