package seedu.whatnow.model.task;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

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
}
