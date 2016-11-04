package guitests;

import guitests.guihandles.DeadlineCardHandle;
import org.junit.Test;

import seedu.simply.commons.core.Messages;
import seedu.simply.logic.commands.AddCommand;
import seedu.simply.testutil.TestDeadline;
import seedu.simply.testutil.TestUtil;
import seedu.simply.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class AddDeadlineCommandTest extends SimplyGuiTest {

    @Test
    public void add() {
        //add one person
        TestDeadline[] currentList = td.getTypicalDeadline();
        TestDeadline deadlineToAdd = TypicalTestTasks.deadline8;
        assertAddSuccess(deadlineToAdd, currentList);
        currentList = TestUtil.addDeadlinesToList(currentList, deadlineToAdd);

        //add another person
        deadlineToAdd = TypicalTestTasks.deadline9;
        assertAddSuccess(deadlineToAdd, currentList);
        currentList = TestUtil.addDeadlinesToList(currentList, deadlineToAdd);

        //add duplicate person
        commandBox.runCommand(TypicalTestTasks.deadline8.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(deadlineListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.deadline1);

        //invalid command
        commandBox.runCommand("adds dead");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestDeadline personToAdd, TestDeadline... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        DeadlineCardHandle addedCard = deadlineListPanel.navigateToPerson(personToAdd.getName().toString());
        assertDeadlineMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestDeadline[] expectedList = TestUtil.addDeadlinesToList(currentList, personToAdd);
        assertTrue(deadlineListPanel.isListMatching(expectedList));
    }

}
