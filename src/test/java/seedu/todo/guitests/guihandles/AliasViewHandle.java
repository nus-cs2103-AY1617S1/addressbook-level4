package seedu.todo.guitests.guihandles;

import java.util.Optional;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

// @@author A0139812A
public class AliasViewHandle extends GuiHandle {

    private static final String ALIASVIEW_TEXT_ID = "#aliasInstructionsText";
    private static final String ALIAS_ITEM_ID = "#aliasItem";

    public AliasViewHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }
    
    /**
     * Checks for the existence of a child element to determine if the view has loaded correctly.
     */
    public boolean hasLoaded() {
        return guiRobot.lookup(ALIASVIEW_TEXT_ID).queryAll().size() > 0;
    }
    
    public AliasItemHandle getAliasItem(String aliasKey, String aliasValue) {
        Optional<Node> itemNode = guiRobot.lookup(ALIAS_ITEM_ID).queryAll().stream()
                .filter(node -> new AliasItemHandle(guiRobot, primaryStage, node).isEqualsTo(aliasKey, aliasValue))
                .findFirst();
        
        if (itemNode.isPresent()) {
            return new AliasItemHandle(guiRobot, primaryStage, itemNode.get());
        } else {
            return null;
        }
    }

}
