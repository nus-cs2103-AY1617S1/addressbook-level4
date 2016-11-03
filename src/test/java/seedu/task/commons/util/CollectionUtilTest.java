package seedu.task.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.item.Deadline;
import seedu.task.model.item.Description;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;

/**
 * Utility methods test cases
 * @@author A0121608N
 */
public class CollectionUtilTest {

    /**
     * Test for helper class CollectionUtil
     */

    @Test
    public void test() throws Exception{
        Name testName = new Name("Task 1");
        Description testDescription = new Description("finish my part");
        Deadline testDeadline = null;
        EventDuration testEventDuration = new EventDuration("today", "next week");
        
        assertTrue(CollectionUtil.isAnyNull(testName, testDescription, testDeadline, testEventDuration));
        assertFalse(CollectionUtil.isAnyNull(testName, testDescription, testEventDuration));
    }

}
