package seedu.menion;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import seedu.menion.background.BackgroundDateCheck;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.Model;
import seedu.menion.model.ModelManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.UserPrefs;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ActivityDate;
import seedu.menion.model.activity.ActivityName;
import seedu.menion.model.activity.ActivityTime;
import seedu.menion.model.activity.Completed;
import seedu.menion.model.activity.Note;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.testutil.TypicalTestActivities;

//@@author A0139277U
public class BackgroundTest {

	Model model;
	ReadOnlyActivityManager testManager;

	@Test
	public void test() {
		
		initialiseModel();
		BackgroundDateCheck backgroundDateChecker = new BackgroundDateCheck();
		backgroundDateChecker.checkActivities(model);
		assertBackgroundCheckSuccess();
		
	}

	private void assertBackgroundCheckSuccess(){
		
		ReadOnlyActivityManager menion = this.model.getActivityManager();
		
		List<ReadOnlyActivity> taskList = menion.getTaskList();
		ReadOnlyActivity testOldTask = taskList.get(0);
		ReadOnlyActivity testNewTask = taskList.get(1);		
		
		assertTrue(testOldTask.isTimePassed());
		assertTrue(testOldTask.isEmailSent());
		assertFalse(testNewTask.isTimePassed());
		assertFalse(testNewTask.isEmailSent());
		
		List<ReadOnlyActivity> eventList = menion.getEventList();
		ReadOnlyActivity testOldEvent = eventList.get(0);
		ReadOnlyActivity testNewEvent = eventList.get(1);
		
		assertTrue(testOldEvent.isTimePassed());
		assertFalse(testNewEvent.isTimePassed());
		
	}
	
	/**
	 * This method gets a typical model to test for the background date checker. In addition, it adds a task 
	 * and an event which is long over.
	 * @return typical model
	 */
	private void initialiseModel(){
		
		model = new ModelManager();
		
		testManager = new ActivityManager(model.getActivityManager());
				
		TypicalTestActivities typicalTestActivities = new TypicalTestActivities();
		testManager = typicalTestActivities.getTypicalActivityManager();
		
		try {
			
			Activity testOldActivity = new Activity(Activity.TASK_TYPE, new ActivityName("Test Past Task"),
					new Note("Hope it works"), new ActivityDate("09-09-1999"), new ActivityTime("0001"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			model.addTask(testOldActivity);
			
			Activity testNewActivity = new Activity(Activity.TASK_TYPE, new ActivityName("Test New Task"),
					new Note("Hope this works :)"), new ActivityDate("09-09-2050"), new ActivityTime("0001"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			model.addTask(testNewActivity);
			
			Activity testOldEvent = new Activity(Activity.EVENT_TYPE, new ActivityName("Test Past Event"),
					new Note("Hope this works too"), new ActivityDate("09-09-1999"), new ActivityTime("0001"),
					new ActivityDate("09-11-1999"), new ActivityTime("0001"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			model.addEvent(testOldEvent);
			
			Activity testNewEvent = new Activity(Activity.EVENT_TYPE, new ActivityName("Test New Event"),
					new Note("Hope this works too"), new ActivityDate("09-09-2050"), new ActivityTime("0001"),
					new ActivityDate("09-11-2050"), new ActivityTime("0001"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			model.addEvent(testNewEvent);
			
		} catch (IllegalValueException e) {
			
			System.out.println(e);
			
		}
		
	}
	
}
