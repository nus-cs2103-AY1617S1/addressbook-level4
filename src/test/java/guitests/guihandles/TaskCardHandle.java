package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.Todo;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#startDate";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String DONE_FIELD_ID = "#isDone";


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
    public String getEndDate() {
        return getTextFromLabel(ENDDATE_FIELD_ID);
    }
    public String getDone() {
        return getTextFromLabel(DONE_FIELD_ID);
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
                    && getEndDate().equals(handle.getEndDate())
                    && getPriority().equals(handle.getPriority())
                    && getDone().equals(handle.getDone());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName();// + " " + " " + getDate() + " " + getPriority();
    }
}
