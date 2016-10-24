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

        public static final String COMMAND_WORD = "find";

        public static final String MESSAGE_USAGE = "Find Tasks: \t" + "find <keyword[s]>";
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


}
