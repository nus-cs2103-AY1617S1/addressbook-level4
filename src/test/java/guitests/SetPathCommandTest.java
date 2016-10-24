package guitests;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Config;
import seedu.taskscheduler.commons.exceptions.DataConversionException;
import seedu.taskscheduler.commons.util.ConfigUtil;
import seedu.taskscheduler.logic.commands.SetpathCommand;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

//@@author A0138696L

public class SetPathCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void setPath() {
       
        // Checking for the existence of User specified filename or path.
        String newPath = "testtaskscheduler";
        commandBox.runCommand("setpath " + newPath);
        File expected = new File(newPath);
        assertEquals(expected.toString(), newPath);
        assertResultMessage(String.format(SetpathCommand.MESSAGE_SUCCESS, newPath + ".xml"));
        
        // Checking for the consistency of setting, repeatedly, of setpath <filename> in ConfigTest.json.
        Config origConfig = initConfig("ConfigTest.json");
        String origPath = origConfig.getTaskSchedulerFilePath().replace(".xml","");
        String newPath2 = "taskscheduler";
        
        commandBox.runCommand("setpath " + newPath2);
        assertResultMessage(String.format(SetpathCommand.MESSAGE_SUCCESS, newPath2 + ".xml"));
        
        origConfig = initConfig("ConfigTest.json");
        String compareString = origConfig.getTaskSchedulerFilePath();
        assertEquals(newPath2, compareString.substring(0,compareString.length()-4));
        
        commandBox.runCommand("setpath " + origPath);
        assertResultMessage(String.format(SetpathCommand.MESSAGE_SUCCESS, origPath + ".xml"));
        
        origConfig = initConfig("ConfigTest.json");
        String compareString2 = origConfig.getTaskSchedulerFilePath();
        assertEquals(origPath, compareString2.substring(0,compareString2.length()-4));
        
    }
    
    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = "ConfigTest.json";

        if(configFilePath != null) {
            configFilePathUsed = configFilePath;
        }

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
        }
        return initializedConfig;
    }
}