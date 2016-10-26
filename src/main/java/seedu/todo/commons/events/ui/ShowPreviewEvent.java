package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.logic.commands.CommandSummary;

import java.util.List;

//@@author A0139021U
/**
 * An event requesting to view the help page.
 */
public class ShowPreviewEvent extends BaseEvent {
    private List<CommandSummary> commandSummaries;

    public ShowPreviewEvent(List<CommandSummary> commandSummaries) {
        this.commandSummaries = commandSummaries;
    }

    public List<CommandSummary> getPreviewInfo() {
        return commandSummaries;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
