package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.flexitrack.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATETIMEINFO_DUEDATE_ID = "#dueDate";
    private static final String DATETIMEINFO_STARTTIME_ID = "#startTime";
    private static final String DATETIMEINFO_ENDTIME_ID = "#endTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDueDate() {
        return getTextFromLabel(DATETIMEINFO_DUEDATE_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(DATETIMEINFO_STARTTIME_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(DATETIMEINFO_ENDTIME_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getName().fullName) && getDueDate().equals(task.getDueDate().toString())
                && getStartTime().equals(task.getStartTime().toString()) && getEndTime().equals(task.getEndTime().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getDueDate().equals(handle.getDueDate())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndTime().equals(handle.getEndTime()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getDueDate();
    }
}
