package seedu.todo.guitests.guihandles;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.guitests.GuiRobot;

//@@author A0139812A
public class AliasItemHandle extends GuiHandle {

    private static final String ALIAS_KEY_TEXT_ID = "#aliasKey";
    private static final String ALIAS_VALUE_TEXT_ID = "#aliasValue";
    private Node node;

    public AliasItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    public String getAliasKey() {
        return getStringFromText(ALIAS_KEY_TEXT_ID, node);
    }
    
    public String getAliasValue() {
        return getStringFromText(ALIAS_VALUE_TEXT_ID, node);
    }
    
    public boolean isEqualsTo(String aliasKey, String aliasValue) {
        return getAliasKey().equals(aliasKey) && getAliasValue().equals(aliasValue);
    }

}
