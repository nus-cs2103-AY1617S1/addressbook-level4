package guitests;

import org.junit.Before;
import org.junit.Test;

import harmony.mastermind.logic.commands.ImportCommand;


//@@author A0138862W
public class ImportCommandTest extends TaskManagerGuiTest{

    
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
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.ics");
        this.assertResultMessage("Import ics success.");
        
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
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.ics");
        this.assertResultMessage("Import ics success.");
        
        // resulting task list in Mastermind should have 4 items
        this.assertListSize(4);
    }
    //@@author A0138862W
    @Test
    public void importics_failure_invalidFilePath(){
        // invalid file path
        this.commandBox.runCommand("import from /invalid/file/path");
        this.assertResultMessage("Invalid command format!\n" + ImportCommand.MESSAGE_USAGE);
        
        // resulting task list in Mastermind should have 0 items still
        this.assertListSize(0);
    }
    
    //@@author A0138862W
    @Test
    public void importics_failure_malformedFileContent(){
        // malformed file content
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/empty.ics");
        this.assertResultMessage("Failed to import ics.");
        
        // resulting task list in Mastermind should have 0 items still
        this.assertListSize(0);
    }
    
    //@@author A0138862W
    @Test
    public void importics_failure_startDateIsAfterEndDate(){
        
        // the malformed date the second task
        // expect only the first task entry is successfully imported
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/startDateIsAfterEndDate.ics");
        this.assertResultMessage("Failed to import ics.");
        
        // resulting task list in Mastermind should have only 1 item
        this.assertListSize(1);
    }

    //@@author A0138862W
    @Test
    public void importics_failure_importTwiceCauseDuplicateException(){
        
        // expect first import successful
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.ics");
        this.assertResultMessage("Import ics success.");
        this.assertListSize(3);
        
        // expect second import failure
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.ics");
        this.assertResultMessage("Failed to import ics. Duplicate task detected when importing.");
        this.assertListSize(3);
    }
    
    //@@author A0138862W
    @Test
    public void importcsv_successWithEmptyTaskList(){
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.csv");
        this.assertResultMessage("Import success: 5 tasks added");
        this.assertListSize(5);
    }
    
    @Test
    public void importcsv_successWithExisitingTaskList(){
        // initilize with an existing tasks
        this.commandBox.runCommand("add Exisiting Task");
        this.assertListSize(1);
        
        // run the importcsv command
        // sample.ics contains 5 items
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.csv");
        this.assertResultMessage("Import success: 5 tasks added");
        
        // resulting task list in Mastermind should have 6 items
        this.assertListSize(6);
    }
    
    //@@author A0138862W
    @Test
    public void importcsv_partialSuccess_malformedEntry(){
        // malformed at 3rd entry
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/malform.csv");
        this.assertResultMessage("Import failure: 4 tasks added \nInvalid lines: 4");
        
        // ignore 3rd entry
        // resulting task list in Mastermind should have 4 items
        this.assertListSize(4);
    }
    
    //@@author A0138862W
    @Test
    public void importcsv_failure_startDateIsAfterEndDate(){
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/startDateIsAfterEndDate.csv");
        this.assertResultMessage("Import failure: 0 tasks added \nInvalid lines: 2");
        
        // resulting task list in Mastermind should have only 0 item
        this.assertListSize(0);
    }
    
    //@@author A0138862W
    @Test
    public void importcsv_failure_importTwiceCauseDuplicateException(){
        
        // expect first import successful
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.csv");
        this.assertResultMessage("Import success: 5 tasks added");
        this.assertListSize(5);
        
        // expect second import failure
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.csv");
        this.assertResultMessage("Import failure: 0 tasks added \nInvalid lines: 2,3,4,5,6");
        this.assertListSize(5);
    }
    
    //@@author A0138862W
    @Test
    public void importtxt_successWithEmptyTaskList(){        
        // run the importics command
        // sample.ics contains 3 items
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.txt");
        this.assertResultMessage("Import success: 3 tasks added");
        
        // resulting task list in Mastermind should have 3 items
        this.assertListSize(3);
    }
    
  //@@author A0138862W
    @Test
    public void importtxt_partialSuccess_containOtherCommand(){        
        // run the importics command
        // sample.ics contains 3 items
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/containOtherCommand.txt");
        this.assertResultMessage("Import failure: 1 tasks added \nInvalid lines: 2,3");
        
        // resulting task list in Mastermind should have 3 items
        this.assertListSize(1);
    }
    
  //@@author A0138862W
    @Test
    public void importtxt_failure_importTwiceCauseDuplicateException(){
        
        // expect first import successful
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.txt");
        this.assertResultMessage("Import success: 3 tasks added");
        this.assertListSize(3);
        
        // expect second import failure
        this.commandBox.runCommand("import from ./src/test/data/ImportCommandTest/sample.txt");
        this.assertResultMessage("Import failure: 0 tasks added \nInvalid lines: 1,2,3");
        this.assertListSize(3);
    }
}
