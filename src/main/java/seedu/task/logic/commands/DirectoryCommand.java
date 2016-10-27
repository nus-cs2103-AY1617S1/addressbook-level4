package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.ExitAppRequestEvent;
import seedu.task.commons.exceptions.DataConversionException;

/**
 * @@author A0147944U
 * Changes working task manager data to data at specified directory.
 */
public class DirectoryCommand extends Command {
    
    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    public static final String COMMAND_WORD = "directory";
    
    public static final String COMMAND_WORD_ALT = "dir";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Load TaskManager with data in given directory. \n"
            + "Parameters: directory/filename OR filename\n"
            + "Example: " + COMMAND_WORD
            + " c:/Users/user/Desktop/TaskManagerBackup1 OR TaskManagerBackup2";

    public static final String MESSAGE_NEW_DIRECTORY_SUCCESS = "New data: %1$s";
    
    public static final String MESSAGE_FILE_NOT_FOUND_ERROR = "File does not exist: %1$s";
    
    //This constant string variable is file extension of the storage file.
    private final String FILE_EXTENSION = ".xml";
    
    //This is the path of the selected storage file.
    private String _destination;
    
    public DirectoryCommand(String newFilePath) {
        
        appendExtension(newFilePath);
        //Check if file supplied by user exists
        if (new File(_destination).exists()) {
            //Retrieve Config file
            Config config = new Config();
            File configFile = new File("config.json");
            try {
                config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
            } catch (IOException e) {
                logger.warning("Error reading from config file " + "config.json" + ": " + e);
                try {
                    throw new DataConversionException(e);
                } catch (DataConversionException e1) {
                    e1.printStackTrace();
                }
            }
            
            //Change TaskManager file path
            config.setTaskManagerFilePath(_destination);
            //Save new Config
            try {
                ConfigUtil.saveConfig(config, "config.json");
            } catch (IOException e) {
                logger.warning("Error saving to config file : " + e);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Appends FILE_EXTENSION to given destination
     * This ensures user will not accidentally override non-.xml files
     */
    private void appendExtension(String destination) {
        if (destination != null) {
            _destination = destination + FILE_EXTENSION;
        }
    }
    
    /**
     * Locates TaskManager.jar file and silently run it via Windows Command Line
     */
    private void restartTaskManagerOnWindows() {
        logger.info("============================ [ Restarting Task Manager ] =============================");
        String command = "";
        String filePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\";
        command = "/c cd /d \"" + filePath + "\" & TaskManager.jar & exit";
        logger.info("DOS command generated:" + command);
         try {
            new ProcessBuilder("cmd",command).start();
        } catch (IOException e) {
            logger.warning("Error starting process. " + e);
        }

    }
    
    

    @Override
    public CommandResult execute(boolean isUndo) {
        //Check if file supplied by user exists
        if (!new File(_destination).exists())
            return new CommandResult(String.format(MESSAGE_FILE_NOT_FOUND_ERROR, _destination));
        
        assert model != null;
        restartTaskManagerOnWindows();
        //Shut down current TaskManager
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(String.format(MESSAGE_NEW_DIRECTORY_SUCCESS, _destination));
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }
    

}