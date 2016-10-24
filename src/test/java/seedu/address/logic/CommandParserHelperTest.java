package seedu.address.logic;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.CommandParserHelper;

//@@author A0139655U
public class CommandParserHelperTest {
    
    CommandParserHelper helper;
    
    @Test
    public void prepareAdd_containsEscape_TwoKeywords() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("\"cut word count from 1000 to 500\" from 1am to 3pm"
                    + " repeat every 3 days -h");
            assertEquals(map.get("taskName").get(), "cut word count from 1000 to 500");
            assertEquals(map.get("startDate").get(), "1am");
            assertEquals(map.get("endDate").get(), "3pm");
            assertEquals(map.get("rate").get(), "3");
            assertEquals(map.get("timePeriod").get(), "days");
            assertEquals(map.get("priority").get(), "h");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_containsEscape_OneKeyword_Recurrence() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("\"cut word count from 1000 to 500\" from 1am repeat every 5 days");
            assertEquals(map.get("taskName").get(), "cut word count from 1000 to 500");
            assertEquals(map.get("startDate").get(), "1am");
            assertEquals(map.get("endDate"), Optional.empty());
            assertEquals(map.get("rate").get(), "5");
            assertEquals(map.get("timePeriod").get(), "days");
            assertEquals(map.get("priority").get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_containsEscape_NoKeyword_Priority() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("\"cut word count from 1000 to 500\" -high");
            assertEquals(map.get("taskName").get(), "cut word count from 1000 to 500");
            assertEquals(map.get("startDate"), Optional.empty());
            assertEquals(map.get("endDate"), Optional.empty());
            assertEquals(map.get("rate"), Optional.empty());
            assertEquals(map.get("timePeriod"), Optional.empty());
            assertEquals(map.get("priority").get(), "high");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_TwoValidKeywords_TwoKeywords() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from 7pm to 8pm repeat every week -l");
            assertEquals(map.get("taskName").get(), "have dinner");
            assertEquals(map.get("startDate").get(), "7pm");
            assertEquals(map.get("endDate").get(), "8pm");
            assertEquals(map.get("rate"), Optional.empty());
            assertEquals(map.get("timePeriod").get(), "week");
            assertEquals(map.get("priority").get(), "l");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_OneValidKeyword_TwoKeywords() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from the beach by 8pm");
            assertEquals(map.get("taskName").get(), "have dinner from the beach");
            assertEquals(map.get("startDate"), Optional.empty());
            assertEquals(map.get("endDate").get(), "8pm");
            assertEquals(map.get("rate"), Optional.empty());
            assertEquals(map.get("timePeriod"), Optional.empty());
            assertEquals(map.get("priority").get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_NoValidKeyword_FourKeywords() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from the beach by my house from a to b");
            assertEquals(map.get("taskName").get(), "have dinner from the beach by my house from a to b");
            assertEquals(map.get("startDate"), Optional.empty());
            assertEquals(map.get("endDate"), Optional.empty());
            assertEquals(map.get("rate"), Optional.empty());
            assertEquals(map.get("timePeriod"), Optional.empty());
            assertEquals(map.get("priority").get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_RepeatDates_TwoKeywords() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from 10:30pm from 10:40pm");
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), "Repeated start times are not allowed.");
        }
    }
    
    @Test
    public void prepareAdd_OneValidKeyword_OneKeyword() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from 10:30pm -low");
            assertEquals(map.get("taskName").get(), "have dinner");
            assertEquals(map.get("startDate").get(), "10:30pm");
            assertEquals(map.get("endDate"), Optional.empty());
            assertEquals(map.get("rate"), Optional.empty());
            assertEquals(map.get("timePeriod"), Optional.empty());
            assertEquals(map.get("priority").get(), "low");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_NoValidKeyword_OneKeyword() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from the beach repeat every monday");
            assertEquals(map.get("taskName").get(), "have dinner from the beach");
            assertEquals(map.get("startDate"), Optional.empty());
            assertEquals(map.get("endDate"), Optional.empty());
            assertEquals(map.get("rate"), Optional.empty());
            assertEquals(map.get("timePeriod").get(), "monday");
            assertEquals(map.get("priority").get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_NoKeyword() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("eat bingsu");
            assertEquals(map.get("taskName").get(), "eat bingsu");
            assertEquals(map.get("startDate"), Optional.empty());
            assertEquals(map.get("endDate"), Optional.empty());
            assertEquals(map.get("rate"), Optional.empty());
            assertEquals(map.get("timePeriod"), Optional.empty());
            assertEquals(map.get("priority").get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }

    @Test
    public void prepareAdd_Everything() {
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("buy nasi lemak from today 1pm to 13th Sep 2016 repeat every 3 weeks");
            assertEquals(map.get("taskName").get(), "buy nasi lemak");
            assertEquals(map.get("startDate").get(), "today 1pm");
            assertEquals(map.get("endDate").get(), "13th Sep 2016");
            assertEquals(map.get("rate").get(), "3");
            assertEquals(map.get("timePeriod").get(), "weeks");
            assertEquals(map.get("priority").get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
}
