package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import tars.commons.util.StringUtil;
import tars.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String STATUS_FIELD_ID = "#status";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String gettaskName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }
    
    public String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getStatus() {
        return getTextFromLabel(STATUS_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return gettaskName().equals(task.getName().taskName) && getPriority().equals(task.priorityString())
                && getStartDate().equals(task.getDateTime().startDateString) && getEndDate().equals(task.getDateTime().endDateString) 
                && getStatus().equals(task.getStatus().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return gettaskName().equals(handle.gettaskName())
                    && getStartDate().equals(handle.getStartDate())
                    && getEndDate().equals(handle.getEndDate()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return gettaskName() + StringUtil.STRING_WHITESPACE + getStartDate() + StringUtil.STRING_WHITESPACE + getEndDate();
    }
}
