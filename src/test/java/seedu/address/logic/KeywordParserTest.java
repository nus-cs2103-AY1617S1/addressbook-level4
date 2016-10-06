package seedu.address.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import seedu.address.logic.parser.KeywordParser;

import org.junit.Test;

public class KeywordParserTest {

    @Test
    public void parses_addCommand_input_task() {

        String input = "add \"Assignment\" by friday tag important";
        KeywordParser parser = new KeywordParser("add", "by", "tag");
        ArrayList<String[]> list = parser.parse(input);
        assertTrue(list.get(0)[0].equals("add"));
        assertTrue(list.get(0)[1].equals("Assignment"));
        assertTrue(list.get(1)[0].equals("by"));
        assertTrue(list.get(1)[1].equals("friday"));
    }

    @Test
    public void parses_addCommand_input_event() {

        String input = "add \"Assignment\" from friday to saturday tag important";
        KeywordParser parser = new KeywordParser("add", "from", "to", "tag");
        ArrayList<String[]> list = parser.parse(input);
        assertTrue(list.get(0)[0].equals("add"));
        assertTrue(list.get(0)[1].equals("Assignment"));
        assertTrue(list.get(1)[0].equals("from"));
        assertTrue(list.get(1)[1].equals("friday"));
        assertTrue(list.get(2)[0].equals("to"));
        assertTrue(list.get(2)[1].equals("saturday"));
    }
}
