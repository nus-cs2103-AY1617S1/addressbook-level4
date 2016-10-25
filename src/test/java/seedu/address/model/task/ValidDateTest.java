package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class ValidDateTest {

    @Test
    public void isValidDate_trueIfDateFormatIsCorrect() {

        ArrayList<String> validDates = new ArrayList<String>();
        ArrayList<String> invalidDates = new ArrayList<String>();

        validDates.add("01/1/18");
        validDates.add("1/1/16");
        validDates.add("1/01/16");
        validDates.add("31/10/10");
        validDates.add("30/6/10");
        validDates.add("6/6/10");
        validDates.add("29/2/16");
        validDates.add("01/01/16");

        invalidDates.add("32/1/16"); // day out of range
        invalidDates.add("1/13/20"); // month out of range
        invalidDates.add("29/2/17"); // 2017 not a leap year
        invalidDates.add("30/2/16"); // February has max 29 days even during leap year
        invalidDates.add("31/6/16"); // June 30 days
        invalidDates.add("111/2/16"); // invalid day
        invalidDates.add("22/2/2008"); // invalid year
        invalidDates.add("29/a/20"); // invalid month

        for (int i = 0; i < validDates.size(); i++) {
            // System.out.println(validDates.get(i) + " is " +
            // Date.isValidDate(validDates.get(i)));
            assertTrue(Time.isValidDate(validDates.get(i)));
        }

        for (int i = 0; i < invalidDates.size(); i++) {
            // System.out.println(invalidDates.get(i) + " is " +
            // Date.isValidDate(invalidDates.get(i)));
            assertFalse(Time.isValidDate(invalidDates.get(i)));
        }
    }
}
