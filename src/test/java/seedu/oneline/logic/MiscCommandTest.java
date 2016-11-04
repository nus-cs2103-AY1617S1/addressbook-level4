package seedu.oneline.logic;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.commands.*;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.oneline.commons.core.Messages.*;

public class MiscCommandTest extends LogicTestManager {
    
    @Test
    public void parseCommand_invalidCommand_invalidFormatMessage() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseCommand_unknownCommand_unknownMessage() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void helpCommand_help_helpShown() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void exitCommand_exit_programExit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void clearCommand_clear_tasksCleared() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), Collections.emptyList());
    }

 
    @Test
    public void listCommand_list_showsAllTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedTB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare task book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTB,
                expectedList);
    }

    @Test
    public void selectCommand_invalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = Messages.getInvalidCommandFormatMessage(SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void sellectCommand_invalidIndex_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void selectCommand_validIndex_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateTaskBook(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }
    
    @Test
    public void undoCommandRedoCommand_undoRedo_changesMade() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithName("Simple task");
        Task task2 = helper.generateTaskWithName("Harder task");
        Task task3 = helper.generateTaskWithName("Hardest task");
        TaskBook expectedTaskBook1 = new TaskBook(model.getTaskBook());
        logic.execute(helper.generateAddCommand(task1));
        TaskBook expectedTaskBook2 = new TaskBook(model.getTaskBook());
        logic.execute(helper.generateAddCommand(task2));
        TaskBook expectedTaskBook3 = new TaskBook(model.getTaskBook());
        logic.execute(helper.generateAddCommand(task3));

        // Undo command
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook3, Arrays.asList(task2, task1));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook2, Arrays.asList(task1));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook1, Collections.emptyList());
        assertCommandBehavior("undo", UndoCommand.MESSAGE_NO_PREVIOUS_STATE, expectedTaskBook1, Collections.emptyList());
        
        // Redo command
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook2, Arrays.asList(task1));
        assertCommandBehavior("redo", RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook3, Arrays.asList(task2, task1));
        Task task4 = helper.generateTaskWithName("Crazy task");
        logic.execute(helper.generateAddCommand(task4));
        TaskBook expectedTaskBook4 = new TaskBook(model.getTaskBook());
        assertCommandBehavior("redo", RedoCommand.MESSAGE_NO_NEXT_STATE, expectedTaskBook4, Arrays.asList(task4, task2, task1));
        
        // Undo find command
        assertCommandBehavior(FindCommand.COMMAND_WORD + " harder", FindCommand.getMessageForTaskListShownSummary(1), expectedTaskBook4, Arrays.asList(task2));
        assertCommandBehavior("undo", UndoCommand.MESSAGE_UNDO_SUCCESS, expectedTaskBook4, Arrays.asList(task4, task2, task1));
        assertCommandBehavior(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_REDO_SUCCESS, expectedTaskBook4, Arrays.asList(task2));
        
    }
}
