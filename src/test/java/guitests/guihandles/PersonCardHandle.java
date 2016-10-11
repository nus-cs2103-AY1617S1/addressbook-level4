package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.menion.model.activity.ReadOnlyActivity;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String NOTE_FIELD_ID = "#note";
    private static final String DATE_FIELD_ID = "#startDate";
    private static final String TIME_FIELD_ID = "#startTime";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getNote() {
        return getTextFromLabel(NOTE_FIELD_ID);
    }

    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }

    public String getTime() {
        return getTextFromLabel(TIME_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyActivity task){
        return getFullName().equals(task.getActivityName().fullName) && getDate().equals(task.getActivityStartDate().value)
                && getTime().equals(task.getActivityStartTime().value) && getNote().equals(task.getNote().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getNote().equals(handle.getNote()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getNote();
    }
}
