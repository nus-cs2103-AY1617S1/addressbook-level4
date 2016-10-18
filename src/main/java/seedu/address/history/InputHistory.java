package seedu.address.history;

public interface InputHistory {
    
    /** Updates the user input history and resets the current state to be the latest input with the given userInput String **/
    public void updateInputHistory(String userInput);
    
    /** Returns whether we are already at the earliest input state (no more previous input in memory to backtrack to) **/
    public boolean isEarliestInput();
    
    /** Returns whether we are already at the latest input state (no more later input in memory to move forward to) **/
    public boolean isLatestInput();
    
    /** Returns the immediate previous input stored in memory and no longer keep in memory **/
    public String popPrevInput();
    
    /** Stores the given input string in memory as the immediate 'previous' input from the current state  **/
    public String pushPrevInput(String input);
    
    /** 
     * Returns the immediate next input (that was previously stored when we try to get previous) stored in memory 
     *  and no longer keep it in memory 
     */
    public String popNextInput();
    
    /** Stores the given input string in memory as the immediate 'next' input from the current state **/
    public String pushNextInput(String input);
    
    /** Returns the current shown input string that is to be displayed to the user **/
    public String getStoredCurrentShownInput();
}
