package guitests;

import guitests.guihandles.TodoCardHandle;
import org.junit.Test;

import seedu.simply.commons.core.Messages;
import seedu.simply.logic.commands.AddCommand;
import seedu.simply.testutil.TestTodo;
import seedu.simply.testutil.TestUtil;
import seedu.simply.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class AddTodoCommandTest extends SimplyGuiTest {

    @Test
    public void add() {
        //add one person
        TestTodo[] currentList = td.getTypicalTodo();
        TestTodo todoToAdd = TypicalTestTasks.todo8;
        assertAddSuccess(todoToAdd, currentList);
        currentList = TestUtil.addTodosToList(currentList, todoToAdd);

        //add another person
        todoToAdd = TypicalTestTasks.todo9;
        assertAddSuccess(todoToAdd, currentList);
        currentList = TestUtil.addTodosToList(currentList, todoToAdd);

        //add duplicate person
        commandBox.runCommand(TypicalTestTasks.todo8.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(todoListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.todo1);

        //invalid command
        commandBox.runCommand("adds dead");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTodo personToAdd, TestTodo... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        TodoCardHandle addedCard = todoListPanel.navigateToPerson(personToAdd.getName().toString());
        assertTodoMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTodo[] expectedList = TestUtil.addTodosToList(currentList, personToAdd);
        assertTrue(todoListPanel.isListMatching(expectedList));
    }

}
