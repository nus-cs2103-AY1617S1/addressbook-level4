package seedu.address.ui;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.commons.events.ui.OverdueChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0147890U
public class DeadlineCard extends UiPart {
    
    private static final String FXML = "DeadlineListCard.fxml";
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label end;
    @FXML
    private Label tags;
    
    private ReadOnlyTask deadline;
    private int displayedIndex;
    
    public DeadlineCard() {
        
    }
    
    public static DeadlineCard load(ReadOnlyTask deadline, int displayedIndex) {
        DeadlineCard card = new DeadlineCard();
        card.deadline = deadline;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
    @FXML
    public void initialize() {
        name.setText(deadline.getName().taskDetails);
        id.setText("D" + displayedIndex + ". ");
        date.setText("Date:" + "    " + deadline.getDate().value);
        end.setText("End time:" + "    " + deadline.getEnd().value);
        tags.setText(deadline.tagsString());
        
        
        registerAsAnEventHandler(this);
        
    }
    
    @Subscribe
    private void handleTaskOverdueChanged(OverdueChangedEvent change) {
        //overdueChangeColor(deadline, cardPane);
        changeColor();
    }

    private void changeColor() {
        int overdueState = overdueChangeColor(deadline, this.cardPane);
        setTextColor(overdueState);
    }

    private void setTextColor(int overdueState) {
        if (overdueState == 1) {
            name.setStyle("-fx-text-fill: red");
            id.setStyle("-fx-text-fill: red");
            date.setStyle("-fx-text-fill: red");
            end.setStyle("-fx-text-fill: red");
            tags.setStyle("-fx-text-fill: red");
        }
        
        if(overdueState == 2) {
            name.setStyle("-fx-text-fill: #004402");
            id.setStyle("-fx-text-fill: #004402");
            date.setStyle("-fx-text-fill: #004402");
            end.setStyle("-fx-text-fill: #004402");
            tags.setStyle("-fx-text-fill: #004402");
        }
        
        if (overdueState == 0) {

            name.setStyle(null);
            id.setStyle(null);
            date.setStyle(null);
            end.setStyle(null);
            tags.setStyle(null);
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
