package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import seedu.todo.commons.util.FxViewUtil;

public class Header extends Component {
	
	private static final String LOGO_IMAGE_PATH = "/images/logo-64x64.png";
	private static final String FXML_PATH = "components/Header.fxml";
	
	// Props
	public String versionString;
	
	// FXML
	@FXML
	private Text headerVersionText;
	@FXML
	private ImageView headerLogoImageView;

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		// Makes the Component full width wrt parent container.
		FxViewUtil.makeFullWidth(this.mainNode);
		
		// Sets version number.
		headerVersionText.setText("version " + versionString);
		
		// Sets logo image.
		Image image = new Image(LOGO_IMAGE_PATH);
		headerLogoImageView.setImage(image);
	}

}
