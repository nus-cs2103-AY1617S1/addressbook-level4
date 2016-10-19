package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

/**
 * Provides a handle to a Event card in the task list panel.
 */
public class EventCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";

    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyEvent task){
        return getFullName().equals(task.getName().fullName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EventCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getFullName().equals(handle.getFullName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}


