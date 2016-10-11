package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.inbx0.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String STARTDATE_FIELD_ID = "#startdate";
    private static final String STARTTIME_FIELD_ID = "#startime";
    private static final String ENDDATE_FIELD_ID = "#enddate";
    private static final String ENDTIME_FIELD_ID = "#endtime";
    private static final String IMPORTANCE_FIELD_ID = "#importance";

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

    public String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }
    
    public String getLevel() {
        return getTextFromLabel(IMPORTANCE_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getName().fullName) && getStartDate().equals(task.getStartDate().value)
                && getStartTime().equals(task.getStartTime().value) && getEndDate().equals(task.getEndDate().value) 
                && getEndTime().equals(task.getEndTime().value) && getLevel().equals(task.getLevel().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getStartDate().equals(handle.getStartDate())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndDate().equals(handle.getEndDate())
                    && getEndTime().equals(handle.getEndTime())
                    && getLevel().equals(handle.getLevel());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getStartDate();
    }
}
