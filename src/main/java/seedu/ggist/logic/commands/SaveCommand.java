package seedu.ggist.logic.commands;

import java.io.File;
import java.util.Optional;

import seedu.ggist.commons.core.Config;
import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.events.storage.ChangeSaveFileEvent;
import seedu.ggist.commons.util.ConfigUtil;
import seedu.ggist.commons.util.FileUtil;
import seedu.ggist.model.ReadOnlyTaskManager;
import seedu.ggist.storage.XmlFileStorage;
import seedu.ggist.storage.XmlSerializableTaskManager;


/**
 * Saves data to a specified location
 */
public class SaveCommand extends Command{

        public static final String COMMAND_WORD = "save";
        
        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves data to a specified valid location.\n"
                + "Parameters: filepath"
                + "Example: " + COMMAND_WORD
                + " /OneDrive/data";
     
    public static final String MESSAGE_SUCCESS = "File location successfully changed to %1$s.";
    private static final String DEFAULT_FILENAME = "/ggist.xml";
    
    private final String filePath;

    public SaveCommand(String filePath) {
        assert filePath != null;
        this.filePath = filePath + DEFAULT_FILENAME;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
       ReadOnlyTaskManager taskManager = model.getTaskManager();
        
        File file = new File(filePath);
        if (file.exists()) {
            return new CommandResult(Messages.MESSAGE_DEST_FILE_EXISTS);
        }
        try {
            FileUtil.createIfMissing(file);
            XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
        } catch (Exception e) {
            return new CommandResult(e.getMessage());
        }
        
        Config updateConfig;
        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            updateConfig = configOptional.get();
            updateConfig.setTaskManagerFilePath(filePath);
            ConfigUtil.saveConfig(updateConfig, Config.DEFAULT_CONFIG_FILE);
        } catch (Exception e) {
            updateConfig = new Config();
        }
        EventsCenter.getInstance().post(new ChangeSaveFileEvent(filePath));
        return new CommandResult(String.format(MESSAGE_SUCCESS , filePath));
    }
    
    @Override
    public  String toString(){
        return COMMAND_WORD;
    }
    
}


