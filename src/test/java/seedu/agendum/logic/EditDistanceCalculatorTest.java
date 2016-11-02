package seedu.agendum.logic;

import org.junit.Test;
import seedu.agendum.logic.parser.EditDistanceCalculator;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

//@@author A0003878Y
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
        assertEquals(EditDistanceCalculator.findCommandCompletion("ad").get(), "add");
        assertEquals(EditDistanceCalculator.findCommandCompletion("ma").get(), "mark");
        assertEquals(EditDistanceCalculator.findCommandCompletion("un"), Optional.empty()); // ambiguous returns nothing. Can be undo or unmark
        assertEquals(EditDistanceCalculator.findCommandCompletion("unm").get(), "unmark");
        assertEquals(EditDistanceCalculator.findCommandCompletion("und").get(), "undo");
        assertEquals(EditDistanceCalculator.findCommandCompletion("st").get(), "store");
        assertEquals(EditDistanceCalculator.findCommandCompletion("de").get(), "delete");
        assertEquals(EditDistanceCalculator.findCommandCompletion("he").get(), "help");
        assertEquals(EditDistanceCalculator.findCommandCompletion("sc").get(), "schedule");
        assertEquals(EditDistanceCalculator.findCommandCompletion("r").get(), "rename");
    }

}
