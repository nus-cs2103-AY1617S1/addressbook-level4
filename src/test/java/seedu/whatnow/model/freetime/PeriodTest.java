//@@author A0139772U
package seedu.whatnow.model.freetime;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class PeriodTest {
    @Test
    public void compareTo_periodEqual_periodAreEqual() {
        Period period1 = new Period("12:00am", "02:00am");
        Period period2 = new Period("12:00am", "02:00am");
        ArrayList<Period> periods = new ArrayList<Period>();
        periods.add(period1);
        periods.add(period2);
        periods.sort(new Period());
        assertEquals(periods.get(0), period1);
        assertEquals(periods.get(1), period2);
    }
    
    @Test
    public void compareTo_firstSmallerThanSecond_periodAreEqual() {
        Period period1 = new Period("12:00am", "02:00am");
        Period period2 = new Period("02:00am", "04:00am");
        ArrayList<Period> periods = new ArrayList<Period>();
        periods.add(period1);
        periods.add(period2);
        periods.sort(new Period());
        assertEquals(periods.get(0), period1);
        assertEquals(periods.get(1), period2);
    }
    
    @Test
    public void compareTo_firstBiggerThanSecond_periodAreEqual() {
        Period period1 = new Period("02:00am", "04:00am");
        Period period2 = new Period("12:00am", "02:00am");
        ArrayList<Period> periods = new ArrayList<Period>();
        periods.add(period1);
        periods.add(period2);
        periods.sort(new Period());
        assertEquals(periods.get(1), period1);
        assertEquals(periods.get(0), period2);
    }
    
    @Test
    public void compareTo_invalidFormat_periodAreEqual() {
        Period period1 = new Period("02-00am", "04-00am");
        Period period2 = new Period("12-00am", "02-00am");
        ArrayList<Period> periods = new ArrayList<Period>();
        periods.add(period1);
        periods.add(period2);
        periods.sort(new Period());
        assertEquals(periods.get(0), period1);
        assertEquals(periods.get(1), period2);
    }
    
    @Test
    public void setter_ObjectInstantiated_validSet() {
        Period period1 = new Period("02-00am", "04-00am");
        period1.setStart("02:00am");
        period1.setEnd("04:00am");
        assertEquals(period1.toString(), "[02:00am, 04:00am]");
    }
}
