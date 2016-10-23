package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
//    @FXML
//    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label done;
    @FXML
    private Label recurring;
    @FXML
    private Label frequency;
//    @FXML
//    private Label tags;
//    @FXML
//    private Label headerTitle;
//    @FXML
//    private Label headerDeadline;

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
//    	headerTitle.setText("Title");
//    	headerDeadline.setText("Deadline");
        name.setText(task.getName().taskName);
//        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().getValue());
        // Temporary design
        done.setText(task.isDone() ? "done" : "");
//        tags.setText(task.tagsString());
        recurring.setText(task.isRecurring()? "recurring":"");
        frequency.setText("not reflected yet");
        
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
