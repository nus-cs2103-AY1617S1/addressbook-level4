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
        String endTime =  twelveHourConvertor(deadline.getEnd().value);
        
        name.setText(deadline.getName().taskDetails);
        id.setText("D" + displayedIndex + ". ");
        date.setText("Date:" + "    " + deadline.getDate().value);
        end.setText("End time:" + "    " + endTime);
        tags.setText(deadline.tagsString());
        
        registerAsAnEventHandler(this);
    }
    
    @Subscribe
    private void handleTaskOverdueChanged(OverdueChangedEvent change) {
        changeColor();
    }

    private void changeColor() {
        int overdueState = overdueChangeBorderColor(deadline, this.cardPane);
        setTextColor(overdueState);
    }

    private void setTextColor(int overdueState) {
        if (overdueState == 1) {
            setFxStyle("red");
        }
        
        if(overdueState == 2) {
            setFxStyle("#004402");
        }
        
        if (overdueState == 0) {
            name.setStyle(null);
            id.setStyle(null);
            date.setStyle(null);
            end.setStyle(null);
            tags.setStyle(null);
        }
    }
    
    private void setFxStyle(String color) {
        name.setStyle("-fx-text-fill: " + color);
        id.setStyle("-fx-text-fill: " + color);
        date.setStyle("-fx-text-fill: " + color);
        end.setStyle("-fx-text-fill: " + color);
        tags.setStyle("-fx-text-fill: " + color);
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
