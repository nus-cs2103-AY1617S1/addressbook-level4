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
    private static final String TIME_ID = "#time";
    private static final String TAG_ID = "#tag";

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
        return getTextFromLabel(TIME_ID);
    }

    public String getTag() {
        return getTextFromLabel(TAG_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        TaskCardParser parser = new TaskCardParser(task);
        return getName().equals(parser.getName()) &&
                getTaskTime().equals(parser.getTime()) &&
                getTag().equals(parser.getTag());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getTaskTime().equals(handle.getTaskTime())
                    && getTag().equals(handle.getTag());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getTaskTime() + " " + getTag();
    }
}
