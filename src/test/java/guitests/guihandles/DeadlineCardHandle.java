package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.malitio.model.task.ReadOnlyDeadline;

/**
 * Provides a handle to a deadline card in the task list panel.
 */
public class DeadlineCardHandle extends GuiHandle {

    private static final String NAME_FIELD_ID = "#name";
    private static final String DUE_FIELD_ID = "#due";

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
    
    public String getDue() {
        return getTextFromLabel(DUE_FIELD_ID);
    }

    public boolean isSameDeadline(ReadOnlyDeadline deadline) {
        return getFullName().equals(deadline.getName().fullName) && getDue().substring(5).equals(deadline.getDue().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DeadlineCardHandle) {
            DeadlineCardHandle handle = (DeadlineCardHandle) obj;
            return getFullName().equals(handle.getFullName()) && getDue().equals(handle.getDue());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}

