package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.SetStorageCommand;

//@@author A0138978E
public class SetStorageCommandTest {

	@Test
	public void setStorage_nullInput() {
		SetStorageCommand command = new SetStorageCommand(null);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(SetStorageCommand.MESSAGE_SET_STORAGE_FAILURE_PATH_INVALID, null)));
	}
	
	@Test
	public void setStorage_emptyInput() {
		SetStorageCommand command = new SetStorageCommand("");
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(SetStorageCommand.MESSAGE_SET_STORAGE_FAILURE_PATH_INVALID, "")));
	}
	
	@Test
	public void setStorage_validLocation() {
		String homeDir = System.getProperty("user.home");
		SetStorageCommand command = new SetStorageCommand(homeDir);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(SetStorageCommand.MESSAGE_SET_STORAGE_SUCCESS, homeDir)));
	}
	

}
