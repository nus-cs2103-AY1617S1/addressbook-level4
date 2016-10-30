package guitests;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.Detail;
import seedu.todo.model.task.Name;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Recurrence;
import seedu.todo.model.task.Recurrence.Frequency;
import seedu.todo.model.task.TaskDate;
import seedu.todo.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;

//@@author A0093896H
public class UpdateCommandTest extends ToDoListGuiTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void update_command_test() throws IllegalValueException {
        
        TestTask[] currentList = td.getTypicalTasks();
        for (TestTask t : currentList) {
            commandBox.runCommand(t.getAddCommand());
        }
        
        TestTask[] reverseList = td.getTypicalTasksReverse();
        
        //update name
        reverseList[0].setName(new Name("changeName"));
        commandBox.runCommand("update 1 changeName");
        assertUpdateSuccess(reverseList);
        
        //update on date and priority
        reverseList[0].setPriority(new Priority("high"));
        reverseList[0].setOnDate(new TaskDate("12/12/1996", TaskDate.TASK_DATE_ON));
        commandBox.runCommand("update 1 on 12/12/1996 priority high");
        assertUpdateSuccess(reverseList);
        
        //remove detail
        reverseList[0].setDetail(new Detail(""));
        commandBox.runCommand("update 1 ; -");
        assertUpdateSuccess(reverseList);
        
        //full update
        reverseList[1].setName(new Name("this is full update"));
        reverseList[1].setDetail(new Detail("haha"));
        reverseList[1].setOnDate(new TaskDate("tomorrow", TaskDate.TASK_DATE_ON));
        reverseList[1].setByDate(new TaskDate("2 days later", TaskDate.TASK_DATE_BY));
        reverseList[1].setPriority(new Priority("mid"));
        reverseList[1].setRecurrence(new Recurrence(Frequency.WEEK));
        commandBox.runCommand("update 2 this is full update on tomorrow by 2 days later priority mid every week ; haha");
        assertUpdateSuccess(reverseList);
}
    
    
    /**
     * Check if the expected list is the same as the gui list
     * @param currentList A copy of the expected list of tasks (after undo).
     */
    private void assertUpdateSuccess(TestTask... expectedList) {
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    
}

