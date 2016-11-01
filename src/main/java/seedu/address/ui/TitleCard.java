package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskCardMarkChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.TaskType;
import seedu.address.model.task.Time;

//@@author A0126649W
public class TitleCard extends UiPart{
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TitleListCard.fxml";
    
    protected static final String COLOR_RED = "label_red";
    protected static final String COLOR_GREY = "label_grey";
    protected static final String COLOR_BLACK = "label_black";

    @FXML
    private HBox cardPane;
    @FXML
    private HBox dateBox;
    @FXML
    private Label name;
    @FXML
    private Text id;
    @FXML
    private Label date;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label tag;
    @FXML
    private CheckBox completeStatus;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TitleCard(){

    }

    public static TitleCard load(ReadOnlyTask task, int displayedIndex){
        TitleCard card = new TitleCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        completeStatus.setSelected(task.getCompleted());
        setEventHandlerForMarkChangedEvent();
        setDesign();
        setTextStyle();
        setTime();
        setTag();
    }
    
    private void setTextStyle() {
        if(task.getCompleted()){
            setColor(COLOR_GREY);
        }else if(task.getTaskType() != TaskType.FLOATING){
            try {
                Time currentTime;
                currentTime = new Time(new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime()));
                int result = task.getTime().get().compareTo(currentTime);
                
                if(result < 0){
                    setColor(COLOR_RED);
                }
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }else{
            setColor(COLOR_BLACK);
        }
    }
    
    private void setColor(String s){
        name.getStyleClass().add(s);
        id.getStyleClass().add(s);
    }
    
    public void setTime(){
        switch(task.getTaskType()) {
        case TIMERANGE:
            end.setText(task.getTime().get().getEndDate().get().toLocalTime()
                       .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
            start.setText("-");
        case DEADLINE:
        case UNTIMED:
            date.setText(task.getTime().get().getStartDateString());
            dateBox.setMaxHeight(HBox.USE_COMPUTED_SIZE);
        case FLOATING: break;
        default:
            assert false: "Task must have TaskType";
        }
    }
    
    public void setTag(){
        if(!task.getTags().isEmpty()){
            tag.setText(task.tagsString());
            tag.setMaxHeight(Label.USE_COMPUTED_SIZE);
        }
    }

    //@@author A0121261Y-unused
    /*public void setDateTimeText(){
        if (task.getTime().isPresent()) {
            time.setText(task.getTime().get().getStartDateString());
           if (task.getTime().get().getUntimedStatus()) {
               startTime.setText(BLANK);
               endTime.setText(BLANK);
           } else if (task.getTime().get().getEndDate().isPresent()) {
               startTime.setText("Starts at: " + task.getTime().get().getStartDate().toLocalTime()
                       .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
               endTime.setText("Ends at: " + task.getTime().get().getEndDate().get().toLocalTime()
                       .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
           } else {
               startTime.setText("Starts at: " + task.getTime().get().getStartDate().toLocalTime()
                       .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
               endTime.setText(BLANK);
           }
       } else {
           time.setText(BLANK);
           startTime.setText(BLANK);
           endTime.setText(BLANK);
       }

    }*/
    //@@author


    @FXML
    private void setDesign() {
        boolean isCompleted = task.getCompleted();

        if (isCompleted) {
            completeStatus.setSelected(true);
        } else {
            completeStatus.setSelected(false);
        }
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
    
    //@@author A0135812L
    private void setEventHandlerForMarkChangedEvent(){
        completeStatus.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskCardMarkChangedEvent(displayedIndex));
            }
        });
    }
}
