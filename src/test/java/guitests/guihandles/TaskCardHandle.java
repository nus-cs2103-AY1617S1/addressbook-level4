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
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String ENDTIME_FIELD_ID = "#endTime";
    private static final String COMPLETESTATUS_FIELD_ID = "#completeStatus";

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

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }
    
    public boolean getCompleteStatus() {
        return getTextFromLabel(COMPLETESTATUS_FIELD_ID).equals("  [Completed]") ? true : false;
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getDescription().toString()) && getPriority().equals("Priority: " + task.getPriority().toString())
                && getEndTime().equals("End Time: " + task.getTimeEnd().toString()) 
                && getStartTime().equals("Start Time: " + task.getTimeStart().toString())
                && getCompleteStatus() == task.getCompleteStatus();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getStartTime().equals(handle.getStartTime()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getStartTime();
    }
}
