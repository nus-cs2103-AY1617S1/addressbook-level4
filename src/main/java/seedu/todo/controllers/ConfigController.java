package seedu.todo.controllers;

import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.ConfigView;

public class ConfigController implements Controller {
    
    private static String NAME = "Configure";
    private static String DESCRIPTION = "Shows current configuration settings or updates them.\n\n"
            + "Available configurations:\n\n"
            + "config storage <PATH>";
    private static String COMMAND_SYNTAX = "config [<setting> <value>]";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX);

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return input.startsWith("config") ? 1 : 0;
    }

    @Override
    public void process(String input) {
        // Re-render
        ConfigView view = UiManager.loadView(ConfigView.class);
        view.render();
    }

}
