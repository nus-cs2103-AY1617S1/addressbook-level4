package guitests;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;

import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.TaskDate;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.util.MyAgendaUtil;

//@@author A0147967J
/**
 * Tests view command and agenda time range change event. Currently only
 * tentative and will add in more later.
 */
public class ViewCommandTest extends TaskMasterGuiTest {

    private final long DAY = 24 * 60 * 60 * 1000;

    @Test
    public void viewSwitch() {
        
        //Add an additional deadline task to verify the changes in tasklist panel.
        commandBox.runCommand(td.incoming.getAddNonFloatingCommand());

        // View today
        TestTask toBeAdded = td.weekly;
        ArrayList<TaskOccurrence> expectedList = new ArrayList<TaskOccurrence>();
        expectedList.add(toBeAdded.getLastAppendedComponent());
        commandBox.runCommand(toBeAdded.getAddRecurringCommand());
        assertViewSuccess("today", expectedList);
        assertTrue(taskListPanel.isListMatching(td.labDeadline.getLastAppendedComponent(),
                td.essayDeadline.getLastAppendedComponent()));

        // View next week today
        TaskOccurrence updated = toBeAdded.getLastAppendedComponent();
        updated.setStartDate(new TaskDate(updated.getStartDate().getDateInLong() + 7 * DAY));
        updated.setEndDate(new TaskDate(updated.getEndDate().getDateInLong() + 7 * DAY));
        expectedList.set(0, updated);
        assertViewSuccess("next week today", expectedList);
        assertTrue(taskListPanel.isListMatching(td.labDeadline.getLastAppendedComponent(),
                td.essayDeadline.getLastAppendedComponent(),
                td.incoming.getLastAppendedComponent()));
             
    }
    
    @Test
    public void viewUndoRedo() {
        
        //Add an additional deadline task to verify the changes in tasklist panel.
        commandBox.runCommand(td.incoming.getAddNonFloatingCommand());

        // View today
        TestTask toBeAdded = td.none;
        ArrayList<TaskOccurrence> expectedList = new ArrayList<TaskOccurrence>();
        expectedList.add(toBeAdded.getLastAppendedComponent());
        commandBox.runCommand(toBeAdded.getAddRecurringCommand());
        assertViewSuccess("today", expectedList);

        // View next week today
        expectedList.clear();
        assertViewSuccess("next week today", expectedList);
        
        //Verifies undo/redo
        expectedList.add(toBeAdded.getLastAppendedComponent());
        commandBox.runCommand("u");
        assertEquals(MyAgendaUtil.getConvertedTime(new TaskDate("today")).truncatedTo(ChronoUnit.DAYS),
                browser.getMyAgenda().getDisplayedLocalDateTime());
        assertIsAgendaMatching(expectedList);
        assertTrue(taskListPanel.isListMatching(td.labDeadline.getLastAppendedComponent(),
                td.essayDeadline.getLastAppendedComponent()));
        
        expectedList.clear();
        commandBox.runCommand("r");
        assertEquals(MyAgendaUtil.getConvertedTime(new TaskDate("next week today")).truncatedTo(ChronoUnit.DAYS),
                browser.getMyAgenda().getDisplayedLocalDateTime());
        assertIsAgendaMatching(expectedList);
        assertTrue(taskListPanel.isListMatching(td.labDeadline.getLastAppendedComponent(),
                td.essayDeadline.getLastAppendedComponent(),
                td.incoming.getLastAppendedComponent()));
        
             
    }

    public void assertViewSuccess(String date, ArrayList<TaskOccurrence> expectedList) {

        commandBox.runCommand("view " + date);
        assertEquals(MyAgendaUtil.getConvertedTime(new TaskDate(date)).truncatedTo(ChronoUnit.DAYS),
                browser.getMyAgenda().getDisplayedLocalDateTime());
        assertIsAgendaMatching(expectedList);

    }
}
