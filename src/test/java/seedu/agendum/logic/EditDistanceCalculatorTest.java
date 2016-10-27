//@@author A0003878Y
package seedu.agendum.logic;

import org.junit.Test;
import seedu.agendum.logic.parser.EditDistanceCalculator;

import static org.junit.Assert.assertEquals;

public class EditDistanceCalculatorTest {

    @Test
    public void commandSuggestionTest() throws Exception {
        assertEquals(EditDistanceCalculator.parseString("adr").get(), "add");
        assertEquals(EditDistanceCalculator.parseString("marc").get(), "mark");
        assertEquals(EditDistanceCalculator.parseString("markk").get(), "mark");
        assertEquals(EditDistanceCalculator.parseString("storee").get(), "store");
        assertEquals(EditDistanceCalculator.parseString("daletr").get(), "delete");
        assertEquals(EditDistanceCalculator.parseString("hell").get(), "help");
        assertEquals(EditDistanceCalculator.parseString("shdule").get(), "schedule");
        assertEquals(EditDistanceCalculator.parseString("rname").get(), "rename");
    }
    
}
