package seedu.todo.guitests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import seedu.todo.TestApp;
import seedu.todo.commons.core.ConfigCenter;
import seedu.todo.commons.core.ConfigDefinition;
import seedu.todo.controllers.ConfigController;

public class ConfigCommandTest extends GuiTest {
    
    @Test
    public void config_showAll_success() {
        console.runCommand("config");
        for (ConfigDefinition configDefinition : ConfigCenter.getInstance().getConfig().getDefinitions()) {
            assertNotNull(configView.getConfigItem(configDefinition));            
        }
    }
    
    @Test
    public void config_invalidConfigName_disambig() {
        console.runCommand("config invalidConfigName someValue");
        assertEquals(ConfigController.TEMPLATE_SET_CONFIG, console.getConsoleInputText());
    }
    
    @Test
    public void config_tooLittleArgs_disambig() {
        console.runCommand("config appTitle");
        assertEquals(ConfigController.TEMPLATE_SET_CONFIG, console.getConsoleInputText());
    }
    
    @Test
    public void config_setAppTitle_success() {
        console.runCommand("config appTitle Pokemon Center");
        assertEquals("Pokemon Center", header.getAppTitle());
        assertEquals("Pokemon Center", ConfigCenter.getInstance().getConfig().getAppTitle());
    }
    
    @Test
    public void config_setDatabaseFilePath_success() {
        console.runCommand("config databaseFilePath databaseMoved.json");
        assertEquals("databaseMoved.json", ConfigCenter.getInstance().getConfig().getDatabaseFilePath());
        
        boolean isFileExists = new File("databaseMoved.json").exists();
        if (isFileExists) {
            new File("databaseMoved.json").delete();
        }
        
        assertTrue(isFileExists);
    }

    @Test
    public void configDatabaseFilePath_noJsonExtension_error() {
        console.runCommand("config databaseFilePath databaseMoved.txt");
        assertEquals(TestApp.SAVE_LOCATION_FOR_TESTING, ConfigCenter.getInstance().getConfig().getDatabaseFilePath());
        assertEquals(String.format(ConfigController.MESSAGE_FAILURE, String.format(ConfigController.MESSAGE_WRONG_EXTENSION, ".json")), 
                console.getConsoleTextArea());
    }
    
}
