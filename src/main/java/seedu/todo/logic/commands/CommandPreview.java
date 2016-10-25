package seedu.todo.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.todo.commons.util.StringUtil;

//@@author A0139021U

/**
 * Represents the preview of a command execution.
 */
public class CommandPreview {
    private static final double CLOSENESS_THRESHOLD = 30d;
    private List<CommandSummary> commandSummaries;

    public CommandPreview(String userInput) {
        commandSummaries = filterCommandSummaries(userInput);
    }
    
    public List<CommandSummary> getPreview() {
        return commandSummaries;
    }

    private List<CommandSummary> filterCommandSummaries(String input) {
        List<CommandSummary> summaries = new ArrayList<>();
        for (String key : CommandMap.getCommandMap().keySet()) {
            if (StringUtil.calculateClosenessScore(key, input) > CLOSENESS_THRESHOLD) {
                summaries.addAll(CommandMap.getCommand(key).getCommandSummary());
            }
        }
        return summaries;
    }
}
