//@@author A0147944U
package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.BackupCommand;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class BackupCommandTest extends TaskManagerGuiTest {

    public static final String filepath_A = TestUtil.getFilePathInSandboxFolder("empty");
    public static final String filepath_B = TestUtil.getFilePathInSandboxFolder("notempty");
    public static final String filepath_C = "c:/inaccessible";
    public static final String filepath_D = "invalid:/drive";
    public static final String filepath_E = "c:/invalid>character";

    @Test
    public void backup() {

        //verify an empty TaskManager can be backed up in a valid directory
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task manager has been cleared!");
        commandBox.runCommand("backup " + filepath_A);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_SUCCESS, filepath_A + ".xml"));
        //assertBackupCommandSuccess();
        
        //verify a non-empty TaskManager can be backed up in a valid directory
        commandBox.runCommand(TypicalTestTasks.taskH.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.taskH));
        commandBox.runCommand("backup " + filepath_B);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_SUCCESS, filepath_B + ".xml"));
        //assertBackupCommandSuccess();
        
      //verify TaskManager can be backed up in a valid directory onto an existing backup
        commandBox.runCommand("backup " + filepath_A);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_SUCCESS, filepath_A + ".xml"));
        //assertBackupCommandSuccess();

        //verify a TaskManager can't be backed up in an inaccessible directory
        commandBox.runCommand("backup " + filepath_C);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_FAILURE, filepath_C + ".xml"));
        //assertBackupCommandFailure();
        
        //verify a TaskManager can't be backed up in an invalid directory
        commandBox.runCommand("backup " + filepath_D);
        assertResultMessage(String.format(BackupCommand.MESSAGE_BACKUP_FAILURE, filepath_D + ".xml"));
        //assertBackupCommandFailure();
        commandBox.runCommand("backup " + filepath_E);
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
        //assertBackupCommandFailure();
    }

    /* Needs more in depth testing
     * 1) Check content of successful backup
     * private void assertBackupCommandSuccess() {

    }
    
    private void assertBackupCommandFailure() {

    }*/
}
