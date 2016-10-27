package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.events.ui.JumpToListRequestEvent;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.oneline.testutil.TaskGenerator;

/**
 * Selects a task identified using it's last displayed index from the task book.
 */
public class GenerateCommand extends Command {

    public final int taskCount;

    public static final String COMMAND_WORD = "gen";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates the specified number of tasks\n"
            + "Parameters: [INDEX (must be a positive integer)]\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Tasks created: %1$s";
    
    public GenerateCommand(int taskCount) {
        this.taskCount = taskCount;
    }

    public static GenerateCommand createFromArgs(String args) throws IllegalValueException {
        args = args.trim();
        if (args.length() == 0) {
            return new GenerateCommand(1);
        }
        Integer index = null;
        try {
            index = Parser.getIndexFromArgs(args);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        if (index == null) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        return new GenerateCommand(index);
    }
    
    @Override
    public CommandResult execute() {
        for (int i = 0; i < taskCount; i++) {
            try {
                model.addTask(TaskGenerator.generateTask());
            } catch (DuplicateTaskException e) {
                i--;
            }
        }
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, taskCount));
    }

}
