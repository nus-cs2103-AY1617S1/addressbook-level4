package seedu.address.logic;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import seedu.address.logic.commands.CommandHelper;

import org.junit.Test;

public class CommandHelperTest {

    @Test
    public void convertToStringMultipleDates_shortName_correct_date_month() {
        String date = "13 sep";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);

        String date2 = "13th sept";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates2.get(0).getDate() == 13);
        assertTrue(dates2.get(0).getMonth() == 8);

        String date3 = "sept 13";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates3.get(0).getDate() == 13);
        assertTrue(dates3.get(0).getMonth() == 8);
    }

    @Test
    public void convertToStringMultipleDates_fullName_correct_date_month() {
        String date = "thirteenth september";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);
    }

    @Test
    public void convertToStringMultipleDates_correct_dayOfWeek_timeOfDay() {
        String date = "next friday 7pm";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDay() == 5);
        assertTrue(dates.get(0).getHours() == 19);
    }

    @Test
    public void convertToStringMultipleDates_getsMultipleDates() {
        String date = "20 aug and 17 april";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.size() == 2);
    }
}
