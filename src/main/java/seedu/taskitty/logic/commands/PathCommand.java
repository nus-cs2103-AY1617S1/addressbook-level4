package seedu.taskitty.logic.commands;

import java.io.IOException;

import seedu.taskitty.commons.util.ConfigUtil;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.model.Model;
import seedu.taskitty.storage.Storage;
import seedu.taskitty.ui.MainWindow;
import seedu.taskitty.MainApp;
import seedu.taskitty.commons.core.Config;
import seedu.taskitty.commons.core.EventsCenter;
import seedu.taskitty.commons.events.storage.PathLocationChangedEvent;
import seedu.taskitty.commons.exceptions.DataConversionException;

//@@author A0135793W
/**
 * Saves TasKitty data to a folder specified by the user
 * @author JiaWern
 *
 */
public class PathCommand extends Command{
    
    public static final String COMMAND_WORD = "path";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " filepath.xml";
    public static final String MESSAGE_USAGE = "This command saves data to/loads data from a location of your choice, Meow!\n";
    public static final String MESSAGE_VALID_FILEPATH_USAGE = "Filepath must end with .xml";

    public static final String MESSAGE_SUCCESS = "Data saved to: %1$s";
    public static final String MESSAGE_CANCELLED = "Save function cancelled.";
    public static final String MESSAGE_FAILED = "Failed to save data to: %1$s";
    public static final String MESSAGE_INVALID_FILEPATH = "Filepath is invalid. \n%1$s";
    public static final String MESSAGE_INVALID_MISSING_FILEPATH = "Filepath is invalid. \n%1$s";
    
    Config config = MainApp.getConfig();
    Storage storage = MainApp.getStorage();
    Model model = MainApp.getModel();
    String configFile = Config.DEFAULT_CONFIG_FILE;
    
    public final String filepath;
    
    public PathCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {
       
        try {
            if (!storage.toOverwriteOrLoad(filepath)) {
                return new CommandResult(MESSAGE_CANCELLED);
            }
            
            config.setTaskManagerFilePath(filepath);
            ConfigUtil.saveConfig(config, configFile);

            storage.setFilePath(config.getTaskManagerFilePath());
            
            if (storage.readTaskManager().isPresent()) {
                model.resetData(storage.readTaskManager().get());
            }
            
            MainWindow.getStatusBarFooter().setSaveLocation("./"+config.getTaskManagerFilePath());

            EventsCenter.getInstance().post(new PathLocationChangedEvent(config.getTaskManagerFilePath()));
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, filepath));
        } catch (IOException io) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(io));
        } catch (DataConversionException dc) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(dc));
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(e));
        } 
    }
}
