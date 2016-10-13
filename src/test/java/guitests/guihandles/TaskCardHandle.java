package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.task.model.item.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullTaskName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }
    
    public Boolean getTaskStatus() {
    	return false;
    }

    

    public boolean isSameTask(ReadOnlyTask task){
        return getFullTaskName().equals(task.getTask().fullName) 
//        		&& getDeadline().equals(task.getDeadline().value)
//                && getTaskStatus().equals(task.getTaskStatus().booleanValue()) //TODO: should status be checked?
                && getDescription().equals(task.getDescription().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullTaskName().equals(handle.getFullTaskName())
                    && getDescription().equals(handle.getDescription()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullTaskName() + " " + getDescription();
    }
}
