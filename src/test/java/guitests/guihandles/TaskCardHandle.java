package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.flexitrack.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#title";
    private static final String DATETIMEINFO_DATE_ID = "#dateTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getTime() {
        return getTextFromLabel(DATETIMEINFO_DATE_ID);
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getName().equals(task.getName().toString()) && getTime().equals(getTimingShown(task));
    }

    private String getTimingShown(ReadOnlyTask task) {
        if (task.getIsEvent()) {
            return " from " + task.getStartTime().toString() + " to " + task.getEndTime().toString();
        } else if (task.getIsTask()) {
            return " by " + task.getDueDate().toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName()) && getTime().equals(handle.getTime()); // TODO:
                                                                                             // compare
                                                                                             // the
                                                                                             // rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getTime();
    }
}
