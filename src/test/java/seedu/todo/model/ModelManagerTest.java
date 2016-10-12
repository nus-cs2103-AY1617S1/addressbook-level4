package seedu.todo.model;

import org.junit.Rule;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javafx.collections.transformation.FilteredList;
import seedu.todo.model.Model;
import seedu.todo.model.ModelManager;
import seedu.todo.model.ToDoList;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todo.testutil.TestTask;
import seedu.todo.testutil.TypicalTestTasks;

import org.junit.Before;

public class ModelManagerTest {

	private Model model;
	private ToDoList toDoList;
	private TypicalTestTasks typicalTestTasks;

	@Before
	public void setup() {
		typicalTestTasks = new TypicalTestTasks();
		toDoList = typicalTestTasks.getTypicalToDoList();

		model = new ModelManager();
		model.resetData(toDoList);
	}

	@Test
    public void shouldAddTask() throws Exception{
    	TestTask[] testTasks = typicalTestTasks.getTypicalTasks();
    	
    	for (int i = 1; i < testTasks.length; i++) {
    		assertTrue(model.getFilteredTaskList().get(i).equals(testTasks[i]));
    	}
    	
    }


}

// addTask