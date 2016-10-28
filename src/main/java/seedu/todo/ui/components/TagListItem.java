package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * @@author A0139812A
 */
public class TagListItem extends MultiComponent {

    private static final String FXML_PATH = "components/TagListItem.fxml";
    private static final Color BULLET_COLOR = Color.rgb(0, 0, 0, 0.3);

    // Props
    public String tag;

    // FXML
    @FXML
    private Text labelText;
    @FXML
    private Circle labelBullet;

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        labelText.setText(tag);
        labelBullet.setFill(BULLET_COLOR);
    }

}
