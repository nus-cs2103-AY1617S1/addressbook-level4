package guitests;

import org.junit.Test;

import seedu.savvytasker.commons.core.Config;
import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.logic.commands.HelpCommand;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.storage.JsonConfigStorage;
import seedu.savvytasker.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static seedu.savvytasker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;

import java.util.Optional;

//@@author A0138431L
public class StorageCommandTest extends SavvyTaskerGuiTest {
	 
	private static final String CONFIG_JSON = "config.json";
	private static final String CONFIG_LOCATION = "./src/test/data/SaveLocationCommandTest";
	    	    
	@Test
	public void saveToValidFilePath_success() throws DataConversionException, IOException, DuplicateTaskException {
	    String testFilePath = "./src/test/data/StorageCommandTest/newStorageLocation/";
	    commandBox.runCommand("storage " + testFilePath);
	    assertWriteToJsonSuccess();
	    assertWriteToXmlSuccess();
	}
	private void assertWriteToJsonSuccess() throws DataConversionException {
	    JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_LOCATION);
	    Optional<Config> config = jsonConfigStorage.readConfig(CONFIG_JSON);
	    assert(config.isPresent());
	} 

	private void assertWriteToXmlSuccess() {
	    TestTask[] currentList = td.getTypicalTasks();
	    assertTrue(taskListPanel.isListMatching(currentList));
	}
	
	@Test
	public void storage() {
        //invalid command
        commandBox.runCommand("store");
        assertResultMessage("Input: store\n" + 
        String.format(MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));
    }

}
//@@author