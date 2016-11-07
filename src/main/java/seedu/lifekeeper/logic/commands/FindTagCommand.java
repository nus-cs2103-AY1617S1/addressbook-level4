package seedu.lifekeeper.logic.commands;

public class FindTagCommand extends Command {
    public static final String COMMAND_WORD = "findtag";

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
