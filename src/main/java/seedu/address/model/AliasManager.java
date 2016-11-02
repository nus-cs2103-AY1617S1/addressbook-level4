package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.alias.UniqueAliasList;

import java.util.*;
import java.util.stream.Collectors;

//@@author A0143756Y-reused
/**
 * Wraps all data at the alias manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AliasManager implements ReadOnlyAliasManager{

    private final UniqueAliasList aliases;
    
    {
    	aliases = new UniqueAliasList();
    }
    
//	private final HashMap<String, String> aliases;
//
//    {
//        aliases = new HashMap<String, String>();
//    }

    public AliasManager() {}

    /**
     * Aliases are copied into this alias manager.
     */
    public AliasManager(ReadOnlyAliasManager toBeCopied) {
        this(toBeCopied.getUniqueAliasList());
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public AliasManager(UniqueAliasList aliases) {
        resetData(aliases.getInternalList());
    }

    public static ReadOnlyAliasManager getEmptyAliasManager() {
        return new AliasManager();
    }
    
    //list overwrite operations

    public ObservableList<Alias> getInternalList() {
        return aliases.getInternalList();
    }

    public void setAliases(List<Alias> aliases) {
        this.aliases.getInternalList().setAll(aliases);
    }

    public void resetData(Collection<? extends ReadOnlyAlias> newAliases) {
        setAliases(newAliases.stream().map(Alias::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyAliasManager newData) {
        resetData(newData.getAliasList());
    }

//// task-level operations

    /**
     * Adds an alias to the alias manager.
     *
     * @throws UniqueAliasList.DuplicateTaskException if an equivalent alias already exists.
     */
    public void addAlias(Alias aliasToAdd) throws UniqueAliasList.DuplicateAliasException {
    	aliases.add(aliasToAdd);
    	sortAliases();
    }
    
    public boolean removeAlias(ReadOnlyAlias aliasToRemove) throws UniqueAliasList.AliasNotFoundException {
    	if(aliases.remove(aliasToRemove)) {
    		return true;
    	} 
    	
    	else {
    		throw new UniqueAliasList.AliasNotFoundException();
    	}		
    }
    
    public boolean ediAlias(int key, Alias aliasToEdit) throws UniqueAliasList.AliasNotFoundException {
    	if(aliases.set(key, aliasToEdit)){
    		sortAliases();
    		return true;
    	}
    	
    	else{
    		throw new UniqueAliasList.AliasNotFoundException();
    	}
    }
    
    private void sortAliases() {
    	Collections.sort(aliases.getInternalList());
    }
    
//    public void addAlias(String key, String value) {
//        aliases.put(key, value);
//    }
//
//    public String removeAlias(String key) {
//        return aliases.remove(key);
//    }
//    
//    public String editAlias(String key, String value) {
//        //syncTagsWithMasterList(p);
//    	return aliases.replace(key, value);
//    }

//// util methods

    @Override
    public String toString() {
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append(aliases.getInternalList().size() + " aliases.\n");
    	
    	ObservableList<Alias> internalList = aliases.getInternalList();
    	for(Alias currentAlias: internalList){
    		stringBuilder.append(currentAlias.toString());
    	}
    	
    	return stringBuilder.toString();
    }
    
//    @Override
//    public String toString() {
//    	String string = "";
//    	Set<String> keySet = aliases.keySet();
//    	String[] keyArray = keySet.toArray(new String[0]);
//    	for(int i = 0; i < keyArray.length; i++) {
//    		string += ("key: " + keyArray[i] + " value: " + aliases.get(keyArray[i]) + "\n");
//    	}
//    	return string;
//    }

    @Override
    public List<ReadOnlyAlias> getAliasList() {
        return Collections.unmodifiableList(aliases.getInternalList());
    }
    
    @Override
    public UniqueAliasList getUniqueAliasList() {
        return this.aliases;
    }

//    public HashMap<String,String> getAlias() {
//    	return aliases;
//    }
//    
//    public String getValueOf(String key) {
//        return aliases.get(key);
//    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof AliasManager // instanceof handles nulls
                && this.aliases.equals(((AliasManager) other).aliases));
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing your own
        return Objects.hash(aliases);
    }
}
