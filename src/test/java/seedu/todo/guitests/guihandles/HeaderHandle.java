package seedu.todo.guitests.guihandles;

import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

public class HeaderHandle extends GuiHandle {
    
    private static final String HEADER_APPTITLE_TEXT_ID = "#headerAppTitle";
    
    public HeaderHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }

    public String getAppTitle() {
        return getStringFromText(HEADER_APPTITLE_TEXT_ID);
    }
    
}
