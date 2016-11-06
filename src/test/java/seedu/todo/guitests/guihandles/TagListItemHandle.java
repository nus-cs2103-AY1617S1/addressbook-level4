package seedu.todo.guitests.guihandles;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

// @@author A0139812A
public class TagListItemHandle extends GuiHandle {

    private static final String TAGNAME_TEXT_ID = "#labelText";
    private Node node;

    public TagListItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    public String getName() {
        return getStringFromText(TAGNAME_TEXT_ID, node);
    }
    
    public boolean isEqualsTo(String tag) {
        if (tag == null) {
            return false;
        }
        
        return getName().equals(tag);
    }
    
}
