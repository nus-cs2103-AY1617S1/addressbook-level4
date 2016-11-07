package seedu.todo.guitests.guihandles;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.commons.util.ListUtil;
import seedu.todo.guitests.GuiRobot;
import seedu.todo.models.Event;

//@@author A0139812A
public class TaskListEventItemHandle extends GuiHandle {

    private static final String TASKLISTEVENTITEM_NAME_ID = "#eventText";
    private static final String TASKLISTEVENTITEM_TAGS_ID = "#eventTagListText";
    private Node node;

    public TaskListEventItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    /**
     * Gets the name of the event.
     */
    public String getName() {
        return getStringFromText(TASKLISTEVENTITEM_NAME_ID, node);
    }
    
    /**
     * Gets the list of tags for this task item.
     */
    public List<String> getTags() {
        String tagsText = getStringFromText(TASKLISTEVENTITEM_TAGS_ID, node);
        
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
     * Checks if this handle is referring to an event of the same data.
     * 
     * @param eventToCompare    Event to compare.
     * @return                  True if they are equal.
     */
    public boolean isEqualsToEvent(Event eventToCompare) {
        if (eventToCompare == null) {
            return false;
        }
        
        return getName().equals(eventToCompare.getName()) && ListUtil.unorderedListEquals(getTags(), eventToCompare.getTagList());
    }
    
}
