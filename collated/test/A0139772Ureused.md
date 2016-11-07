# A0139772Ureused
###### \java\guitests\ClearCommandTest.java
``` java
package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends WhatNowGuiTest {

    @Test
    public void clear() {
        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.h.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.h));
        commandBox.runCommand("delete schedule 1");
        assertListSize(0);
        
        //verify other commands can work after a clear command
        commandBox.runCommand(td.a.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.a));
        commandBox.runCommand("delete todo 1");
        assertListSize(1);
        
        //verify other commands can work after a clear command
        commandBox.runCommand(td.a.getAddCommand());
        commandBox.runCommand(td.b.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.a, td.b));
        commandBox.runCommand("delete todo 1");
        assertListSize(2);
        
        //verify other commands can work after a clear command
        commandBox.runCommand(td.a.getAddCommand());
        commandBox.runCommand(td.b.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.a, td.b));
        commandBox.runCommand("delete schedule 1");
        assertListSize(1);
        
        //verify other commands can work after a clear command
        commandBox.runCommand(td.a.getAddCommand());
        commandBox.runCommand(td.b.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.a, td.b));
        commandBox.runCommand("delete schedule 1");
        commandBox.runCommand("delete schedule 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("WhatNow has been cleared!");
    }
}
```
