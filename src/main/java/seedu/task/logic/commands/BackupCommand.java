//@@author A0147944U
package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;

import org.apache.commons.io.FileUtils;

/**
 * Saves task manager data at specified directory.
 */
public class BackupCommand extends Command {
    
    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves TaskManager in given directory. \nIf only filename is given, file is saved in root directory of TaskManager. \n"
            + "Parameters: directory/filename OR filename\n"
            + "Example: " + COMMAND_WORD
            + " c:/Users/user/Desktop/TaskManagerBackup1 OR TaskManagerBackup2";

    public static final String MESSAGE_BACKUP_SUCCESS = "Backup successful: %1$s";
    
    public static final String MESSAGE_BACKUP_FAILURE = "Backup unsuccessful: %1$s , invalid location";

//    private final Task toBackup;
//    private final Model model;
    
    //This constant string variable is file extension of the storage file.
    private final String FILE_EXTENSION = ".xml";
    
    //This is the path of the current data file.
    private String _source;
    
    //This is the path of the storage file.
    private String _destination;
    
    public BackupCommand(String destination) {
        setDestination(destination);
        setSource();
        File newFile = new File(this._destination);
        File source = new File(this._source);
        if (!source.exists()) {
            return;
        }
        if (!FileUtil.isFileExists(newFile)) {
                try {
                    FileUtil.createFile(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        try {
            FileUtils.copyFile(source, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setDestination(String destination) {
        if (destination != null) {
            
            _destination = destination + FILE_EXTENSION;
        }
    }
    
    public void setSource() {
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
        _source = config.getTaskManagerFilePath();
    }

    

    @Override
    public CommandResult execute(boolean isUndo) {
        //assert _directory != null;
        boolean check = new File(_destination).exists();
        if (!check)
            return new CommandResult(String.format(MESSAGE_BACKUP_FAILURE, _destination));
        return new CommandResult(String.format(MESSAGE_BACKUP_SUCCESS, _destination));
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }

}
