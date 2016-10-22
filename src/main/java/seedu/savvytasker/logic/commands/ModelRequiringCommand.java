package seedu.savvytasker.logic.commands;

import seedu.savvytasker.model.Model;

/** 
 * Represents a command which requires the Model class as a dependency.
 * Commands should inherit this class if they only require dependency the model
 * and not the logic. 
*/
public abstract class ModelRequiringCommand extends Command {
    protected Model model;
    
    public void setModel(Model model) { 
        this.model = model; 
    }
}
