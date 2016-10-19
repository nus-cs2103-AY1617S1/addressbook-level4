//package guitests;
//
//import org.junit.Test;
//
//import seedu.address.testutil.TypicalTestTasks;
//
//import static org.junit.Assert.assertTrue;
//
//public class ClearCommandTest extends TaskManagerGuiTest {
//
//    @Test
//    public void clear() {
//
//        // verify a non-empty list can be cleared
//        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
//        assertTrue(todayTaskListTabPanel.isListMatching(td.getTodayTasks()));
//        assertTrue(tomorrowTaskListTabPanel.isListMatching(td.getTomorrowTasks()));
//        assertTrue(in7DaysTaskListTabPanel.isListMatching(td.getIn7DaysTasks()));
//        assertTrue(in30DaysTaskListTabPanel.isListMatching(td.getIn30DaysTasks()));
//        assertTrue(somedayTaskListTabPanel.isListMatching(td.getSomedayTasks()));
//        assertClearCommandSuccess();
//
//        // verify other commands can work after a clear command
//        commandBox.runCommand(TypicalTestTasks.someday1.getAddCommand());
//        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.someday1));
//        commandBox.runCommand("del 1");
//        assertListSize(0);
//
//        // verify clear command works when the list is empty
//        assertClearCommandSuccess();
//        
//        // verify the lists in the tab pane can be cleared
//        
//    }
//
//    private void assertClearCommandSuccess() {
//        commandBox.runCommand("clear");
//        assertListSize(0);
//        assertTodayListSize(0);
//        assertTomorrowListSize(0);
//        assertIn7DaysListSize(0);
//        assertIn30DaysListSize(0);
//        assertSomedayListSize(0);
//        assertResultMessage("Task manager has been cleared!");
//    }
//}
