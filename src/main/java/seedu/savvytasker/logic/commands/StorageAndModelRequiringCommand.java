package seedu.savvytasker.logic.commands;

import seedu.savvytasker.model.Model;
import seedu.savvytasker.storage.Storage;

//@@author A0138431L
/** 
 * Represents a command which requires the Storage class as a dependency.
 * Commands should inherit this class if they only require dependency for
 * storage and model components
*/
public abstract class StorageAndModelRequiringCommand extends Command {
    protected Storage storage;
    protected Model model;
    
    public void setStorage(Storage storage) { 
        this.storage = storage; 
    }
    
    public void setModel(Model model) {
        this.model = model;
    }
}
