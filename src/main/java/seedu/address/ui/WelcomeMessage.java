package seedu.address.ui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class WelcomeMessage extends UiPart {
	private static final String FXML = "CMDoWelcome.fxml";
	private AnchorPane welcomePane;
	
	@Override
	public void setNode(Node node) {
		welcomePane = (AnchorPane) node;		
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}

	public static WelcomeMessage load(AnchorPane welcomeMessagePlaceholder) {
		
		return null;
	}

	
}
