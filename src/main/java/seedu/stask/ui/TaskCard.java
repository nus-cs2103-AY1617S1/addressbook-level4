package seedu.stask.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.stask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private String indexAlphabet;
    private int displayedIndex;


    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex, String indexAlphabet){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        card.indexAlphabet = indexAlphabet;
        return UiPartLoader.loadUiPart(card);

    }


    //@@author A0139024M
    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        name.setWrapText(true);  //wrapping function of name 
        name.setMinWidth(50);
        id.setWrapText(true);
        id.setText(indexAlphabet + displayedIndex + ". ");
        description.setText(task.getDescription().value);
        description.setWrapText(true);
        date.setText(task.getDatetime().getDateString());
        date.setWrapText(true);
        time.setText(task.getDatetime().getTimeString());
        time.setWrapText(true);
        tags.setText(task.tagsString());
        tags.setWrapText(true);
        setStyleBaseOnStatus(task);
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

    /**
     * Sets the command box style to indicate an overdue,completed or no status tasks.
     * @param task
     */
    private void setStyleBaseOnStatus(ReadOnlyTask task) {
        setDefaultStyle();
        setBorderColourBaseOnStatus(task.getStatus().toString());
    }
    
    /**
     * Set the border of the Task card to indicate status of Task in UI
     * @param status
     */
    private void setBorderColourBaseOnStatus(String status) {
        switch (status) {
            case "OVERDUE": 
                cardPane.setStyle("-fx-border-color: #202020 #202020 red red");
            break;
            case "DONE": 
                cardPane.setStyle("-fx-border-color: #202020 #202020 blue blue");
            break;
            case "EXPIRE" : 
                cardPane.setStyle("-fx-border-color: #202020 #202020 magenta magenta");
            break;
            default: 
                cardPane.setStyle("-fx-border-color: #202020 #202020 #F0F0F0 #F0F0F0");
            break;
        }
    }

    private void setDefaultStyle() {
        name.setStyle("-fx-text-fill: #888888");
        id.setStyle("-fx-text-fill: #888888");
        description.setStyle("-fx-text-fill: #888888");
        date.setStyle("-fx-text-fill: #888888");
        time.setStyle("-fx-text-fill: #888888");
        tags.setStyle("-fx-text-fill: #00CC00");
    }
}