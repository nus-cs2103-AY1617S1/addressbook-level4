package seedu.stask.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.stask.commons.util.CommandUtil;

//@@author A0139024M
public class CommandUtilTest {
    
    @Test
    public void isInValidIndex(){
        assertFalse(CommandUtil.isValidIndex("C1", 1, 1));   
    }
    
    @Test
    public void getTaskType(){
        assertNull(CommandUtil.getTaskType("C"));
    }

}
