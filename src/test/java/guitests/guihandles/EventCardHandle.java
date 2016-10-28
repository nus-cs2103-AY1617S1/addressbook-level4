package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todoList.model.task.ReadOnlyTask;


/**
 * Provides a handle to a task card in the task list panel.
 */
//@@author A0132157M
public class EventCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#Name";
    private static final String DATE_FIELD_ID = "#Nate";
    private static final String ENDDATE_FIELD_ID = "#EndDate";
    private static final String ST_FIELD_ID = "#StartTime";
    private static final String ENDTIME_FIELD_ID = "#EndTime";
    private static final String DONE_FIELD_ID = "#Done";
    
    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(ST_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }
    public String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }
    public String getDone() {
        return getTextFromLabel(DONE_FIELD_ID);
    }

    public boolean isSameEvent(ReadOnlyTask task){
        return getName().equals(task.getName().name);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getName().equals(handle.getName())
                    && getStartDate().equals(handle.getStartDate())
                    && getEndDate().equals(handle.getEndDate())
                    && getStartTime().equals(handle.getStartTime()) 
                    && getEndTime().equals(handle.getEndTime())
                    && getDone().equals(handle.getDone());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getStartDate() + " " + getEndDate() + " " + getStartTime() + " " + getEndTime() + " " + getDone();
    }
}
