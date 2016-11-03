package seedu.taskitty.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskitty.commons.util.DateUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

public class EventCard extends UiPart {

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label id;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public static EventCard load(ReadOnlyTask task, int displayedIndex){
        EventCard card = new EventCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        
        //@@author A0130853L
        boolean isDone = task.getIsDone();
        if (isDone) {
            cardPane.setStyle("-fx-background-color: grey");
            name.setStyle("-fx-text-fill: white");
            id.setStyle("-fx-text-fill: white");
            startDate.setStyle("-fx-text-fill: white");
            endDate.setStyle("-fx-text-fill: white");
        }
        
        //@@author A0139930B
        name.setText(task.getName().fullName);
        
        TaskDate startTaskDate = task.getPeriod().getStartDate();
        TaskTime startTaskTime = task.getPeriod().getStartTime();
        startTime.setText(DateUtil.formatTimeForUI(startTaskTime));
        startDate.setText(DateUtil.formatDateForUI(startTaskDate));
        
        TaskDate endTaskDate = task.getPeriod().getEndDate();
        TaskTime endTaskTime = task.getPeriod().getEndTime();
        endTime.setText(DateUtil.formatTimeForUI(endTaskTime));
        endDate.setText(DateUtil.formatDateForUI(endTaskDate));
        
        id.setText("e" + displayedIndex + ". ");
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
