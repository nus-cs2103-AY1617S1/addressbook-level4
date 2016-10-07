package seedu.ggist.logic.parser;

import static org.junit.Assert.*;


import org.junit.Test;

import seedu.ggist.logic.commands.Command;
import seedu.ggist.logic.commands.CommandStub;

public class ParserTest {
    
    Parser parser = new Parser();
    String userInput_valid = "add buy milk, 18 oct, 1800, 1900, high, daily ";
    Command valid_add_command = new CommandStub("buy milk", "18 oct", "1800", "1900", "high", "daily");

    @Test
        /**
         * Test Parser prepareAdd method
         */
        public void prepareAdd_valid_input_returnsTrue(){
            assertSame(valid_add_command, parser.parseCommand(userInput_valid));
        }
}

