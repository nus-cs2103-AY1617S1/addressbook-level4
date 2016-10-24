package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.FindTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

public class FindTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList emptyTaskList;
	InMemoryTaskList taskList;
	
	@Before
	public void setup() throws IllegalValueException {
		// Initialize task list to hold 3 tasks ("Task 1", "Task 2", "Task 3")
		emptyTaskList = TestUtil.setupEmptyTaskList();
		taskList = TestUtil.setupFloatingTasks(3);
	}
	
	@Test
	public void findTask_noTasksAdded() {
		/*
		 * CommandResult should return a string that indicates 0 tasks found
		 * (since there no tasks are added).
		 */
		Set<String> stringsToFind = createSetOfStrings("Task");
		FindTaskCommand command = new FindTaskCommand(stringsToFind);
		command.setData(emptyTaskList);
		
		assertTasksFound(command, 0);
	}
	
	@Test
	public void findTask_emptyString() {
		/*
		 * CommandResult should return a string that indicates 0 tasks found
		 * (since no task can be an empty string)
		 */
		Set<String> stringsToFind = createSetOfStrings("");
		FindTaskCommand command = new FindTaskCommand(stringsToFind);
		command.setData(taskList);
		
		assertTasksFound(command, 0);
	}
	
	@Test
	public void findTask_noWordsMatch() {
		/*
		 * CommandResult should return a string that indicates 0 tasks found
		 * (since none of the words match) 
		 */
		Set<String> stringsToFind = createSetOfStrings("Tak", "Tasks", "12", "23");
		FindTaskCommand command = new FindTaskCommand(stringsToFind);
		command.setData(taskList);
		
		assertTasksFound(command, 0);
	}
	
	@Test
	public void findTask_substringMatch() {
		/*
		 * CommandResult should return a string that indicates 3 tasks found
		 * (since substring match matches all words containing the substring)
		 */
		Set<String> stringsToFind = createSetOfStrings("Ta", "as", "sk", "Tas", "ask");
		FindTaskCommand command = new FindTaskCommand(stringsToFind);
		command.setData(taskList);
		
		assertTasksFound(command, 3);
	}
	
	@Test
	public void findTask_oneWordMatch() {
		/* 
		 * CommandResult should return a string that indicates 1 task found
		 * (since only 1 task's description matches)
		 */
		Set<String> stringsToFind = createSetOfStrings("1");
		FindTaskCommand command = new FindTaskCommand(stringsToFind);
		command.setData(taskList);
		
		assertTasksFound(command, 1);
	}
	
	@Test
	public void findTask_oneWordMatchMultipleTasks() {
		/* 
		 * CommandResult should return a string that indicates 3 tasks found
		 * (since 'Task' is found in all 3 tasks in the task list)
		 */
		Set<String> stringsToFind = createSetOfStrings("Task");
		FindTaskCommand command = new FindTaskCommand(stringsToFind);
		command.setData(taskList);
		
		assertTasksFound(command, 3);
	}
	
	@Test
	public void findTask_multipleWordsMatchMultipleTasks() {
		/* 
		 * CommandResult should return a string that indicates 2 tasks found
		 * (since '1' and '2' belong to 2 different tasks in the task list)
		 */
		Set<String> stringsToFind = createSetOfStrings("1", "2");
		FindTaskCommand command = new FindTaskCommand(stringsToFind);
		command.setData(taskList);
		
		assertTasksFound(command, 2);
	}

	
	
	// Create a Set of strings in order to construct a FindTaskCommand instance
	public Set<String> createSetOfStrings(String... stringsToFind) {
		Set<String> setToFind = new HashSet<String>();
		for (String s : stringsToFind) {
			setToFind.add(s);
		}
		return setToFind;
	}
	
	/* Given a command and number of tasks expected to be found, execute the command 
	 * and assert that the feedback corresponds to the numTasksExpected
	 */
	public void assertTasksFound(FindTaskCommand command, int numTasksExpected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, numTasksExpected)));		
	}
}