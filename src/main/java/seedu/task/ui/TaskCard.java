package seedu.task.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    
    @FXML
    private Text name;
    
    @FXML
    private Label id;
    
    @FXML
    private SVGPath star;
    
    @FXML
    private Label openTime;
    
    @FXML
    private Label closeTime;

    @FXML
    private AnchorPane tagsListPlaceholder;

    private ReadOnlyTask task;
    private int displayedIndex;
    private boolean isSelected;
    private TagListPanel tagListPanel;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex, boolean isSelected){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        card.isSelected = isSelected;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        setCardDetails();
        setVisualFlags();
        showExtendedInformation();
    }
    
    private void setCardDetails() {
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        
        openTime.setText("Start: "+task.getOpenTime().toPrettyString());
        closeTime.setText("End: "+task.getCloseTime().toPrettyString());
        
        tagListPanel = TagListPanel.load(getPrimaryStage(), tagsListPlaceholder, task.getTags().getInternalList());
    }
    
    private void setVisualFlags() {
        if (!task.getImportance()) {
            star.setOpacity(0.0);
        }
        
        if (task.getComplete()) {
            cardPane.setId("cardPane-completed");
            name.setStrikethrough(true);
        }
    }
    
    private void showExtendedInformation() {
        openTime.managedProperty().bind(openTime.visibleProperty());
        closeTime.managedProperty().bind(closeTime.visibleProperty());
        openTime.setVisible(isSelected);
        closeTime.setVisible(isSelected);
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) 
    {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
