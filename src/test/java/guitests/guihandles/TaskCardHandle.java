package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todoList.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TODO_FIELD_ID = "#Todo";
    private static final String PRIORITY_FIELD_ID = "#Priority";
    private static final String STARTTIME_FIELD_ID = "#StartTime";
    private static final String ENDTIME_FIELD_ID = "#EndTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTodo() {
        return getTextFromLabel(TODO_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }

    public boolean isSametask(ReadOnlyTask task){
        return getTodo().equals(task.getTodo().todo) && getPriority().equals(task.getPriority().priority)
                && getStartTime().equals(task.getStartTime().startTime) && getEndTime().equals(task.getEndTime().endTime);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTodo().equals(handle.getTodo())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTodo() + " " + getPriority();
    }
}
