package seedu.todo.guitests.guihandles;

import java.util.Optional;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.controllers.CommandDefinition;
import seedu.todo.guitests.GuiRobot;

//@@author A0139812A
public class HelpViewHandle extends GuiHandle {
    
    private static final String HELPVIEW_PLACEHOLDER_ID = "#helpCommandsPlaceholder";
    private static final String HELP_ITEM_ID = "#helpCommandItem";

    public HelpViewHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }
    
    /**
     * Checks for the existence of a child element to determine if the view has loaded correctly.
     */
    public boolean hasLoaded() {
        return guiRobot.lookup(HELPVIEW_PLACEHOLDER_ID).queryAll().size() > 0;
    }
    
    public HelpItemHandle getHelpItem(CommandDefinition commandDefinition) {
        Optional<Node> itemNode = guiRobot.lookup(HELP_ITEM_ID).queryAll().stream()
                .filter(node -> new HelpItemHandle(guiRobot, primaryStage, node).isEqualsTo(commandDefinition))
                .findFirst();
        
        if (itemNode.isPresent()) {
            return new HelpItemHandle(guiRobot, primaryStage, itemNode.get());
        } else {
            return null;
        }
    }
}
