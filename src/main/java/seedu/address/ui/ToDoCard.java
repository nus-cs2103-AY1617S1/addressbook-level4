package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.todo.ReadOnlyToDo;

public class ToDoCard extends UiPart{

    private static final String FXML = "ToDoListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;

    private ReadOnlyToDo toDo;
    private int displayedIndex;

    public ToDoCard(){ }

    public static ToDoCard load(ReadOnlyToDo toDo, int displayedIndex){
        ToDoCard card = new ToDoCard();
        card.toDo = toDo;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        title.setText(toDo.getTitle().title);
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
