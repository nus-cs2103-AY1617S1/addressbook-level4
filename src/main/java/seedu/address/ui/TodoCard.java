package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class TodoCard extends UiPart {
    
    private static final String FXML = "TodoListCard.fxml";
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label tags;
    
    private ReadOnlyTask todo;
    private int displayedIndex;
    
    public TodoCard() {
        
    }
    
    public static TodoCard load(ReadOnlyTask todo, int displayedIndex) {
        TodoCard card = new TodoCard();
        card.todo = todo;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
    @FXML
    public void initialize() {
        name.setText(todo.getName().taskDetails);
        id.setText("T" + displayedIndex + ". ");
        date.setText(todo.getDate().value);
        start.setText(todo.getStart().value);
        end.setText(todo.getEnd().value);
        tags.setText(todo.tagsString());
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
