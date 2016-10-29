package seedu.ggist.logic.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.ggist.commons.core.Config;
import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.events.storage.ChangeSaveFileEvent;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.ConfigUtil;
import seedu.ggist.commons.util.FileUtil;
import seedu.ggist.model.ReadOnlyTaskManager;
import seedu.ggist.storage.XmlFileStorage;
import seedu.ggist.storage.XmlSerializableTaskManager;

//@@author A0138411N
/**
 * Saves data to a specified location
 */
public class SaveCommand extends Command{

        public static final String COMMAND_WORD = "save";
        
        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves data to a specified valid location.\n"
                + "Parameters: filepath\n"
                + "Example: " + COMMAND_WORD
                + " C:/Users/Documents/OneDrive/data or ggist2.xml";
     
    public static final String MESSAGE_SUCCESS = "File location successfully changed to %1$s.";
    private static final String MESSAGE_FAIL = "Specified directory does not exists";
    private static final String DEFAULT_FILENAME = "/ggist.xml";
    private static final String DEFAULT_FILE_FOLDER = "data/";
    private static final String DEFAULT_FILE_EXTENSION = "xml";
    
    private String filePath;

    public SaveCommand(String filePath) throws IllegalValueException {
        assert filePath != null;
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        if (new File(filePath).isDirectory()) {
            this.filePath = filePath + DEFAULT_FILENAME;
        } else if (!Files.exists(Paths.get(filePath)) && DEFAULT_FILE_EXTENSION.equals(extension)) {
            this.filePath = DEFAULT_FILE_FOLDER + filePath;
        } else {
            throw new IllegalValueException(MESSAGE_FAIL);
        }
   
    }

    
    @Override
    public CommandResult execute() {
        assert model != null;
        
       ReadOnlyTaskManager taskManager = model.getTaskManager();
        
        File file = new File(filePath);

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
        indicateCorrectCommandExecuted();
        return new CommandResult(String.format(MESSAGE_SUCCESS , filePath));
    }
    
    @Override
    public  String toString(){
        return COMMAND_WORD;
    }
    
}


