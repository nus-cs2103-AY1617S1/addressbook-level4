package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String STARTDATETIME_FIELD_ID = "#startDateTime";
    private static final String ENDDATETIME_FIELD_ID = "#endDateTime";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTitle() {
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getEndDateTime() {
        return getTextFromLabel(ENDDATETIME_FIELD_ID);
    }

    public String getStartDateTime() {
        return getTextFromLabel(STARTDATETIME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getTitle().equals(task.getTitle().fullTitle)
                && getDescription().equals(task.getDescription().description)
                && getStartDateTime().equals("Start:  " + task.getStartDateTime().toString().replaceAll(" ", "    Time:  "))
                && getEndDateTime().equals("End:    " + task.getEndDateTime().toString().replaceAll(" ", "    Time:  "));
    }

    public boolean isMarkedTask(ReadOnlyTask task) {
        return node.getStyle().equals("-fx-background-color: #98FB98;")
                && task.isCompleted()
                && getTitle().equals(task.getTitle().fullTitle)
                && getDescription().equals(task.getDescription().description)
                && getStartDateTime().equals("Start:  " + task.getStartDateTime().toString().replaceAll(" ", "    Time:  "))
                && getEndDateTime().equals("End:    " + task.getEndDateTime().toString().replaceAll(" ", "    Time:  "));
    }
    
    public boolean isUnmarkedTask(ReadOnlyTask task) {
        return node.getStyle().equals("-fx-background-color: white;")
                && !task.isCompleted()
                && getTitle().equals(task.getTitle().fullTitle)
                && getDescription().equals(task.getDescription().description)
                && getStartDateTime().equals("Start:  " + task.getStartDateTime().toString().replaceAll(" ", "    Time:  "))
                && getEndDateTime().equals("End:    " + task.getEndDateTime().toString().replaceAll(" ", "    Time:  "));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTitle().equals(handle.getTitle()) && getEndDateTime().equals(handle.getEndDateTime())
                    && getStartDateTime().equals(handle.getStartDateTime()) && getDescription().equals(handle.getDescription());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTitle() + " " + getEndDateTime();
    }
}
