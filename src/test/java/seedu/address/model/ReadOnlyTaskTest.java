package seedu.address.model;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.End;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Start;
import seedu.address.model.task.Task;

//@@author A0138993L
public class ReadOnlyTaskTest {
	
    private ReadOnlyTask events;
    private ReadOnlyTask deadline;
    private ReadOnlyTask todo;

    public ReadOnlyTaskTest() throws Exception{
        events =  new Task(new Name("Event"), new Date("12-12-16"), new Start("1212"), new End("1234"), 1, 0, false, new UniqueTagList());
        deadline = new Task(new Name("deadline"), new Date("12-12-16"), new End("2359"), 2, 0, false, new UniqueTagList());
        todo = new Task(new Name("todo"), 3, false, new UniqueTagList());
    }
    
    /*
     * Tests for event tasks
     */
    @Test
    public void events_printAsString() {
        String expected = "Event Date: 12-12-16 Start: 1212 End: 1234 overdue: 0 category: 1 complete: false Tags: ";
        assertEquals(expected, events.getAsText());
    }

    @Test
    public void events_getName() throws Exception {
        Name expected = new Name("Event");
        assertEquals(expected, events.getName());
    }

    @Test
    public void events_getDate() throws Exception {
        Date expected = new Date("12-12-16");
        assertEquals(expected, events.getDate());
    }

    @Test
    public void events_getStart() throws Exception {
        Start expected = new Start("1212");
        assertEquals(expected, events.getStart());
    }

    @Test
    public void events_getEnd() throws Exception {
    	End expected = new End("1234");
        assertEquals(expected, events.getEnd());
    }

    @Test
    public void events_getTaskCat() {
    	int expected = 1;
        assertEquals(expected, events.getTaskCategory());
    }
    
    @Test
    public void events_getIsCompleted() {
    	boolean expected = false;
    	assertEquals(expected, events.getIsCompleted());
    }

    @Test
    public void events_getOverdue() {
    	int expected = 0;
    	assertEquals(expected, events.getOverdue());
    }
    
    /*
     * Tests for deadline tasks
     */
    @Test
    public void deadline_printAsString() {
        String expected = "deadline Date: 12-12-16 Start: null End: 2359 overdue: 0 category: 2 complete: false Tags: ";
        assertEquals(expected, deadline.getAsText());
    }

    @Test
    public void deadline_getName() throws Exception {
        Name expected = new Name("deadline");
        assertEquals(expected, deadline.getName());
    }

    @Test
    public void deadline_getDate() throws Exception {
        Date expected = new Date("12-12-16");
        assertEquals(expected, deadline.getDate());
    }

    @Test
    public void deadline_getEnd() throws Exception {
    	End expected = new End("2359");
        assertEquals(expected, deadline.getEnd());
    }

    @Test
    public void deadline_getTaskCat() {
    	int expected = 2;
        assertEquals(expected, deadline.getTaskCategory());
    }
    
    @Test
    public void deadline_getIsCompleted() {
    	boolean expected = false;
    	assertEquals(expected, deadline.getIsCompleted());
    }

    @Test
    public void deadline_getOverdue() {
    	int expected = 0;
    	assertEquals(expected, deadline.getOverdue());
    }
    
    /*
     * Tests for todo task
     */
    @Test
    public void todo_printAsString() {
        String expected = "todo Date: null Start: null End: null overdue: 0 category: 3 complete: false Tags: ";
        assertEquals(expected, todo.getAsText());
    }

    @Test
    public void todo_getName() throws Exception {
        Name expected = new Name("todo");
        assertEquals(expected, todo.getName());
    }

    @Test
    public void todo_getTaskCat() {
    	int expected = 3;
        assertEquals(expected, todo.getTaskCategory());
    }
    
    @Test
    public void todo_getIsCompleted() {
    	boolean expected = false;
    	assertEquals(expected, todo.getIsCompleted());
    }

}