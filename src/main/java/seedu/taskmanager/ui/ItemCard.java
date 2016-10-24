package seedu.taskmanager.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import seedu.taskmanager.logic.commands.DoneCommand;
import seedu.taskmanager.logic.commands.NotDoneCommand;
import seedu.taskmanager.logic.Logic;
import seedu.taskmanager.logic.commands.DeleteCommand;
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
    
    //@@author A0140060A
    @FXML
    private Text name;
    //@@author 
    
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
    @FXML
    private Button doneButton;
    @FXML
    private Button deleteButton;

    private int displayedIndex;
    private Logic logic;
    public static final String DONE_TEXT = "Done";
    public static final String UNDONE_TEXT = "Not Done";
    
    @FXML
    public void doneButtonAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        if (button.getText().equals(UNDONE_TEXT)) {
            button.setText(DONE_TEXT);
            logic.execute(DoneCommand.COMMAND_WORD + " " + displayedIndex);
        } else {
            button.setText(UNDONE_TEXT);
            logic.execute(NotDoneCommand.COMMAND_WORD + " " + displayedIndex);
        }
    }
    
    @FXML
    public void deleteButtonAction(ActionEvent event) {
        logic.execute(DeleteCommand.COMMAND_WORD + " " + displayedIndex);
    }

    public ItemCard(){

    }

    public static ItemCard load(ReadOnlyItem item, int displayedIndex, Logic logic){
        ItemCard card = new ItemCard();
        card.item = item;
        card.displayedIndex = displayedIndex;
        card.configure(logic);
        return UiPartLoader.loadUiPart(card);
    }
    
    public void configure(Logic logic) {
        this.logic = logic;
    }

    @FXML
    public void initialize() {
        name.setText(item.getName().value);
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
        if (item.getDone()) {
            doneButton.setText(DONE_TEXT);
        } else {
        	doneButton.setText(UNDONE_TEXT);
        }
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
