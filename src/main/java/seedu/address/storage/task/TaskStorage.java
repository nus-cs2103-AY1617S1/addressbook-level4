package seedu.address.storage.task;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.events.model.AliasChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Alias;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.storage.*;
import seedu.address.storage.alias.AliasStorage;

import java.io.IOException;
import java.util.Optional;

/**
 * API of the TaskStorage component
 */
//@@author A0143107U
public interface TaskStorage extends TaskManagerStorage, UserPrefsStorage, AliasStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManagerFilePath();

    @Override
    Optional<UniqueItemCollection<Task>> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(UniqueItemCollection<Task> taskManager) throws IOException;
    
    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent tmce);
    
    @Override
    String getAliasFilePath();
    
    @Override
    Optional<UniqueItemCollection<Alias>> readAlias() throws DataConversionException, IOException;

    @Override
    void saveAlias(UniqueItemCollection<Alias> alias) throws IOException;
    
    /**
     * Saves the current version of the Alias to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAliasChangedEvent(AliasChangedEvent ace);
    

}
