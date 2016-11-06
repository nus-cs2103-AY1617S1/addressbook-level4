//@@author A0141021H
package seedu.whatnow.model.task;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;

import seedu.whatnow.commons.exceptions.IllegalValueException;

import static org.junit.Assert.assertFalse;

public class DateTest {

    @Test
    public void isValidDate_month_returnTrue(){
        assertTrue(TaskDate.isValidMonth("jAn"));
        assertTrue(TaskDate.isValidMonth("january"));
        assertTrue(TaskDate.isValidMonth("fEb"));
        assertTrue(TaskDate.isValidMonth("febrUARY"));
        assertTrue(TaskDate.isValidMonth("mAr"));
        assertTrue(TaskDate.isValidMonth("MaRcH"));
        assertTrue(TaskDate.isValidMonth("aPr"));
        assertTrue(TaskDate.isValidMonth("april"));
        assertTrue(TaskDate.isValidMonth("may"));
        assertTrue(TaskDate.isValidMonth("MAY"));
        assertTrue(TaskDate.isValidMonth("juN"));
        assertTrue(TaskDate.isValidMonth("jUNe"));
        assertTrue(TaskDate.isValidMonth("Jul"));
        assertTrue(TaskDate.isValidMonth("juLY"));
        assertTrue(TaskDate.isValidMonth("AuG"));
        assertTrue(TaskDate.isValidMonth("aUgUst"));
        assertTrue(TaskDate.isValidMonth("seP"));
        assertTrue(TaskDate.isValidMonth("SepTEMber"));
        assertTrue(TaskDate.isValidMonth("oCt"));
        assertTrue(TaskDate.isValidMonth("OCToBER"));
        assertTrue(TaskDate.isValidMonth("NOV"));
        assertTrue(TaskDate.isValidMonth("noVEMber"));
        assertTrue(TaskDate.isValidMonth("dEc"));
        assertTrue(TaskDate.isValidMonth("deCEMbeR"));
    }

    @Test
    public void isValidDate_month_returnFalse(){
        assertFalse(TaskDate.isValidMonth("month"));
        assertFalse(TaskDate.isValidMonth("junuary"));
        assertFalse(TaskDate.isValidMonth("freb"));
        assertFalse(TaskDate.isValidMonth("muy"));
        assertFalse(TaskDate.isValidMonth("apirl"));
        assertFalse(TaskDate.isValidMonth("junne"));
        assertFalse(TaskDate.isValidMonth("jully"));
        assertFalse(TaskDate.isValidMonth("augustus"));
        assertFalse(TaskDate.isValidMonth("septimber"));
        assertFalse(TaskDate.isValidMonth("octi"));
        assertFalse(TaskDate.isValidMonth("novmember"));
        assertFalse(TaskDate.isValidMonth("decamber"));
    }

    @Test
    public void isValidDate_today_returnTrue() throws ParseException, IllegalValueException{
        assertTrue(TaskDate.getIsValidDate("today"));
        assertTrue(TaskDate.getIsValidDate("tdy"));
        assertTrue(TaskDate.getIsValidDate("TDY"));
    }

    @Test
    public void isValidDate_today_returnFalse() throws ParseException, IllegalValueException{
        assertFalse(TaskDate.getIsValidDate("2day"));
    }

    @Test
    public void isValidDate_tmr_returnTrue() throws ParseException, IllegalValueException{
        assertTrue(TaskDate.getIsValidDate("tomorrow"));
        assertTrue(TaskDate.getIsValidDate("tmr"));
        assertTrue(TaskDate.getIsValidDate("TMR"));
    }

    @Test
    public void isValidDate_tmr_returnFalse() throws ParseException, IllegalValueException{
        assertFalse(TaskDate.getIsValidDate("tmmr"));
    }

    @Test
    public void isValidDate_variousDateFormat_returnTrue() throws ParseException, IllegalValueException{
        assertTrue(TaskDate.getIsValidDate("03/12/2019"));
        assertTrue(TaskDate.getIsValidDate("5/11/2017"));
        assertTrue(TaskDate.getIsValidDate("7/8/2018"));
        assertTrue(TaskDate.getIsValidDate("8/5/2017"));
        assertTrue(TaskDate.getIsValidDate("30/12/2018"));
        assertTrue(TaskDate.getIsValidDate(TaskDate.formatDatetoStandardDate("01.12.2020")));
        assertTrue(TaskDate.getIsValidDate(TaskDate.formatDatetoStandardDate("13/06/2019")));
        assertTrue(TaskDate.getIsValidDate(TaskDate.formatDatetoStandardDate("7-10-2023")));
        assertTrue(TaskDate.getIsValidDate(TaskDate.formatDatetoStandardDate("19 12 2019")));
        assertTrue(TaskDate.getIsValidDate(TaskDate.formatDatetoStandardDate("05072018")));
    }
    
    @Test
    public void isValidDate_wrongDate_returnFalse() throws ParseException, IllegalValueException { 
        assertFalse(TaskDate.getIsValidDate("34/12/2018"));
        assertFalse(TaskDate.getIsValidDate("11/18/2016"));
        assertFalse(TaskDate.getIsValidDate("44/20/2018"));
    }
    
    @Test
    public void isValidDate_PastDateFormat_returnTrue() throws ParseException, IllegalValueException{
        boolean checkPastDate = false;
        try {
            TaskDate.getIsValidDate("14/10/2015");
        }
        catch(IllegalValueException e){
            checkPastDate = true;
        }
        assertTrue(checkPastDate);
    }

    @Test
    public void isValidDate_checkDateValidity_returnFalse() throws ParseException, IllegalValueException{
        assertFalse(TaskDate.getIsValidDate("32/09/2018"));
        assertFalse(TaskDate.getIsValidDate("31/02/2020"));
        assertFalse(TaskDate.getIsValidDate("10/16/2020"));
    }
    @Test
    public void isValidDate_dateRange_returnTrue() throws ParseException {
        assertTrue(TaskDate.getIsValidDateRange(null, null));
        assertTrue(TaskDate.getIsValidDateRange("12/12/2017", "23/11/2018"));
        assertTrue(TaskDate.getIsValidDateRange("12/12/2016", "12/12/2016"));
    }
    
    @Test
    public void isValidDate_invalidDateRange_returnFalse() throws ParseException {
        assertFalse(TaskDate.getIsValidDateRange("12/12/2016", "23/11/2015"));
        assertFalse(TaskDate.getIsValidDateRange("12/11/2019", "23/09/2017"));
    }
}
