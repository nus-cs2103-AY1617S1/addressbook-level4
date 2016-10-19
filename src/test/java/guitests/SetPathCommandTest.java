package guitests;

import org.junit.Test;
import seedu.address.logic.commands.SetPathCommand;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.ConfigUtilTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Optional;

public class SetPathCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void SetPathCommandTest() {
        
        Config origConfig = initConfig(Config.DEFAULT_CONFIG_FILE);
        String origPath = origConfig.getTaskSchedulerFilePath().replace("data/", "").replace(".xml","");
        String newPath = "testtaskscheduler";
        
        commandBox.runCommand("setpath " + newPath);
        assertResultMessage(String.format(SetPathCommand.MESSAGE_SUCCESS, "data/" + newPath + ".xml"));
        
        origConfig = initConfig(Config.DEFAULT_CONFIG_FILE);
        String compareString = origConfig.getTaskSchedulerFilePath();
        assertEquals(newPath, compareString.substring(5,compareString.length()-4));
        
        commandBox.runCommand("setpath " + origPath);
        assertResultMessage(String.format(SetPathCommand.MESSAGE_SUCCESS, "data/" + origPath + ".xml"));
        
        origConfig = initConfig(Config.DEFAULT_CONFIG_FILE);
        String compareString2 = origConfig.getTaskSchedulerFilePath();
        assertEquals(origPath, compareString2.substring(5,compareString2.length()-4));
    }
    
    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

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
