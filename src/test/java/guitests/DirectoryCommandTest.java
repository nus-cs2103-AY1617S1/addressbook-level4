package guitests;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;
import seedu.task.testutil.TestUtil;

// @@author A0147944U
public class DirectoryCommandTest extends TaskManagerGuiTest {
    
    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);
    
    public static final String filepath_A = TestUtil.getFilePathInSandboxFolder("backup_pre_existing");

    @Test
    public void directory() {
        
        // Run directory command onto an existing file
        commandBox.runCommand("directory " + filepath_A);
    }
    
    @Test
    public void directory2() {
        
        // Run directory command onto an existing file
        
        Config config = new Config();
        File configFile = new File("config.json");
        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + "config.json" + ": " + e);
        }
        config.getTaskManagerFilePath().equals(filepath_A + ".xml");
    }

}
