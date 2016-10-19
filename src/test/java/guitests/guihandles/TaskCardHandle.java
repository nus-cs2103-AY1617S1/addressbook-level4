package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.item.ReadOnlyTask;

/**
 * Provides a handle to a floating task card in the item list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRIORITY_FIELD_ID = "#priority";


    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    
    protected String getTextFromPriorityRectangle(String fieldId){
        return getTextFromPriorityRectangle(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPriority() {
        // Priority is no longer a Label
        // return getTextFromLabel(PRIORITY_FIELD_ID);
        
        
        return getTextFromPriorityRectangle(PRIORITY_FIELD_ID);
        
    }


    public boolean isSameFloatingTask(ReadOnlyTask task){
        return getName().equals(task.getName().name) && getPriority().equals(task.getPriorityValue().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getPriority();
    }
}
