package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#name";
    private static final String DUEDATE_FIELD_ID = "#dueDate";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String DESCRIPTION_FIELD_ID = "#description";

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

    public String getDueDate() {
        return getTextFromLabel(DUEDATE_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(STARTDATE_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getTitle().equals(task.getTitle().fullTitle)
                && getDescription().equals(task.getDescription().description)
                && getStartDate().equals("Start:  " + task.getStartDate().toString().replaceAll(" ", "    Time:  "))
                && getDueDate().equals("End:    " + task.getDueDate().toString().replaceAll(" ", "    Time:  "));
    }

    public boolean isMarkedTask(ReadOnlyTask task) {
        return node.getStyle().equals("-fx-background-color: yellow;")
                && task.isCompleted()
                && getTitle().equals(task.getTitle().fullTitle)
                && getDescription().equals(task.getDescription().description)
                && getStartDate().equals("Start:  " + task.getStartDate().toString().replaceAll(" ", "    Time:  "))
                && getDueDate().equals("End:    " + task.getDueDate().toString().replaceAll(" ", "    Time:  "));
    }
    
    public boolean isUnmarkedTask(ReadOnlyTask task) {
        return node.getStyle().equals("-fx-background-color: white;")
                && !task.isCompleted()
                && getTitle().equals(task.getTitle().fullTitle)
                && getDescription().equals(task.getDescription().description)
                && getStartDate().equals("Start:  " + task.getStartDate().toString().replaceAll(" ", "    Time:  "))
                && getDueDate().equals("End:    " + task.getDueDate().toString().replaceAll(" ", "    Time:  "));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTitle().equals(handle.getTitle()) && getDueDate().equals(handle.getDueDate())
                    && getStartDate().equals(handle.getStartDate()) && getDescription().equals(handle.getDescription());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTitle() + " " + getDueDate();
    }
}
