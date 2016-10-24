package seedu.address.logic.commands;

import java.util.Set;

public class ListByTagCommand extends Command {
    public static final String COMMAND_WORD = "list-by-tag";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with entered tag";

    private final String keywords;

    public ListByTagCommand(String keywords) {
        this.keywords = keywords;
    }
    
    @Override
    public CommandResult execute() {
        model.updateFilteredTagListToShowAll(keywords);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
