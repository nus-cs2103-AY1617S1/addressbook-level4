//@@author A0138967J
package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.ReadOnlyTask;

public class WeekSummaryCard extends UiPart{

    private static final String FXML = "WeekSummaryCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Text name;
    @FXML
    private Label details;
    @FXML
    private Label tags;
    @FXML
    private Circle priorityLevel;

    private ReadOnlyTask task;

    public static WeekSummaryCard load(ReadOnlyTask task){
        WeekSummaryCard card = new WeekSummaryCard();
        card.task = task;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        details.setText(task.getDetail().value);
      
        initPriority();
        tags.setText(task.tagsString());
        
        if(task.getCompletion().isCompleted()) {
            markComplete();
        }
        
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    
    private void initPriority() {
        if (task.getPriority().toString().equals(Priority.LOW)) {
            priorityLevel.setFill(Color.web("#b2ff59"));
            priorityLevel.setStroke(Color.LIMEGREEN);
        } else if (task.getPriority().toString().equals(Priority.MID)) {
            priorityLevel.setFill(Color.web("#fff59d"));
            priorityLevel.setStroke(Color.web("#ffff00"));
        } else {
            priorityLevel.setFill(Color.RED);
            priorityLevel.setStroke(Color.web("#c62828"));
        }
    }
    
    
    private void markComplete() {
        name.setFill(Color.LIGHTGREY);
        name.setStyle("-fx-strikethrough: true");
        name.setOpacity(50);
        
        details.setTextFill(Color.LIGHTGREY);
        tags.setTextFill(Color.LIGHTGREY);

        priorityLevel.setFill(Color.WHITE);
        priorityLevel.setStroke(Color.WHITE);

    }
}
