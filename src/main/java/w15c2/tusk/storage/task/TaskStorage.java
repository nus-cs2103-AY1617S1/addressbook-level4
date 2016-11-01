package w15c2.tusk.storage.task;

import java.io.IOException;
import java.util.Optional;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.events.model.AliasChangedEvent;
import w15c2.tusk.commons.events.model.TaskManagerChangedEvent;
import w15c2.tusk.commons.events.storage.DataSavingExceptionEvent;
import w15c2.tusk.commons.exceptions.DataConversionException;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.UserPrefs;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.storage.*;
import w15c2.tusk.storage.alias.AliasStorage;

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
