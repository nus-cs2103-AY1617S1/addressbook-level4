package tars.logic;

import java.io.File;

import org.junit.Test;

import tars.commons.util.FileUtil;
import tars.logic.commands.CdCommand;
import tars.model.Tars;
import tars.storage.TarsStorage;
import tars.storage.XmlTarsStorage;

/**
 * Logic command test for cd
 * 
 * @@author A0124333U
 */
public class CdLogicCommandTest extends LogicCommandTest {
  @Test
  public void execute_cd_incorrectArgsFormatErrorMessageShown() throws Exception {
    assertCommandBehavior("cd ", CdCommand.MESSAGE_INVALID_FILEPATH);
  }

  @Test
  public void execute_cd_invalidFileTypeErrorMessageShown() throws Exception {
    assertCommandBehavior("cd invalidFileType", CdCommand.MESSAGE_INVALID_FILEPATH);
  }

  @Test
  public void execute_cd_new_file_success() throws Exception {
    String tempTestTarsFilePath = saveFolder.getRoot().getPath() + "TempTestTars.xml";
    assertCommandBehavior("cd " + tempTestTarsFilePath,
        String.format(CdCommand.MESSAGE_SUCCESS_NEW_FILE, tempTestTarsFilePath));
  }

  @Test
  public void execute_cd_existing_file_failureToReadExistingFile() throws Exception {
    String existingFilePath = saveFolder.getRoot().getPath() + "TempTars.xml";
    File existingFile = new File(existingFilePath);
    FileUtil.createIfMissing(existingFile);
    assertCommandBehavior("cd " + existingFilePath, CdCommand.MESSAGE_FAILURE_READ_FILE);
  }

  @Test
  public void execute_cd_existing_file_success() throws Exception {
    String existingFilePath = saveFolder.getRoot().getPath() + "TempTars.xml";
    TarsStorage testStorage = new XmlTarsStorage(existingFilePath);
    testStorage.saveTars(Tars.getEmptyTars());
    assertCommandBehavior("cd " + existingFilePath,
        String.format(CdCommand.MESSAGE_SUCCESS_EXISTING_FILE, existingFilePath));
  }
}
