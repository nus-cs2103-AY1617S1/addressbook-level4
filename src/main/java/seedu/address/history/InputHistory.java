package seedu.address.history;

//@@author A0093960X
/**
 * Interface API for the InputHistory.
 */
public interface InputHistory {

    /**
     * Updates the input history with the given input and resets the input
     * history to the latest input state
     * 
     * @param input The input to update the input history with
     */
    public void updateInputHistory(String input);

    /**
     * Returns whether we are already at the earliest input state (no more
     * previous input in memory to backtrack to)
     * 
     * @return A boolean representing whether we are already at the earliest
     *         input of the input history
     */
    public boolean isEarliestInput();

    /**
     * Returns whether we are already at the latest input state (no more later
     * input in memory to move forward to)
     * 
     * @return A boolean representing whether we are already at the latest input
     *         of the input history
     */
    public boolean isLatestInput();

    /**
     * Executes the action to get the next input from the input history,
     * returning that next input
     * 
     * @return The next input String from the input history
     */
    public String nextStep();

    /**
     * Executes the action to get the previous input from the input history,
     * storing the current input if currently at the latest input state
     * 
     * @param input The input to store if currently at the latest input state
     * @return The previous input String from the input history
     */
    public String prevStep(String input);
}
