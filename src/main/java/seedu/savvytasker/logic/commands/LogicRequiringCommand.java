package seedu.savvytasker.logic.commands;

import seedu.savvytasker.logic.Logic;

/** 
 * Represents a command which requires the Logic class as a dependency.
 * Commands should inherit this class if they only require dependency the logic
 * and not the model. 
*/
public abstract class LogicRequiringCommand extends Command {
    protected Logic logic;
    
    public void setLogic(Logic logic) { 
        this.logic = logic;
    }
}
