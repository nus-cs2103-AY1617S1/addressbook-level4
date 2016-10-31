
package seedu.menion.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import seedu.menion.commons.core.Config;
import seedu.menion.commons.core.EventsCenter;
import seedu.menion.commons.events.ui.ExitAppRequestEvent;
import seedu.menion.commons.events.ui.ModifyStorageEvent;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.storage.XmlFileStorage;
import seedu.menion.storage.XmlSerializableActivityManager;

//@@author A0139515A
/**
 * Modify the activity manager storage location.
 */
public class ModifyStoragePathCommand extends Command {

    public static final String COMMAND_WORD = "modify";
    public static final String MESSAGE_POPUP = "You have successfully changed Menion's storage location. Please restart Menion (:";
    public static final String MESSAGE_SUCCESS = "You have successfully changed Menion's storage location to %1$s \n";
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
    	assert model != null;
    	
    	model.updateRecentChangedActivity(null);
    	
    	ReadOnlyActivityManager before = new ActivityManager(model.getActivityManager());
    	String newPath;
    	
        if (pathToChange != null) {
            Config config = new Config();
            Optional<Config> configOptional = null;
    		try {
    			configOptional = ConfigUtil.readConfig(config.DEFAULT_CONFIG_FILE);
    		} catch (DataConversionException e1) {
    			return new CommandResult(MESSAGE_FAILURE);
    		}
    		
            Config initializedConfig = configOptional.orElse(new Config());
            
            String root = System.getProperty("user.home");
            newPath = root + File.separator + pathToChange;
            System.out.println(newPath);
        	initializedConfig.setActivityManagerFilePath(newPath);
        	try {
				ConfigUtil.saveConfig(initializedConfig, initializedConfig.DEFAULT_CONFIG_FILE);
			} catch (IOException e) {
				return new CommandResult("Unable to create file");
			}
        	
        	File file = new File(newPath);
        	try {
				FileUtil.createIfMissing(file);
			} catch (IOException e) {
				return new CommandResult("Unable to create file");
			}
        
    		try {
				XmlFileStorage.saveDataToFile(file, new XmlSerializableActivityManager(before));
				
			} catch (FileNotFoundException e) {
				return new CommandResult("File not found");
			} 
    		ReadOnlyActivityManager dataToRead;
			try {
				dataToRead = XmlUtil.getDataFromFile(file, ReadOnlyActivityManager.class);
				model.resetData(dataToRead);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
         	
        	EventsCenter.getInstance().post(new ModifyStorageEvent(newPath));
        	 
        	return new CommandResult(String.format(MESSAGE_SUCCESS, pathToChange));
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
}