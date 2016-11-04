# A0141006Bunused
###### /java/seedu/cmdo/ui/WelcomeMessage.java
``` java
/**
 * To implement welcome screen
 * (in progress)
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
```
