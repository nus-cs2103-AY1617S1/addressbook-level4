
package seedu.todo.guitests.guihandles;

import java.util.Optional;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.commons.core.ConfigDefinition;
import seedu.todo.guitests.GuiRobot;

// @@author A0139812A
public class ConfigViewHandle extends GuiHandle {

    private static final String CONFIGVIEW_TEXT_ID = "#configInstructionsText";
    private static final String CONFIG_ITEM_ID = "#configItem";

    public ConfigViewHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }
    
    /**
     * Checks for the existence of a child element to determine if the view has loaded correctly.
     */
    public boolean hasLoaded() {
        return guiRobot.lookup(CONFIGVIEW_TEXT_ID).queryAll().size() > 0;
    }
    
    public ConfigItemHandle getConfigItem(ConfigDefinition configDefinition) {
        Optional<Node> itemNode = guiRobot.lookup(CONFIG_ITEM_ID).queryAll().stream()
                .filter(node -> new ConfigItemHandle(guiRobot, primaryStage, node).isEqualsTo(configDefinition))
                .findFirst();
        
        if (itemNode.isPresent()) {
            return new ConfigItemHandle(guiRobot, primaryStage, itemNode.get());
        } else {
            return null;
        }
    }

}
