package seedu.malitio.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in Malitio whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {
  //@@author a0126633j
    public static final String COMMAND_WORD = "find";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds [specified] tasks whose names contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " [f/d/e] adjust bring chill";
     
    private static final String FLOATING_TASK_KEYWORD = "f";
    private static final String DEADLINE_KEYWORD = "d";
    private static final String EVENT_KEYWORD = "e";
    
    private final Set<String> keywords;
    private final String typeOfTask;

    public FindCommand(String type, Set<String> keywords) { 
        this.keywords = keywords;
        this.typeOfTask = type;
    }

    @Override
    public CommandResult execute() {

        switch (typeOfTask) {
        case FLOATING_TASK_KEYWORD: 
            model.updateFilteredTaskList(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredFloatingTaskList().size()));
        case DEADLINE_KEYWORD:
            model.updateFilteredDeadlineList(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredDeadlineList().size()));
        case EVENT_KEYWORD:
            model.updateFilteredEventList(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredEventList().size()));
 
        default: //find in all lists
            model.updateFilteredTaskList(keywords);
            model.updateFilteredDeadlineList(keywords);
            model.updateFilteredEventList(keywords);    
            return new CommandResult(getMessageForTaskListShownSummary(
                    model.getFilteredFloatingTaskList().size() +
                    model.getFilteredDeadlineList().size() + 
                    model.getFilteredEventList().size()));
        }
    }

}
