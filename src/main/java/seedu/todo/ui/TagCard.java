package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.todo.model.tag.Tag;

//@@author A0142421X
/**
 * Tag Card controller for Tag Panel in GUI
 */
public class TagCard extends UiPart {
    
    private static final String FXML = "TagCard.fxml";

    private final Color[] colors = {Color.web("#ef9a9a"), Color.web("#ffe082"), 
            Color.web("#fff59d"), Color.web("#c5e1a5"), Color.web("#81d4fa"), 
            Color.web("#b39ddb"), Color.web("#b39ddb")};
    
    @FXML
    private HBox cardPane;
    
    @FXML
    private Text tags;
    
    @FXML
    private Text tasksCount;
    
    private Tag tag;
    private static int displayedIndex;
    
    public static TagCard load(Tag tag, int displayedIndex){
        TagCard card = new TagCard();
        card.tag = tag;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
    public void initialize() {
        tags.setText(tag.getName());
        tasksCount.setText(tag.getCount() + "");
        Color color = randomiseCardPaneColor();
        cardPane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public HBox getLayout() {
        return cardPane;
    }
    
    private Color randomiseCardPaneColor() {
        return colors[(displayedIndex - 1) % colors.length];
        
    }
    
    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
