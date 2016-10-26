package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todoList.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#Name";
    private static final String DATE_FIELD_ID = "#Date";
    private static final String PRIORITY_FIELD_ID = "#Priority";
    //private static final String ENDTIME_FIELD_ID = "#EndTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }


    public boolean isSametask(ReadOnlyTask task){
        return getName().equals(task.getName().name);// && getDate().equals(task.getDate().date)
                //&& getPriority().equals(task.getPriority().priority);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getDate().equals(handle.getDate())
                    && getPriority().equals(handle.getPriority());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + " " + getDate() + " " + getPriority();
    }
}
