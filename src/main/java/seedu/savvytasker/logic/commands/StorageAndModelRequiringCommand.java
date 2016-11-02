package seedu.savvytasker.logic.commands;

import seedu.savvytasker.model.Model;
import seedu.savvytasker.storage.Storage;

/** 
 * Represents a command which requires the Storage class as a dependency.
 * Commands should inherit this class if they only require dependency the model
 * and not the logic. 
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
