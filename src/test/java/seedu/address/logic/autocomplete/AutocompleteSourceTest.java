package seedu.address.logic.autocomplete;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.parser.CommandParserList;

//@@author A0138978E
public class AutocompleteSourceTest {
	
	@Test
	public void getCommands() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Set<String> expectedCommands = new HashSet<String>();
		Class<?>[] parserList = CommandParserList.getList();
		
		for (Class<?> parser : parserList) {
			expectedCommands.add((String) parser.getField("COMMAND_WORD").get(null));
		}
		
		assertEquals(expectedCommands, AutocompleteSource.getCommands());
		
	}
}
