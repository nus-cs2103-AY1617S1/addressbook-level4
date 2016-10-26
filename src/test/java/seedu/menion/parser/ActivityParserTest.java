package seedu.menion.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.logic.parser.ActivityParser;

public class ActivityParserTest {

	@Test
	public void checkOutputOfListCommand_returnsArgument() {
		
		ActivityParser testParser = new ActivityParser();
		String arguments = "list all";
		
		testParser.parseCommand(arguments);
		
	}

}
