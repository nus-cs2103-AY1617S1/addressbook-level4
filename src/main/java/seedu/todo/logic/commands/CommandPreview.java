package seedu.todo.logic.commands;

import java.util.ArrayList;
import java.util.List;

//@@author A0139021U

/**
 * Represents the preview of a command execution.
 */
public class CommandPreview {
    private static List<CommandSummary> commandSummaries;

    public CommandPreview(String feedback) {
        if (commandSummaries == null) {
            commandSummaries = collectCommandSummaries();
        }
    }
    
    public List<CommandSummary> getPreview() {
        return commandSummaries;
    }

    private List<CommandSummary> collectCommandSummaries() {
        List<CommandSummary> summaries = new ArrayList<>();
        for (String key : CommandMap.getCommandMap().keySet()) {
            summaries.addAll(CommandMap.getCommand(key).getCommandSummary());
        }
        return summaries;
    }
}
