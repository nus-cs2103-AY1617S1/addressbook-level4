package guitests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0147967J
/**
 * Tests if browser panel can display correct tasks.
 */
public class BrowserPanelTest extends TaskMasterGuiTest{
	
	private final long DAY  = 24*60*60*1000;
	
	@Test
	public void browserPanelTest(){
		
		//Initial case: Out dated tasks/floating/deadlines not displayed, in the list 10 tasks
		assertEquals(0, browser.getMyAgenda().appointments().size());
		
		//Add Non recurring tasks display once
		TestTask toBeAdded = td.none;
		ArrayList<TaskComponent> expectedList = new ArrayList<TaskComponent>();
		expectedList.add(toBeAdded.getLastAppendedComponent());
		commandBox.runCommand(toBeAdded.getAddNonFloatingCommand());
		assertIsAgendaMatching(expectedList);
		
		//Add weekly, monthly or yearly display once
		toBeAdded = td.weekly;
		expectedList.add(toBeAdded.getLastAppendedComponent());
		commandBox.runCommand(toBeAdded.getAddRecurringCommand());
		assertIsAgendaMatching(expectedList);
		
		toBeAdded = td.monthly;
		expectedList.add(toBeAdded.getLastAppendedComponent());
		commandBox.runCommand(toBeAdded.getAddRecurringCommand());
		assertIsAgendaMatching(expectedList);
		
		toBeAdded = td.yearly;
		expectedList.add(toBeAdded.getLastAppendedComponent());
		commandBox.runCommand(toBeAdded.getAddRecurringCommand());
		assertIsAgendaMatching(expectedList);
		
		
		//Add daily tasks, depends on day of week, add copies
		toBeAdded = td.daily;
		expectedList.add(toBeAdded.getLastAppendedComponent());
		expectedList.addAll(getCopies(toBeAdded.getLastAppendedComponent()));
		commandBox.runCommand(toBeAdded.getAddRecurringCommand());
		assertIsAgendaMatching(expectedList);
		
		//Archive current task, style change reflected
		toBeAdded.getLastAppendedComponent().archive();
		TaskComponent toBeArchived = toBeAdded.getLastAppendedComponent();
		expectedList.set(4, toBeArchived);
		commandBox.runCommand("done 15");
		assertIsAgendaMatching(expectedList);
		
		//With block command, style change reflected
		toBeAdded = td.block;
		expectedList.add(toBeAdded.getLastAppendedComponent());
		commandBox.runCommand(toBeAdded.getBlockCommand());
		assertIsAgendaMatching(expectedList);
		
	}	
	
	private ArrayList<TaskComponent> getCopies(TaskComponent t){
		ArrayList<TaskComponent> list = new ArrayList<TaskComponent>();
		int dayOfWeek = TestUtil.getConvertedTime(t.getStartDate()).getDayOfWeek().getValue()%7;
		for(int i = 1; i<=6-dayOfWeek;i++){
			TaskComponent copy = new TaskComponent(t);
			copy.setStartDate(new TaskDate(t.getStartDate().getDateInLong() + DAY*i));
			copy.setEndDate(new TaskDate(t.getEndDate().getDateInLong() + DAY*i));
			list.add(copy);
		}
		return list;
	}
}
