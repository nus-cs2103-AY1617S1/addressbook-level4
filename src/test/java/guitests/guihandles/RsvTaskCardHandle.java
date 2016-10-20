package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import tars.model.task.rsv.RsvTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class RsvTaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";

    private Node node;

    public RsvTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getRsvTaskName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public boolean isSameRsvTask(RsvTask rsvTask) {
        return getRsvTaskName().equals(rsvTask.getName().taskName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RsvTaskCardHandle) {
            RsvTaskCardHandle handle = (RsvTaskCardHandle) obj;
            return getRsvTaskName().equals(handle.getRsvTaskName()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getRsvTaskName();
    }
}
