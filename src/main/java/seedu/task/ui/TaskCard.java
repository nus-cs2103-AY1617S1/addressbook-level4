package seedu.task.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private Label openTime;
    @FXML
    private Label closeTime;

    @FXML
    private AnchorPane tagsListPlaceholder;

    private ReadOnlyTask task;
    private int displayedIndex;
    private TagListPanel tagListPanel;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        openTime.setText("Start: "+task.getOpenTime().toPrettyString());
        closeTime.setText("End: "+task.getCloseTime().toPrettyString());
        tagListPanel = TagListPanel.load(getPrimaryStage(), tagsListPlaceholder, task.getTags().getInternalList());
        setComplete();
    }
    
    private void setComplete() {
        if (task.getComplete()) {
            cardPane.setId("cardPane-completed");
            name.setStrikethrough(true);
        }
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
