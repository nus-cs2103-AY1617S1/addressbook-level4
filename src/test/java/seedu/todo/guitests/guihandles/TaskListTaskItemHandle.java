package seedu.todo.guitests.guihandles;

import java.time.LocalTime;
import java.util.List;

import java.util.Arrays;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.ListUtil;
import seedu.todo.guitests.GuiRobot;
import seedu.todo.models.Task;

//@@author A0139812A
public class TaskListTaskItemHandle extends GuiHandle {

    private static final String TASKLISTTASKITEM_NAME_ID = "#taskText";
    private static final String TASKLISTTASKITEM_TIME_ID = "#taskTime";
    private static final String TASKLISTTASKITEM_TAGS_ID = "#taskTagListText";
    private Node node;

    public TaskListTaskItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    /**
     * Gets the name of the task.
     */
    public String getName() {
        return getStringFromText(TASKLISTTASKITEM_NAME_ID, node);
    }
    
    /**
     * Gets the formatted time of the task.
     */
    public LocalTime getTime() {
        String timeText = getStringFromText(TASKLISTTASKITEM_TIME_ID, node);
        
        if (timeText.length() <= 0) {
            return null;
        }
        
        return DateUtil.parseTime(timeText);
    }
    
    /**
     * Gets the list of tags for this task item.
     */
    public List<String> getTags() {
        String tagsText = getStringFromText(TASKLISTTASKITEM_TAGS_ID, node);
        
        // Strip square brackets off
        tagsText = tagsText.replaceAll("\\[|\\]", "");
        
        // Check for empty string... because Java returns an array of size 1
        // when you split an empty string.
        if (tagsText.length() <= 0) {
            return Arrays.asList(new String[] {});
        }
        
        return Arrays.asList(tagsText.split(", "));
    }
    
    /**
     * Checks if this handle task time is equal to that of a task provided.
     * 
     * @param taskToCompare     Task to compare.
     * @return                  True if the time of the task is equal.
     */
    public boolean isTimeEqual(Task taskToCompare) {
        boolean isFloating = taskToCompare.getDueDate() == null;
        
        if (isFloating) {
            return getTime() == null;
        } else {
            return getTime() != null && getTime().equals(taskToCompare.getDueDate().toLocalTime());
        }
    }
    
    /**
     * Checks if this handle is referring to a task with the same data.
     * 
     * @param taskToCompare     Task to compare.
     * @return                  True if they are equal.
     */
    public boolean isEqualsToTask(Task taskToCompare) {
        if (taskToCompare == null) {
            return false;
        }
        
        return getName().equals(taskToCompare.getName()) && isTimeEqual(taskToCompare) 
                && ListUtil.unorderedListEquals(taskToCompare.getTagList(), getTags());
    }

}
