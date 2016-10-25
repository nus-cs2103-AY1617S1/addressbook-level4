package guitests;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;

import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0147967J
/**
 * Tests view command and agenda time range change event.
 * Currently only tentative and will add in more later. 
 */
public class ViewCommandTest extends TaskMasterGuiTest{
	
	private final long DAY  = 24*60*60*1000;
	@Test
	public void view(){
		
		//View today
		TestTask toBeAdded = td.weekly;
		ArrayList<TaskComponent> expectedList = new ArrayList<TaskComponent>();
		expectedList.add(toBeAdded.getLastAppendedComponent());
		commandBox.runCommand(toBeAdded.getAddRecurringCommand());
		assertViewSuccess("today", expectedList);
		
		//View next week today
		TaskComponent updated = toBeAdded.getLastAppendedComponent();
		updated.setStartDate(new TaskDate(updated.getStartDate().getDateInLong() + 7 * DAY));
		updated.setEndDate(new TaskDate(updated.getEndDate().getDateInLong() + 7 * DAY));
		expectedList.set(0, updated);
		assertViewSuccess("next week today", expectedList);
	}
	
	public void assertViewSuccess(String date, ArrayList<TaskComponent> expectedList){
		
		commandBox.runCommand("view "+date);
		assertEquals(TestUtil.getConvertedTime(new TaskDate(date)).truncatedTo(ChronoUnit.DAYS),
				browser.getMyAgenda().getDisplayedLocalDateTime());
		assertIsAgendaMatching(expectedList);
		
	}
}
