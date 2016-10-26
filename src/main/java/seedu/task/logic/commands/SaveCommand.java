package seedu.task.logic.commands;

import java.io.IOException;

import seedu.task.commons.core.Config;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.storage.StorageManager;
//import seedu.task.storage.XmlTaskManagerStorage;

//import seedu.task.storage.XmlTaskManagerStorage;

/**
 * Save storage file to a specific folder.
 */
public class SaveCommand extends Command {

public static final String COMMAND_WORD = "save";

public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change location of the save file "
        + "Parameters: FolderPath"
        + " Example: " + COMMAND_WORD
        + " C:\\Users\\<username>\\Desktop\\CS2103 Tutorial";

    private String changePathLink;
    
    public SaveCommand(String arguments){
        
        this.changePathLink = arguments;
    }
    
    
    @Override
    public CommandResult execute() {
        
        /*
         * Change the folder path and update config.json
         */
        Config config = new Config();
        String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        
        try{
            config.setTaskManagerFilePath(changePathLink + "/data/taskmanager.xml");
            ConfigUtil.saveConfig(config, configFilePathUsed);
            
         
            new StorageManager(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());
            
            return new CommandResult("Change save path:" + changePathLink + " Updated");
            
            //new XmlTaskManagerStorage(config.getTaskManagerFilePath());
        }
        
        catch(IOException e){
			//remove this command from list for undo
			model.getCommandForUndo();
            return new CommandResult("Failed to save config file: "+ StringUtil.getDetails(e));
        }
        
    }

    //@@author A0153411W
	/**
	 * Save Command is not reversible.
	 */
	@Override
	public CommandResult executeUndo() {
		return this.execute();
	}


	@Override
	public boolean isReversible() {
		return true;
	}

}
