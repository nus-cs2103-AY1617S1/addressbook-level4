package seedu.agendum.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import seedu.agendum.model.task.ReadOnlyTask;

//@@author A0148031R
public class TaskCard extends UiPart {
    
    private static final String FXML = "TaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label time;

    private ReadOnlyTask task;
    private String displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int Index){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = String.valueOf(Index) + ".";
        return UiPartLoader.loadUiPart(card);
    }
    
    public static TaskCard load(ReadOnlyTask task){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = "";
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        
        if(task.isOverdue()) {
            cardPane.setStyle("-fx-background-color: rgb(255, 169, 147)");
        } else if(task.isUpcoming()) {
            cardPane.setStyle("-fx-background-color: rgb(255, 229, 86)");
        } else {
            cardPane.setStyle("-fx-background-color: rgba(255,255,255,0.6)");
        }
        
        name.setTextFill(Color.web("#555555"));

        name.setText(task.getName().fullName);
        id.setText(displayedIndex);
        
        if(task.isOverdue()) {
            time.setText("Overdue\nScheduled: "+ formatTime());
        } else {
            time.setText(formatTime());
        }
        
    }
    
    public String formatTime() {
        StringBuilder sb = new StringBuilder();
        Optional<LocalDateTime> start = task.getStartDateTime();
        Optional<LocalDateTime> end = task.getEndDateTime();
        
        DateTimeFormatter startFormat = DateTimeFormatter.ofPattern("HH:mm EEE, dd MMM");
        
		if(start.isPresent()) {
			sb.append("from ").append(start.get().format(startFormat));
		}
		if(end.isPresent()) {
			sb.append(sb.length()>0 ? " to " : "by ");
			sb.append(end.get().format(startFormat));
		}
		
        return sb.toString().replace("AM", "am").replace("PM","pm");
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
