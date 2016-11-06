package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskitty.commons.util.DateTimeUtil;
import seedu.taskitty.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String START_DATE_FIELD_ID = "#startDate";
    private static final String START_TIME_FIELD_ID = "#startTime";
    private static final String END_DATE_FIELD_ID = "#endDate";
    private static final String END_TIME_FIELD_ID = "#endTime";
    private static final String TAG_FIELD_ID = "#tags";
    private static final String CARDPANE_FIELD_ID = "#cardPane";
    private static final String NULL_END_DATE_FIELD = "$endDate";
    private static final String NULL_START_DATE_FIELD = "$startDate";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    //@@author A0130853L
    // get the CSS in line style of the specified label, returned as a string
    protected String getStyleFromLabel(String fieldId) {
    	return getStyleFromLabel(fieldId, node);
    }
    
    // get the CSS in line style of the specified Hbox, returned as a string
    protected String getStyleFromHbox(String fieldId) {
        return getStyleFromHbox(fieldId, node);
    }
    // get the CSS in line style of the name of the task card as a helper method for checking if its marked as done.
    public String getDoneStyle() {
    	return getStyleFromLabel(NAME_FIELD_ID);
    }
 // get the CSS in line style of the name of the task card as a helper method for checking if its marked as overdue.
    public String getOverdueStyle() {
        return getStyleFromHbox(CARDPANE_FIELD_ID);
    }
    //@@author
    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }
    
    //@@author A0139052L
    public String getStartDate() {
        return getTextFromLabel(START_DATE_FIELD_ID);
    }
    
    public String getStartTime() {
        return getTextFromLabel(START_TIME_FIELD_ID);
    }
     
    public String getEndDate() {
        return getTextFromLabel(END_DATE_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(END_TIME_FIELD_ID);
    }    
    
    public String getTags() {
        return getTextFromLabel(TAG_FIELD_ID);
    }
    
    public boolean isSameTask(ReadOnlyTask task){
        if (isTodo() && task.isTodo()) {
            return isSameTodo(task);
        } else if (isDeadline() && task.isDeadline()) {
            return isSameDeadline(task);
        } else if (task.isEvent()) {
            return isSameEvent(task);
        }
        return false;
    }

    private boolean isTodo() {
        return getEndDate().equals(NULL_END_DATE_FIELD);
    }
    
    private boolean isDeadline() {
        return getStartDate().equals(NULL_START_DATE_FIELD);
    }
    
    
    private boolean isSameEvent(ReadOnlyTask task) {
        return hasSameNameAndTags(task)
                && hasSameEndDateAndTime(task)
                && hasSameStartDateAndTime(task);              
    }

    private boolean isSameDeadline(ReadOnlyTask task) {
        return hasSameNameAndTags(task)
                && hasSameEndDateAndTime(task);
    }

    private boolean isSameTodo(ReadOnlyTask task) {
        return hasSameNameAndTags(task);
    }
    
    private boolean hasSameNameAndTags(ReadOnlyTask task) {
        return getFullName().equals(task.getName().fullName)
        && getTags().equals(task.tagsString());
    }
    
    private boolean hasSameEndDateAndTime(ReadOnlyTask task) {
        return getEndTime().equals(DateTimeUtil.formatTimeForUI(task.getPeriod().getEndTime()))
                && getEndDate().equals(DateTimeUtil.formatDateForUI(task.getPeriod().getEndDate()));
    }
    
    private boolean hasSameStartDateAndTime(ReadOnlyTask task) {
        return getStartTime().equals(DateTimeUtil.formatTimeForUI(task.getPeriod().getStartTime()))
                && getStartDate().equals(DateTimeUtil.formatDateForUI(task.getPeriod().getStartDate()));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getTags().equals(handle.getTags())
                    && getStartDate().equals(handle.getStartDate())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndDate().equals(handle.getEndDate())
                    && getEndTime().equals(handle.getEndTime());//TODO: compare the rest
        }
        return super.equals(obj);
    }

    //@@author
    @Override
    public String toString() {
        return getFullName();
    }
}
