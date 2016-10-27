package seedu.todo.guitests.guihandles;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

/**
 * @@author A0139812A
 */
public class TaskListTaskItemHandle extends GuiHandle {

    private static final String TASKLISTTASKITEM_NAME_ID = "#taskText";
    private Node node;

    public TaskListTaskItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    /**
     * Gets the name of the task.
     */
    public String getName() {
        return getStringFromText(TASKLISTTASKITEM_NAME_ID, node);
    }

}
