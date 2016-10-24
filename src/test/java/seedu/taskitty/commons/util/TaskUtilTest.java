package seedu.taskitty.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskUtilTest {

    @Test
    public void getCategoryIndex(){
        assertEquals(TaskUtil.getCategoryIndex('t') , 0);
        assertEquals(TaskUtil.getCategoryIndex('d') , 1);        
        assertEquals(TaskUtil.getCategoryIndex('e') , 2);
        assertEquals(TaskUtil.getCategoryIndex('a') , 0);
    }

}
