package seedu.address.logic;

import static org.junit.Assert.*;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;

import org.junit.Test;

public class ParserTest {

    @Test
    public void prepareAdd_preparesCorrectParameters() {
        Parser parser = new Parser();
        Command add = parser.parseCommand("add \"Assignment\" by monday repeattime daily 4 tag late");

    }

}
