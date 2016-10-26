//@@author A0139916U
package seedu.savvytasker.model.alias;

import java.util.Iterator;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AliasSymbolList implements Iterable<AliasSymbol> {
    private final ObservableList<AliasSymbol> internalList = FXCollections.observableArrayList();
    
    /** Default constructor */
    public AliasSymbolList() {}
    
    /**
     * Copy constructor
     * @param src the list to copy from, cannot be null
     */
    public AliasSymbolList(AliasSymbolList src) {
        assert src != null;
        internalList.addAll(src.internalList);
    }
    
    @Override
    public Iterator<AliasSymbol> iterator() {
        return internalList.iterator();
    }
    
    /**
     * Returns true if a symbol with the specified symbolKeyword exists in the list
     * @param symbolKeyword the symbolKeyword to check for
     * @return true if exists, false otherwise
     */
    public boolean contains(String symbolKeyword) {
        for (AliasSymbol symbol : internalList) {
            if (symbol.getKeyword().equals(symbolKeyword))
                return true;
        }
        
        return false;
    }
    
    /**
     * Returns true if the symbol exists in the list. Both keyword and representation must match.
     * @param symbol the symol to check for
     * @return true if exists, false otherwise
     */
    public boolean contains(AliasSymbol symbol) {
        return internalList.contains(symbol);
    }
    
    /**
     * Adds a symbol to the list. The symbol being added must not have a keyword that clashes with another
     * symbol in the list.
     * @param symbol the symbol to add, cannot be null
     * @throws DuplicateSymbolKeywordException if a symbol with the same keyword already exists
     */
    public void addAliasSymbol(AliasSymbol symbol) throws DuplicateSymbolKeywordException {
        assert symbol != null;
        if (contains(symbol.getKeyword()))
            throw new DuplicateSymbolKeywordException();
        internalList.add(symbol);
    }
    
    /**
     * Removes a symbol from the list.
     * @param symbol the symbol to remove, cannot be null
     * @throws SymbolKeywordNotFoundException if no such symbol can be found.
     */
    public void removeAliasSymbol(AliasSymbol symbol) throws SymbolKeywordNotFoundException {
        assert symbol != null;
        if (!contains(symbol))
            throw new SymbolKeywordNotFoundException();
        internalList.remove(symbol);        
    }
    
    /**
     * Replace an old symbol with a new symbol. The new symbol's keyword must equal to the old
     * symbol's keyword.
     * 
     * @param oldSymbol the old symbol to be replaced, cannot be null
     * @param newSymbol the new symbol to used to replace, cannot be null
     * @throws SymbolKeywordNotFoundException if an old symbol with the keyword is not found.
     */
    public void replaceAliasSymbol(AliasSymbol oldSymbol, AliasSymbol newSymbol) throws SymbolKeywordNotFoundException {
        assert oldSymbol != null && newSymbol != null;
        assert oldSymbol.getKeyword().equals(newSymbol.getKeyword());
        
        removeAliasSymbol(oldSymbol);
        internalList.add(newSymbol);
    }
    
    /**
     * Clears this list and copy all elements from the other list to this.
     * @param other the other list
     */
    public void reset(AliasSymbolList other) {
        assert other != null;
        internalList.addAll(other.internalList); // AliasSymbol is immutable, no need for deep copy.
    }
    
    /**
     * Gets the size of this list.
     * @return the size of this list
     */
    public int size() {
        return internalList.size();
    }
    
    public List<AliasSymbol> asReadonly() {
        return Collections.unmodifiableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasSymbolList // instanceof handles nulls
                && this.internalList.equals( ((AliasSymbolList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
