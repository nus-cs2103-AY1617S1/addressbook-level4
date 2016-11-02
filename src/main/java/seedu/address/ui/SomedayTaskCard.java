package seedu.address.ui;
//@@author A0142184L
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;

public class SomedayTaskCard extends UiPart{

    private static final String FXML = "SomedayTaskCard.fxml";

    @FXML
    private VBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label taskType;
    @FXML
    private Label taskStatus;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public SomedayTaskCard(){

    }

    public static SomedayTaskCard load(ReadOnlyTask task, int displayedIndex){
    	SomedayTaskCard card = new SomedayTaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        taskName.setText(task.getName().value);
        id.setText(displayedIndex + ". ");
        taskType.setText(task.getTaskType().toString());
        setTaskStatus();
        tags.setText(task.tagsString());
    }
    
    private void setTaskStatus() {
		if (task.getStatus().isDone()) {
			taskStatus.setText(task.getStatus().value.toString().toUpperCase());
			taskStatus.setStyle("-fx-font-size: 14");
			taskStatus.setStyle("-fx-text-fill: green");
		}
	}

    public VBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (VBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}