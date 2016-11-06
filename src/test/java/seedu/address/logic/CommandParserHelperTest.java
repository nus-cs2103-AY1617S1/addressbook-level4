package seedu.address.logic;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.CommandParserHelper;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.TimePeriod;

//@@author A0139655U
public class CommandParserHelperTest {
    
    CommandParserHelper helper;
    
    @Test
    public void prepareAdd_containsEscape() {
        // EP: Two keywords in escape input
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("\"cut word count from 1000 to 500\" from 1am to 3pm"
                    + " repeat every 3 days -h");
            assertEquals(map.get(Name.getMapNameKey()).get(), "cut word count from 1000 to 500");
            assertEquals(map.get(DateTime.getMapStartDateKey()).get(), "1am");
            assertEquals(map.get(DateTime.getMapEndDateKey()).get(), "3pm");
            assertEquals(map.get(RecurrenceRate.getMapRateKey()).get(), "3");
            assertEquals(map.get(TimePeriod.getTimePeriodKey()).get(), "days");
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "h");
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: One keyword in escape input
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("\"cut word count from 1000 to 500\" "
                    + "from 1am repeat every 5 days");
            assertEquals(map.get(Name.getMapNameKey()).get(), "cut word count from 1000 to 500");
            assertEquals(map.get(DateTime.getMapStartDateKey()).get(), "1am");
            assertEquals(map.get(DateTime.getMapEndDateKey()), Optional.empty());
            assertEquals(map.get(RecurrenceRate.getMapRateKey()).get(), "5");
            assertEquals(map.get(TimePeriod.getTimePeriodKey()).get(), "days");
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: No keyword in escape input
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("\"cut word count from 1000 to 500\" -high");
            assertEquals(map.get(Name.getMapNameKey()).get(), "cut word count from 1000 to 500");
            assertEquals(map.get(DateTime.getMapStartDateKey()), Optional.empty());
            assertEquals(map.get(DateTime.getMapEndDateKey()), Optional.empty());
            assertEquals(map.get(RecurrenceRate.getMapRateKey()), Optional.empty());
            assertEquals(map.get(TimePeriod.getTimePeriodKey()), Optional.empty());
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "high");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }

    @Test
    public void prepareAdd_verifyKeywordsParsed() {
        // EP: Two valid keywords out of four keywords
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from the beach by my house "
                    + "from 7pm to 8pm repeat every week -l");
            assertEquals(map.get(Name.getMapNameKey()).get(), "have dinner from the beach by my house");
            assertEquals(map.get(DateTime.getMapStartDateKey()).get(), "7pm");
            assertEquals(map.get(DateTime.getMapEndDateKey()).get(), "8pm");
            assertEquals(map.get(RecurrenceRate.getMapRateKey()), Optional.empty());
            assertEquals(map.get(TimePeriod.getTimePeriodKey()).get(), "week");
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "l");
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: Two valid keywords out of two keywords
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from 7pm to 8pm repeat every week -l");
            assertEquals(map.get(Name.getMapNameKey()).get(), "have dinner");
            assertEquals(map.get(DateTime.getMapStartDateKey()).get(), "7pm");
            assertEquals(map.get(DateTime.getMapEndDateKey()).get(), "8pm");
            assertEquals(map.get(RecurrenceRate.getMapRateKey()), Optional.empty());
            assertEquals(map.get(TimePeriod.getTimePeriodKey()).get(), "week");
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "l");
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: One valid keyword out of two keywords
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from the beach by 8pm");
            assertEquals(map.get(Name.getMapNameKey()).get(), "have dinner from the beach");
            assertEquals(map.get(DateTime.getMapStartDateKey()), Optional.empty());
            assertEquals(map.get(DateTime.getMapEndDateKey()).get(), "8pm");
            assertEquals(map.get(RecurrenceRate.getMapRateKey()), Optional.empty());
            assertEquals(map.get(TimePeriod.getTimePeriodKey()), Optional.empty());
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: One valid keyword out of one keyword
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from 10:30pm -low");
            assertEquals(map.get(Name.getMapNameKey()).get(), "have dinner");
            assertEquals(map.get(DateTime.getMapStartDateKey()).get(), "10:30pm");
            assertEquals(map.get(DateTime.getMapEndDateKey()), Optional.empty());
            assertEquals(map.get(RecurrenceRate.getMapRateKey()), Optional.empty());
            assertEquals(map.get(TimePeriod.getTimePeriodKey()), Optional.empty());
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "low");
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: No valid keyword out of one keyword
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("have dinner from the beach");
            assertEquals(map.get(Name.getMapNameKey()).get(), "have dinner from the beach");
            assertEquals(map.get(DateTime.getMapStartDateKey()), Optional.empty());
            assertEquals(map.get(DateTime.getMapEndDateKey()), Optional.empty());
            assertEquals(map.get(RecurrenceRate.getMapRateKey()), Optional.empty());
            assertEquals(map.get(TimePeriod.getTimePeriodKey()), Optional.empty());
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: No valid keyword out of zero keyword
        try {
            helper = new CommandParserHelper();
            HashMap<String, Optional<String>> map = helper.prepareAdd("eat bingsu");
            assertEquals(map.get(Name.getMapNameKey()).get(), "eat bingsu");
            assertEquals(map.get(DateTime.getMapStartDateKey()), Optional.empty());
            assertEquals(map.get(DateTime.getMapEndDateKey()), Optional.empty());
            assertEquals(map.get(RecurrenceRate.getMapRateKey()), Optional.empty());
            assertEquals(map.get(TimePeriod.getTimePeriodKey()), Optional.empty());
            assertEquals(map.get(Priority.getMapPriorityKey()).get(), "medium");
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void prepareAdd_invalidDates() {
        // EP: repeat start dates
        try {
            helper = new CommandParserHelper();
            helper.prepareAdd("have dinner from 10:30pm from 10:40pm");
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), CommandParserHelper.getMessageRepeatedStartTime());
        }
        
        // EP: repeat end dates
        try {
            helper = new CommandParserHelper();
            helper.prepareAdd("have dinner to 10:30pm by 10:40pm");
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), CommandParserHelper.getMessageRepeatedEndTime());
        }
    }
}
