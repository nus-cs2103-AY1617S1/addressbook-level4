package seedu.emeraldo.logic.commands;

import java.io.IOException;

import seedu.emeraldo.commons.core.Config;
import seedu.emeraldo.commons.core.EventsCenter;
import seedu.emeraldo.commons.events.storage.SaveLocationChangedEvent;
import seedu.emeraldo.commons.util.ConfigUtil;

//@@author A0139342H
/**
 * Changes the save location of emeraldo.xml
 */
public class SaveToCommand extends Command{
    
    public static final String COMMAND_WORD = "saveto";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the location of the xml data file.\n"
            + "Parameters: FILEPATH\n"
            + "Example: " + COMMAND_WORD
            + " C:/emeraldo_task/";
    
    public static final String MESSAGE_SUCCESS = "Save location changed to %1$s";
    
    public static final String MESSAGE_ERROR = "Failed to change save location";
    
    public static final String FILE_NAME = "emeraldo.xml";
    
    public static final String DEFAULT_LOCATION = "./data/";
    
    private String filepath;
    
    
    public SaveToCommand(String filepath){
        this.filepath = filepath;
    }
    
    public CommandResult execute() {

        try {
            filepath = filepath + FILE_NAME;
            
            Config config = new Config();
            config.setEmeraldoFilePath(filepath);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            
            EventsCenter.getInstance().post(new SaveLocationChangedEvent(filepath));
            return new CommandResult(String.format(MESSAGE_SUCCESS, filepath));
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }
        
        
    }

}
