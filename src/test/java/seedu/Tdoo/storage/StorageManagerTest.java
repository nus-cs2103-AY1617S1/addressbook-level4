package seedu.Tdoo.storage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.Tdoo.commons.core.LogsCenter;
import seedu.Tdoo.commons.events.model.DeadlineListChangedEvent;
import seedu.Tdoo.commons.events.model.EventListChangedEvent;
import seedu.Tdoo.commons.events.model.TodoListChangedEvent;
import seedu.Tdoo.commons.events.storage.DataSavingExceptionEvent;
import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.model.ReadOnlyTaskList;
import seedu.Tdoo.model.TaskList;
import seedu.Tdoo.model.UserPrefs;
import seedu.Tdoo.model.task.*;
import seedu.Tdoo.model.task.attributes.EndDate;
import seedu.Tdoo.model.task.attributes.EndTime;
import seedu.Tdoo.model.task.attributes.Name;
import seedu.Tdoo.model.task.attributes.Priority;
import seedu.Tdoo.model.task.attributes.StartDate;
import seedu.Tdoo.model.task.attributes.StartTime;
import seedu.Tdoo.storage.JsonUserPrefsStorage;
import seedu.Tdoo.storage.Storage;
import seedu.Tdoo.storage.StorageManager;
import seedu.Tdoo.storage.XmlDeadlineListStorage;
import seedu.Tdoo.storage.XmlEventListStorage;
import seedu.Tdoo.storage.XmlTodoListStorage;
import seedu.Tdoo.testutil.EventsCollector;
import seedu.Tdoo.testutil.TypicalTestDeadline;
import seedu.Tdoo.testutil.TypicalTestEvent;
import seedu.Tdoo.testutil.TypicalTestTask;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageManagerTest {

	private StorageManager storageManager;

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Before
	// @@author A0132157M reused
	public void setup() {
		storageManager = new StorageManager(getTempFilePath("TodoList.xml"), getTempFilePath("EventList.xml"),
				getTempFilePath("DeadlineList.xml"), getTempFilePath("prefs"));
	}

	private String getTempFilePath(String fileName) {
		return testFolder.getRoot().getPath() + fileName;
	}

	/*
	 * Note: This is an integration test that verifies the StorageManager is
	 * properly wired to the {@link JsonUserPrefsStorage} class. More extensive
	 * testing of UserPref saving/reading is done in {@link
	 * JsonUserPrefsStorageTest} class.
	 */

	@Test
	public void prefsReadSave() throws Exception {
		UserPrefs original = new UserPrefs();
		original.setGuiSettings(300, 600, 4, 6);
		storageManager.saveUserPrefs(original);
		UserPrefs retrieved = storageManager.readUserPrefs().get();
		assertEquals(original, retrieved);
	}

	@Test
	public void TodoListReadSave() throws Exception {
		TaskList original = new TypicalTestTask().getTypicalTodoList();
		LogsCenter.getLogger(StorageManagerTest.class).info("XXXXXX: " + original.getTasks());
		storageManager.saveTodoList(original);
		ReadOnlyTaskList retrieved = storageManager.readTodoList().get();
		LogsCenter.getLogger(StorageManagerTest.class).info("gretrieved et(): " + retrieved.toString());

		assertEquals(original, new TaskList(retrieved));
		// More extensive testing of TodoList saving/reading is done in
		// XmlTodoListStorageTest
	}

	@Test
	// @@author A0132157M reused
	public void EventListReadSave() throws Exception {
		TaskList original = new TypicalTestEvent().getTypicalEventList();
		storageManager.saveEventList(original);
		ReadOnlyTaskList retrieved = storageManager.readEventList().get();
		assertEquals(original, new TaskList(retrieved));
	}

	@Test
	// @@author A0132157M reused
	public void DeadlineListReadSave() throws Exception {
		TaskList original = new TypicalTestDeadline().getTypicalDeadlineList();
		storageManager.saveDeadlineList(original);
		ReadOnlyTaskList retrieved = storageManager.readDeadlineList().get();
		assertEquals(original, new TaskList(retrieved));
	}

	@Test
	public void getTodoListFilePath() {
		assertNotNull(storageManager.getTodoListFilePath());
	}

	@Test
	// @@author A0132157M reused
	public void getEventListFilePath() {
		assertNotNull(storageManager.getEventListFilePath());
	}

	@Test
	// @@author A0132157M reused
	public void getDeadlineListFilePath() {
		assertNotNull(storageManager.getDeadlineListFilePath());
	}

	@Test
	public void handleTodoListChangedEvent_exceptionThrown_eventRaised() throws IOException, IllegalValueException {
		// Create a StorageManager while injecting a stub with wrong format that
		// throws an exception when the save method is called
		Storage storage = new StorageManager(new XmlTodoListStorageExceptionThrowingStub("dummy"),
				new XmlEventListStorageExceptionThrowingStub("dummy"),
				new XmlDeadlineListStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
		EventsCollector eventCollector = new EventsCollector();
		storage.handleTodoListChangedEvent(new TodoListChangedEvent(new TodoListExceptionThrowingStub()));
		assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
	}

	@Test
	// @@author A0132157M reused
	public void handleEventListChangedEvent_exceptionThrown_eventRaised() throws IOException, IllegalValueException {
		// Create a StorageManager while injecting a stub that throws an
		// exception when the save method is called
		Storage storage = new StorageManager(null, new XmlEventListStorageExceptionThrowingStub("dummy"), null,
				new JsonUserPrefsStorage("dummy"));
		EventsCollector eventCollector = new EventsCollector();
		storage.handleEventListChangedEvent(new EventListChangedEvent(new EventListExceptionThrowingStub()));
		assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
	}

	@Test
	// @@author A0132157M reused
	public void handleDeadlineListChangedEvent_exceptionThrown_eventRaised() throws IOException, IllegalValueException {
		// Create a StorageManager while injecting a stub that throws an
		// exception when the save method is called
		Storage storage = new StorageManager(null, null, new XmlDeadlineListStorageExceptionThrowingStub("dummy"),
				new JsonUserPrefsStorage("dummy"));
		EventsCollector eventCollector = new EventsCollector();
		storage.handleDeadlineListChangedEvent(new DeadlineListChangedEvent(new DeadlineListExceptionThrowingStub()));
		assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
	}

	// @@author A0144061U
	/**
	 * A Todo Stub class to throw wrong format exception when saving data
	 */
	class TodoListExceptionThrowingStub extends TaskList {

		public TodoListExceptionThrowingStub() throws IllegalValueException {
			super();
			Task wrongFormatTask = new Event(new Name("Assignment"), new StartDate("12-12-2016"),
					new EndDate("14-12-2016"), new StartTime("14:00"), new EndTime("16:00"), "done");
			super.addTask(wrongFormatTask);
		}
	}

	// @@author A0144061U
	/**
	 * A Event Stub class to throw wrong format exception when saving data
	 */
	class EventListExceptionThrowingStub extends TaskList {

		public EventListExceptionThrowingStub() throws IllegalValueException {
			super();
			Task wrongFormatTask = new Todo(new Name("Assignment"), new StartDate("12-12-2016"),
					new EndDate("14-12-2016"), new Priority("HIGH"), "done");
			super.addTask(wrongFormatTask);
		}
	}

	/**
	 * A Deadline Stub class to throw wrong format exception when saving data
	 */
	class DeadlineListExceptionThrowingStub extends TaskList {

		public DeadlineListExceptionThrowingStub() throws IllegalValueException {
			super();
			Task wrongFormatTask = new Todo(new Name("Assignment"), new StartDate("12-12-2016"),
					new EndDate("14-12-2016"), new Priority("HIGH"), "done");
			super.addTask(wrongFormatTask);
		}
	}

	// @@author A0144061U
	/**
	 * A Stub class to throw an exception when the save method is called
	 */
	class XmlTodoListStorageExceptionThrowingStub extends XmlTodoListStorage {

		public XmlTodoListStorageExceptionThrowingStub(String filePath) {
			super(filePath);
		}

		// @Override
		public void saveTodoList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
			throw new IOException("dummy exception");
		}
	}

	// @@author A0132157M reused
	class XmlEventListStorageExceptionThrowingStub extends XmlEventListStorage {

		public XmlEventListStorageExceptionThrowingStub(String filePath) {
			super(filePath);
		}

		// @Override
		public void saveEventList(ReadOnlyTaskList EventList, String filePath) throws IOException {
			throw new IOException("dummy exception");
		}
	}

	// @@author A0132157M reused
	class XmlDeadlineListStorageExceptionThrowingStub extends XmlDeadlineListStorage {

		public XmlDeadlineListStorageExceptionThrowingStub(String filePath) {
			super(filePath);
		}

		// @Override
		public void saveDeadlineList(ReadOnlyTaskList DeadlineList, String filePath) throws IOException {
			throw new IOException("dummy exception");
		}
	}

}
