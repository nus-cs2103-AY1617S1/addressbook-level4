//@@author A0147944U
package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.ExitAppRequestEvent;

//import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Saves task manager data at specified directory.
 */
public class DirectoryCommand extends Command {
    
    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    public static final String COMMAND_WORD = "directory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Load TaskManager with data in given directory. \n"
            + "Parameters: directory/filename OR filename\n"
            + "Example: " + COMMAND_WORD
            + " c:/Users/user/Desktop/TaskManagerBackup1 OR TaskManagerBackup2";

    public static final String MESSAGE_NEW_DIRECTORY_SUCCESS = "New data: %1$s";
    
    public static final String MESSAGE_FILE_NOT_FOUND_ERROR = "File does not exist: %1$s";
    
    //This constant string variable is file extension of the storage file.
    private final String FILE_EXTENSION = ".xml";
    
    //This is the path of the storage file.
    private String _newFilePath;
    
    public DirectoryCommand(String newFilePath) {
        appendExtension(newFilePath);
        boolean check = new File(_newFilePath).exists();
        if (check) {
            Config config = new Config();
            File configFile = new File("config.json");
            try {
                config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
            } catch (IOException e) {
                logger.warning("Error reading from config file " + "config.json" + ": " + e);
                try {
                    throw new DataConversionException(e);
                } catch (DataConversionException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
            config.setTaskManagerFilePath(_newFilePath);
            
            try {
                ConfigUtil.saveConfig(config, "config.json");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
               
        }
    }
    
    public void appendExtension(String filepath) {
        if (filepath != null) {
            _newFilePath = filepath + FILE_EXTENSION;
        }
    }

    @Override
    public CommandResult execute(boolean isUndo) {
        boolean check = new File(_newFilePath).exists();
        if (!check)
            return new CommandResult(String.format(MESSAGE_FILE_NOT_FOUND_ERROR, _newFilePath));
        assert model != null;
        logger.info("============================ [ Restarting Task Manager ] =============================");
        String command = "";
         try {
            String filePath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\";
            command = "/c cd /d \"" + filePath + "\" & TaskManager.jar & exit";
            new ProcessBuilder("cmd",command).start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(command);

        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(String.format(MESSAGE_NEW_DIRECTORY_SUCCESS, _newFilePath));
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }
    

}