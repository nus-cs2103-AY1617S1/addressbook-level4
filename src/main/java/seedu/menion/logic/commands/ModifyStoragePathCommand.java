//@@author A0139515A
package seedu.menion.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import seedu.menion.commons.core.Config;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.storage.XmlSerializableActivityManager;

/**
 * Clears the activity manager.
 */
public class ModifyStoragePathCommand extends Command {

    public static final String COMMAND_WORD = "modify";
    public static final String MESSAGE_SUCCESS = "You have successfully changed Menion's storage location to %1$s";
    public static final String MESSAGE_FAILURE = "Please provide a valid storage path!";
    private final String pathToChange;
    
    public ModifyStoragePathCommand(String pathToChange) {
    	if (!pathToChange.isEmpty()) {
    		this.pathToChange = pathToChange.trim();
    	}
    	else {
    		this.pathToChange = null;
    	}
    }


    @Override
    public CommandResult execute() {
    	ReadOnlyActivityManager before = new ActivityManager(model.getActivityManager());
        
        if (pathToChange != null) {
            Config config = new Config();
            Optional<Config> configOptional = null;
    		try {
    			configOptional = ConfigUtil.readConfig(config.DEFAULT_CONFIG_FILE);
    		} catch (DataConversionException e1) {
    			return new CommandResult(MESSAGE_FAILURE);
    		}
    		
            Config initializedConfig = configOptional.orElse(new Config());
            
        	initializedConfig.setActivityManagerFilePath(pathToChange);
        	try {
				ConfigUtil.saveConfig(initializedConfig, initializedConfig.DEFAULT_CONFIG_FILE);
			} catch (IOException e) {
				return new CommandResult(MESSAGE_FAILURE);
			}
        	
        	File file = new File(initializedConfig.getActivityManagerFilePath());
        	try {
				FileUtil.createIfMissing(file);
			} catch (IOException e) {
				return new CommandResult(MESSAGE_FAILURE);
			}
        	
        	try {
				XmlUtil.saveDataToFile(file, new XmlSerializableActivityManager(before));
			} catch (FileNotFoundException | JAXBException e) {
				// TODO Auto-generated catch block
				return new CommandResult(MESSAGE_FAILURE);
			}
        	
        	return new CommandResult(String.format(MESSAGE_SUCCESS, pathToChange));
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
}