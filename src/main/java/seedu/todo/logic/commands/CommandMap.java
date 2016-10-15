package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CommandMap {
    public static Map<String, Class<? extends BaseCommand>> COMMAND_MAP = ImmutableMap.<String, Class<? extends BaseCommand>>builder()
        .put("add", AddCommand.class)
        .put("complete", CompleteCommand.class)
        .put("delete", DeleteCommand.class)
        .put("edit", EditCommand.class)
        .put("exit", ExitCommand.class)
        .put("pin", PinCommand.class)
        .build();
}
