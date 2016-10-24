package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.logic.commands.models.UnaliasCommandModel;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;
import seedu.savvytasker.model.alias.SymbolKeywordNotFoundException;

/**
 * Command to remove aliases
 */
public class UnaliasCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an alias for a command or parameter. "
            + "Parameters: s/SHORT_KEYWORD\n"
            + "Example: " + COMMAND_WORD
            + " s/pjm";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_UNREGOGNIZED_ALIAS = "This alias is not in use";

    private UnaliasCommandModel commandModel;
    private AliasSymbol toUndo;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UnaliasCommand(UnaliasCommandModel commandModel) {
        assert commandModel != null;
        this.commandModel = commandModel;
        this.toUndo = null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        AliasSymbol toRemove = null;
        for(AliasSymbol symbol : model.getSavvyTasker().getReadOnlyListOfAliasSymbols()) {
            if (symbol.getKeyword().equals(this.commandModel.getKeyword())) {
                toRemove = symbol;
                break;
            }
        }
        
        try {
            if (toRemove == null)
                return new CommandResult(MESSAGE_UNREGOGNIZED_ALIAS);
            toUndo = toRemove;
            model.removeAliasSymbol(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (SymbolKeywordNotFoundException e) {
            return new CommandResult(MESSAGE_UNREGOGNIZED_ALIAS);
        }
    }
    
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the unalias command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        execute();
        return true;
    }
    /**
     * Undo the unalias command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {

        try {
            model.addAliasSymbol(toUndo);
        } catch (DuplicateSymbolKeywordException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    @Override
    public boolean isUndo() {
        return false;
    }
    
    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    @Override
    public boolean isRedo(){
        return false;
    }

}
