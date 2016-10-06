package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.item.ReadOnlyToDo;

public class ToDoCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label type;
    @FXML
    private Label tags;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;

    private ReadOnlyToDo todo;
    private int displayedIndex;

    public ToDoCard(){

    }

    public static ToDoCard load(ReadOnlyToDo todo, int displayedIndex){
        ToDoCard card = new ToDoCard();
        card.todo = todo;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(todo.getName().value);
        id.setText(displayedIndex + ". ");
        type.setText(todo.getType().value);
        tags.setText(todo.tagsString());
        startDate.setText(todo.getStartDate().value);
        startTime.setText(todo.getStartTime().value);
        endDate.setText(todo.getEndDate().value);
        endTime.setText(todo.getEndTime().value);
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
