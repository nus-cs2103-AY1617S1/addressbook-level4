package seedu.oneline.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor; 

public class TaskCard extends UiPart {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox taskCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label tag;
    @FXML
    private Label color; 

    private ReadOnlyTask task;
    private TagColor tagColor; 
    private int displayedIndex;

    public TaskCard() {

    }

    public static TaskCard load(ReadOnlyTask task, TagColor tagColor, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.tagColor = tagColor; 
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        TaskCardParser parser = new TaskCardParser(task);
        name.setText(parser.getName());
        id.setText(displayedIndex + ". ");
        time.setText(parser.getTime());
        tag.setText(task.getTag().equals(Tag.EMPTY_TAG) ?
                    "" : task.getTag().toString());
        color.setStyle("-fx-text-fill: " + tagColor.toHTMLColor());
    }

    public HBox getLayout() {
        return taskCardPane;
    }

    @Override
    public void setNode(Node node) {
        taskCardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
