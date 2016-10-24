package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskitty.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
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
    //@@author A0130853L
    // get the CSS in line style of the specified label, returned as a string
    protected String getStyleFromLabel(String fieldId) {
    	return getStyleFromLabel(fieldId, node);
    }
    
    // get the CSS in line style of the name of the task card as a helper method for checking if its marked as done.
    public String getStyle() {
    	return getStyleFromLabel(NAME_FIELD_ID);
    }
    //@@author
    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getPhone() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyTask person){
        return getFullName().equals(person.getName().fullName);
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
