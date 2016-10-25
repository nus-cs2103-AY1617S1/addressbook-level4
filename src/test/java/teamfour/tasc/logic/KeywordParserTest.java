package teamfour.tasc.logic;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import teamfour.tasc.logic.parser.KeywordParser;

public class KeywordParserTest {

    //@@author A0127014W
    @Test
    public void parses_addCommand_input_floatingTask() {

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
        assertEquals(list.get("add"), "Assignment");
        assertEquals(list.get("by"), "friday");
        assertEquals(list.get("tag"), "important");
    }

    @Test
    public void parses_addCommand_input_event() {

        String input = "add \"Assignment\" from friday to saturday tag important";
        KeywordParser parser = new KeywordParser("add", "from", "to", "tag");
        HashMap<String, String> list = parser.parse(input);
        assertTrue(list.get("add").equals("Assignment"));
        assertEquals(list.get("from"), "friday");
        assertEquals(list.get("to"), "saturday");
    }

    @Test
    public void parses_addCommand_input_multipleTags() {

        String input = "add \"Assignment\" by friday tag important school urgent";
        KeywordParser parser = new KeywordParser("add", "by", "tag");
        HashMap<String, String> list = parser.parse(input);
        assertTrue(list.get("add").equals("Assignment"));
        assertEquals(list.get("by"), "friday");
        assertTrue(list.get("tag").equals("important school urgent"));
    }

    @Test
    public void parses_addCommand_optionalInputs() {

        String input = "add \"Assignment\" tag important";
        KeywordParser parser = new KeywordParser("add", "by", "tag");
        HashMap<String, String> list = parser.parseKeywordsWithoutFixedOrder(input);
        assertEquals(list.get("add"), "Assignment");
        assertEquals(list.get("tag"), "important");

        String input2 = "add \"Assignment\" from monday to thursday";
        KeywordParser parser2 = new KeywordParser("add", "from", "to", "tag");
        HashMap<String, String> list2 = parser2.parseKeywordsWithoutFixedOrder(input2);
        assertEquals(list2.get("add"), "Assignment");
        assertEquals(list2.get("from"), "monday");
        assertEquals(list2.get("to"), "thursday");
    }
    //@@author

    @Test
    public void parses_removeKeyword_hasNoValue() {
        String input = "update 1 removefrom removeby removeto";
        KeywordParser parser = new KeywordParser("update", "removefrom", "removeby", "removeto");
        HashMap<String, String> list = parser.parseKeywordsWithoutFixedOrder(input);
        assertEquals("", list.get("removefrom"));
        assertEquals("", list.get("removeto"));
        assertEquals("", list.get("removeby"));
    }
}
