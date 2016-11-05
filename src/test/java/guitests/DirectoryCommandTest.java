package guitests;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;
import seedu.task.logic.commands.DirectoryCommand;
import seedu.task.testutil.TestUtil;

// @@author A0147944U
public class DirectoryCommandTest extends TaskManagerGuiTest {
    
    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);
    
    public static final String filepath_A = TestUtil.getFilePathInSandboxFolder("backup_pre_existing");
    public static final String filepath_B = TestUtil.getFilePathInSandboxFolder("really_inexistent_file");

    @Test
    public void directory() {
        
        // Run directory command onto an existing file
        commandBox.runCommand("directory " + filepath_A);
        // This message is intended for JUnit testing as it does not run via a .jar file
        assertResultMessage(DirectoryCommand.MESSAGE_UNSUPPORTED_OPERATING_SYSTEM);
        assertTrue(isConfigFileUpdated());
        
        // Run directory command onto a non-existing file
        commandBox.runCommand("directory " + filepath_B);
        assertResultMessage(String.format(DirectoryCommand.MESSAGE_FILE_NOT_FOUND_ERROR, (TestUtil.getFilePathInSandboxFolder("really_inexistent_file") + ".xml")));
    }
    
    private Boolean isConfigFileUpdated() {
        Config config = new Config();
        File configFile = new File("config.json");
        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + "config.json" + ": " + e);
        }
        return (config.getTaskManagerFilePath().equals(filepath_A + ".xml"));
    }

}
