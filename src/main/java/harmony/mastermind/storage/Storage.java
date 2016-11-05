package harmony.mastermind.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.storage.DataSavingExceptionEvent;
import harmony.mastermind.commons.exceptions.DataConversionException;
import harmony.mastermind.commons.exceptions.FolderDoesNotExistException;
import harmony.mastermind.commons.exceptions.UnwrittableFolderException;
import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.model.ReadOnlyTaskManager;
import harmony.mastermind.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    //initializing variables 
    static String SAVE_FILE = "data.txt";
    static final String ERROR_READ = "Unable to read from file!\nTry checking " + SAVE_FILE + " or continue using to start over.\n\n";
    static final String ERROR_CREATE = "Problem creating file!";
    static final String ERROR_NOT_FOUND = "File not found";
    static final String NULL = "-";
    static final String SPACE = " ";
    
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManagerFilePath();

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, FileNotFoundException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;
        
    /**
     * Saves the current version of the Mastermind to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent tmce);
}
