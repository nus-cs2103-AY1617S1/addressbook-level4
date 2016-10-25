package seedu.address.history;

import java.util.Stack;

/**
 * Stores the history of user inputs for navigating previous and next user inputs using up and down arrow keys.
 */
public class InputHistoryManager implements InputHistory{
    
    private static InputHistoryManager theInputHistoryManager;
    
    // command inputs
    private Stack<String> prevCommands;
    private String currentStoredCommandShown;
    private Stack<String> nextCommands;
    
    // Private constructor for Singleton Pattern
    private InputHistoryManager(){
        prevCommands = new Stack<String>();
        nextCommands = new Stack<String>();
        currentStoredCommandShown = "";
    }
    
    // Use Singleton Pattern here
    public static InputHistoryManager getInstance() {
        if (theInputHistoryManager == null){
            theInputHistoryManager = new InputHistoryManager();
        }
        return theInputHistoryManager;
    }
    
    // Methods dealing with up and down arrow to retrieve prev/next entered commands
    @Override
    public void updateInputHistory(String userInput) {
        assert prevCommands != null && nextCommands != null && currentStoredCommandShown != null;
        
        if (!isLatestInput()) {
            pushPrevInput(currentStoredCommandShown);
        }
        
        while (!isLatestInput()) {
            // last 'next' is the one that i typed halfway, don't push it in my history 
            if (nextCommands.size() == 1){
                nextCommands.pop();
                break;
            }
                
            prevCommands.push(nextCommands.pop());
        }
        pushPrevInput(userInput);
        currentStoredCommandShown = "";
    }
    
    @Override
    public boolean isEarliestInput() {
        assert prevCommands != null;
        return prevCommands.isEmpty();
    }
    
    @Override
    public boolean isLatestInput() {
        assert nextCommands != null;
        return nextCommands.isEmpty();
    }
    
    public String getNextInput() {
        return null;
    }
    
    @Override
    public String popPrevInput() {
        assert prevCommands != null;
        this.currentStoredCommandShown = prevCommands.pop();
        return currentStoredCommandShown;
    }
    
    @Override
    public String pushPrevInput(String input) {
        assert prevCommands != null;
        return prevCommands.push(input);
    }
    
    @Override
    public String popNextInput() {
        assert nextCommands != null;
        this.currentStoredCommandShown = nextCommands.pop();
        return currentStoredCommandShown;
    }
    
    @Override
    public String pushNextInput(String input) {
        assert nextCommands != null;
        return nextCommands.push(input);
    }
    
    @Override
    public String getStoredCurrentShownInput() {
        return currentStoredCommandShown;
    }
    
}
