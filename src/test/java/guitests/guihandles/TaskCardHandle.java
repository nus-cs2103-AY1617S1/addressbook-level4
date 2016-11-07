package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.item.ReadOnlyTask;

/**
 * Provides a handle to a floating task card in the item list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String INDEX_FIELD_ID = "#id";
    private static final String START_DATE_FIELD_ID = "#startDate";
    private static final String END_DATE_FIELD_ID = "#startDate";
    private static final String RECURRENCE_RATE_FIELD_ID = "#startDate";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    
    protected String getTextFromPriorityRectangle(String fieldId){
        return getTextFromPriorityRectangle(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromPriorityRectangle(PRIORITY_FIELD_ID);
        
    }
    
    public String getIndex() {
        return getTextFromLabel(INDEX_FIELD_ID);
        
    }
    
    public String getStartDate() {
        return getTextFromLabel(START_DATE_FIELD_ID);
        
    }
    
    public String getEndDate() {
        return getTextFromLabel(END_DATE_FIELD_ID);
        
    }
    
    public String getRecurrenceRate() {
        return getTextFromLabel(RECURRENCE_RATE_FIELD_ID);
        
    }
    

    //@@author
    public boolean isSameTask(ReadOnlyTask task){
        return getName().equals(task.getName().getTaskName()) && getPriority().equals(task.getPriorityValue().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getPriority().equals(handle.getPriority())
                    && getIndex().equals(handle.getIndex())
                    && getStartDate().equals(handle.getStartDate())
                    && getEndDate().equals(handle.getEndDate())
                    && getRecurrenceRate().equals(handle.getRecurrenceRate());
        }
        return super.equals(obj);
    }

    //@@author
    @Override
    public String toString() {
        return getName() + " " + getPriority();
    }
}
