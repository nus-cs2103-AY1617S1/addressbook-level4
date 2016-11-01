package seedu.forgetmenot.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;

public class TaskTest {

    @Test
    public void checkOverdue_checkIfGivenTimeIsOverdue_returnTrueIfOverdue() throws IllegalValueException {
        
        ArrayList<Task> overdueTasks = generateOverdueTasks();
        ArrayList<Task> notOverdueTasks = generateNotOverdueTasks();
        
        for (int i = 0; i < overdueTasks.size(); i++)
            assertTrue(overdueTasks.get(i).checkOverdue());
        for (int i = 0; i < notOverdueTasks.size(); i++)
            assertFalse(notOverdueTasks.get(i).checkOverdue());
    }
    
    public ArrayList<Task> generateNotOverdueTasks() throws IllegalValueException {
        ArrayList<Task> notOverdueTasks = new ArrayList<Task>();

        notOverdueTasks.add(new Task(new Name("Not overdue task"), new Done(false), new Time("tomorrow"), new Time(""),
                new Recurrence("")));
        notOverdueTasks.add(new Task(new Name("Not overdue task"), new Done(false), new Time(""),
                new Time("three days later"), new Recurrence("")));
        notOverdueTasks.add(new Task(new Name("Not overdue task"), new Done(false), new Time("tomorrow"),
                new Time("day after tomorrow"), new Recurrence("")));

        return notOverdueTasks;

    }

    public ArrayList<Task> generateOverdueTasks() throws IllegalValueException {
        ArrayList<Task> overdueTasks = new ArrayList<Task>();

        overdueTasks.add(new Task(new Name("overdue start time task"), new Done(false), new Time("1/1/16"),
                new Time(""), new Recurrence("")));
        overdueTasks.add(new Task(new Name("overdue deadline"), new Done(false), new Time(""), new Time("2/2/16"),
                new Recurrence("")));
        overdueTasks.add(new Task(new Name("overdue event task"), new Done(false), new Time("1/12/15"),
                new Time("2/12/15"), new Recurrence("")));

        return overdueTasks;
    }

}
