package guitests.guihandles;

import java.time.format.DateTimeFormatter;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Time;

//@@author A0126649W
/**
 * Provides a handle to a task card in the task list panel.
 */
public class TitleCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#date";
    private static final String ENDTIME_FIELD_ID = "#end";

    private Node node;

    public TitleCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }
    
    public String getDate() {
        return getTextFromLabel(DATE_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        switch(task.getTaskType()) {
        case TIMERANGE:
            if(!isEndTimeEqual(task)){
                return false;
            }
        case DEADLINE:
        case UNTIMED:
            if(!isDateEqual(task)){
                return false;
            }
        case FLOATING: break;
        default:
            assert false: "Task must have TaskType";
        }
        
        return isNameEqual(task);
    }

    private boolean isNameEqual(ReadOnlyTask task) {
        return getTaskName().equals(task.getName().taskName);
    }

    private boolean isDateEqual(ReadOnlyTask task) {
        return getDate().equals(task.getTime().get().getStartDateString());
    }

    private boolean isEndTimeEqual(ReadOnlyTask task) {
        return getEndTime().equals(task.getTime().get().getEndDate().get().toLocalTime()
                   .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
    }
    //@@author

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TitleCardHandle) {
            TitleCardHandle handle = (TitleCardHandle) obj;
            return getTaskName().equals(handle.getTaskName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName();
    }
}
