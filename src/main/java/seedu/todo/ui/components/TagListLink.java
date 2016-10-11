package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.ui.UiPartLoader;

public class TagListLink extends MultiComponent {

	private static final String FXML_PATH = "components/TagListLink.fxml";
	
	// Props
	public String iconPath;
	public String linkLabel;
	
	// FXML
	@FXML
	private ImageView imageView;
	@FXML
	private Text labelText;
	
	public static TagListLink load(Stage primaryStage, Pane placeholderPane) {
	    return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new TagListLink());
	}
	
	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		imageView.setImage(new Image(iconPath));
		labelText.setText(linkLabel);
	}

}
