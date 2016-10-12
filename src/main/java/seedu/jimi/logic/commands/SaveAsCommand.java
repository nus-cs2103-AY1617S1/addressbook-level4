package seedu.jimi.logic.commands;

import java.util.Optional;
import seedu.jimi.commons.core.Config;
import seedu.jimi.commons.exceptions.DataConversionException;
import seedu.jimi.commons.util.ConfigUtil;

/**
 *  
 * @author Wei Yin 
 * 
 * Sets save directory for the tasks and events in Jimi.
 */
public class SaveAsCommand extends Command {

    public static final String COMMAND_WORD = "saveas";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set a new save directory for all your tasks and events in Jimi.\n"
            + "Parameters: FILEPATH/FILENAME.xml \n"
            + "Example: " + COMMAND_WORD
            + " C:/dropbox/taskbook.xml";

    public static final String MESSAGE_SUCCESS = "Save directory changed: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This save directory is originally used in Jimi";
    public static final String CONFIG_FILE_NOT_FOUND = "Config file is not found. Using default config file properties...";

    private String taskBookFilePath;

    /**
     * Convenience constructor using raw values.
     */
    public SaveAsCommand(String filePath)  {
        this.taskBookFilePath = filePath;
    }
    
    @Override
    public CommandResult execute() {
        String defaultConfigFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(defaultConfigFilePathUsed);
            Config config = configOptional.orElse(new Config());
            config.setTaskBookFilePath(taskBookFilePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskBookFilePath));
        } catch (DataConversionException e) {
            Config config = new Config();
            config.setTaskBookFilePath(taskBookFilePath);
            return new CommandResult(String.format(CONFIG_FILE_NOT_FOUND, MESSAGE_SUCCESS, taskBookFilePath));
        }
    }

}
