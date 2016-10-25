package seedu.savvytasker.logic.commands;

import seedu.savvytasker.logic.Logic;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;
import seedu.savvytasker.model.alias.SymbolKeywordNotFoundException;

/**
 * Command to create aliases
 */
public class AliasCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for a command or parameter. "
            + "Parameters: k/KEYWORD s/SHORT_KEYWORD\n"
            + "Example: " + COMMAND_WORD
            + " k/Project Meeting s/pjm";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias is already in use";
    public static final String MESSAGE_INVALID_KEYWORD = "Unable to use a command name as a keyword!";

    private Logic logic;
    private final String keyword;
    private final String representingText;
    /**
     * Creates an alias command
     */
    public AliasCommand(String keyword, String representingText) {
        assert keyword != null;
        assert representingText != null;
        this.keyword = keyword;
        this.representingText = representingText;
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        AliasSymbol toAdd = new AliasSymbol(keyword, representingText);
        
        if (logic.canParseHeader(toAdd.getKeyword())) {
            return new CommandResult(MESSAGE_INVALID_KEYWORD);
        }
        
        try {
            model.addAliasSymbol(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateSymbolKeywordException e) {
            return new CommandResult(MESSAGE_DUPLICATE_ALIAS);
        }
    }
    
    /**
     * Provides logic related dependencies to the alias command.
     */
    @Override
    public void setLogic(Logic logic) {
        assert logic != null;
        this.logic = logic;
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
     * Redo the alias command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        execute();
        return true;
    }
    /**
     * Undo the alias command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // TODO Auto-generated method stub
        
        assert model != null;
        
        AliasSymbol toRemove = null;
        for(AliasSymbol symbol : model.getSavvyTasker().getReadOnlyListOfAliasSymbols()) {
            if (symbol.getKeyword().equals(this.keyword)) {
                toRemove = symbol;
                break;
            }
        }
        try {
            model.removeAliasSymbol(toRemove);
        } catch (SymbolKeywordNotFoundException e) {
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
