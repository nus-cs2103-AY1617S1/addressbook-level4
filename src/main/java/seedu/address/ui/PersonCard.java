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
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().value);
        date.setText(task.getDatetime().getDateString());
        time.setText(task.getDatetime().getTimeString());
        tags.setText(task.tagsString());
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
            name.setStyle("-fx-text-fill: red");
            id.setStyle("-fx-text-fill: red");
            description.setStyle("red");
            date.setStyle("-fx-text-fill: red");
            time.setStyle("-fx-text-fill: red");
            tags.setStyle("-fx-text-fill: red");
        }
        else if(status.equals("DONE")){
            name.setStyle("-fx-text-fill: blue");
            id.setStyle("-fx-text-fill: blue");
            description.setStyle("blue");
            date.setStyle("-fx-text-fill: blue");
            time.setStyle("-fx-text-fill: blue");
            tags.setStyle("-fx-text-fill: blue");
        }
        else if(status.equals("NONE")){
        	name.setStyle("-fx-text-fill: black");
        	id.setStyle("-fx-text-fill: black");
        	description.setStyle("-fx-text-fill: black");
        	date.setStyle("-fx-text-fill: black");
        	time.setStyle("-fx-text-fill: black");
        	tags.setStyle("-fx-text-fill: black");
        }
        else if(status.equals("EXPIRE")){
            name.setStyle("-fx-text-fill: deeppink");
            id.setStyle("-fx-text-fill: deeppink");
            description.setStyle("deeppink");
            date.setStyle("-fx-text-fill: deeppink");
            time.setStyle("-fx-text-fill: deeppink");
            tags.setStyle("-fx-text-fill: deeppink");
        }
        
    }   
}