package seedu.address.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.*;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.EventsCollector;
import seedu.address.testutil.ToDoBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Model model;
    private Logic logic;
    private EventsCollector eventsCollector;
    private LocalDateTime now = LocalDateTime.now();
    private File toDoListFile;
    private File userPrefsFile;

    @Before
    public void setup() throws IOException {
        model = new ModelManager();

        toDoListFile = folder.newFile();
        userPrefsFile  = folder.newFile();
        logic = new LogicManager(model, new StorageManager(
            toDoListFile.getAbsolutePath(),
            userPrefsFile.getAbsolutePath()
        ));

        eventsCollector = new EventsCollector();
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_emptyString() {
        CommandResult result = logic.execute("");
        assertTrue(result.hasError());
        assertEquals(Messages.MESSAGE_MISSING_COMMAND_WORD, result.getFeedback());
    }

    @Test
    public void execute_unknownCommand() {
        CommandResult result = logic.execute("unknownCommand");
        assertTrue(result.hasError());
        assertEquals(Messages.MESSAGE_UNKNOWN_COMMAND, result.getFeedback());
    }

    @Test
    public void execute_add_missingTitle() {
        CommandResult result = logic.execute("add");
        assertTrue(result.hasError());
        assertEquals(Messages.MESSAGE_MISSING_TODO_TITLE, result.getFeedback());

        assertFalse(wasToDoListChangedEventPosted());
    }

    @Test
    public void execute_add_title() throws IllegalValueException {
        CommandResult result = logic.execute("add valid title");
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(new ToDoBuilder("valid title").build()));
    }

    @Test
    public void execute_add_titleDueDate() throws IllegalValueException {
        CommandResult result = logic.execute("add valid title by 10 Feb 2016 11:59");
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("valid title")
                .withDueDate(LocalDateTime.of(2016, 2, 10, 11, 59))
                .build()));
    }

    @Test
    public void execute_add_titleDateRange() throws IllegalValueException {
        String command = "add valid title from 10 Dec 2016 11:59 to 11 Apr 2017 23:10";
        CommandResult result = logic.execute(command);
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("valid title")
                .withDateRange(
                    LocalDateTime.of(2016, 12, 10, 11, 59),
                    LocalDateTime.of(2017, 4, 11, 23, 10)
                )
                .build()));
    }

    @Test
    public void execute_add_titleTags() throws IllegalValueException {
        String command = "add valid title #tag1 #tag2";
        CommandResult result = logic.execute(command);
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("valid title")
                .withTags(
                    "tag1", "tag2"
                )
                .build()));
    }

    @Test
    public void execute_add_titleDueDateTags() throws IllegalValueException {
        String command = "add valid title by 1 Mar 2016 20:01 #tag1 #tag2";
        CommandResult result = logic.execute(command);
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("valid title")
                .withTags(
                    "tag1", "tag2"
                )
                .withDueDate(
                    LocalDateTime.of(2016, 3, 1, 20, 1)
                )
                .build()));
    }

    @Test
    public void execute_edit_invalidIndex() throws IllegalValueException {
        String command = "edit 10";
        CommandResult result = logic.execute(command);
        assertTrue(result.hasError());

        assertFalse(wasToDoListChangedEventPosted());
        assertEquals(String.format(Messages.MESSAGE_TODO_ITEM_INDEX_INVALID, 10), result.getFeedback());
    }

    @Test
    public void execute_edit_title() throws IllegalValueException {
        logic.execute("add title");

        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .build()));

        String command = "edit 1 new title";
        CommandResult result = logic.execute(command);
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("new title")
                .build()));
    }

    @Test
    public void execute_edit_tags() throws IllegalValueException {
        logic.execute("add title #tag1 #tag2");

        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .withTags("tag1", "tag2")
                .build()));

        String command = "edit 1 #tag2 #tag3 #tag4";
        CommandResult result = logic.execute(command);
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .withTags("tag2", "tag3", "tag4")
                .build()));
    }

    @Test
    public void execute_edit_dueDate() throws IllegalValueException {
        logic.execute("add title by 10 Jan 1994 12:00");

        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .withDueDate(LocalDateTime.of(1994, 1, 10, 12, 0))
                .build()));

        String command = "edit 1 by 11 Apr 1995 00:12";
        CommandResult result = logic.execute(command);
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .withDueDate(LocalDateTime.of(1995, 4, 11, 0, 12))
                .build()));
    }

    @Test
    public void execute_edit_dateRange() throws IllegalValueException {
        logic.execute("add title from 10 Jan 1994 12:00 to 21 Jan 1994 13:00");

        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .withDateRange(
                    LocalDateTime.of(1994, 1, 10, 12, 0),
                    LocalDateTime.of(1994, 1, 21, 13, 0)
                )
                .build()));

        String command = "edit 1 from 10 Sep 1984 12:00 to 21 Sep 1984 13:00";
        CommandResult result = logic.execute(command);
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .withDateRange(
                    LocalDateTime.of(1984, 9, 10, 12, 0),
                    LocalDateTime.of(1984, 9, 21, 13, 0)
                )
                .build()));
    }

    @Test
    public void execute_clear() {
        logic.execute("add title from 10 Jan 1994 12:00 to 21 Jan 1994 13:00");
        logic.execute("add title2 #tag1 #tag2");

        eventsCollector.reset();
        assertFalse(wasToDoListChangedEventPosted());
        assertTrue(model.getToDoList().getToDoList().size() == 2);

        CommandResult result = logic.execute("clear");
        assertFalse(result.hasError());
        assertTrue(wasToDoListChangedEventPosted());
        assertTrue(model.getToDoList().getToDoList().size() == 0);
    }

    @Test
    public void execute_help() {
        logic.execute("help");
        assertTrue(wasShowHelpRequestEventPosted());
    }

    @Test
    public void execute_delete_invalidIndex() {
        CommandResult result = logic.execute("delete 2");
        assertTrue(result.hasError());

        assertEquals(String.format(Messages.MESSAGE_TODO_ITEM_INDEX_INVALID, 2), result.getFeedback());
    }

    @Test
    public void execute_delete_index() throws IllegalValueException {
        logic.execute("add title");
        logic.execute("add title2");

        eventsCollector.reset();
        assertFalse(wasToDoListChangedEventPosted());

        assertTrue(ifToDoExists(
            new ToDoBuilder("title2")
                .build()));

        CommandResult result = logic.execute("delete 2");
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertFalse(ifToDoExists(
            new ToDoBuilder("title2")
                .build()));
        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .build()));
    }

    @Test
    public void execute_finish_invalidIndex() {
        CommandResult result = logic.execute("finish 2");
        assertTrue(result.hasError());

        assertEquals(String.format(Messages.MESSAGE_TODO_ITEM_INDEX_INVALID, 2), result.getFeedback());
    }

    @Test
    public void execute_finish_index() throws IllegalValueException {
        logic.execute("add title");
        logic.execute("add title2");

        eventsCollector.reset();
        assertFalse(wasToDoListChangedEventPosted());

        assertTrue(ifToDoExists(
            new ToDoBuilder("title2")
                .build()));

        CommandResult result = logic.execute("finish 2");
        assertFalse(result.hasError());

        assertTrue(wasToDoListChangedEventPosted());
        assertFalse(ifToDoExists(
            new ToDoBuilder("title2")
                .build()));
        assertTrue(ifToDoExists(
            new ToDoBuilder("title")
                .build()));
    }

    @Test
    public void execute_find_keywords() throws IllegalValueException {
        logic.execute("add title");
        logic.execute("add title2");
        logic.execute("add title 3");
        logic.execute("add somethingelse");

        eventsCollector.reset();
        assertFalse(wasToDoListChangedEventPosted());

        CommandResult result = logic.execute("find title");
        assertFalse(result.hasError());

        assertFalse(wasToDoListChangedEventPosted());

        assertTrue(ifToDoExistsFiltered(
            new ToDoBuilder("title")
                .build()));
        assertTrue(ifToDoExistsFiltered(
            new ToDoBuilder("title2")
                .build()));
        assertTrue(ifToDoExistsFiltered(
            new ToDoBuilder("title 3")
                .build()));
        assertFalse(ifToDoExistsFiltered(
            new ToDoBuilder("somethingelse")
                .build()));
    }

    @Test
    public void execute_exit()  {
        logic.execute("exit");
        assertTrue(eventsCollector.hasCollectedEvent(ExitAppRequestEvent.class));
    }

    private boolean wasToDoListChangedEventPosted() {
        return eventsCollector.hasCollectedEvent(ToDoListChangedEvent.class);
    }

    private boolean wasShowHelpRequestEventPosted() {
        return eventsCollector.hasCollectedEvent(ShowHelpRequestEvent.class);
    }

    /**
     * Checks if a to-do exists in the model
     */
    private boolean ifToDoExists(ReadOnlyToDo readOnlyToDo) {
        return model.getToDoList().getToDoList().contains(readOnlyToDo);
    }

    /**
     * Checks if a to-do exists in the filtered list of model
     */
    private boolean ifToDoExistsFiltered(ReadOnlyToDo readOnlyToDo) {
        return model.getFilteredToDoList().contains(readOnlyToDo);
    }
}
