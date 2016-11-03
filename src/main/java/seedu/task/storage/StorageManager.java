package seedu.task.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.events.model.TaskBookChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.UserPrefs;
import seedu.taskcommons.core.ComponentManager;
import seedu.taskcommons.core.LogsCenter;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
/**
 * Manages storage of TaskBook data in local storage.
 * 
 */
public class StorageManager extends ComponentManager implements Storage {

	
	private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
	private XmlTaskBookStorage taskBookStorage;
	private JsonUserPrefStorage userPrefStorage;

	public StorageManager(String taskBookFilePath, String userPrefsFilePath) {
		super();
		this.taskBookStorage = new XmlTaskBookStorage(taskBookFilePath);
		this.userPrefStorage = new JsonUserPrefStorage(userPrefsFilePath);
	}

	// ================ UserPrefs methods ==============================

	@Override
	public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
		return userPrefStorage.readUserPrefs();
	}

	@Override
	public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
		userPrefStorage.saveUserPrefs(userPrefs);
	}

	// ================ TaskBook methods ==============================

	@Override
	public String getTaskBookFilePath() {
		return taskBookStorage.getTaskBookFilePath();
	}

	@Override
	// read from project main directory 
	public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {

		return readTaskBook(taskBookStorage.getTaskBookFilePath());
	}

	@Override
	//read from specified saved file path
	public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException {
		logger.fine("Attempting to read data from file: " + filePath);
		return taskBookStorage.readTaskBook(filePath);
	}

	@Override
	// save to project main directory
	public void saveTaskBook(ReadOnlyTaskBook taskBookManager) throws IOException {
		saveTaskBook(taskBookManager, taskBookStorage.getTaskBookFilePath());
	}

	@Override
	// save to specified file path
	public void saveTaskBook(ReadOnlyTaskBook taskManager, String filePath) throws IOException {
		logger.fine("Attempting to write to data file: " + filePath);
		taskBookStorage.saveTaskBook(taskManager, filePath);
	}

	
	@Override
	@Subscribe
	public void handleTaskBookChangedEvent(TaskBookChangedEvent event) {
		logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
		try {
			saveTaskBook(event.data);
		} catch (IOException e) {
			raise(new DataSavingExceptionEvent(e));
		}
	}

}
