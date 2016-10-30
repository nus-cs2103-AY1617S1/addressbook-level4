package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.logic.commands.CommandSummary;

import java.util.List;

//@@author A0135805H
/**
 * An event requesting to view the help page.
 */
public class ShowHelpEvent extends BaseEvent {
    private List<CommandSummary> commandSummaries;
    
    public ShowHelpEvent(List<CommandSummary> commandSummaries) {
        this.commandSummaries = commandSummaries;
    }

    public List<CommandSummary> getCommandSummaries() {
        return commandSummaries;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
