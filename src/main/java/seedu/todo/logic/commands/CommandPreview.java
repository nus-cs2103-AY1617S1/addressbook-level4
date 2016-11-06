package seedu.todo.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import seedu.todo.commons.util.StringUtil;

//@@author A0139021U

/**
 * Represents all relevant commands that will be used to show to the user.
 */
public class CommandPreview {
    private List<CommandSummary> commandSummaries;

    public CommandPreview(String userInput) {
        commandSummaries = filterCommandSummaries(userInput);
    }
    
    public List<CommandSummary> getPreview() {
        return commandSummaries;
    }

    private List<CommandSummary> filterCommandSummaries(String input) {
        List<CommandSummary> summaries = new ArrayList<>();
        List<String> keys = CommandMap.filterCommandKeys(input);
        CommandMap.getCommandSummaryMap().forEach((key, value) -> {
            if (keys.contains(key)) {
                summaries.addAll(value);
            }
        });
        return summaries;
    }
}
