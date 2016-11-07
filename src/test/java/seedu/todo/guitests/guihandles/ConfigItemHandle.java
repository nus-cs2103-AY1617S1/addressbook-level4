package seedu.todo.guitests.guihandles;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.commons.core.ConfigDefinition;
import seedu.todo.guitests.GuiRobot;

// @@author A0139812A
public class ConfigItemHandle extends GuiHandle {

    private static final String CONFIG_NAME_TEXT_ID = "#configName";
    private static final String CONFIG_DESC_TEXT_ID = "#configDescription";
    private static final String CONFIG_VALUE_TEXT_ID = "#configValue";
    private Node node;

    public ConfigItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    public String getConfigName() {
        return getStringFromText(CONFIG_NAME_TEXT_ID, node);
    }
    
    public String getConfigDescription() {
        return getStringFromText(CONFIG_DESC_TEXT_ID, node);
    }
    
    public String getConfigValue() {
        return getStringFromText(CONFIG_VALUE_TEXT_ID, node);
    }
    
    public boolean isEqualsTo(ConfigDefinition configDefinition) {
        return getConfigName().equals(configDefinition.getConfigName())
                && getConfigDescription().equals(configDefinition.getConfigDescription())
                && getConfigValue().equals(configDefinition.getConfigValue());
    }

}
