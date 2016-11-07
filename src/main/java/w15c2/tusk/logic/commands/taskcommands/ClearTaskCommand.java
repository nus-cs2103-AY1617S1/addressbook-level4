package w15c2.tusk.logic.commands.taskcommands;

import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.HideHelpRequestEvent;
import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.CommandResult;

//@@author A0139817U
/**
 * Deletes all the tasks in the Model that are currently listed.
 */
public class ClearTaskCommand extends Command {

        public static final String COMMAND_WORD = "clear";
        public static final String ALTERNATE_COMMAND_WORD = null;
        public static final String COMMAND_FORMAT = COMMAND_WORD;
        public static final String COMMAND_DESCRIPTION = "Clear Current List"; 

        public static final String HELP_MESSAGE_USAGE = "Clear Tasks: \t clear";
        
        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes all the tasks that are currently listed\n"
        		 + "Example: " + COMMAND_WORD;
        
        public static final String MESSAGE_CLEAR_TASKS_SUCCESS = "%d tasks deleted!";

        public ClearTaskCommand() {}

        /**
         * Clears the tasks in the model that are listed.
         * 
         * @return CommandResult Result of the execution of the clear command.
         */
        @Override
        public CommandResult execute() {
            int deleted = model.clearTasks();
            closeHelpWindow();
            return new CommandResult(String.format("%d tasks deleted!", deleted));
        }
}
