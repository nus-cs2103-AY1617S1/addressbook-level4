package seedu.address.history;

import java.util.Stack;

/**
 * Stores the history of undoable and redoable effects for UndoCommand to use.
 *
 */
public class History {

    // command effects
    private Stack<ReversibleEffect> undoableEffects;
    private Stack<ReversibleEffect> redoableEffects;
    
    // command inputs
    private Stack<String> prevCommands;
    private String currentStoredCommandShown;
    private Stack<String> nextCommands;
    
    public History(){
        undoableEffects = new Stack<ReversibleEffect>();
        redoableEffects = new Stack<ReversibleEffect>();
        prevCommands = new Stack<String>();
        nextCommands = new Stack<String>();
        currentStoredCommandShown = "";
    }
    
    // Methods dealing with undo and redo
    
    public void update(ReversibleEffect reversibleEffect){
        assert undoableEffects != null;
        undoableEffects.push(reversibleEffect);
    }
    
    public boolean isEarliest(){
        assert undoableEffects != null;
        return undoableEffects.isEmpty();
    }
    
    public boolean isLatest(){
        assert redoableEffects != null;
        return redoableEffects.isEmpty();
    }
    
    public ReversibleEffect undoStep(){
        assert redoableEffects != null && undoableEffects != null;
        return redoableEffects.push(undoableEffects.pop());
    }
    
    public ReversibleEffect redoStep(){
        assert redoableEffects != null && undoableEffects != null;
        return undoableEffects.push(redoableEffects.pop());
    }
    
    public void resetRedo(){
        redoableEffects = new Stack<ReversibleEffect>();
    }
    
    // Methods dealing with up and down arrow to retrieve prev/next entered commands
    public void updateInputHistory(String userInput){
        assert prevCommands != null && nextCommands != null && currentStoredCommandShown != null;
        
        if (!isLatestCommand()) {
            pushPrevCommandInput(currentStoredCommandShown);
        }
        
        while (!isLatestCommand()) {
            // last 'next' is the one that i typed halfway, don't push it in my history 
            if (nextCommands.size() == 1){
                nextCommands.pop();
                break;
            }
                
            prevCommands.push(nextCommands.pop());
        }
        pushPrevCommandInput(userInput);
        currentStoredCommandShown = "";
    }
    
    public boolean isEarliestCommand(){
        assert prevCommands != null;
        return prevCommands.isEmpty();
    }
    
    public boolean isLatestCommand(){
        assert nextCommands != null;
        return nextCommands.isEmpty();
    }
    
    public String popPrevCommandInput(){
        assert prevCommands != null;
        this.currentStoredCommandShown = prevCommands.pop();
        return currentStoredCommandShown;
    }
    
    public String pushPrevCommandInput(String input){
        assert prevCommands != null;
        return prevCommands.push(input);
    }
    
    public String popNextCommandInput(){
        assert nextCommands != null;
        this.currentStoredCommandShown = nextCommands.pop();
        return currentStoredCommandShown;
    }
    
    public String pushNextCommandInput(String input){
        assert nextCommands != null;
        return nextCommands.push(input);
    }
    
    public void updateCurrentShownCommand(String input){
        assert input != null;
        this.currentStoredCommandShown = input;
    }
    
    public String getStoredCurrentShownCommand(){
        return currentStoredCommandShown;
    }
    
}
