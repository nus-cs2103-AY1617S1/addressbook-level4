//@@ author A0127686R
package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.flexitrack.logic.Logic;
import seedu.flexitrack.logic.LogicManager;
import seedu.flexitrack.logic.commands.CommandResult;
import seedu.flexitrack.logic.commands.GapCommand;
import seedu.flexitrack.model.Model;
import seedu.flexitrack.model.ModelManager;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.storage.StorageManager;
import seedu.flexitrack.testutil.TestTask;

public class GapCommandTest extends FlexiTrackGuiTest {

    @ClassRule
    public static TemporaryFolder saveFolder = new TemporaryFolder();

    private static Model model;
    private static Logic logic;


    @BeforeClass
    public static void setupMoreEvents(){ 
        model = new ModelManager();
        String tempFlexiTrackerFile = saveFolder.getRoot().getPath() + "TempFlexiTracker.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempFlexiTrackerFile, tempPreferencesFile));
       
        TestTask[] typicalTask = td.getTypicalSortedTasks();
        for (TestTask task: typicalTask){
            try {
                model.addTask(new Task(task));
            } catch (DuplicateTaskException e) {
                assert true; 
            }
        }
        
        TestTask[] typicalEvent = td.getTypicalEventTasks();
        for (TestTask task: typicalEvent){
            try {
                model.addTask(new Task(task));
            } catch (DuplicateTaskException e) {
                assert true; 
            }
        }
    }
    
    @Test
    public void execute_gap_hour() throws Exception {
        String gapUserCommand = "gap hour";
        String messageShown = "The earliest 1 hour free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 08 2016 11:00" + "  to: " + "Nov 08 2016 15:00"
                + "\nBetween:  " + "Nov 08 2016 16:00" + "  to: " + "Nov 09 2016 14:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);
    
        gapUserCommand = "gap 3h";
        messageShown = "The earliest 3 hours free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 08 2016 11:00" + "  to: " + "Nov 08 2016 15:00"
                + "\nBetween:  " + "Nov 08 2016 16:00" + "  to: " + "Nov 09 2016 14:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);
    
        gapUserCommand = "gap 7 hours";
        messageShown = "The earliest 7 hours free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 08 2016 16:00" + "  to: " + "Nov 09 2016 14:00"
                + "\nBetween:  " + "Nov 09 2016 16:00" + "  to: " + "Nov 12 2016 10:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);

        gapUserCommand = "gap  4h n/2";
        messageShown = "The earliest 4 hours free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 08 2016 11:00" + "  to: " + "Nov 08 2016 15:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);
    }
    
    @Test
    public void execute_gap_minute_test() throws Exception {
        String gapUserCommand = "gap 40m";
        String messageShown = "The earliest 40 minutes free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 08 2016 11:00" + "  to: " + "Nov 08 2016 15:00"
                + "\nBetween:  " + "Nov 08 2016 16:00" + "  to: " + "Nov 09 2016 14:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);

        gapUserCommand = "gap 30minutes";
        messageShown = "The earliest 30 minutes free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 08 2016 11:00" + "  to: " + "Nov 08 2016 15:00"
                + "\nBetween:  " + "Nov 08 2016 16:00" + "  to: " + "Nov 09 2016 14:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);

        gapUserCommand = "gap minute n/1";
        messageShown = "The earliest 1 minute free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);
    }
    
    @Test
    public void execute_gap_day_test() throws Exception {
        String gapUserCommand = "gap 2d";
        String messageShown = "The earliest 2 days free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 09 2016 16:00" + "  to: " + "Nov 12 2016 10:00"
                + "\nBetween:  " + "Nov 12 2016 14:00" + "  to: " + "Nov 18 2016 09:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);

        gapUserCommand = "gap 3 days";
        messageShown = "The earliest 3 days free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 12 2016 14:00" + "  to: " + "Nov 18 2016 09:00"
                + "\nBetween:  " + "Nov 22 2016 21:00" + "  to: " + "Nov 16 2017 19:00";
        assertGapCommandSuccess(gapUserCommand, messageShown);

        gapUserCommand = "gap day n/6";
        messageShown = "The earliest 1 day free time are found... "
                + "\nBetween:  now                        " + "  to: " + "Nov 08 2016 09:00"
                + "\nBetween:  " + "Nov 09 2016 16:00" + "  to: " + "Nov 12 2016 10:00"
                + "\nBetween:  " + "Nov 12 2016 14:00" + "  to: " + "Nov 18 2016 09:00"
                + "\nBetween:  " + "Nov 20 2016 14:00" + "  to: " + "Nov 22 2016 19:00"
                + "\nBetween:  " + "Nov 22 2016 21:00" + "  to: " + "Nov 16 2017 19:00"
                + "\nFree from: " + "Nov 16 2017 20:15" + " onwards. ";
        assertGapCommandSuccess(gapUserCommand, messageShown);
    }
    
    @Test
    public void execute_invalid_help() throws Exception {
        String invalidUserInput = "gap 5";
        assertGapCommandSuccess(invalidUserInput, ("Invalid command format! \n" + GapCommand.MESSAGE_USAGE));

        invalidUserInput = "gap";
        assertGapCommandSuccess(invalidUserInput, ("Invalid command format! \n" + GapCommand.MESSAGE_USAGE));
        
        invalidUserInput = "gap 5 months";
        assertGapCommandSuccess(invalidUserInput, ("Invalid command format! \n" + GapCommand.MESSAGE_USAGE));

        invalidUserInput = "gap 2 days n/";
        assertGapCommandSuccess(invalidUserInput, ("Invalid command format! \n" + GapCommand.MESSAGE_USAGE));

        invalidUserInput = "gap day n/three";
        assertGapCommandSuccess(invalidUserInput, ("Invalid command format! \n" + "Please enter number in digit."));

        invalidUserInput = "gap two day";
        assertGapCommandSuccess(invalidUserInput, ("Invalid command format! \n" + "Please enter number in digit."));

    }

    /**
     * Check if the system output is the same as expected output. 
     */
    private void assertGapCommandSuccess(String inputCommand, String expectedMessage) {
        // Execute the command
        CommandResult result = logic.execute(inputCommand);

        // Check if the expectedMessage is the same as the result
        assertEquals(expectedMessage, result.feedbackToUser);
    }

}
