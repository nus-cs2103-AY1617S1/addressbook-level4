package seedu.task.logic.commands;

import java.io.File;
import java.io.IOException;
import seedu.task.commons.core.Config;
import org.apache.commons.io.FileUtils;

/**
 * Saves task manager data at specified directory.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves TaskManager in given directory. \nIf only filename is given, file is saved in root directory of TaskManager. \n"
            + "Parameters: directory/filename OR filename\n"
            + "Example: " + COMMAND_WORD
            + " c:/Users/user/Desktop/TaskManagerBackup1 OR TaskManagerBackup2";

    public static final String MESSAGE_BACKUP_SUCCESS = "Backup successful: %1$s";

//    private final Task toBackup;
//    private final Model model;

    //This constant string variable is used to append messages for readability.
    private final String STRING_EMPTY = "";
    
    //This constant string variable is file extension of the storage file.
    private final String FILE_EXTENSION = ".xml";
    
    //This is the path of the current data file.
    private String _source;
    
    //This is the path of the storage file.
    private String _directory;
    
    public BackupCommand(String directory) {
        setDirectory(directory);
        setSource();
        File newFile = new File(this._directory);
        File source = new File(this._source);
        if (!source.exists()) {
            return;
        }
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            FileUtils.copyFile(source, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setDirectory(String directory) {
        if (directory != null) {
            _directory = directory + FILE_EXTENSION;
        }
    }
    
    public void setSource() {
        Config newConfig = new Config();
        _source = newConfig.getTaskManagerFilePath();
    }

    

    @Override
    public CommandResult execute(boolean isUndo) {
        assert _directory != null;
        //return new CommandResult(MESSAGE_BACKUP_SUCCESS);
        return new CommandResult(String.format(MESSAGE_BACKUP_SUCCESS, _directory));
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
