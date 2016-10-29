package seedu.menion;

import static org.junit.Assert.*;

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
import seedu.menion.testutil.TypicalTestActivities;

public class BackgroundTest {

	Model model;
	ReadOnlyActivityManager testManager;

	@Test
	public void test() {
		
		initialiseModel();
		BackgroundDateCheck.checkActivities(model);

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
					new Completed(Completed.UNCOMPLETED_ACTIVITY));
			model.addTask(testOldActivity);
			
			Activity testOldEvent = new Activity(Activity.EVENT_TYPE, new ActivityName("Test Past Event"),
					new Note("Hope this works too"), new ActivityDate("09-09-1999"), new ActivityTime("0001"),
					new ActivityDate("09-11-1999"), new ActivityTime("0001"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY));
			model.addEvent(testOldEvent);
			
		} catch (IllegalValueException e) {
			
			System.out.println(e);
			
		}
		
	}
	
}
