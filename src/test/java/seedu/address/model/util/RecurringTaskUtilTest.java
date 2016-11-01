package seedu.address.model.util;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

import seedu.address.logic.util.RecurringTaskUtil;
import seedu.address.model.task.RecurringType;

//@@author A0135782Y
public class RecurringTaskUtilTest {
    @Test
    public void getNumElapsedTaskToAppend_noStartDate_daily() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2016-10-13"), null, getLocalDateByString("2016-10-11"), RecurringType.DAILY);
        assertEquals(numElapsed, 2);
    }

    @Test
    public void getNumElapsedTaskToAppend_hasStartDate_daily() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2016-10-13"), getLocalDateByString("2016-10-11"), getLocalDateByString("2016-10-12"), RecurringType.DAILY);
        assertEquals(numElapsed, 2);
    }    

    @Test
    public void getNumElapsedTaskToAppend_noStartDate_weekly() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2016-10-12"), null, getLocalDateByString("2016-10-11"), RecurringType.WEEKLY);
        assertEquals(numElapsed, 1);
    }

    @Test
    public void getNumElapsedTaskToAppend_hasStartDate_weekly() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2016-10-13"), getLocalDateByString("2016-10-11"), getLocalDateByString("2016-10-12"), RecurringType.WEEKLY);
        assertEquals(numElapsed, 1);
    }        

    @Test
    public void getNumElapsedTaskToAppend_noStartDate_monthly() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2016-11-01"), null, getLocalDateByString("2016-10-11"), RecurringType.MONTHLY);
        assertEquals(numElapsed, 1);
    }

    @Test
    public void getNumElapsedTaskToAppend_hasStartDate_monthly() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2016-11-01"), getLocalDateByString("2016-10-11"), getLocalDateByString("2016-10-12"), RecurringType.MONTHLY);
        assertEquals(numElapsed, 1);
    }            

    @Test
    public void getNumElapsedTaskToAppend_noStartDate_yearly() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2017-01-01"), null, getLocalDateByString("2016-10-11"), RecurringType.YEARLY);
        assertEquals(numElapsed, 1);
    }

    @Test
    public void getNumElapsedTaskToAppend_hasStartDate_yearly() {
        int numElapsed = RecurringTaskUtil.getNumElapsedTaskToAppend(getLocalDateByString("2017-01-01"), getLocalDateByString("2016-10-11"), getLocalDateByString("2016-10-12"), RecurringType.YEARLY);
        assertEquals(numElapsed, 1);
    }        
    
    private LocalDate getLocalDateByString(String dateToConsider) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.getDefault());  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(dateToConsider, formatter);
        return date;
    }
}
