package seedu.savvytasker.model.task;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.savvytasker.model.task.TaskList;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;


//@@author A0139915W
public class TaskListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void taskList_addDuplicate() throws DuplicateTaskException, InvalidDateException {
        thrown.expect(DuplicateTaskException.class);
        TaskList tasks = new TaskList();
        Task t = new Task("Test Task");
        t.setId(1);
        tasks.add(t); // passes
        assertEquals(1, tasks.getInternalList().size());
        tasks.add(t); // fails
    }

    @Test
    public void taskList_addInvalidDate() throws DuplicateTaskException, InvalidDateException {
        thrown.expect(InvalidDateException.class);
        TaskList tasks = new TaskList();
        Task t = new Task("Test Task");
        t.setId(1);
        t.setStartDateTime(getDate("31/12/2016"));
        t.setEndDateTime(getDate("31/12/2015"));
        tasks.add(t); // fails, end date earlier than start date
    }
    
    @Test
    public void taskList_removeNonExistent() throws TaskNotFoundException {
        thrown.expect(TaskNotFoundException.class);
        TaskList tasks = new TaskList();
        Task t = new Task("Test Task");
        t.setId(1);
        assertEquals(0, tasks.getInternalList().size());
        tasks.remove(t); // fails
    }
    
    @Test
    public void taskList_replaceNonExistent() throws TaskNotFoundException, InvalidDateException {
        thrown.expect(TaskNotFoundException.class);
        TaskList tasks = new TaskList();
        Task t = new Task("Test Task");
        t.setId(1);
        assertEquals(0, tasks.getInternalList().size());
        tasks.replace(t, t); // fails
    }
    
    @Test
    public void taskList_replaceInvalidDate() throws TaskNotFoundException, InvalidDateException, DuplicateTaskException {
        thrown.expect(InvalidDateException.class);
        TaskList tasks = new TaskList();
        Task t = new Task("Test Task");
        t.setId(1);
        t.setStartDateTime(getDate("30/12/2016"));
        t.setEndDateTime(getDate("31/12/2016"));
        tasks.add(t);
        assertEquals(1, tasks.getInternalList().size());
        t.setStartDateTime(getDate("31/12/2016"));
        t.setEndDateTime(getDate("31/12/2015"));
        tasks.replace(t, t); // fails, end date earlier than start date
    }

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private Date getDate(String ddmmyyyy) {
        try {
            return format.parse(ddmmyyyy);
        } catch (Exception e) {
            assert false; //should not get an invalid date....
        }
        return null;
    }
}
//@@author
