package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class PersonCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";

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


    public PersonCard(){

    }

    public static PersonCard load(ReadOnlyTask task, int displayedIndex, String indexAlphabet){
        PersonCard card = new PersonCard();
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
        setStyleToIndicateOverdueTask(task);
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

    //@@author
    /**
     * Sets the command box style to indicate an overdue,completed or no status tasks.
     * @param task
     */
    private void setStyleToIndicateOverdueTask(ReadOnlyTask task) {
        String status = task.getStatus().toString();
        if(status.equals("OVERDUE")){
            cardPane.setStyle("-fx-border-color: #202020 #202020 red red");
            name.setStyle("-fx-text-fill: #888888");
            id.setStyle("-fx-text-fill: #888888");
            description.setStyle("-fx-text-fill: #888888");
            date.setStyle("-fx-text-fill: #888888");
            time.setStyle("-fx-text-fill: #888888");
            tags.setStyle("-fx-text-fill: ##00CC0");
        }
        else if(status.equals("DONE")){
            cardPane.setStyle("-fx-border-color: #202020 #202020 blue blue");
            name.setStyle("-fx-text-fill: #888888");
            id.setStyle("-fx-text-fill: #888888");
            description.setStyle("-fx-text-fill: #888888");
            date.setStyle("-fx-text-fill: #888888");
            time.setStyle("-fx-text-fill: #888888");
            tags.setStyle("-fx-text-fill: ##00CC0");
        }
        else if(status.equals("NONE")){
            cardPane.setStyle("-fx-border-color: #202020 #202020 #F0F0F0 #F0F0F0");
            name.setStyle("-fx-text-fill: #888888");
            id.setStyle("-fx-text-fill: #888888");
            description.setStyle("-fx-text-fill: #888888");
            date.setStyle("-fx-text-fill: #888888");
            time.setStyle("-fx-text-fill: #888888");
            tags.setStyle("-fx-text-fill: ##00CC0");
        }
        else if(status.equals("EXPIRE")){
            cardPane.setStyle("-fx-border-color: #202020 #202020 magenta magenta");
            name.setStyle("-fx-text-fill: #888888");
            id.setStyle("-fx-text-fill: #888888");
            description.setStyle("-fx-text-fill: #888888");
            date.setStyle("-fx-text-fill: #888888");
            time.setStyle("-fx-text-fill: #888888");
            tags.setStyle("-fx-text-fill: ##00CC0");
        }

    }
    //@@author
}