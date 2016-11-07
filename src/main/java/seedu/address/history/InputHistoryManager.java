package seedu.address.history;

import java.util.Stack;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import seedu.address.commons.core.LogsCenter;

//@@author A0093960X
/**
 * Stores the history of user inputs for navigating previous and next user
 * inputs.
 */
public class InputHistoryManager implements InputHistory {

    private static final Logger logger = LogsCenter.getLogger(InputHistory.class);

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

        logger.info("Updating the input history.");
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
        logger.info("Executing the prevStep method to get the previous input from input history.");

        String inputToStore = decideInputToStore(currentInput);
        pushToNextInput(inputToStore);
        return popFromPrevInput();
    }

    @Override
    public String nextStep() {
        logger.info("Executing the nextStep method to get the previous input from input history.");

        pushToPrevInput(currentStoredInputShown);
        return popFromNextInput();
    }

    // private helper methods below

    /**
     * Returns the correct input to store depending on whether the current state
     * is at the latest input state.
     * 
     * @param currentInput The current user input String
     * @return The correct input to store into the input history
     */
    private String decideInputToStore(String currentInput) {
        if (isLatestInput()) {
            logger.info("Decide to store the given currentInput String to input history.");
            return currentInput;
        } else {
            logger.info("Decide to store the currentStoredInputShown String to input history.");
            return currentStoredInputShown;
        }
    }

    /**
     * Resets the current stored input shown to an empty string.
     */
    private void resetCurrentStoredInputShown() {
        logger.info("Resetting the current stored input shown.");
        currentStoredInputShown = StringUtils.EMPTY;
    }

    /**
     * Resets the previous and next input history to the latest state,
     * transferring all the valid next input into the previous input history.
     */
    private void resetInputHistoryToLatestState() {
        logger.info("Resetting the input history to the latest state.");

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
