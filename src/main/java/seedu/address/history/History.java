package seedu.address.history;

import java.util.Stack;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Stores the history of undoable and redoable commands for UndoCommand to use.
 * Also stores the history of user inputs for navigating previous and next user inputs using up and down arrow keys.
 */
public class History implements UndoableCommandHistory, InputHistory{
    
    private static History theHistory;

    // command effects
    private Stack<UndoableCommand> undoableCommands;
    private Stack<UndoableCommand> redoableCommands;
    
    // command inputs
    private Stack<String> prevCommands;
    private String currentStoredCommandShown;
    private Stack<String> nextCommands;
    
    // Private constructor for Singleton Pattern
    private History(){
        undoableCommands = new Stack<UndoableCommand>();
        redoableCommands = new Stack<UndoableCommand>();
        prevCommands = new Stack<String>();
        nextCommands = new Stack<String>();
        currentStoredCommandShown = "";
    }
    
    // Use Singleton Pattern here
    public static History getInstance(){
        if (theHistory == null){
            theHistory = new History();
        }
        return theHistory;
    }
    
    // Methods dealing with undo and redo

    public void updateCommandHistory(UndoableCommand undoableCommand){
        assert undoableCommands != null;
        undoableCommands.push(undoableCommand);
    }
    
    public boolean isEarliestCommand(){
        assert undoableCommands != null;
        return undoableCommands.isEmpty();
    }
    
    public boolean isLatestCommand(){
        assert redoableCommands != null;
        return redoableCommands.isEmpty();
    }
    
    public UndoableCommand undoStep(){
        assert redoableCommands != null && undoableCommands != null;
        return redoableCommands.push(undoableCommands.pop());
    }
    
    public UndoableCommand redoStep(){
        assert redoableCommands != null && undoableCommands != null;
        return undoableCommands.push(redoableCommands.pop());
    }
    
    public void resetRedo(){
        // not sure if using clear() gives worse performance
        redoableCommands = new Stack<UndoableCommand>();
    }
    
    // Methods dealing with up and down arrow to retrieve prev/next entered commands
    public void updateInputHistory(String userInput){
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
    
    public boolean isEarliestInput(){
        assert prevCommands != null;
        return prevCommands.isEmpty();
    }
    
    public boolean isLatestInput(){
        assert nextCommands != null;
        return nextCommands.isEmpty();
    }
    
    public String popPrevInput(){
        assert prevCommands != null;
        this.currentStoredCommandShown = prevCommands.pop();
        return currentStoredCommandShown;
    }
    
    public String pushPrevInput(String input){
        assert prevCommands != null;
        return prevCommands.push(input);
    }
    
    public String popNextInput(){
        assert nextCommands != null;
        this.currentStoredCommandShown = nextCommands.pop();
        return currentStoredCommandShown;
    }
    
    public String pushNextInput(String input){
        assert nextCommands != null;
        return nextCommands.push(input);
    }
    
    public void updateCurrentShownInput(String input){
        assert input != null;
        this.currentStoredCommandShown = input;
    }
    
    public String getStoredCurrentShownInput(){
        return currentStoredCommandShown;
    }
    
}
