//@@author A0138967J
package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.todo.model.task.ReadOnlyTask;

public class SummaryCard extends UiPart{

    private static final String FXML = "SummaryCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label details;
    @FXML
    private Label tags;

    private ReadOnlyTask task;

    public SummaryCard(){

    }

    public static SummaryCard load(ReadOnlyTask task){
        SummaryCard card = new SummaryCard();
        card.task = task;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        details.setText(task.getDetail().value);
        tags.setText(task.tagsString());
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
