package seedu.address.logic.commands.taskcommands;

import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.logic.commands.CommandResult;

/**
 * Finds and lists all tasks in TaskManager whose description contains any of the argument keywords.
 * 
 */
public class FindTaskCommand extends TaskCommand {

        public static final String[] COMMAND_WORD = {"find"};

        public static final String HELP_MESSAGE_USAGE = "Find Tasks: \t" + "find <keyword[s]>";
        
        public static final String MESSAGE_USAGE = COMMAND_WORD[0] + ": Finds all tasks whose names contain any of "
                + "the specified keywords and displays them as a list with index numbers.\n"
                + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                + "Example: " + COMMAND_WORD[0] + " dinner lunch meeting";
        private final Set<String> keywords;

        public FindTaskCommand(Set<String> keywords) {
            this.keywords = keywords;
        }

        @Override
        public CommandResult execute() {
            model.filterTasks(keywords);
            EventsCenter.getInstance().post(new HideHelpRequestEvent());
            return new CommandResult(getMessageForTaskListShownSummary(model.getCurrentFilteredTasks().size()));
        }
        
        /**
         * Retrieve the details of the task for testing purposes
         */
        public Set<String> getKeywords() {
        	return keywords;
        }


}
