package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

//@@author A0142421X
/**
 * Provides a handle to exit the app.
 */
public class ExitHandle extends GuiHandle {
	
	private static final String EXIT_TITLE = "Exit";
	private static final String EXIT_ROOT_FIELD_ID = "#exitRoot";
	
	public ExitHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, EXIT_TITLE);
    }

}
