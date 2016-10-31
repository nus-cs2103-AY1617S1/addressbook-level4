package seedu.task.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.task.model.item.ReadOnlyTask;
import com.guigarage.flatterfx.emoji.EmojiFactory;


public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label index;
    @FXML
    private Label description;
    @FXML
    private Label deadline;
    

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    //@@author-A0127570H
    @FXML
    public void initialize() {
        name.setText(task.getNameWithStatus());
        index.setText(displayedIndex + ". ");
        initialiseDescription();
        initialiseDeadline();   
        setCompletionBackgroundText();
    }

    private void initialiseDeadline() {
        deadline.setText(task.getDeadlineToString());
        if (task.getDeadline().isPresent()) {
            deadline.setManaged(true);
        } else {
            deadline.setManaged(false);
        }
    }

    private void initialiseDescription() {
        description.setText(task.getDescriptionToString().trim());
        if (task.getDescription().isPresent()) {
            description.setManaged(true);
        } else {
            description.setManaged(false);
        }
    }

    //Adds the lavender colour to the background if the task status is completed
    private void setCompletionBackgroundText() {
        if (task.getTaskStatus()) {
            cardPane.getStyleClass().add("status-complete");
        }
    }
    //@@author

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
