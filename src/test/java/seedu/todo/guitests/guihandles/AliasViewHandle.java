package seedu.todo.guitests.guihandles;

import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

// @@author A0139812A
public class AliasViewHandle extends GuiHandle {

    private static final String ALIASVIEW_TEXT_ID = "#aliasInstructionsText";

    public AliasViewHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }
    
    /**
     * Checks for the existence of a child element to determine if the view has loaded correctly.
     */
    public boolean hasLoaded() {
        return guiRobot.lookup(ALIASVIEW_TEXT_ID).queryAll().size() > 0;
    }

}
