package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

public class ActivityCard extends UiPart{

    private static final String FXML = "ActivityCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label activityType;
    @FXML
    private Label name;
    @FXML
    private Label note;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label status;
    
    private ReadOnlyActivity activity;
    
    private int displayedIndex;

    public ActivityCard(){
        
    }

    public static ActivityCard load(ReadOnlyActivity activity, int displayedIndex){
        ActivityCard card = new ActivityCard();
        card.activity = activity;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {

        if (activity.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
            activityType.setText(activity.getActivityType().toString());
            name.setText(activity.getActivityName().fullName);
            note.setText(activity.getNote().toString());
            status.setText(activity.getActivityStatus().toString());
        } else if (activity.getActivityType().equals(Activity.TASK_TYPE)) {
            activityType.setText(activity.getActivityType().toString());
            name.setText(activity.getActivityName().fullName);
            note.setText(activity.getNote().toString());
            startDate.setText(activity.getActivityStartDate().toString());
            startTime.setText(activity.getActivityStartTime().toString());
            status.setText(activity.getActivityStatus().toString());
        } else if (activity.getActivityType().equals(Activity.EVENT_TYPE)) {
            activityType.setText(activity.getActivityType().toString());
            name.setText(activity.getActivityName().fullName);
            note.setText(activity.getNote().toString());
            startDate.setText(activity.getActivityStartDate().toString());
            startTime.setText(activity.getActivityStartTime().toString()); 
            endDate.setText(activity.getActivityEndDate().toString());
            endTime.setText(activity.getActivityEndTime().toString());
            status.setText(activity.getActivityStatus().toString());
        }
        id.setText(displayedIndex + ". ");    
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
