package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.menion.model.activity.ReadOnlyActivity;

/**
 * Provides a handle to a activity card in the activity list panel.
 */
public class FloatingTaskCardHandle extends GuiHandle {
    
    private static final String NAME_FIELD_ID = "#name";
    private static final String NOTE_FIELD_ID = "#note";

    private Node node;

    public FloatingTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getActivityName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getNote() {
        return getTextFromLabel(NOTE_FIELD_ID);
    }

    public boolean isSameActivity(ReadOnlyActivity activity){
        return getActivityName().equals(activity.getActivityName().fullName) && getNote().equals(activity.getNote().value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getActivityName().equals(handle.getActivityName())
                    && getNote().equals(handle.getNote()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getActivityName() + " " + getNote();
    }
}
