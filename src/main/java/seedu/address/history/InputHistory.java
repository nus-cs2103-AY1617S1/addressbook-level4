package seedu.address.history;

//@@author A0093960X
public interface InputHistory {
    
    /** Updates the user input history and resets the current state to be the latest input with the given userInput String **/
    public void updateInputHistory(String userInput);
    
    /** Returns whether we are already at the earliest input state (no more previous input in memory to backtrack to) **/
    public boolean isEarliestInput();
    
    /** Returns whether we are already at the latest input state (no more later input in memory to move forward to) **/
    public boolean isLatestInput();

    /** Executes the action to get the next input from the input history **/
    String nextStep();

    /** Executes the action to get the prev input from the input history **/
    String prevStep(String currentInput);
}
