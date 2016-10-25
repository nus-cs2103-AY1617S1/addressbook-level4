//@@author A0146752B
package seedu.address.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.logic.parser.DeleteParser;

public class DeleteParserTest {

    @Test
    public void checkDeleteParserParseArgument_returnsCorrectArguments() {

       String arguments = "event 1";
       
       assertEquals("event", DeleteParser.parseArguments(arguments).get(0));
       assertEquals("1", DeleteParser.parseArguments(arguments).get(1));
       
    }

}
