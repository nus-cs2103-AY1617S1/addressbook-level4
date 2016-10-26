package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todoList.model.task.ReadOnlyTask;


/**
 * Provides a handle to a task card in the task list panel.
 */
public class DeadlineCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#Name";
    private static final String DATE_FIELD_ID = "#Date";
    private static final String ET_FIELD_ID = "#EndTime";
    
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

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(ET_FIELD_ID);
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
                    && getDate().equals(handle.getDate())
                    && getEndTime().equals(handle.getEndTime()); 
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getDate() + " " + getEndTime();
    }
}
