
package seedu.menion.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import seedu.menion.commons.core.Config;
import seedu.menion.commons.core.EventsCenter;
import seedu.menion.commons.events.storage.StoragePathChangedEvent;
import seedu.menion.commons.events.ui.ModifyStorageEvent;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;

//@@author A0139515A
/**
 * Modify the activity manager storage location.
 */
public class ModifyStoragePathCommand extends Command {

    public static final String COMMAND_WORD = "modify";
    public static final String DEFAULT_STORAGE_PATH = new File(ModifyStoragePathCommand.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + File.separator + "data/menion.xml";
    public static final String MESSAGE_SUCCESS = "You have successfully changed Menion's storage location to %1$s \n";
    public static final String MESSAGE_FAILURE = "Please provide a valid storage path!\n" + 
    										"Examples to modify storage path: \n" + 
    										"Modify to default storage path: modify default\n" +
    										"Modify to a specified storage path: modify [FILEPATH]\n" +
    										"Note that file path specified will be in user home directory.";
    private final String pathToChange;
    
    public ModifyStoragePathCommand(String pathToChange) {
    	if (!pathToChange.isEmpty()) {
    		if (pathToChange.toLowerCase().trim().equals("default")) {
    			this.pathToChange = DEFAULT_STORAGE_PATH;
    		}
    		else {
    			this.pathToChange = pathToChange.trim();
    		}  		
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
    			return new CommandResult("Unable to read configuration file");
    		}
    		
    		Config initializedConfig = configOptional.orElse(new Config());
    		
    		if (!pathToChange.equals(DEFAULT_STORAGE_PATH)) {
	            String root = System.getProperty("user.home");
	            newPath = root + File.separator + pathToChange;
    		}
    		else {
    			newPath = pathToChange;
    		}
    		
    		//deleting old files
    		File oldStorage =  new File(initializedConfig.getActivityManagerFilePath());
    		oldStorage.delete();
    		
            // Saving configuration
        	initializedConfig.setActivityManagerFilePath(newPath);
        	try {
				ConfigUtil.saveConfig(initializedConfig, initializedConfig.DEFAULT_CONFIG_FILE);
			} catch (IOException e) {
				return new CommandResult("Unable to save configuration");
			}
        	
        	// Setting up new storage location
    		XmlActivityManagerStorage newStorage = new XmlActivityManagerStorage(newPath);
    		EventsCenter.getInstance().post(new StoragePathChangedEvent(newStorage, before));		

			// Update status bar
			EventsCenter.getInstance().post(new ModifyStorageEvent(newPath));
			
        	return new CommandResult(String.format(MESSAGE_SUCCESS, pathToChange));
        }
        return new CommandResult(MESSAGE_FAILURE);
    }

    private void deleteFileOrFolder(File file){
	    try {
	        for (File f : file.listFiles()) {
	            f.delete();
	            deleteFileOrFolder(f);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(System.err);
	    }
    }
}