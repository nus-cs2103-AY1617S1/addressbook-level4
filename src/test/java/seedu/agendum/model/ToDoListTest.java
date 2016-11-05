package seedu.agendum.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.Name;
import seedu.agendum.model.task.Task;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0148095X
public class ToDoListTest {

    private Task alice, bob, charlie;
    private ToDoList one, another;
    
    @Before
    public void setUp() throws IllegalValueException{
        alice = new Task(new Name("meet alice"));
        bob = new Task(new Name("meet bob"));
        charlie = new Task(new Name("meet charlie"));
        
        one = new ToDoList();
        one.addTask(alice);
        one.addTask(bob);
        
        another = new ToDoList();
        another.addTask(alice);
        another.addTask(bob);
    }

    
    @Test
    public void equals_symmetric_returnsTrue() {
        assertTrue(one.equals(another) && another.equals(one));
    }
    
    @Test
    public void hashCode_symmetric_returnsTrue() {
        assertTrue(one.hashCode() == another.hashCode());
    }
    
    @Test
    public void getEmptyToDoList() {
        assertTrue(ToDoList.getEmptyToDoList().getTaskList().isEmpty());
    }
    
    @Test
    public void removeTask_taskExists_removedSuccessfully() throws TaskNotFoundException {
        one.removeTask(alice);
        assertFalse(one.getTaskList().contains(alice));
    }
    
    @Test (expected = TaskNotFoundException.class)
    public void removeTask_taskDoesNotExist_throwsTaskNotFoundException() throws TaskNotFoundException {
        one.removeTask(charlie);
    }
}
