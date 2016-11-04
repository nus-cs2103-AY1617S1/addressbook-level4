package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.simply.model.task.ReadOnlyTask;

/**
 * @@author A0138993L
 * Provides a handle to a deadline card in the deadline list panel.
 */
public class DeadlineCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String END_FIELD_ID = "#end";
    private static final String DATE_FIELD_ID = "#date";

    private Node node;

    public DeadlineCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
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
        return getTextFromLabel(END_FIELD_ID);
    }

    public String getPhone() {
        return getTextFromLabel(DATE_FIELD_ID);
    }


    public boolean isSamePerson(ReadOnlyTask person){
        return getFullName().equals(person.getName().toString()) && getPhone().substring(5).trim().equals(person.getDate().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DeadlineCardHandle) {
            DeadlineCardHandle handle = (DeadlineCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getAddress().equals(handle.getAddress())
                    && getPhone().equals(handle.getPhone()); 
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getAddress();
    }
}
