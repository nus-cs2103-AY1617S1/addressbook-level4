package seedu.address.model.util;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.util.RecurringTaskUtil;
import seedu.address.model.task.RecurringType;

//@@author A0135782Y
public class RecurringTaskUtilTest {
    private static final String CURRENT_DATE = "2016-10-13";
    RecurringTaskUtilHelper helper;
    
    @Before
    public void setup() {
        helper = new RecurringTaskUtilHelper();
    }
    
    @Test
    public void getNumElapsedTaskToAppend() {
        getNumElapsedTaskToAppend_daily();
        getNumElapsedTaskToAppend_weekly();
        getNumElapsedTaskToAppend_monthly();
        getNumElapsedTaskToAppend_yearly();
    }
    
    public void getNumElapsedTaskToAppend_daily() {
        int numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), null, helper.getLocalDateByString("2016-10-11"), RecurringType.DAILY);
        assertEquals(numElapsed, 2);
        numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), helper.getLocalDateByString("2016-10-11"), helper.getLocalDateByString("2016-10-12"), RecurringType.DAILY);
        assertEquals(numElapsed, 2);        
    } 

    public void getNumElapsedTaskToAppend_weekly() {
        int numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), null, helper.getLocalDateByString("2016-10-11"), RecurringType.WEEKLY);
        assertEquals(numElapsed, 1);
        numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), helper.getLocalDateByString("2016-10-11"), helper.getLocalDateByString("2016-10-12"), RecurringType.WEEKLY);
        assertEquals(numElapsed, 1);    
    }     

    public void getNumElapsedTaskToAppend_monthly() {
        int numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), null, helper.getLocalDateByString("2016-09-11"), RecurringType.MONTHLY);
        assertEquals(numElapsed, 1);
        numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), helper.getLocalDateByString("2016-09-11"), helper.getLocalDateByString("2016-09-12"), RecurringType.MONTHLY);
        assertEquals(numElapsed, 1);
    }

    public void getNumElapsedTaskToAppend_yearly() {
        int numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), null, helper.getLocalDateByString("2015-10-11"), RecurringType.YEARLY);
        assertEquals(numElapsed, 1);
        numElapsed = RecurringTaskUtil.getElapsedPeriodToAppend(
                helper.getLocalDateByString(CURRENT_DATE), helper.getLocalDateByString("2015-10-11"), helper.getLocalDateByString("2015-10-12"), RecurringType.YEARLY);
        assertEquals(numElapsed, 1);
    }

    class RecurringTaskUtilHelper {
        public LocalDate getLocalDateByString(String dateToConsider) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale(Locale.getDefault());  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
            LocalDate date = LocalDate.parse(dateToConsider, formatter);
            return date;
        }
    }
}
