package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import jym.manager.model.task.ReadOnlyTask;

/**
 * Provides a handle to a Task card in the Task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String DESCRIPTION_FIELD_ID = "#desc";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String DEADLINE_FIELD_ID = "#deadline";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask Task){
        return getFullName().equals(Task.getDescription().toString()) 
        		&& getDeadline().equals(Task.getDate().toString())
                && getAddress().equals(Task.getLocation().toString());
    }

    private Object getDeadline() {
		return getTextFromLabel(DEADLINE_FIELD_ID);
	}

	@Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getAddress().equals(handle.getAddress()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getAddress();
    }
}
