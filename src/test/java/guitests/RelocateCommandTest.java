package guitests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import harmony.mastermind.commons.util.FileUtil;

//@@author A0139194X
public class RelocateCommandTest extends TaskManagerGuiTest {

    private static String SECOND_TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/MigrationMastermindStorageTest/");
    private static String ORIGINAL_FOLDER = FileUtil.getPath("./data");

    
    @Test
    public void relocate_success() {
        this.commandBox.runCommand("relocate " + SECOND_TEST_DATA_FOLDER);
        this.assertResultMessage("Relocated save location to " + SECOND_TEST_DATA_FOLDER);
        
        reset();
    }
    
    //resets save location
    private void reset() {
        this.commandBox.runCommand("relocate " + ORIGINAL_FOLDER);
    }
}
