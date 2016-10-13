package seedu.taskmanager.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.model.item.ReadOnlyItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

public class ItemCard extends UiPart{

    private static final String FXML = "ItemListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label itemType;
    @FXML
    private Label endTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endFromNow;
    @FXML
    private Label startTime;
    @FXML
    private Label startDate;
    @FXML
    private Label tags;

    private ReadOnlyItem item;
    private int displayedIndex;

    public ItemCard(){

    }

    public static ItemCard load(ReadOnlyItem item, int displayedIndex){
        ItemCard card = new ItemCard();
        card.item = item;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(item.getName().value);
        id.setText(displayedIndex + ". ");
        itemType.setText(item.getItemType().value);
        endTime.setText(item.getEndTime().value);
        endDate.setText(item.getEndDate().value);
        String endDateString = item.getEndDate().value + " " + item.getEndTime().value;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
        Date endFromNowDate;
        Date currentDate = new Date();
        String endFromNowText = "";
        if (item.getItemType().value.equals(ItemType.DEADLINE_WORD) || item.getItemType().value.equals(ItemType.EVENT_WORD)) {
            try {
                endFromNowDate = df.parse(endDateString);
                PrettyTime p = new PrettyTime();
                endFromNowText = p.format(endFromNowDate);
                if (currentDate.before(endFromNowDate)) { // Future Deadline
                    endFromNow.setText("Ends " + endFromNowText);
                } else { // Past Deadline
            	    endFromNow.setText("Ended " + endFromNowText);
                }
            } catch (ParseException e) {
                endFromNow.setText(endFromNowText);
                e.printStackTrace();
            }
        } else {
        	endFromNow.setText(endFromNowText);
        }
        startTime.setText(item.getStartTime().value);
        startDate.setText(item.getStartDate().value);
        tags.setText(item.tagsString());
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
