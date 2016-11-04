package seedu.address.history;

//@@author A0093960X
/**
 * API of the InputHistory component
 */
public interface InputHistory {

    /**
     * Updates the input history with the given userInput String and resets the
     * input history to the latest input state
     * 
     * @param input the input to update the input history with
     */
    public void updateInputHistory(String input);

    /**
     * Returns whether we are already at the earliest input state (no more
     * previous input in memory to backtrack to)
     * 
     * @return boolean representing whether we are already at the earliest input
     *         of the input history
     */
    public boolean isEarliestInput();

    /**
     * Returns whether we are already at the latest input state (no more later
     * input in memory to move forward to)
     * 
     * @return boolean representing whether we are already at the latest input
     *         of the input history
     */
    public boolean isLatestInput();

    /**
     * Executes the action to get the next input from the input history
     * 
     * @return the next input String from the input history
     */
    public String nextStep();

    /**
     * Executes the action to get the previous input from the input history,
     * storing the current input if currently at the latest input state
     * 
     * @param currentInput the current input
     * @return the previous input String from the input history
     */
    public String prevStep(String currentInput);
}
