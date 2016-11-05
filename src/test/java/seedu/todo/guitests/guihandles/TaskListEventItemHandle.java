package seedu.todo.guitests.guihandles;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;
import seedu.todo.models.Event;

/**
 * @@author A0139812A
 */
public class TaskListEventItemHandle extends GuiHandle {

    private static final String TASKLISTEVENTITEM_NAME_ID = "#eventText";
    private Node node;

    public TaskListEventItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    /**
     * Gets the name of the event.
     */
    public String getName() {
        return getStringFromText(TASKLISTEVENTITEM_NAME_ID, node);
    }
    
    /**
     * Checks if this handle is referring to an event of the same data.
     * 
     * @param eventToCompare    Event to compare.
     * @return                  True if they are equal.
     */
    public boolean isEqualsToEvent(Event eventToCompare) {
        if (eventToCompare == null) {
            return false;
        }
        
        return getName().equals(eventToCompare.getName());
    }
    
}
