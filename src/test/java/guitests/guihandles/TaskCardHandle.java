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
    private static final String LINE_2_ID = "#line2";
    private static final String LINE_3_ID = "#line3";

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

    public String getLine1() {
        return getTextFromLabel(LINE_1_ID);
    }

    public String getLine2() {
        return getTextFromLabel(LINE_2_ID);
    }

    public String getLine3() {
        return getTextFromLabel(LINE_3_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        TaskCardParser parser = new TaskCardParser(task);
        return getName().equals(parser.getName()) &&
                getLine1().equals(parser.getTime()) &&
                getLine2().equals(parser.getLine2()) &&
                getLine3().equals(parser.getLine3());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getLine1().equals(handle.getLine1())
                    && getLine2().equals(handle.getLine2())
                    && getLine3().equals(handle.getLine3());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getLine1() + " " + getLine2() + " " + getLine3();
    }
}
