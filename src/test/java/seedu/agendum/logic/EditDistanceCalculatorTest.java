//@@author A0003878Y
package seedu.agendum.logic;

import org.junit.Test;
import seedu.agendum.logic.parser.EditDistanceCalculator;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class EditDistanceCalculatorTest {

    @Test
    public void closestCommandMatchTest() throws Exception {
        assertEquals(EditDistanceCalculator.closestCommandMatch("adr").get(), "add");
        assertEquals(EditDistanceCalculator.closestCommandMatch("marc").get(), "mark");
        assertEquals(EditDistanceCalculator.closestCommandMatch("markk").get(), "mark");
        assertEquals(EditDistanceCalculator.closestCommandMatch("storee").get(), "store");
        assertEquals(EditDistanceCalculator.closestCommandMatch("daletr").get(), "delete");
        assertEquals(EditDistanceCalculator.closestCommandMatch("hell").get(), "help");
        assertEquals(EditDistanceCalculator.closestCommandMatch("shdule").get(), "schedule");
        assertEquals(EditDistanceCalculator.closestCommandMatch("rname").get(), "rename");
    }

    @Test
    public void commandCompletion() throws Exception {
        assertEquals(EditDistanceCalculator.commandCompletion("ad").get(), "add");
        assertEquals(EditDistanceCalculator.commandCompletion("ma").get(), "mark");
        assertEquals(EditDistanceCalculator.commandCompletion("un"), Optional.empty()); // ambiguous returns nothing. Can be undo or unmark
        assertEquals(EditDistanceCalculator.commandCompletion("unm").get(), "unmark");
        assertEquals(EditDistanceCalculator.commandCompletion("und").get(), "undo");
        assertEquals(EditDistanceCalculator.commandCompletion("st").get(), "store");
        assertEquals(EditDistanceCalculator.commandCompletion("de").get(), "delete");
        assertEquals(EditDistanceCalculator.commandCompletion("he").get(), "help");
        assertEquals(EditDistanceCalculator.commandCompletion("sc").get(), "schedule");
        assertEquals(EditDistanceCalculator.commandCompletion("r").get(), "rename");
    }

}
