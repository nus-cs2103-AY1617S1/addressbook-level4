package tars.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import tars.model.task.DateTime;
import tars.model.task.rsv.RsvTask;

public class RsvTaskCard extends UiPart{

    private static final String FXML = "RsvTaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private TextArea dateTimeList;
  
    private RsvTask rsvTask;
    private int displayedIndex;

    public RsvTaskCard(){

    }

    public static RsvTaskCard load(RsvTask rsvTask, int displayedIndex){
        RsvTaskCard card = new RsvTaskCard();
        card.rsvTask = rsvTask;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(rsvTask.getName().taskName);
        id.setText(displayedIndex + ". ");
        setDateTimeList();
    }
    
    private void setDateTimeList() {
        ArrayList<DateTime> dateTimeArrayList = rsvTask.getDateTimeList();
        String toSet = "[";
        int count = 1;
        for (DateTime dt : dateTimeArrayList) {
            toSet += String.valueOf(count) + ". " + dt.toString() + ", ";
            count++;
        }
        // remove last comma and whitespace and add closing bracket
        toSet = toSet.trim().substring(0, toSet.length()-2) + "]";
        dateTimeList.setText(toSet);
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
