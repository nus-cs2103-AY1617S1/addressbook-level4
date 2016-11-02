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

// @@author A0147944U
/**
 * Saves task manager data at specified directory.
 */
public class BackupCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    public static final String COMMAND_WORD = "backup";

    public static final String COMMAND_WORD_ALT = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves TaskManager in given directory. \n"
            + "If only filename is given, file is saved in root directory of TaskManager. \n"
            + "Parameters: directory/filename OR filename\n"
            + "Example: " + COMMAND_WORD + " c:/Users/user/Desktop/TaskManagerBackup1 OR TaskManagerBackup2";

    public static final String MESSAGE_BACKUP_SUCCESS = "Backup successful: %1$s %1$s";

    public static final String MESSAGE_BACKUP_FAILURE = "Backup unsuccessful: %1$s , invalid location";

    public static final String MESSAGE_BACKUP_ERROR = "Backup unsuccessful: %1$s , data mismatch";

    /* This constant string variable is file extension of the storage file */
    private final String FILE_EXTENSION = ".xml";

    /* This is the path of the current data file */
    private String _source;

    /* This is the path of the backup data file */
    private String _destination;

    /*
     * This is information if the command had overwritten an existing data file
     * or created a new data file
     */
    private String _overwrite;

    public BackupCommand(String destination) {
        // Prepare files
        appendExtension(destination);
        getCurrentData();
        File newFile = new File(this._destination);
        File source = new File(this._source);

        // Cancel attempt if unable to retrieve source
        if (!source.exists()) {
            return;
        }
        createFileIfNotExisting(newFile);
        copyData(newFile, source);
    }

    /**
     * Replaces data in Config file with the updated data
     */
    private void copyData(File newFile, File source) {
        // Copy current data to
        try {
            FileUtils.copyFile(source, newFile);
        } catch (IOException e) {
            logger.warning("Error copying current data to defined backup file.");
            e.printStackTrace();
        }
    }

    /**
     * Creates file on drive if it does not exist
     */
    private void createFileIfNotExisting(File newFile) {
        if (!FileUtil.isFileExists(newFile)) {
            // Update string on creation of file
            _overwrite = "created";
            try {
                FileUtil.createFile(newFile);
            } catch (IOException e) {
                logger.warning("Error creating defined backup file.");
                e.printStackTrace();
            }
        } else {
            // Update string on overwriting a file
            _overwrite = "overwritten";
        }
    }

    /**
     * Appends FILE_EXTENSION to given destination.
     * This ensures user will not accidentally override non-xml files.
     */
    private void appendExtension(String destination) {
        if (destination != null) {
            _destination = destination + FILE_EXTENSION;
        }
    }

    /**
     * Read config file to determine location of current data accessed in TaskManager.
     */
    private void getCurrentData() {
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

        /**
         * Check if new backup file was not created
         * Possible scenario where file was not created:
         * Given path is protected and thus inaccessible by TaskManager or Given path can not exist
         * i.e. invalid drive letter, invalid characters
         */
        assert _destination != null;
        if (!FileUtil.isFileExists(new File(_destination))) {
            return new CommandResult(String.format(MESSAGE_BACKUP_FAILURE, _destination));
        }

        /**
         * Check if new backup file data matches the current data.
         * Possible scenario where it doesn't match:
         * If a file of same path as given already exists and is write-protected
         */
        try {
            String destinationFileData = FileUtil.readFromFile(new File(_destination));
            String sourceFileData = FileUtil.readFromFile(new File(_source));
            if (!destinationFileData.equals(sourceFileData)) {
                return new CommandResult(String.format(MESSAGE_BACKUP_ERROR, _destination));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Considered successful if it passes the two tests above
         */
        return new CommandResult(String.format(MESSAGE_BACKUP_SUCCESS, _destination, _overwrite));
    }

    @Override
    public CommandResult execute(int index) {
        return null;
    }

}
