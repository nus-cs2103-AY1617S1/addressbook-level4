package seedu.todo.logic;

import com.google.common.eventbus.Subscribe;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.model.ToDoListChangedEvent;
import seedu.todo.commons.events.ui.JumpToListRequestEvent;
import seedu.todo.commons.events.ui.ShowHelpRequestEvent;
import seedu.todo.logic.commands.*;
import seedu.todo.model.Model;
import seedu.todo.model.ModelManager;
import seedu.todo.model.ReadOnlyToDoList;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.*;
import seedu.todo.storage.StorageManager;

import seedu.todo.testutil.TestDataHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.todo.commons.core.Messages.*;

public class AuxLogicTest extends CommandLogicTest {

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
