package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.forgetmenot.TestApp;
import seedu.forgetmenot.logic.commands.SetStorageCommand;
import seedu.forgetmenot.testutil.TestUtil;

//@@author A0147619W
public class SetStorageCommandTest extends TaskManagerGuiTest{
	
	@Test
	public void setstorage() throws InterruptedException, IOException {
		String wrongExtensionFilePath = "WrongExtension";
		commandBox.runCommand("setstorage " + wrongExtensionFilePath);
		assertResultMessage(SetStorageCommand.MESSAGE_WRONG_EXTENSION);
		
		String unWriteableFilePath = TestUtil.getFilePathInSandboxFolder("unwritable.xml");
		File unWriteableFile = new File(unWriteableFilePath);
		File unWriteableFolder = new File(unWriteableFilePath).getParentFile();
		unWriteableFolder.setWritable(false);
		Thread.sleep(300);
		commandBox.runCommand("setstorage " + unWriteableFilePath);
		assertResultMessage(SetStorageCommand.MESSAGE_NO_PERMISSION);
		
		
		unWriteableFolder.setWritable(true);
		Thread.sleep(300);
		unWriteableFile.createNewFile();
		Thread.sleep(300);
		unWriteableFolder.setWritable(false);
		Thread.sleep(300);
		commandBox.runCommand("setstorage " + unWriteableFilePath);
		assertResultMessage(SetStorageCommand.MESSAGE_ALREADY_EXISTS_NO_OVERWRITE);
		
		unWriteableFolder.setWritable(true);
		Thread.sleep(300);
		unWriteableFile.delete();
		Thread.sleep(300);
		
		String alreadyExistsFilePath = TestApp.SAVE_LOCATION_FOR_TESTING;
		commandBox.runCommand("setstorage " + alreadyExistsFilePath);
		assertResultMessage(String.format(SetStorageCommand.MESSAGE_ALREADY_EXISTS_SUCCESS, alreadyExistsFilePath));
		
		String newFilePath = TestUtil.getFilePathInSandboxFolder("newFile.xml");
		File newFile = new File(newFilePath);
		newFile.delete();
		Thread.sleep(300);
		commandBox.runCommand("setstorage " + newFilePath);
		assertResultMessage(String.format(SetStorageCommand.MESSAGE_SUCCESS, newFilePath));
		
		String resetFilePath = "data/taskmanager.xml";
		commandBox.runCommand("setstorage " + resetFilePath); // Reset storage location back to default
	}
	

}
