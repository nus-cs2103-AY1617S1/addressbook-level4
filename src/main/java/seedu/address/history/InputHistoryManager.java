package seedu.address.history;

import java.util.Stack;

import org.apache.commons.lang.StringUtils;

//@@author A0093960X
/**
 * Stores the history of user inputs for navigating previous and next user
 * inputs.
 */
public class InputHistoryManager implements InputHistory {

    private static InputHistoryManager theInputHistoryManager;

    // command inputs
    private Stack<String> prevInputs;
    private Stack<String> nextInputs;
    private String currentStoredInputShown;

    // Private constructor for Singleton Pattern
    private InputHistoryManager() {
        prevInputs = new Stack<String>();
        nextInputs = new Stack<String>();
        resetCurrentStoredInputShown();
    }

    // Use Singleton Pattern here
    public static InputHistoryManager getInstance() {
        if (theInputHistoryManager == null) {
            theInputHistoryManager = new InputHistoryManager();
        }
        return theInputHistoryManager;
    }

    @Override
    public void updateInputHistory(String userInput) {
        assert prevInputs != null && nextInputs != null && currentStoredInputShown != null;

        if (!isLatestInput()) {
            pushToPrevInput(currentStoredInputShown);
        }

        resetInputHistoryToLatestState();
        pushToPrevInput(userInput);
        resetCurrentStoredInputShown();
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

        pushToNextInput(inputToStore);
        return popFromPrevInput();
    }

    @Override
    public String nextStep() {
        pushToPrevInput(currentStoredInputShown);
        return popFromNextInput();
    }

    // private helper methods below

    /**
     * Resets the current stored input shown to an empty string.
     */
    private void resetCurrentStoredInputShown() {
        currentStoredInputShown = StringUtils.EMPTY;
    }

    /**
     * Resets the previous and next input history to the latest state,
     * transferring all the valid next input into the previous input history.
     */
    private void resetInputHistoryToLatestState() {

        boolean isEarliestNextInputValid = isLatestInput();
        String nextInputToTransfer;

        while (!isLatestInput()) {
            nextInputToTransfer = popFromNextInput();
            pushToPrevInput(nextInputToTransfer);
        }

        if (!isEarliestNextInputValid) {
            popFromPrevInput();
        }
    }

    /**
     * Pops and returns the last stored previous input from the previous input
     * history, updating the current stored input shown to that input. <br>
     * Asserts that the previous input history is non-empty.
     * 
     * @return The last stored previous input String
     */
    private String popFromPrevInput() {
        assert prevInputs != null && prevInputs.size() > 0;
        currentStoredInputShown = prevInputs.pop();
        return currentStoredInputShown;
    }

    /**
     * Pops and returns the last stored next input from the next input history,
     * updating the current stored input shown to that input. <br>
     * Asserts that the next input history is non-empty.
     * 
     * @return The last stored next input String
     */
    private String popFromNextInput() {
        assert nextInputs != null && nextInputs.size() > 0;
        currentStoredInputShown = nextInputs.pop();
        return currentStoredInputShown;
    }

    /**
     * Pushes the given input into the previous input history.
     */
    private void pushToPrevInput(String input) {
        assert prevInputs != null;
        prevInputs.push(input);
    }

    /**
     * Pushes the given input into the next input history.
     */
    private void pushToNextInput(String input) {
        assert nextInputs != null;
        nextInputs.push(input);
    }

}
