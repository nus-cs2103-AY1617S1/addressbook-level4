package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todoList.model.task.ReadOnlyTask;


/**
 * Provides a handle to a task card in the task list panel.
 */
//@@author A0132157M
public class DeadlineCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#Name";
    private static final String STARTDATE_FIELD_ID = "#Date";
    private static final String END_FIELD_ID = "#EndTime";
    private static final String DONE_FIELD_ID = "#Done";
    
    private Node node;

    public DeadlineCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(END_FIELD_ID);
    }
    public String getDone() {
        return getTextFromLabel(DONE_FIELD_ID);
    }


    public boolean isSameDeadline(ReadOnlyTask task){
        return getName().equals(task.getName().name) 
               ;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DeadlineCardHandle) {
            DeadlineCardHandle handle = (DeadlineCardHandle) obj;
            return getName().equals(handle.getName())
                    && getStartDate().equals(handle.getStartDate())
                    && getEndTime().equals(handle.getEndTime())
                    && getDone().equals(handle.getDone()); 
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getStartDate() + " " + getEndTime() + " " + getDone();
    }
}
