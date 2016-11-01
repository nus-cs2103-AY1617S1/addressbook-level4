package w15c2.tusk.logic.autocomplete;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import w15c2.tusk.logic.autocomplete.AutocompleteSource;
import w15c2.tusk.logic.parser.CommandParserList;

//@@author A0138978E
public class AutocompleteSourceTest {
	
	@Test
	public void getCommands() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Set<String> expectedCommands = new HashSet<String>();
		Class<?>[] parserList = CommandParserList.getList();
		
		for (Class<?> parser : parserList) {
			String command = (String) parser.getField("COMMAND_WORD").get(null);
			String altCommandWord = (String) parser.getField("ALTERNATE_COMMAND_WORD").get(null);
			
			expectedCommands.add(command);
            if (altCommandWord != null) {
                expectedCommands.add(altCommandWord);
            }
		}
		
		assertEquals(expectedCommands, AutocompleteSource.getCommands());
		
	}
}
