package seedu.taskitty.logic.commands;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.ButtonType;
import seedu.taskitty.commons.util.ConfigUtil;
import seedu.taskitty.commons.util.FileUtil;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.commons.util.UiUtil;
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
 * 
 * @author JiaWern
 *
 */
public class PathCommand extends Command {

    public static final String COMMAND_WORD = "path";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD + " <filepath>.xml";
    public static final String MESSAGE_USAGE = "This command saves data to/loads data from a location of your choice, Meow!\n";
    public static final String MESSAGE_VALID_FILEPATH_USAGE = "Filepath must end with .xml";

    public static final String MESSAGE_SAVE_SUCCESS = "Data saved to: %1$s";
    public static final String MESSAGE_LOAD_SUCCESS = "Data loaded from: %1$s";
    public static final String MESSAGE_CANCELLED = "Save function cancelled.";
    public static final String MESSAGE_FAILED = "Failed to save data to: %1$s";
    public static final String MESSAGE_INVALID_FILEPATH = "Filepath is invalid. \n%1$s";
    public static final String MESSAGE_INVALID_MISSING_FILEPATH = "Filepath is invalid. \n%1$s";

    private Config config = MainApp.getConfig();
    private Storage storage = MainApp.getStorage();
    private Model model = MainApp.getModel();
    private String configFile = Config.DEFAULT_CONFIG_FILE;

    public final String filepath;

    public PathCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {

        try {
            File file = new File(filepath);
            boolean isFileExist = FileUtil.isFileExists(file);
            
            if (isFileExist) {
                return manageUserResponse(file);
            }
            
            changeConfigAndStorageFilePath(false);

            updateMainWindowAndEventCenter();

            return new CommandResult(String.format(MESSAGE_SAVE_SUCCESS, filepath));
        } catch (IOException io) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(io));
        } catch (DataConversionException dc) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(dc));
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(e));
        }
    }

    /**
     * Manages path command execution based on user response if a particular file exists
     * @param file
     * @return CommandResult based on user response
     * @throws DataConversionException
     * @throws IOException
     */
    private CommandResult manageUserResponse(File file) throws DataConversionException, IOException {
        ButtonType isUserResponseOverwrite = getUserButtonChoice(file);
        
        if (isUserResponseOverwrite == ButtonType.CANCEL) {
            return new CommandResult(MESSAGE_CANCELLED);
        } else if (isUserResponseOverwrite == UiUtil.load) {
            changeConfigAndStorageFilePath(true);
            if (storage.readTaskManager().isPresent()) {
                model.resetData(storage.readTaskManager().get());
            } 
            updateMainWindowAndEventCenter();

            return new CommandResult(String.format(MESSAGE_LOAD_SUCCESS, filepath));
        } else {
            changeConfigAndStorageFilePath(false);
            updateMainWindowAndEventCenter();

            return new CommandResult(String.format(MESSAGE_SAVE_SUCCESS, filepath));
        }
    }

    /**
     * Updates Main Window and Event Center after path command has been run
     */
    private void updateMainWindowAndEventCenter() {
        MainWindow.getStatusBarFooter().setSaveLocation(config.getTaskManagerFilePath());

        EventsCenter.getInstance().post(new PathLocationChangedEvent(config.getTaskManagerFilePath()));
    }

    /**
     * Changes file path in config and storage
     * @param isLoad
     * @throws IOException
     * @throws DataConversionException
     */
    private void changeConfigAndStorageFilePath(boolean isLoad) throws IOException, DataConversionException {
        config.setTaskManagerFilePath(filepath);
        ConfigUtil.saveConfig(config, configFile);

        storage.setFilePath(config.getTaskManagerFilePath(), isLoad);
    }

    /**
     * Gets the button result after user has chosen
     * @param file
     * @return
     * @throws DataConversionException
     * @throws IOException
     */
    private ButtonType getUserButtonChoice(File file) throws DataConversionException, IOException {
        return storage.getButton(file);
    }
}
