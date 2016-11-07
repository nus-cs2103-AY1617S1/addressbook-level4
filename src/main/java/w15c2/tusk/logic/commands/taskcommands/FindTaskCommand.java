package w15c2.tusk.logic.commands.taskcommands;

import java.util.Set;

import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.CommandResult;

/**
 * Finds and lists all tasks in TaskManager whose description contains any of the argument keywords.
 * 
 */
public class FindTaskCommand extends Command {

        public static final String COMMAND_WORD = "find";
        public static final String ALTERNATE_COMMAND_WORD = null;
        
        public static final String COMMAND_FORMAT = COMMAND_WORD + " <keyword[s]>";
        public static final String COMMAND_DESCRIPTION = "Find Tasks"; 
        
        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
                + "the specified keywords and displays them as a list with index numbers.\n"
                + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                + "Example: " + COMMAND_WORD + " dinner lunch meeting";
        private final Set<String> keywords;

        public FindTaskCommand(Set<String> keywords) {
            this.keywords = keywords;
        }

        @Override
        public CommandResult execute() {
            model.filterTasks(keywords);
            closeHelpWindow();
            return new CommandResult(getMessageForTaskListShownSummary(model.getCurrentFilteredTasks().size()));
        }
        
        /**
         * Retrieve the details of the task for testing purposes
         */
        public Set<String> getKeywords() {
        	return keywords;
        }


}
