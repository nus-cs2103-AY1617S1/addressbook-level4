package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@@author A0135817B
public class CommandMap {
    // List of command classes. Remember to register new commands here so that the
    // dispatcher can recognize them
    public static List<Class<? extends BaseCommand>> commandClasses = ImmutableList.of(
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
        ViewCommand.class
    );
    
    private static Map<String, Class<? extends BaseCommand>> commandMap;
    
    private static void buildCommandMap() {
        commandMap = new LinkedHashMap<>();
        
        for (Class<? extends BaseCommand> command : CommandMap.commandClasses) {
            String commandName = getCommand(command).getCommandName().toLowerCase();
            commandMap.put(commandName, command);
        }
    }

    public static Map<String, Class<? extends BaseCommand>> getCommandMap() {
        if (commandMap == null) {
            buildCommandMap();
        }
        
        return commandMap;
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
}
