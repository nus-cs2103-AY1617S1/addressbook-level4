package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.ggist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TASKNAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#taskDate";
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String ENDTIME_FIELD_ID = "#endTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(TASKNAME_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getTaskName().taskName) && getDate().equals(task.getDate().value)
                && getStartTime().equals(task.getStartTime().value) && getEndTime().equals(task.getEndTime().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getDate().equals(handle.getDate()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getDate();
    }
}
