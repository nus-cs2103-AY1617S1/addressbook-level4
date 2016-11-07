package seedu.lifekeeper.logic.commands;

public class FindTagCommand extends Command {
    public static final String COMMAND_WORD = "findtag";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entries which contain any tags matching "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    private final String keywords;

    public FindTagCommand(String keywords) {
        this.keywords = keywords;
    }
    
    @Override
    public CommandResult execute() {
        model.updateFilteredByTagListToShowAll(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
