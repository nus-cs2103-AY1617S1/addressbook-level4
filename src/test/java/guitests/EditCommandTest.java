package guitests;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.logic.commands.EditCommand;
import seedu.malitio.testutil.TestDeadline;
import seedu.malitio.testutil.TestEvent;
import seedu.malitio.testutil.TestFloatingTask;
import seedu.malitio.testutil.TestUtil;
import seedu.malitio.ui.DeadlineListPanel;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//@@author A0129595N
public class EditCommandTest extends MalitioGuiTest {

    @Test
    public void editFloatingtask() {

        // Edit name of floating task
        TestFloatingTask[] currentList = td.getTypicalFloatingTasks();
        TestFloatingTask toEdit = td.floatingTask1;
        TestFloatingTask edited = td.editedFloatingTask1;
        commandBox.runCommand("edit f1 how are you");
        assertEditSuccess(edited, 0, currentList);

        // Edit tags of floating task
        toEdit = td.floatingTask2;
        edited = td.editedFloatingTask2;
        commandBox.runCommand("edit f2 t/omg");
        assertEditSuccess(edited, 1, currentList);

        // Edit both name and tags of floatingtask
        toEdit = td.floatingTask3;
        edited = td.editedFloatingTask3;
        commandBox.runCommand("edit f3 Tell Nobody t/heello");
        assertEditSuccess(edited, 2, currentList);

        // Edit with an invalid index
        commandBox.runCommand("edit f200");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // Edit a task to one which already exists
        commandBox.runCommand("edit f1 Tell Nobody t/heello");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void editDeadline() {

        // Edit name (only) of deadline
        TestDeadline[] currentList = td.getTypicalDeadlines();
        TestDeadline toEdit = td.deadline1;
        TestDeadline edited = td.editedDeadline1;
        commandBox.runCommand("edit d1 Cut more hair ");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo"); // revert back to original state

        // Edit due date (only) of dateline
        toEdit = td.deadline2;
        edited = td.editedDeadline2;
        commandBox.runCommand("edit d2 by 22 dec 12am");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        // Edit tag (only) of deadline
        toEdit = td.deadline3;
        edited = td.editedDeadline3;
        commandBox.runCommand("edit d3 t/Pineapple t/Pen");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        // Edit name, duedate and tags of deadline
        toEdit = td.deadline4;
        edited = td.editedDeadline4;
        commandBox.runCommand("edit d4 I want to sleep by 25 oct 11pm t/damntired");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        // Edit with an invalid index
        commandBox.runCommand("edit d200");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // Edit a deadline to one which already exists
        commandBox.runCommand("edit d1 Practice singing by 12-25 12am t/Christmas t/Carols");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_DEADLINE);
    }
    
    @Test
    public void editEvent() {
        
        // Edit name (only) of event
        TestEvent[] currentList = td.getTypicalEvents();
        TestEvent toEdit = td.event1;
        TestEvent edited = td.editedEvent1;
        commandBox.runCommand("edit e1 Eat with dad");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo"); // revert back to original state

        // Edit start (only) of event
        toEdit = td.event2;
        edited = td.editedEvent2;
        commandBox.runCommand("edit e2 start 22 feb 2017 1pm");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");
        
        // Edit end (only) of event
        toEdit = td.event3;
        edited = td.editedEvent3;
        commandBox.runCommand("edit e3 end 30 march 2017 9pm");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        // Edit tag (only) of event
        toEdit = td.event4;
        edited = td.editedEvent4;
        commandBox.runCommand("edit e4 t/fun t/yahoo");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        // Edit name, start, end and tags of deadline
        toEdit = td.event5;
        edited = td.editedEvent5;
        commandBox.runCommand("edit e5 Outing start 02-14-2017 10am end 02-14-2017 8pm t/dressup");
        assertEditSuccess(edited, toEdit, currentList);
        commandBox.runCommand("undo");

        // Edit with an invalid index
        commandBox.runCommand("edit e200");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // Edit an event to one which already exists
        commandBox.runCommand("edit e1 New year party start 12-31-2017 12am end 12-31-2017 11.59pm t/null");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_EVENT);
        
        // Edit an event's start date later than end date
        commandBox.runCommand("edit e5 start 12-26-2017");
        assertResultMessage(EditCommand.MESSAGE_INVALID_EVENT);

    }

    /**
     * @param edited
     * @param index
     * @param currentList
     */
    private void assertEditSuccess(TestFloatingTask edited, int index, TestFloatingTask... currentList) {
        currentList = TestUtil.replaceTaskFromList(currentList, edited, index);
        assertTrue(floatingTaskListPanel.isListMatching(currentList));
    }

    /**
     * @param edited
     * @param toEdit
     * @param currentList
     * @return updated TestDeadline array.
     */
    private void assertEditSuccess(TestDeadline edited, TestDeadline toEdit, TestDeadline... currentList) {
        currentList = TestUtil.removeTasksFromList(currentList, toEdit);
        currentList = TestUtil.addTasksToList(currentList, edited);
        assertTrue(deadlineListPanel.isListMatching(currentList));
    }

    /**
     * @param edited
     * @param toEdit
     * @param currentList
     * @return updated TestDeadline array.
     */
    private void assertEditSuccess(TestEvent edited, TestEvent toEdit, TestEvent... currentList) {
        currentList = TestUtil.removeTasksFromList(currentList, toEdit);
        currentList = TestUtil.addTasksToList(currentList, edited);
        try {
            assertTrue(eventListPanel.isListMatching(currentList));
        } catch (IllegalArgumentException | IllegalValueException e) {
            assert false : "Not possible";
        }
    }

}