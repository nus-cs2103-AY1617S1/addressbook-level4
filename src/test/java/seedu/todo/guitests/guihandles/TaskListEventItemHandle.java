package seedu.todo.guitests.guihandles;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

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
    
}
