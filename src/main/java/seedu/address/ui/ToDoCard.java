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
    private Label titleLabel;
    @FXML
    private Label indexLabel;

    private ReadOnlyToDo toDo;
    private int index;

    public ToDoCard(){ }

    public static ToDoCard load(ReadOnlyToDo toDo, int index){
        ToDoCard card = new ToDoCard();
        card.toDo = toDo;
        card.index = index;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        titleLabel.setText(toDo.getTitle().title);
        indexLabel.setText(String.valueOf(index));
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
