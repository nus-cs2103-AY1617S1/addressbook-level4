package seedu.cmdo.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.cmdo.commons.core.LogsCenter;
import java.util.logging.Logger;

public class WelcomeMessage extends UiPart {
	private static final Logger logger = LogsCenter.getLogger(WelcomeMessage.class);
	private static final String FXML = "CMDoWelcome.fxml";
	private static final String ICON = "/images/Logo.png";
	private static final String TITLE = "Welcome";
	
	private Stage welcomeStage;
	
	private static AnchorPane welcomePane;
	
	@Override
	public void setNode(Node node) {
		welcomePane = (AnchorPane) node;		
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}

	public static WelcomeMessage load(Stage welcomeMessageStage) {
		logger.fine("Showing welcome page.");
        WelcomeMessage welcomeMessage = UiPartLoader.loadUiPart(welcomeMessageStage, welcomePane, new WelcomeMessage());
        welcomeMessage.configure();
        return welcomeMessage;
	}
	
	private void configure(){
        Scene scene = new Scene(welcomePane);
        //Null passed as the parent stage to make it non-modal.
        welcomeStage = createDialogStage(TITLE, null, scene);
        welcomeStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(welcomeStage, ICON);
    }

	public void show() {
		welcomeStage.showAndWait();
	}	
}
