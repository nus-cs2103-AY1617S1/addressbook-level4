package seedu.cmdo.ui;

import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import seedu.cmdo.commons.core.LogsCenter;
import java.util.logging.Logger;

/**
 * To implement welcome screen
 * (in progress)
 * 
 * @@author A0141006B
 */

public class WelcomeMessage extends UiPart {
	private static final Logger logger = LogsCenter.getLogger(WelcomeMessage.class);
	private static final String FXML = "CMDoWelcome.fxml";
	private static final String ICON = "/images/Welcome.png";
	
	
	@FXML
	private ImageView img;
	
	@FXML
	private AnchorPane welcomePane;

	@Override
	public void setNode(Node node) {
		welcomePane = (AnchorPane) node;		
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}
	
	public AnchorPane getWelcomePane(){
		logger.info("Displaying welcome message...");
		welcomePane = new AnchorPane();
		ImageView img = new ImageView(ICON);
		img.setFitHeight(100);
		img.setFitWidth(100);
		welcomePane.getChildren().add(img);
		return welcomePane;
	}
}
