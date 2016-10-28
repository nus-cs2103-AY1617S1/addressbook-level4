package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * @@author A0139812A
 */
public class SidebarCounter extends MultiComponent {

    private static final String FXML_PATH = "components/SidebarCounter.fxml";

    // Props
    public String iconPath;
    public String label;

    // FXML
    @FXML
    private ImageView imageView;
    @FXML
    private Text labelText;

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        imageView.setImage(new Image(iconPath));
        labelText.setText(label);
    }

}
