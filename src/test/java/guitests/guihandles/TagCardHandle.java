//@@author A0142421X-unused
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * Provides a handle to a tag card in the tag list panel
 */
public class TagCardHandle extends GuiHandle {
	private static final String TAG_NAME_FIELD_ID = "#tagName";
	
	private Node node;
	
	public TagCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
		super(guiRobot, primaryStage, null);
        this.node = node;
	}
	
	protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
	
	public String getTagName() {
		return getTextFromLabel(TAG_NAME_FIELD_ID);
	}
	
	public boolean isSameTag(Tag tag){
        return getTagName().equals(tag.getName()); 
    }

}
