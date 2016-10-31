package guitests;

import org.junit.Before;
import org.junit.Test;


//@@author A0138862W
public class ImportIcsCommandTest extends TaskManagerGuiTest{

    
    @Before
    public void before(){
        this.commandBox.runCommand("clear");
        this.assertListSize(0);
    }

    //@@author A0138862W
    @Test
    public void importics_successWithEmptyTaskList(){        
        // run the importics command
        // sample.ics contains 3 items
        this.commandBox.runCommand("importics from ./src/test/data/ImportIcsTest/sample.ics");
        this.assertResultMessage("Imported ics.");
        
        // resulting task list in Mastermind should have 3 items
        this.assertListSize(3);
    }
    
    //@@author A0138862W
    @Test
    public void importics_successWithExisitingTaskList(){
        // initilize with an existing tasks
        this.commandBox.runCommand("add Exisiting Task");
        this.assertListSize(1);
        
        // run the importics command
        // sample.ics contains 3 items
        this.commandBox.runCommand("importics from ./src/test/data/ImportIcsTest/sample.ics");
        this.assertResultMessage("Imported ics.");
        
        // resulting task list in Mastermind should have 4 items
        this.assertListSize(4);
    }
    //@@author A0138862W
    @Test
    public void importics_failure_invalidFilePath(){
        // invalid file path
        this.commandBox.runCommand("importics from /invalid/file/path");
        this.assertResultMessage("Failed to import ics.");
        
        // resulting task list in Mastermind should have 0 items still
        this.assertListSize(0);
    }
    
    //@@author A0138862W
    @Test
    public void importics_failure_malformedFileContent(){
        // malformed file content
        this.commandBox.runCommand("importics from ./src/test/data/ImportIcsTest/empty.ics");
        this.assertResultMessage("Failed to import ics.");
        
        // resulting task list in Mastermind should have 0 items still
        this.assertListSize(0);
    }
    
    //@@author A0138862W
    @Test
    public void importics_failure_startDateIsAfterEndDate(){
        
        // the malformed date the second task
        // expect only the first task entry is successfully imported
        this.commandBox.runCommand("importics from ./src/test/data/ImportIcsTest/startDateIsAfterEndDate.ics");
        this.assertResultMessage("Failed to import ics.");
        
        // resulting task list in Mastermind should have only 1 item
        this.assertListSize(1);
    }

    //@@author A0138862W
    @Test
    public void importics_failure_importTwiceCauseDuplicateException(){
        
        // expect first import successful
        this.commandBox.runCommand("importics from ./src/test/data/ImportIcsTest/sample.ics");
        this.assertResultMessage("Imported ics.");
        this.assertListSize(3);
        
        // expect second import failure
        this.commandBox.runCommand("importics from ./src/test/data/ImportIcsTest/sample.ics");
        this.assertResultMessage("Failed to import ics. Duplicate task detected when importing.");
        this.assertListSize(3);
    }
}
