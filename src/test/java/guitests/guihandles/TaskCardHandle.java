package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.ui.TaskCardParser;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String LINE_1_ID = "#line1";
    private static final String RECURRENCE_ID = "#recurrence";

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

    public String getTaskTime() {
        return getTextFromLabel(LINE_1_ID);
    }

    public String getRecurrence() {
        return getTextFromLabel(RECURRENCE_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        TaskCardParser parser = new TaskCardParser(task);
        return getName().equals(parser.getName()) &&
                getTaskTime().equals(parser.getTime()) &&
                getRecurrence().equals(parser.getRecurrence());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getTaskTime().equals(handle.getTaskTime())
                    && getRecurrence().equals(handle.getRecurrence());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getTaskTime() + " " + getRecurrence();
    }
}
