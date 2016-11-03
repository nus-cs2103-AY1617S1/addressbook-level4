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
public class TaskCardHandle extends GuiHandle {
    private static final String TASK_NAME_FIELD_ID = "#taskName";
    private static final String TASK_TYPE_FIELD_ID = "#taskType";
    private static final String START_DATE_FIELD_ID = "#startDateAndTime";
    private static final String END_DATE_FIELD_ID = "#endDateAndTime";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
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
    
    public String getTags() {
    	return getTextFromLabel(TAGS_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getTaskName().equals(task.getName().value)
               && getTaskType().equals(task.getTaskType().toString())
               && getStartDate().equals(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()))
               && getEndDate().equals(TaskDateTimeFormatter.formatToShowDateAndTime(task.getEndDate().get()))
               && getTags().equals(task.tagsString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getTaskType().equals(handle.getTaskType())
                    && getStartDate().equals(handle.getStartDate())
                    && getEndDate().equals(handle.getEndDate())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getTaskType() + " " + getStartDate() + " " + getEndDate() + "" + getTags();
    }
}
