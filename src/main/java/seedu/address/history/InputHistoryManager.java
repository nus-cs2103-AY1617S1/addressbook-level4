package seedu.address.history;

import java.util.Stack;

//@@author A0093960X
/**
 * Stores the history of user inputs for navigating previous and next user inputs using up and down arrow keys.
 */
public class InputHistoryManager implements InputHistory{
    
    private static InputHistoryManager theInputHistoryManager;
    
    // command inputs
    private Stack<String> prevInputs;
    private Stack<String> nextInputs;
    private String currentStoredInputShown;
    
    // Private constructor for Singleton Pattern
    private InputHistoryManager(){
        prevInputs = new Stack<String>();
        nextInputs = new Stack<String>();
        currentStoredInputShown = "";
    }
    
    // Use Singleton Pattern here
    public static InputHistoryManager getInstance() {
        if (theInputHistoryManager == null){
            theInputHistoryManager = new InputHistoryManager();
        }
        return theInputHistoryManager;
    }
    
    @Override
    public void updateInputHistory(String userInput) {
        assert prevInputs != null && nextInputs != null && currentStoredInputShown != null;
        
        // user tried to access a prev input
        // first store back that prev input that was shown
        if (!isLatestInput()) {
            pushPrevInput(currentStoredInputShown);
        }
        
        // transfer all the next input back to prev input in the right order
        while (!isLatestInput()) {
            
            // last 'next' is the one that i typed halfway, don't push it in my history 
            if (isLastNextInput()) {
                nextInputs.pop();
                break;
            }
                
            transferNextInputToPrevInputHistory();
        }
        
        pushPrevInput(userInput);
        currentStoredInputShown = "";
    }

    /**
     * @return
     */
    private boolean isLastNextInput() {
        return nextInputs.size() == 1;
    }

    /**
     * Transfers a next input to the prev input stack.
     */
    private void transferNextInputToPrevInputHistory() {
        String nextCmdToTransfer = nextInputs.pop();
        prevInputs.push(nextCmdToTransfer);
    }
    
    @Override
    public boolean isEarliestInput() {
        assert prevInputs != null;
        return prevInputs.isEmpty();
    }
    
    @Override
    public boolean isLatestInput() {
        assert nextInputs != null;
        return nextInputs.isEmpty();
    }
    
    @Override
    public String prevStep(String currentInput) {
        String inputToStore;
        
        if (isLatestInput()) {
            inputToStore = currentInput;
        } else {
            inputToStore = currentStoredInputShown;
        }
        
        pushNextInput(inputToStore);
        return popPrevInput();
    }
    
    @Override
    public String nextStep() {
        pushPrevInput(currentStoredInputShown);
        return popNextInput();
    }
    
    // private helper methods below
    
    private String popPrevInput() {
        assert prevInputs != null;
        currentStoredInputShown = prevInputs.pop();
        return currentStoredInputShown;
    }  
    
    private String pushPrevInput(String input) {
        assert prevInputs != null;
        return prevInputs.push(input);
    }
    
    private String popNextInput() {
        assert nextInputs != null;
        currentStoredInputShown = nextInputs.pop();
        return currentStoredInputShown;
    }
    
    private String pushNextInput(String input) {
        assert nextInputs != null;
        return nextInputs.push(input);
    }

}
