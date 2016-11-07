package w15c2.tusk.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import w15c2.tusk.logic.commands.CommandHistory;

//@@author A0138978E
public class CommandHistoryTest {
	
	private CommandHistory commandHistory;
	
	@Before
	public void setup() {
		commandHistory = new CommandHistory();
	}
	
	@Test
	public void getPreviousCommand_noHistory_returnEmptyString() {
		assertEquals("", commandHistory.getPreviousCommand());
	}
	
	@Test
	public void getPreviousCommand_oneCommandInHistory_returnCommand() {
		String commandText = "add meeting";
		
		commandHistory.addCommandTextToHistory(commandText);
		assertEquals(commandText, commandHistory.getPreviousCommand());
	}
	
	
	@Test
	public void getPreviousCommandTwice_oneCommandInHistory_returnCommand() {
		String commandText = "add meeting";
		
		commandHistory.addCommandTextToHistory(commandText);
		assertEquals(commandText, commandHistory.getPreviousCommand());
		
		commandHistory.addCommandTextToHistory(commandText);
		assertEquals(commandText, commandHistory.getPreviousCommand());
	}
	
	@Test
	public void getPreviousCommand_twoCommandsInHistory_returnCommands() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getPreviousCommand());
	}
	
	@Test
	public void getPreviousCommandTwice_twoCommandsInHistory_returnCommands() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getPreviousCommand());
		assertEquals(commandText1, commandHistory.getPreviousCommand());
	}
	
	@Test
	public void getNextCommand_noHistory_returnEmptyString() {
		assertEquals(commandHistory.getNextCommand(), "");
	}
	
	@Test
	public void getNextCommand_oneCommandInHistory_returnCommand() {
		String commandText = "add meeting";
		
		commandHistory.addCommandTextToHistory(commandText);
		assertEquals(commandText, commandHistory.getNextCommand());
	}
	
	
	@Test
	public void getNextCommandTwice_oneCommandInHistory_returnCommand() {
		String commandText = "add meeting";
		
		commandHistory.addCommandTextToHistory(commandText);
		assertEquals(commandText, commandHistory.getNextCommand());
		
		commandHistory.addCommandTextToHistory(commandText);
		assertEquals(commandText, commandHistory.getNextCommand());
	}
	
	
	@Test
	public void getNextCommand_twoCommandsInHistory_returnCommand() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getNextCommand());
	}
	
	@Test
	public void getNextCommandTwice_twoCommandsInHistory_returnCommand() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getNextCommand());
		assertEquals(commandText2, commandHistory.getNextCommand());
	}
	
	@Test
	public void getPreviousThenNext_oneCommandInHistory_returnCommand() {
		String commandText = "add meeting";
		
		commandHistory.addCommandTextToHistory(commandText);
		assertEquals(commandText, commandHistory.getPreviousCommand());
		assertEquals(commandText, commandHistory.getNextCommand());
	}
	
	@Test
	public void getPreviousThenNext_twoCommandsInHistory_returnCommands() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getPreviousCommand());
		assertEquals(commandText2, commandHistory.getNextCommand());
	}
	
	@Test
	public void getPreviousThenPreviousThenNext_twoCommandsInHistory_returnCommands() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getPreviousCommand());
		assertEquals(commandText1, commandHistory.getPreviousCommand());
		assertEquals(commandText2, commandHistory.getNextCommand());
	}
	
	@Test
	public void getPreviousThenPreviousThenPreviousThenNext_twoCommandsInHistory_returnCommands() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getPreviousCommand());
		assertEquals(commandText1, commandHistory.getPreviousCommand());
		assertEquals(commandText1, commandHistory.getPreviousCommand());
		assertEquals(commandText2, commandHistory.getNextCommand());
	}
	
	@Test
	public void getNextThenPrevious_twoCommandsInHistory_returnCommands() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getNextCommand());
		assertEquals(commandText1, commandHistory.getPreviousCommand());
	}
	
	@Test
	public void getNextThenPreviousThenNext_twoCommandsInHistory_returnCommands() {
		String commandText1 = "add meeting";
		String commandText2 = "add dinner";
		
		commandHistory.addCommandTextToHistory(commandText1);
		commandHistory.addCommandTextToHistory(commandText2);
		assertEquals(commandText2, commandHistory.getNextCommand());
		assertEquals(commandText1, commandHistory.getPreviousCommand());
		assertEquals(commandText2, commandHistory.getNextCommand());
	}
}
