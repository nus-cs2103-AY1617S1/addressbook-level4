package guitests;

import org.junit.Test;

//@@author A0093960X
public class InputHistoryTest extends DearJimGuiTest {

    /*
    @Test
    public void getPrevInput_noPrevInput_noChange() {
        assertGetPrevInputSuccess("");
    }
  

    @Test
    public void getNextInput_noNextInput_noChange() {
        assertGetNextInputSuccess("");
    }
    */

    @Test
    public void getPrevAndNextInput_singlePrevInput_ableToNavigatePrevAndNext() {
        commandBox.runCommand("list done");
        assertCommandInput("");
        assertGetPrevInputSuccess("list done");
        assertGetNextInputSuccess("");
    }

    @Test
    public void getPrevInput_navigatePrevInputWhileTypingHalfwayOnLatestInput_incompleteLatestInputSaved() {
        commandBox.runCommand("find lol");
        commandBox.enterCommand("I hope this command is saved when I press up!!");
        assertGetPrevInputSuccess("find lol");
        assertGetNextInputSuccess("I hope this command is saved when I press up!!");
        assertGetNextInputSuccess("I hope this command is saved when I press up!!");
    }

    @Test
    public void getPrevInput_typingOverAPrevInput_savedPrevInputIsNotOverwritten() {
        commandBox.runCommand("list done");
        commandBox.runCommand("find lol");
        assertGetPrevInputSuccess("find lol");
        assertGetPrevInputSuccess("list done");
        commandBox.runCommand("This input will not overwrite the previous one that was here");
        assertCommandInput("");
        assertGetPrevInputSuccess("This input will not overwrite the previous one that was here");
        assertGetPrevInputSuccess("find lol");
        assertGetPrevInputSuccess("list done");
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
