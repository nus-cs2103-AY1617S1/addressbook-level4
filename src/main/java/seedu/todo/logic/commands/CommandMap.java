package seedu.todo.logic.commands;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import seedu.todo.commons.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//@@author A0139021U
public class CommandMap {
    // Threshold for accuracy of the fuzzy match
    private static final double CLOSENESS_THRESHOLD = 50d;
    private static final int COMMAND_INDEX = 0;

    // List of command classes. Remember to register new commands here so that the
    // dispatcher can recognize them
    private static List<Class<? extends BaseCommand>> commandClasses = ImmutableList.of(
            AddCommand.class,
            ClearCommand.class,
            CompleteCommand.class,
            DeleteCommand.class,
            EditCommand.class,
            ExitCommand.class,
            HelpCommand.class,
            PinCommand.class,
            UndoCommand.class,
            RedoCommand.class,
            SaveCommand.class,
            LoadCommand.class,
            ShowCommand.class,
            FindCommand.class,
            ViewCommand.class,
            TagCommand.class
    );

    private static Map<String, Class<? extends BaseCommand>> commandMap;
    private static Map<String, List<CommandSummary>> commandSummaryMap;

    private static void buildCommandMap() {
        // Use immutable map so commands are presented to user in the same order every time
        ImmutableMap.Builder<String, Class<? extends BaseCommand>> commandMapBuilder = ImmutableMap.builder();

        for (Class<? extends BaseCommand> command : CommandMap.commandClasses) {
            String commandName = getCommand(command).getCommandName().toLowerCase();
            commandMapBuilder.put(commandName, command);
        }

        commandMap = commandMapBuilder.build();
    }

    public static BaseCommand getCommand(String key) {
        return getCommand(getCommandMap().get(key));
    }

    public static BaseCommand getCommand(Class<? extends BaseCommand> command) {
        try {
            return command.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            e.printStackTrace();
            return null; // This shouldn't happen
        }
    }

    public static Map<String, Class<? extends BaseCommand>> getCommandMap() {
        if (commandMap == null) {
            buildCommandMap();
        }

        return commandMap;
    }

    private static void buildCommandSummariesMap() {
        // Use immutable map so summaries are presented to user in the same order every time
        ImmutableMap.Builder<String, List<CommandSummary>> commandSummaryBuilder = ImmutableMap.builder();

        for (String key : getCommandMap().keySet()) {
            commandSummaryBuilder.put(key, CommandMap.getCommand(key).getCommandSummary());
        }
        commandSummaryMap = commandSummaryBuilder.build();
    }

    public static Map<String, List<CommandSummary>> getCommandSummaryMap() {
        if (commandSummaryMap == null) {
            buildCommandSummariesMap();
        }

        return commandSummaryMap;
    }

    public static List<CommandSummary> getAllCommandSummary() {
        // convert map to list
        List<CommandSummary> commandSummariesList = new ArrayList<>();
        getCommandSummaryMap().values().forEach(commandSummariesList::addAll);
        return commandSummariesList;
    }

    public static List<String> filterCommandKeys(String input) {
        List<String> commands = new ArrayList<>();

        // No input
        if (StringUtil.isEmpty(input)) {
            return commands;
        }
        Set<String> allCommands = getCommandMap().keySet();
        String command = getUserCommand(input);

        if (allCommands.contains(command)) {
            // If one-to-one correspondence, return only the command
            commands.add(command);
        } else {
            // Else add relevant commands if they fit the criteria
            commands = allCommands.stream().filter(key -> key.startsWith(command) ||
                    StringUtil.calculateClosenessScore(key, command) > CLOSENESS_THRESHOLD
            ).collect(Collectors.toList());
        }
        return commands;
    }

    private static String getUserCommand(String userInput) {
        List<String> inputList = Lists.newArrayList(Splitter.on(" ")
                .trimResults()
                .omitEmptyStrings()
                .split(userInput.toLowerCase()));
        return inputList.get(COMMAND_INDEX);
    }
}
