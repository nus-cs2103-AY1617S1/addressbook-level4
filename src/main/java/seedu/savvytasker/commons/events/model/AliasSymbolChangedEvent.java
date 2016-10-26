//@@author A0139916U
package seedu.savvytasker.commons.events.model;

import seedu.savvytasker.commons.events.BaseEvent;
import seedu.savvytasker.model.alias.AliasSymbol;

/**
 * Represents an event where the user has added an alias symbol.
 */
public class AliasSymbolChangedEvent extends BaseEvent {
    /**
     * The action that was performed, i.e. the symbol was added or removed.
     */
    public enum Action {
        Added,
        Removed;
    }
    
    public final AliasSymbol symbol;
    public final Action action;
    
    public AliasSymbolChangedEvent(AliasSymbol symbol, Action action) {
        assert symbol != null;
        assert action != null;
        
        this.symbol = symbol;
        this.action = action;
    }
    
    @Override
    public String toString() {
        return "Alias symbol " + action.toString() + ": " + symbol.toString();
    }

}
