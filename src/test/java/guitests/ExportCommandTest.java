package guitests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

//@@author A0138862W
public class ExportCommandTest extends TaskManagerGuiTest{

    //@@author A0138862W
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    //@@author A0138862W
    @Before
    public void before(){
        this.commandBox.runCommand("clear");
        this.assertListSize(0);
        
        //add a floating task
        this.commandBox.runCommand("add a floating task");
        this.assertListSize(1);
        
        //add a deadline
        this.commandBox.runCommand("add a deadline by next friday 7pm");
        this.assertListSize(2);
        
        //add an event
        this.commandBox.runCommand("add an event from tomorrow 8pm to next monday 7pm");
        this.assertListSize(3);
    }
    
    //@@author A0138862W
    @Test
    public void export_success() throws IOException{
        final long EXPECTED_FILE_LENGTH_ALLCSV = 271;
        final long EXPECTED_FILE_LENGTH_TASKSCSV = 155;
        final long EXPECTED_FILE_LENGTH_DEADLINESEVENTSCSV = 208;
        
        // export all tasks
        File allCsv = testFolder.newFile("all.csv");
        this.commandBox.runCommand("export to "+allCsv.getAbsolutePath());
        this.assertResultMessage("CSV exported.");
        assertTrue(allCsv.exists());
        assertEquals(EXPECTED_FILE_LENGTH_ALLCSV, allCsv.length());
        
        // export only floating tasks
        File tasksCsv = testFolder.newFile("tasks.csv");
        this.commandBox.runCommand("export tasks to "+tasksCsv.getAbsolutePath());
        this.assertResultMessage("CSV exported.");
        assertTrue(tasksCsv.exists());
        assertEquals(EXPECTED_FILE_LENGTH_TASKSCSV, tasksCsv.length());
        
        // export deadlines and events 
        File deadlinesEventsCsv = testFolder.newFile("deadlines-events.csv");
        this.commandBox.runCommand("export deadlines events to "+deadlinesEventsCsv.getAbsolutePath());
        this.assertResultMessage("CSV exported.");
        assertTrue(deadlinesEventsCsv.exists());
        assertEquals(EXPECTED_FILE_LENGTH_DEADLINESEVENTSCSV, deadlinesEventsCsv.length());
    }
    
    //@@author A0138862W
    @Test
    public void export_failure_invalidFilePath(){
        this.commandBox.runCommand("export to /invalid/file/path");
        this.assertResultMessage("Failed to export CSV.");
    }
    
    

}
