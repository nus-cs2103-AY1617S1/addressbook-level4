package seedu.address.logic.commands;

import seedu.address.model.ModelManager;
import seedu.address.model.alias.Alias;

//@@author A0143756Y
/**
 * Sets alias for an existing command.
 * Command alias and corresponding command phrase will be saved to XML file, "aliasbook.xml" in ./data folder.
 */
public class AddAliasCommand extends Command {

    public static final String COMMAND_WORD = "add-alias";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
    		+ ": Sets alias for an existing command.\n"
    		+ "Parameters: 'COMMAND_ALIAS' = 'COMMAND_PHRASE'\n"
    		+ "Example: " + COMMAND_WORD + " 'add-dl' = 'add deadline'\n";
    
    public static final String MESSAGE_SUCCESS = "Command alias '%s' set for command phrase '%s'.\n";
    
    private final String commandAlias; 
    
    private final String commandPhrase;
    
    public AddAliasCommand(String commandAlias, String commandPhrase) {
    	this.commandAlias = commandAlias;
    	this.commandPhrase = commandPhrase;
    }

	@Override
    public CommandResult execute() {
        assert model != null;
        
        model.saveState();
        
        try{
        	Alias aliasToAdd = new Alias(commandAlias, commandPhrase);
        	
        	model.addAlias(aliasToAdd);
        	
        	return new CommandResult(String.format(MESSAGE_SUCCESS, commandAlias, commandPhrase));
        } catch {
        	
        }
        	

    }
}
