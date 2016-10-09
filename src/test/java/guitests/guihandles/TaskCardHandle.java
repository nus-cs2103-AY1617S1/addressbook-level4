package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskman.model.event.Activity;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String ReadOnlyTask_FIELD_ID = "#readOnlyTask";
    private static final String SCHEDULE_FIELD_ID = "#schedule";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullTitle() {
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
    }

    public String getStatus() {
        return getTextFromLabel(STATUS_FIELD_ID);
    }

    public String getReadOnlyTask() {
        return getTextFromLabel(ReadOnlyTask_FIELD_ID);
    }
    
    public String getSchedule() {
        return getTextFromLabel(SCHEDULE_FIELD_ID);
    }
    
    public boolean isSameTask(Activity task){
        return getFullTitle().equals(task.getTitle().title)
        		&& getDeadline().equals(task.getDeadline().toString())
                && getStatus().equals(task.getStatus().toString())
                && getReadOnlyTask().equals(task.getFrequency().toString())
                && getSchedule().equals(task.getSchedule().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullTitle().equals(handle.getFullTitle())
                    && true; // TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullTitle() + " " + getSchedule();
    }
}
