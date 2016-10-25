package seedu.taskitty.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

//@@author A0139052L
public class TaskUtilTest {

    @Test
    public void getCategoryIndex(){
        
        assertEquals(TaskUtil.getCategoryIndex('t') , 0);
        assertEquals(TaskUtil.getCategoryIndex('d') , 1);
        //test uppercase character
        assertEquals(TaskUtil.getCategoryIndex('E') , 2);
        //invalid character should give default todo index
        assertEquals(TaskUtil.getCategoryIndex('a') , 0);
    }

}
