package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@@author A0135817B
public class CommandMap {
    // List of command classes. Remember to register new commands here so that the
    // dispatcher can recognize them
    private static List<Class<? extends BaseCommand>> commandClasses = ImmutableList.of(
        AddCommand.class,
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
        commandMap = new LinkedHashMap<>();
        
        for (Class<? extends BaseCommand> command : CommandMap.commandClasses) {
            String commandName = getCommand(command).getCommandName().toLowerCase();
            commandMap.put(commandName, command);
        }
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

    //@@author A0139021U
    private static void buildCommandSummariesMap() {
        // Use linked hashmap so summaries are presented to user in the same order every time
        commandSummaryMap = new LinkedHashMap<>();
        for (String key : getCommandMap().keySet()) {
            commandSummaryMap.put(key, CommandMap.getCommand(key).getCommandSummary());
        }
    }

    /**
     * Gets a map of command keys to command summaries
     * @return map of command summaries
     */
    public static Map<String, List<CommandSummary>> getCommandSummaryMap() {
        if (commandSummaryMap == null) {
            buildCommandSummariesMap();
        }

        return commandSummaryMap;
    }

    /**
     * Gets a full list of all command summaries.
     * @return list of command summaries
     */
    public static List<CommandSummary> getAllCommandSummary() {
        // convert map to list
        List<CommandSummary> commandSummariesList = new ArrayList<>();
        getCommandSummaryMap().values().forEach(commandSummariesList::addAll);
        return commandSummariesList;
    }
}
