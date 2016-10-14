package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.cmdo.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#detail";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DATE_FIELD_ID = "#dbd";
    private static final String TIME_FIELD_ID = "#dbt";

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
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getDueByDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }

    public String getDueByTime() {
        return getTextFromLabel(TIME_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getDetail().equals(task.getDetail().details) && getDueByDate().equals(task.getDueByDate().getFriendlyString())
                && getDueByTime().equals(task.getDueByTime().getFriendlyString()) && getPriority().equals(task.getPriority().value.toString());
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
