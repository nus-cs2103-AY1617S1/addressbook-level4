package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import w15c2.tusk.model.Alias;

//@@author A0138978E
/*
 * Provides a handle to a particular alias card
 */
public class AliasCardHandle extends GuiHandle {
    private static final String ALIAS_SHORTCUT_FIELD_ID = "#cardAlias";
    private static final String ALIAS_COMMAND_FIELD_ID = "#command";
    
    private Node node;

    public AliasCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getShortcut() {
        return getTextFromLabel(ALIAS_SHORTCUT_FIELD_ID);
    }
    
    public String getSentence() {
        return getTextFromLabel(ALIAS_COMMAND_FIELD_ID);
    }

    public boolean isSameAlias(Alias alias){
        return getShortcut().equals(alias.getShortcut()) && getSentence().equals(alias.getSentence());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AliasCardHandle) {
            AliasCardHandle handle = (AliasCardHandle) obj;
            return getShortcut().equals(handle.getShortcut());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getShortcut();
    }
}
