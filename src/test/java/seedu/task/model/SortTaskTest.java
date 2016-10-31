package seedu.task.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Deadline;
import seedu.task.model.item.Name;
import seedu.task.model.item.Task;

//@@author A0127570H
public class SortTaskTest {

    @Test
    public void sortTaskTest() throws IllegalValueException {

        // Setup
        Deadline earlyDeadline = new Deadline("tomorrow 7pm");
        Deadline lateDeadline = new Deadline("day after 7pm");
        Name name = new Name("Project");

        Task floatTask = new Task(name, null, null, false);
        Task earlyTask = new Task(name, null, earlyDeadline, false);
        Task lateTask = new Task(name, null, lateDeadline, false);

        assertEquals(floatTask.sortDesc(floatTask), 0);
        assertEquals(floatTask.sortDesc(earlyTask), -1);
        assertEquals(earlyTask.sortDesc(floatTask), 1);
        assertEquals(earlyTask.sortDesc(lateTask), -1);
               
    }
}
