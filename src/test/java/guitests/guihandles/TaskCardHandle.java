package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String LOCATION_FIELD_ID = "#locationLabel";
    private static final String STARTTIME_FIELD_ID = "#startTimeLabel";
    private static final String ENDTIME_FIELD_ID = "#endTimeLabel";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(LOCATION_FIELD_ID).replace(" at ", "");
    }

    public String getPhone() {
        return getTextFromLabel(STARTTIME_FIELD_ID).replace(" from ", "");
    }

    public String getEmail() {
        return getTextFromLabel(ENDTIME_FIELD_ID).replace(" to ", "");
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullName().equals(task.getName().fullName) && getPhone().equals(task.getStartTime().value)
                && getEmail().equals(task.getEndTime().value) && getAddress().equals(task.getDeadline().value);
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
