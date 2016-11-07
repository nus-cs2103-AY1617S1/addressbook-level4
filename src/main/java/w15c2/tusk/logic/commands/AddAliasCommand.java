package w15c2.tusk.logic.commands;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.parser.ParserSelector;
import w15c2.tusk.model.Alias;

//@@author A0143107U
/**
 * Adds an alias to Model.
 */
public class AddAliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";
    public static final String ALTERNATE_COMMAND_WORD = null;
    public static final String COMMAND_FORMAT = COMMAND_WORD + " <SHORTCUT> <SENTENCE>";
    public static final String COMMAND_DESCRIPTION = "Add an Alias"; 
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a one-word alias for any sentence to be used as a command. "
            + "Parameters: SHORTCUT SENTENCE\n"
            + "Example: " + COMMAND_WORD
            + " am add Meeting";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias already exists in TaskManager";

    private final Alias toAdd;

    /**
     * AddAlias has two parameters, shortcut and sentence
     * This AddAliasCommand constructor takes in a shortcut and a sentence and adds an Alias.
     *
     * @param shortcut					Shortcut of the alias to be added.
     * @param sentence					Sentence of the alias to be added.
     * @throws IllegalValueException 	If shortcut or sentence is invalid.
     */
    public AddAliasCommand(String shortcut, String sentence)
            throws IllegalValueException {
    	if (shortcut == null || shortcut.isEmpty()) {
    		throw new IllegalValueException("Shortcut to AddAliasCommand constructor is empty.\n" + MESSAGE_USAGE);
    	}
    	if (sentence == null || sentence.isEmpty()) {
    		throw new IllegalValueException("Sentence to AliasCommand constructor is empty.\n" + MESSAGE_USAGE);
    	}
        boolean isCommandWord =  ParserSelector.getIsCommandWord(shortcut);
        if(isCommandWord){
    		throw new IllegalValueException("Shortcut " + shortcut + " cannot be an alias as it is a command word.\n" + MESSAGE_USAGE);
        }
        this.toAdd = new Alias(shortcut, sentence);
    }
    
    /**
     * Retrieve the details of the alias to add for testing purposes
     */
    public String getAliasDetails() {
    	return toAdd.toString();
    }
    

    /**
     * Adds the prepared alias to the Model.
     * 
     * @return CommandResult Result of the execution of the add alias command.
     */
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addAlias(toAdd);
            closeHelpWindow();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueItemCollection.DuplicateItemException e) {
            return new CommandResult(MESSAGE_DUPLICATE_ALIAS);
        }

    }

}
