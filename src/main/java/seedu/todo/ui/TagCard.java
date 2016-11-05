//@@author A0142421X
package seedu.todo.ui;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.todo.model.tag.Tag;

public class TagCard extends UiPart{
	private static final String FXML = "TagCard.fxml";
	
    @FXML
    private HBox cardPane;
    
    @FXML
    private Text tags;
    
    @FXML
    private Text tasksCount;
    
    private Tag tag;
    private static int displayedIndex;
    
    public TagCard() {}
    
    public static TagCard load(Tag tag){
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
        Color[] colors = {Color.MEDIUMPURPLE, Color.BEIGE, Color.PALEGREEN, Color.BLANCHEDALMOND, Color.HONEYDEW, Color.TOMATO};
        
        Random rand = new Random();
        int n = rand.nextInt(colors.length);
        return colors[n];
        
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