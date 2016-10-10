package seedu.address.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.logic.parser.AddParser;

public class AddParserTest {

	
	@Test
	public void checkIsTask_returnsTrue() {

		String arguments = "by: 10-08-2016 1900";

		assertTrue(AddParser.isTask(arguments));

	}

}
