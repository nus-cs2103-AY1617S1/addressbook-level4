package guitests;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.CommandResult;
import seedu.tasklist.logic.commands.DoneCommand;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.tasklist.model.Model;
import seedu.tasklist.model.ModelManager;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

public class DoneCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private Model model;
	private String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";
	private String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The task index provided is invalid";
	
	
	@Before
	public void initialize() throws DuplicateTaskException, IllegalValueException{
		setup();
	}
	
    public void setup() throws DuplicateTaskException, IllegalValueException {
    	model = new ModelManager();
    	try{
    		model.addTask(new Task(new TaskDetails("test1"), 
            		new StartTime("5pm"), new EndTime("6pm"), new Priority("low"), new UniqueTagList()));
            model.addTask(new Task(new TaskDetails("test2"), 
            		new StartTime("3pm"), new EndTime("4pm"), new Priority("med"), new UniqueTagList()));
    	}
    	catch (IllegalValueException e){
    		assert false: "Add task will succeed";
    	}
    }
    
    @Test
    public void doneTest1_by_index() throws DuplicateTaskException, IllegalValueException, IOException, JSONException, ParseException{
    	setup();
    	Command doneCommand = new DoneCommand(1);
    	doneCommand.setData(model);
    	CommandResult result = doneCommand.execute();
    	String expectedString = String.format(MESSAGE_DONE_TASK_SUCCESS, model.getTaskList().getTaskList().get(0).getTaskDetails());
    	assertEquals(result.feedbackToUser, expectedString);
    }
/**
    @Test
    public void doneTest2_by_name() throws DuplicateTaskException, IllegalValueException, IOException, JSONException, ParseException {
    	setup();
    	Command doneCommand = new DoneCommand("test2");
    	doneCommand.setData(model);
    	CommandResult result = doneCommand.execute();
    	String expectedString = String.format(MESSAGE_DONE_TASK_SUCCESS, model.getTaskList().getTaskList().get(1).getTaskDetails());
    	assertEquals(result.feedbackToUser, expectedString);
    }
*/    
    @Test
    public void doneTest3_throws_invalid_index() throws DuplicateTaskException, IllegalValueException, IOException, JSONException, ParseException {
    	setup();
    	Command doneCommand = new DoneCommand(3);
    	doneCommand.setData(model);
    	CommandResult result = doneCommand.execute();
    	String expectedString = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    	assertEquals(result.feedbackToUser, expectedString);
    }
    
}
