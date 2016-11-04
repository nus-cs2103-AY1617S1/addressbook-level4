// @@author A0147944U
package guitests;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.logic.commands.BackupCommand;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.logging.Logger;

public class BackupCommandTest extends TaskManagerGuiTest {

    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    public static final String filepath_A = TestUtil.getFilePathInSandboxFolder("backup_not_pre_existing");
    public static final String filepath_B = TestUtil.getFilePathInSandboxFolder("backup_pre_existing");
    public static final String filepath_C = "c:/inaccessible";
    public static final String filepath_D = "invalid:/drive";
    public static final String filepath_E = "c:/invalid>character";
    public static final String filepath_F = TestUtil.getFilePathInSandboxFolder("protected");

    @Test
    public void backup() {
        // Remove backup_not_pre_existing.xml if it exists
        File notSupposedToBeHere = new File(filepath_A+ ".xml");
        if (notSupposedToBeHere.exists()) {
            logger.info("'backup_not_pre_existing' exists");
            if(FileUtils.deleteQuietly(notSupposedToBeHere)) {
                logger.info("Not anymore");
            } else {
                logger.warning("Unable to delete backup_not_pre_existing");
            }
        } else {
            logger.info("'backup_not_pre_existing' does not exist ");
        }
        
        //verify an empty TaskManager can be backed up in a valid directory
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task manager has been cleared!");
        commandBox.runCommand("backup " + filepath_A);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_SUCCESS, filepath_A + ".xml", "created"));
        
        //verify a non-empty TaskManager can be backed up in a valid directory
        commandBox.runCommand("add Help Jim with his task, at 2016-10-25 9am");
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.taskH));
        commandBox.runCommand("backup " + filepath_B);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_SUCCESS, filepath_B + ".xml", "overwritten"));
        
        //verify TaskManager can be backed up in a valid directory onto an existing backup
        commandBox.runCommand("backup " + filepath_A);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_SUCCESS, filepath_A + ".xml", "overwritten"));

        /* Disabled as Travis is does not have an inaccessible directory
        //verify a TaskManager can't be backed up in an inaccessible directory
        commandBox.runCommand("backup " + filepath_C);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_FAILURE, filepath_C + ".xml"));
        */
        
        /* Disabled as unable to test with FilePicker
        //verify if invalid directory given, FilePicker will be presented
        commandBox.runCommand("backup " + filepath_D);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_FAILURE, filepath_D + ".xml"));
        commandBox.runCommand("backup " + filepath_E);
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
        */
        
        //verify a TaskManager will detect if a file is protected
        commandBox.runCommand("backup " + filepath_F);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_ERROR, filepath_F + ".xml"));
    }
}
