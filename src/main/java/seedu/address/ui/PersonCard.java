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
    private int displayedIndex;


    public PersonCard(){

    }

    public static PersonCard load(ReadOnlyTask task, int displayedIndex){
        PersonCard card = new PersonCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);

    }


    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        name.setWrapText(true);  //wrapping function of name 
        //name.setMaxWidth(450);
        //name.setPrefWidth(400);
        name.setMinWidth(50);
        id.setText(displayedIndex + ". ");
        id.setWrapText(true);
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
     */
    private void setStyleToIndicateOverdueTask(ReadOnlyTask task) {
        String status = task.getStatus().toString();
        if(status.equals("OVERDUE")){
            cardPane.setStyle("-fx-border-color: red");
            name.setStyle("-fx-text-fill: red");
            id.setStyle("-fx-text-fill: red");
            description.setStyle("-fx-text-fill: red");
            date.setStyle("-fx-text-fill: red");
            time.setStyle("-fx-text-fill: red");
            tags.setStyle("-fx-text-fill: red");
        }
        else if(status.equals("DONE")){
            cardPane.setStyle("-fx-border-color: blue");
            name.setStyle("-fx-text-fill: blue");
            id.setStyle("-fx-text-fill: blue");
            description.setStyle("-fx-text-fill: blue");
            date.setStyle("-fx-text-fill: blue");
            time.setStyle("-fx-text-fill: blue");
            tags.setStyle("-fx-text-fill: blue");
        }
        else if(status.equals("NONE")){
            cardPane.setStyle("-fx-border-color: aliceblue");
            name.setStyle("-fx-text-fill: black");
            id.setStyle("-fx-text-fill: black");
            description.setStyle("-fx-text-fill: #202020");
            date.setStyle("-fx-text-fill: #202020");
            time.setStyle("-fx-text-fill: #202020");
            tags.setStyle("-fx-text-fill: #202020");
        }
        else if(status.equals("EXPIRE")){
            cardPane.setStyle("-fx-border-color: magenta");
            name.setStyle("-fx-text-fill: magenta");
            id.setStyle("-fx-text-fill: magenta");
            description.setStyle("-fx-text-fill: magenta");
            date.setStyle("-fx-text-fill: magenta");
            time.setStyle("-fx-text-fill: magenta");
            tags.setStyle("-fx-text-fill: magenta");
        }

    }   
}