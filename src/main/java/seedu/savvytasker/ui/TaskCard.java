package seedu.savvytasker.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.savvytasker.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";
    private static final String EMPTY_FIELD = " - ";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label description;
    @FXML
    private Label tags;

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

    @FXML
    public void initialize() {
        taskName.setText(task.getTaskName());
        Date startDate = task.getStartDateTime();
        if (startDate != null) {
            this.startDate.setText(startDate.toString());
        } else {
            this.startDate.setText(EMPTY_FIELD);
        }
        Date endDate = task.getEndDateTime();
        if (endDate != null) {
            this.endDate.setText(endDate.toString());
        } else {
            this.endDate.setText(EMPTY_FIELD);
        }
        String description = task.getDescription();
        if (description != null) {
            this.description.setText(description);
        } else {
            this.description.setText(EMPTY_FIELD);
        }
        id.setText(displayedIndex + ". ");
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
