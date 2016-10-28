package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.core.EventsCenter;
import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.taskitty.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    
    public static final String[] ALL_COMMAND_WORDS = {
                AddCommand.COMMAND_WORD, ViewCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD, DoneCommand.COMMAND_WORD,
                SaveCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, 
                ClearCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
            };
    
    public static final String[] ALL_COMMAND_MESSAGE_PARAMETER = {
                AddCommand.MESSAGE_PARAMETER, ViewCommand.MESSAGE_PARAMETER,
                FindCommand.MESSAGE_PARAMETER, EditCommand.MESSAGE_PARAMETER,
                DeleteCommand.MESSAGE_PARAMETER, DoneCommand.MESSAGE_PARAMETER,
                SaveCommand.MESSAGE_PARAMETER, UndoCommand.MESSAGE_PARAMETER, 
                ClearCommand.MESSAGE_PARAMETER, RedoCommand.MESSAGE_PARAMETER,  
                HelpCommand.MESSAGE_PARAMETER, ExitCommand.MESSAGE_PARAMETER,
            };
    
    public static final String[] ALL_COMMAND_MESSAGE_USAGE = {
                AddCommand.MESSAGE_USAGE, ViewCommand.MESSAGE_USAGE,
                FindCommand.MESSAGE_USAGE, EditCommand.MESSAGE_USAGE,
                DeleteCommand.MESSAGE_USAGE, DoneCommand.MESSAGE_USAGE,
                SaveCommand.MESSAGE_USAGE, UndoCommand.MESSAGE_USAGE, 
                ClearCommand.MESSAGE_USAGE, RedoCommand.MESSAGE_USAGE, 
                HelpCommand.MESSAGE_USAGE, ExitCommand.MESSAGE_USAGE,
            };
    
    public static final String MESSAGE_FORMAT = "Format: ";
    
    protected Model model;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();
    
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
}
