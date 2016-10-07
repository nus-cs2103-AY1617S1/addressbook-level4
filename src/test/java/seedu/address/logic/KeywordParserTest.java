package seedu.address.logic;

import static org.junit.Assert.*;

import java.util.HashMap;

import seedu.address.logic.parser.KeywordParser;

import org.junit.Test;

public class KeywordParserTest {

    @Test
    public void parses_addCommand_input_onlyTask() {

        String input = "add \"Assignment\"";
        KeywordParser parser = new KeywordParser("add");
        HashMap<String, String> list = parser.parse(input);
        assertTrue(list.get("add").equals("Assignment"));
    }

    @Test
    public void parses_addCommand_input_task() {

        String input = "add \"Assignment\" by friday tag important";
        KeywordParser parser = new KeywordParser("add", "by", "tag");
        HashMap<String, String> list = parser.parse(input);
        assertTrue(list.get("add").equals("Assignment"));
        assertTrue(list.get("by").equals("friday"));
    }

    @Test
    public void parses_addCommand_input_event() {

        String input = "add \"Assignment\" from friday to saturday tag important";
        KeywordParser parser = new KeywordParser("add", "from", "to", "tag");
        HashMap<String, String> list = parser.parse(input);
        assertTrue(list.get("add").equals("Assignment"));
        assertTrue(list.get("from").equals("friday"));
        assertTrue(list.get("to").equals("saturday"));
    }

    @Test
    public void parses_addCommand_input_multipleTags() {

        String input = "add \"Assignment\" by friday tag important school urgent";
        KeywordParser parser = new KeywordParser("add", "by", "tag");
        HashMap<String, String> list = parser.parse(input);
        assertTrue(list.get("add").equals("Assignment"));
        assertTrue(list.get("by").equals("friday"));
        assertTrue(list.get("tag").equals("important school urgent"));
    }
}
