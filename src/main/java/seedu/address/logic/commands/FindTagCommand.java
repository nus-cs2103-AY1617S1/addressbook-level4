package seedu.address.logic.commands;

import java.util.Set;

public class FindTagCommand extends Command {
    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with entered tag";

    private final String keywords;

    public FindTagCommand(String keywords) {
        this.keywords = keywords;
    }
    
    @Override
    public CommandResult execute() {
        model.updateFilteredByTagListToShowAll(keywords);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
