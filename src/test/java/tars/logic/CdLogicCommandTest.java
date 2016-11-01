package tars.logic;

import org.junit.Test;

import tars.logic.commands.CdCommand;

/**
 * Logic command test for cd
 * 
 * @@author A0124333U
 */
public class CdLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_cd_incorrectArgsFormatErrorMessageShown()
            throws Exception {
        assertCommandBehavior("cd ", CdCommand.MESSAGE_INVALID_FILEPATH);
    }

    @Test
    public void execute_cd_invalidFileTypeErrorMessageShown() throws Exception {
        assertCommandBehavior("cd invalidFileType",
                CdCommand.MESSAGE_INVALID_FILEPATH);
    }

    @Test
    public void execute_cd_success() throws Exception {
        String tempTestTarsFilePath =
                saveFolder.getRoot().getPath() + "TempTestTars.xml";
        assertCommandBehavior("cd " + tempTestTarsFilePath,
                String.format(CdCommand.MESSAGE_SUCCESS, tempTestTarsFilePath));
    }
}
