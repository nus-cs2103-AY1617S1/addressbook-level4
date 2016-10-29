package seedu.menion;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.Model;
import seedu.menion.model.ModelManager;
import seedu.menion.model.UserPrefs;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ActivityDate;
import seedu.menion.model.activity.ActivityName;
import seedu.menion.model.activity.ActivityTime;
import seedu.menion.model.activity.Completed;
import seedu.menion.model.activity.Note;
import seedu.menion.testutil.TypicalTestActivities;

public class BackgroundTest {

	@Test
	public void test() {
		Model model = getTypicalModel(); 
	}

	private Model getTypicalModel(){
		
		ActivityManager testManager = new ActivityManager();
		TypicalTestActivities.loadActivityManagerWithSampleData(testManager);
		
		try {
			
			Activity testOldActivity = new Activity(Activity.TASK_TYPE, new ActivityName("Test Past Task"),
					new Note("Hope it works"), new ActivityDate("09-09-1999"), new ActivityTime("0001"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY));
			testManager.addTask(testOldActivity);
			
		} catch (IllegalValueException e) {
			
			System.out.println(e);
			
		}
		
		Model model = new ModelManager(testManager, new UserPrefs());
		
		return model;
	}
	
}
