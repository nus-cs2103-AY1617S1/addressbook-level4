package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.item.ReadOnlyItem;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class ItemCardHandle extends GuiHandle {
    private static final String DESC_FIELD_ID = "#description";

    private Node node;

    public ItemCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullDescription() {
        return getTextFromLabel(DESC_FIELD_ID);
    }

    public boolean isSameItem(ReadOnlyItem item){
        return getFullDescription().equals(item.getDescription().getFullDescription());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ItemCardHandle) {
            ItemCardHandle handle = (ItemCardHandle) obj;
            return getFullDescription().equals(handle.getFullDescription());
            //TODO: update with more things to compare
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullDescription();
    }
}
