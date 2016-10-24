package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Savvy Tasker as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }
    
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Redo the exit command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return true;
    }

    /**
     * Undo the exit command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
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
