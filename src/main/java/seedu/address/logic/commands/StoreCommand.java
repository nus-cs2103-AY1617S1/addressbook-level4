
package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.ChangeStorageFilePathEvent;
import seedu.address.storage.XmlTaskManagerStorage;

//@@author A0139498J
/**
* Changes storage file location of task manager.
*/
public class StoreCommand extends Command {

    public static final String XML_FILE_EXTENSION = ".xml";
    public static final String COMMAND_WORD = "store";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the storage location. "
            + "Parameters: store [FILEPATH]\n" 
            + "Example: " + COMMAND_WORD 
            + "data/hello_bunny" + XML_FILE_EXTENSION;      
    public static final String TOOL_TIP = "store [FILEPATH]";
    public static final String MESSAGE_SUCCESS = "Storage location changed to %1$s. You need to restart DearJim"
            + "for the changes to take effect.";
    public static final String MESSAGE_FAILURE = "Unable to write to the storage location specified. "
            + "Please choose another storage location.";
   
    public final String storageFilePath;

    public StoreCommand(String storageFilePath) {
        this.storageFilePath = storageFilePath;
    }
    
    @Override
    public CommandResult execute() {        
        assert model != null;
       
        Config config = new Config();
        config.setTaskManagerFilePath(storageFilePath);
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(storageFilePath);
        
        try {
            xmlTaskManagerStorage.saveTaskManager(model.getTaskManager(), storageFilePath);
            ConfigUtil.saveConfig(config,  Config.DEFAULT_CONFIG_FILE);
            EventsCenter.getInstance().post(new ChangeStorageFilePathEvent(storageFilePath));
            return new CommandResult(String.format(MESSAGE_SUCCESS, storageFilePath));
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

}