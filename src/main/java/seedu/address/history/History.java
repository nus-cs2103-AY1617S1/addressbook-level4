package seedu.address.history;

import java.util.Stack;

/**
 * Stores the history of undoable and redoable effects for UndoCommand to use.
 *
 */
public class History {

    private Stack<ReversibleEffect> undoableEffects;
    private Stack<ReversibleEffect> redoableEffects;
    
    public History(){
        undoableEffects = new Stack<ReversibleEffect>();
        redoableEffects = new Stack<ReversibleEffect>();
    }
    
    public void update(ReversibleEffect reversibleEffect){
        assert undoableEffects != null;
        undoableEffects.push(reversibleEffect);
    }
    
    public boolean isEarliest(){
        assert undoableEffects != null;
        return undoableEffects.size() == 0;
    }
    
    public boolean isLatest(){
        assert redoableEffects != null;
        return redoableEffects.size() == 0;
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
}
