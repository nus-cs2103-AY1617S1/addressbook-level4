package guitests.guihandles;

import java.text.SimpleDateFormat;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String START_DATE_FIELD_ID = "#startDate";
    private static final String DUE_DATE_FIELD_ID = "#dueDate";
    private static final String STATUS_FIELD_ID = "#status";
    
    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTitle() {
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getStartDate() {
        return getTextFromLabel(START_DATE_FIELD_ID);
    }

    public String getDueDate() {
        return getTextFromLabel(DUE_DATE_FIELD_ID);
    }
    
    public String getStatus() {
        return getTextFromLabel(STATUS_FIELD_ID);
    }
    
    public boolean isSameTask(ReadOnlyTask task){
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy HH:mm");
    	return getTitle().equals(task.getTitle().fullTitle) && getDescription().equals(task.getDescription().fullDescription)
                && getStartDate().equals("Start Date: " + dateFormat.format(task.getStartDate().startDate)) && getDueDate().equals("Due Date: " + dateFormat.format(task.getDueDate().dueDate))
                && getStatus().equals(task.getStatus().status);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTitle().equals(handle.getTitle())
                    && getDescription().equals(handle.getDescription())
                    && getStartDate().equals(handle.getStartDate())
                    && getDueDate().equals(handle.getDueDate())
                    && getStatus().equals(handle.getStatus()); 
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTitle() + " " + getDescription()+ " " + getStartDate()+ " " + getDueDate()+ " " + getStatus();
    }
}
