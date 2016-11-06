package seedu.address.testutil;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;

//@@author A0093960X
/**
 * A Stub class for the Storage component
 *
 */
public class StorageStub extends ComponentManager implements Storage{

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException { 
        return;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        return;
    }

    @Override
    public String getTaskManagerFilePath() {
        return null;
    }

    @Override
    public void setTaskManagerFilePath(String filePath) {
        return;
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        return;       
    }

    @Override
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        return;
    }

}
