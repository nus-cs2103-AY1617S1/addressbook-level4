package seedu.savvytasker.logic.commands;

import seedu.savvytasker.logic.Logic;
import seedu.savvytasker.logic.commands.models.AliasCommandModel;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.DuplicateSymbolKeywordException;

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

    private AliasCommandModel commandModel;
    private Logic logic;
    /**
     * Creates an alias command
     */
    public AliasCommand(AliasCommandModel commandModel) {
        assert commandModel != null;
        this.commandModel = commandModel;
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        AliasSymbol toAdd = new AliasSymbol(commandModel.getKeyword(),
                commandModel.getRepresentingText());
        
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
    
    @Override
    public void setLogic(Logic logic) {
        assert logic != null;
        this.logic = logic;
    }
    
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
        // TODO Auto-generated method stub
        return false;
    }
    /**
     * Undo the alias command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // TODO Auto-generated method stub
        return false;
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
