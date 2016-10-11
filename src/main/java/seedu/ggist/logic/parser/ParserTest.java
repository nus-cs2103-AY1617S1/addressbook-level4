package seedu.ggist.logic.parser;

import static org.junit.Assert.*;


import org.junit.Test;

import seedu.ggist.logic.commands.Command;
import seedu.ggist.logic.commands.CommandStub;
import seedu.ggist.logic.commands.IncorrectCommand;

public class ParserTest {
    
    Parser parser = new Parser();
    String userInput_valid = "add la la la,oct 18, 1800";
    Command invalid_command = new IncorrectCommand("");

    @Test
        /**
         * Test Parser prepareAdd method
         */
        public void prepareAdd_valid_input_returnsTrue(){
            Command test = parser.parseCommand(userInput_valid);
        }
}

