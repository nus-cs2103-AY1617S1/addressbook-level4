package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayAliasListEvent;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.UniqueAliasList.DuplicateAliasException;

//@@author A0143756Y
/**
 * Sets alias for an existing command.
 * Command alias and corresponding command phrase will be saved to XML file, "aliasbook.xml" in ./data folder.
 */
public class AddAliasCommand extends Command {

    public static final String COMMAND_WORD = "add-alias";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
    		+ ": Sets alias for a phrase.\n"
    		+ "Parameters: 'ALIAS' = 'ORIGINAL_PHRASE'\n"
    		+ "Example: " + COMMAND_WORD + " 'add-dl' = 'add deadline'\n";
    
    public static final String MESSAGE_SUCCESS = "Alias '%s' set for phrase '%s'.\n";
    
    public static final String MESSAGE_DUPLICATE_ALIAS = "Alias already exists in the alias manager.\n";
    
    public static final String MESSAGE_INVALID_ALIAS = "Alias '%s' is invalid. Alias cannot be a sub-string or "
    		+ "a super-string of any previously set alias. Please re-enter command with a valid alias.\n";
    
    private final String alias; 
    
    private final String originalPhrase;
    
    public AddAliasCommand(String alias, String originalPhrase) {
    	this.alias = alias;
    	this.originalPhrase = originalPhrase;
    }

	@Override
    public CommandResult execute() {
    	EventsCenter.getInstance().post(new DisplayAliasListEvent(model.getFilteredAliasList()));

        assert model != null;
        model.saveState();
        
        try{
        	
        	if(!model.validateAliasforAddAliasCommand(alias)){
        		
        		model.undoSaveState();
        		return new CommandResult(String.format(MESSAGE_INVALID_ALIAS, alias));	
        		
        	}
        	
        	Alias aliasToAdd = new Alias(alias, originalPhrase);
        	model.addAlias(aliasToAdd); //Throws DuplicateAliasException
        	return new CommandResult(String.format(MESSAGE_SUCCESS, alias, originalPhrase));
        	
        } catch (DuplicateAliasException ex){
        	
        	model.loadPreviousState();
            return new CommandResult(String.format(MESSAGE_DUPLICATE_ALIAS));
            
        }
    }
}
