package seedu.todo.logic.commands;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import seedu.todo.commons.util.StringUtil;

//@@author A0139021U

/**
 * Represents all relevant commands that will be used to show to the user.
 */
public class CommandPreview {
    private static final int COMMAND_INDEX = 0;
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

        if (StringUtil.isEmpty(input)) {
            return summaries;
        }
        
        List<String> inputList = Lists.newArrayList(Splitter.on(" ")
                .trimResults()
                .omitEmptyStrings()
                .split(input.toLowerCase()));
        
        String command = inputList.get(COMMAND_INDEX);

        CommandMap.getCommandMap().keySet().parallelStream().filter(key ->
                StringUtil.calculateClosenessScore(key, command) > CLOSENESS_THRESHOLD || key.startsWith(command))
            .forEach(key -> summaries.addAll(CommandMap.getCommand(key).getCommandSummary()));

        return summaries;
    }
}
