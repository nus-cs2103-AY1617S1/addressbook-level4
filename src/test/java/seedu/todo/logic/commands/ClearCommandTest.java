package seedu.todo.logic.commands;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.property.TaskViewFilter;
import seedu.todo.model.task.ImmutableTask;

public class ClearCommandTest extends CommandTest {
    
    ImmutableTask task1, task2, task3;

    @Before
    public void setUp() throws Exception {
        task1 = model.add("Hello", task -> {task.setCompleted(true); 
                                    task.setEndTime(LocalDateTime.now().plusHours(2));
                                    task.setPinned(true);
                                    } );
        task2 = model.add("Hi", task -> {task.setCompleted(true); 
                                         task.setEndTime(LocalDateTime.now().plusHours(2));
                                         task.setPinned(false);
        } );
        task3 = model.add("Goodbye", task -> {task.setCompleted(false); 
                                     task.setEndTime(LocalDateTime.now().plusHours(3));
                                     task.setPinned(false);
                                     });
    }

    @Test
    public void testClear_Default() throws ValidationException {
        this.assertTotalTaskCount(3);
        execute(true);
        this.assertTotalTaskCount(0);
    }
    
    @Test
    public void testClear_withFilter() throws ValidationException {
        model.view(TaskViewFilter.COMPLETED);
        execute(true);
        assertTaskExist(task3);
        assertTaskNotExist(task2);
        assertTaskNotExist(task1);
        
    }

    @Override
    protected BaseCommand commandUnderTest() {
        return new ClearCommand();
    }

}
