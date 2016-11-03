package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.savvytasker.model.task.ReadOnlyTask;

//@@author A0139915W
/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TASKNAME_FIELD_ID = "#taskName";
    private static final String DETAILS_FIELD_ID = "#details";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromLabel(TASKNAME_FIELD_ID);
    }
    
    public String getDetails() {
        return getTextFromLabel(DETAILS_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getTaskName().equals(task.getTaskName()) && getDetails().equals(task.getTextForUi());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName()) && 
                    getDetails().equals(handle.getDetails());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName();
    }
}
//@@author
