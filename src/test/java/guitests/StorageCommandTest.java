package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.tasklist.commons.core.Config;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.util.ConfigUtil;
import seedu.tasklist.logic.commands.StorageCommand;
import seedu.tasklist.storage.Storage;

public class StorageCommandTest extends TaskListGuiTest {

	private String testStorageCommandFilePath = "src/test/data/StorageCommandTestFolder/tesTaskList.xml";
	private String testFileName = "tesTaskList.xml";
	private final String COMMAND_WORD = "storage ";
	private String defaultFilePath = "src/test/data/sandbox/tempTaskList.xml";
	private String defaultConfigFile = "src/test/data/ConfigUtilTest/TestConfig.json";
	

	private Config config;
	private Storage storage;
	
	/*@Before
	 public void setup(){
		config.setTaskListFilePath(defaultFilePath);
		try {
			ConfigUtil.saveConfig(config, defaultConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 storage.setTaskListFilePath(defaultFilePath);
	}
*/
	/*// Change File path
	@Test
	public void ChangeFilePath() {

		assertFilePathChange(COMMAND_WORD + testStorageCommandFilePath, getPath(testStorageCommandFilePath));
	}*/

	// Invalid Command
	@Test
	public void invalidStorageCommand() {
		commandBox.runCommand("storages docs/");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	// Invalid file Path
	@Test
	public void invalidFilePath() {
		commandBox.runCommand("storage doc");
		assertResultMessage(StorageCommand.MESSAGE_FILE_PATH_NOT_EXIST);
	}

	public String getPath(String filepath) {
		File file = new File(filepath);
		String fileName = file.getName().toString();
		return fileName;
	}

	private void assertFilePathChange(String filepath, String fileName) {
		commandBox.runCommand(filepath);
		assertTrue(fileName.equals(testFileName));
	}
}