package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.UndoneCommand;

public class PersonCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";

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
    private Label startTime;
    @FXML
    private Label startDate;
    @FXML
    private Label tags;
    @FXML
    private Button doneButton;
    @FXML
    private Button deleteButton;
    
    private ReadOnlyItem person;
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
            logic.execute(UndoneCommand.COMMAND_WORD + " " + displayedIndex);
        }
    }
    
    @FXML
    public void deleteButtonAction(ActionEvent event) {
        logic.execute(DeleteByIndexCommand.COMMAND_WORD + " " + displayedIndex);
    }

    public PersonCard(){

    }

    public static PersonCard load(ReadOnlyItem person, int displayedIndex, Logic logic){
        PersonCard card = new PersonCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        card.configure(logic);
        return UiPartLoader.loadUiPart(card);
    }
    
    public void configure(Logic logic) {
        this.logic = logic;
    }

    @FXML
    public void initialize() {
        name.setText(person.getName().value);
        id.setText(displayedIndex + ". ");
        itemType.setText(person.getItemType().value);
        endTime.setText(person.getEndTime().value);
        endDate.setText(person.getEndDate().value);
        startTime.setText(person.getStartTime().value);
        startDate.setText(person.getStartDate().value);
        if (person.getDone()) {
            doneButton.setText(DONE_TEXT);
        } else {
        	doneButton.setText(UNDONE_TEXT);
        }
        tags.setText(person.tagsString());
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
