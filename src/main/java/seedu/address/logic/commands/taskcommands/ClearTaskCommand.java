package seedu.address.logic.commands.taskcommands;

import seedu.address.logic.commands.CommandResult;

/**
 * Deletes all the tasks that are currently listed
 */
public class ClearTaskCommand extends TaskCommand {

        public static final String COMMAND_WORD = "clear";

        public static final String HELP_MESSAGE_USAGE = "Clear Tasks: \t clear";
        
        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes all the tasks that are currently listed";
        
        public static final String MESSAGE_CLEAR_TASKS_SUCCESS = "%d tasks deleted!";

        public ClearTaskCommand() {}

        @Override
        public CommandResult execute() {
            int deleted = model.clearTasks();
            return new CommandResult(String.format("%d tasks deleted!", deleted));
        }
}
