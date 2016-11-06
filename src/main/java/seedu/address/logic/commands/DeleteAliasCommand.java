package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.DisplayAliasListEvent;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.alias.UniqueAliasList.AliasNotFoundException;

//@@author A0143756Y-reused
/**
 * Deletes an alias identified using its displayed index from the last alias listing .
 */
public class DeleteAliasCommand extends Command {

    public static final String COMMAND_WORD = "del-alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the alias identified by the index number displayed in the last alias listing.\n"
            + "Parameters: INDEX (positive integer) [MORE_INDICES] ... \n"
            + "Example: " + COMMAND_WORD + " 1 3";
 
    public static final String MESSAGE_DELETE_ALIAS_SUCCESS = "Deleted aliases: %1$s";

    private final int[] targetIndices;
    private ArrayList<Alias> recentDeletedAliases;

    public DeleteAliasCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }


    @Override
    public CommandResult execute() {
    	EventsCenter.getInstance().post(new DisplayAliasListEvent(model.getFilteredAliasList()));

    	model.saveState();
    	
        UnmodifiableObservableList<ReadOnlyAlias> lastShownList = model.getFilteredAliasList();

        ArrayList<ReadOnlyAlias> aliasesToDelete = new ArrayList<>();
        
        for (int i=0; i<targetIndices.length; i++) {
        	if (lastShownList.size() < targetIndices[i]) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
            }

            aliasesToDelete.add(lastShownList.get(targetIndices[i] - 1));
        }
        
        try {
            model.deleteAliases(aliasesToDelete);
        } catch (AliasNotFoundException pnfe) {
        	model.undoSaveState();
        	// TODO use variable instead
        	return new CommandResult("Task index does not exist in displayed list.");
        }
        
        recentDeletedAliases = new ArrayList<>();
        for (ReadOnlyAlias alias : aliasesToDelete) {
        	recentDeletedAliases.add(new Alias(alias));
        }
        
        return new CommandResult(String.format(MESSAGE_DELETE_ALIAS_SUCCESS, aliasesToDelete));
    }

}
