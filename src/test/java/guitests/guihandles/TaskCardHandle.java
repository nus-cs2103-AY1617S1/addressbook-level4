package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskman.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String EMAIL_FIELD_ID = "#email";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getTitle().title) && getDeadline().equals(task.getDeadline().value)
                && getEmail().equals(task.getEmail().value) && getAddress().equals(task.getAddress().value);
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
