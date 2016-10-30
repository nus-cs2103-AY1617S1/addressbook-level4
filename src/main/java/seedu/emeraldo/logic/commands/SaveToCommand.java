package seedu.emeraldo.logic.commands;

import java.io.IOException;

import seedu.emeraldo.commons.core.Config;
import seedu.emeraldo.commons.core.EventsCenter;
import seedu.emeraldo.commons.events.storage.SaveLocationChangedEvent;
import seedu.emeraldo.commons.util.ConfigUtil;
import seedu.emeraldo.storage.StorageManager;

public class SaveToCommand extends Command{
    
    public static final String COMMAND_WORD = "saveto";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the location of the xml data file"
            + "Parameters: filepath"
            + "Example: " + COMMAND_WORD
            + " C:/emeraldo_task/";
    
    public static final String MESSAGE_SUCCESS = "Save location changed";
    
    public static final String MESSAGE_ERROR = "Failed to change save location";
    
    public static final String FILE_NAME = "emeraldo.xml";
    
    private String filepath;
    
    
    public SaveToCommand(String filepath){
        this.filepath = filepath;
    }
    
    public CommandResult execute() {
        /*
        TO-DO:
            change filepath in XmlEmeraldoStorage
            change filepath in Config.java
            indicateEmeraldoSaveLocationChanged()
        */

        try {
            filepath = filepath + FILE_NAME;
            Config config = new Config();
            config.setEmeraldoFilePath(filepath);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            EventsCenter.getInstance().post(new SaveLocationChangedEvent(filepath));
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_SUCCESS);
        }
        
        
    }

}
