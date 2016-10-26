package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.cmdo.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#detail";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String START_TIME = "#st";
    private static final String START_DATE = "#sd";
    private static final String END_DATE = "#dbd";
    private static final String END_TIME = "#dbt";
    private static final String ID = "#id";
    private static final String TAG = "#tags";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    public String getID(String id) {
    	return getTextFromLabel(ID);
    }
    
    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getDetail() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }
    
    public String getStartDate() {
    	return getTextFromLabel(START_DATE);
    }
    
    public String getStartTime() {
    	return getTextFromLabel(START_TIME);
    }
    
    public String getDueByDate() {
        return getTextFromLabel(END_DATE);
    }

    public String getDueByTime() {
        return getTextFromLabel(END_TIME);
    }
    
    public String getTag() {
    	return getTextFromLabel(TAG);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getDetail().equals(task.getDetail().details)
        		&& getDueByDate().equals(task.getDueByDate().getFriendlyEndString()) 
                && getDueByTime().equals(task.getDueByTime().getFriendlyEndString()) 
                && getPriority().equals(task.getPriority().value) 
                && getTag().equals(task.tagsString())
                && getStartTime().equals(task.getDueByTime().getFriendlyStartString())
                && getStartDate().equals(task.getDueByDate().getFriendlyStartString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getDetail().equals(handle.getDetail())
                    && getPriority().equals(handle.getPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDetail() + " " + getPriority();
    }
}
