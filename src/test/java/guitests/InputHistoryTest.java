package guitests;

import org.junit.Test;

public class InputHistoryTest extends AddressBookGuiTest {
    
    @Test
    public void prevAndNext() {
        
        // TEST 1: No previous or next input
        // Up and down should give blank
        assertGetNextInputSuccess("");
        assertGetPrevInputSuccess("");
        assertGetNextInputSuccess("");
        
        // TEST 2: Enter a single "list" command
        commandBox.runCommand("list");
        assertCommandInput("");
        assertGetNextInputSuccess("");
        assertGetPrevInputSuccess("list");
        assertGetPrevInputSuccess("list");
        assertGetNextInputSuccess("");
        
        // TEST 3: Enter a single "find" command
        commandBox.runCommand("find lol");
        assertCommandInput("");
        assertGetNextInputSuccess("");
        assertGetPrevInputSuccess("find lol");
        assertGetPrevInputSuccess("list");
        assertGetPrevInputSuccess("list");
        assertGetNextInputSuccess("find lol");
        
        // TEST 4: Enter a single "add" command while in the middle of the prev and next inputs
        commandBox.runCommand("add test add -low");
        assertCommandInput("");
        assertGetNextInputSuccess("");
        assertGetPrevInputSuccess("add test add -low");
        assertGetPrevInputSuccess("find lol");
        assertGetPrevInputSuccess("list");
        assertGetPrevInputSuccess("list");
        assertGetNextInputSuccess("find lol");
        assertGetNextInputSuccess("add test add -low");
        assertGetNextInputSuccess("");        
        
    }
    
    private void assertGetPrevInputSuccess(String expected) {
        commandBox.getPreviousInput();
        assertCommandInput(expected);
    }
    
    private void assertGetNextInputSuccess(String expected) {
        commandBox.getNextInput();
        assertCommandInput(expected);
    }

}
