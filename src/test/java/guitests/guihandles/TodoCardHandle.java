package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * @@author A0138993L
 * Provides a handle to a todo card in the todo list panel.
 */
public class TodoCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";

    private Node node;

    public TodoCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyTask person){
        return getFullName().equals(person.getName().toString()); 
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TodoCardHandle) {
            TodoCardHandle handle = (TodoCardHandle) obj;
            return getFullName().equals(handle.getFullName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " ";
    }
}
