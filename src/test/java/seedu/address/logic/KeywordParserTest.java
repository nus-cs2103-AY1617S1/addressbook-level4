package seedu.address.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import seedu.address.logic.parser.KeywordParser;

import org.junit.Test;

public class KeywordParserTest {

    @Test
    public void parses_addCommand_input_no_tag() {

        String input = "add Assignment by friday tag important";
        KeywordParser parser = new KeywordParser("add", "by", "tag");
        ArrayList<String[]> list = parser.parse(input);
        assertTrue(list.get(0)[0].equals("Assignment"));

    }

}
