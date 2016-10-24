package seedu.address.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    ImageView tickmark = new ImageView("file:///C:/Users/hastyrush/Documents/main/src/main/resources/images/tick.png");
    
    @FXML
    private GridPane gridpane;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label address;
    @FXML
    private Label start;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        EventsCenter.getInstance().registerHandler(card);
        return UiPartLoader.loadUiPart(card);
    }
    
    @Subscribe
    private void modelChangedEvent(TaskManagerChangedEvent change) {
    	  cardPane.getChildren().add(tickmark);
    	  gridpane.add(tickmark, 1, 0);
    	
    	
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
//        date.setText(task.getDate().value);
        address.setText("End Time: " + task.getEndTime().appearOnUIFormat());
        start.setText("Start Time: " + task.getStartTime().appearOnUIFormat());
        tags.setText(task.tagsString());
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
