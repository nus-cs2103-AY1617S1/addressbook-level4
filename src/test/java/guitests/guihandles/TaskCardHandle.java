package guitests.guihandles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.agendum.model.task.ReadOnlyTask;

//@@author A0148031R
/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String INDEX_FIELD_ID = "#id";
    private static final String TIME_FIELD_ID = "#time";

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

    public String getTaskIndex() {
        return getTextFromLabel(INDEX_FIELD_ID);
    }

    public String getTime() {
        return getTextFromLabel(TIME_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        // the completion status will be checked by which panel it belongs in
        return getName().equals(task.getName().fullName)
            && getTime().equals(formatTime(task));
    }
    

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                && getTaskIndex().equals(handle.getTaskIndex())
                && getTime().equals(handle.getTime());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskIndex() + " " + getName() + "Time: " + getTime();
    }

    private String formatTime(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        Optional<LocalDateTime> start = task.getStartDateTime();
        Optional<LocalDateTime> end = task.getEndDateTime();

        DateTimeFormatter startFormat = DateTimeFormatter.ofPattern("HH:mm EEE, dd MMM");

        if (start.isPresent()) {
            sb.append("from ").append(start.get().format(startFormat));
        }
        if (end.isPresent()) {
            sb.append(sb.length() > 0 ? " to " : "by ");
            sb.append(end.get().format(startFormat));
        }

        return sb.toString().replace("AM", "am").replace("PM", "pm");
    }
}
