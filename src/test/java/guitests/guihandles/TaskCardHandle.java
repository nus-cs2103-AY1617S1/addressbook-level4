package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getDetail() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getDueByDate() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }

    public String getDueByTime() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getDetail().equals(task.getDetail()) && getDueByDate().equals(task.getDueByDate().value)
                && getDueByTime().equals(task.getDueByTime().value) && getPriority().equals(task.getPriority().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getDetail().equals(handle.getDetail())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDetail() + " " + getPriority();
    }
}
