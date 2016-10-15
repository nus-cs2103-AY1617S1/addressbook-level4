package seedu.todo.controllers;

import java.io.IOException;
import java.util.List;

import seedu.todo.MainApp;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.ConfigView;

public class ConfigController implements Controller {
    
    private static String NAME = "Configure";
    private static String DESCRIPTION = "Shows current configuration settings or updates them.";
    private static String COMMAND_SYNTAX = "config [<setting> <value>]";
    
    private static final String MESSAGE_SHOWING = "Showing all settings.";
    private static final String MESSAGE_FAILURE = "Could not update settings: %s";
    private static final String MESSAGE_INVALID_INPUT = "Invalid config setting provided!";
    private static final String SPACE = " ";
    private static final int ARGS_LENGTH = 2;
    
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
        input = input.replaceFirst("config", "").trim();
        
        if (input.length() > 0) {
            
            String[] args = input.split(SPACE);
            
            // Check args length
            if (args.length != ARGS_LENGTH) {
                failWithMessage(MESSAGE_INVALID_INPUT);
                return;
            }
            
            assert args.length == ARGS_LENGTH;
            
            // Split by args
            String configName = args[0];
            String configValue = args[1];
            
            // Get current config
            Config config = MainApp.getConfig();
            
            // Check name
            List<String> validConfigDefinitions = config.getDefinitionsNames();
            if (!validConfigDefinitions.contains(configName)) {
                failWithMessage(MESSAGE_INVALID_INPUT);
                return;
            }
            
            // Set value
            config = updateConfigByName(config, configName, configValue);
            
            // Save config
            try {
                ConfigUtil.saveConfig(config, MainApp.getConfigFilePath());
            } catch (IOException e) {
                failWithMessage(String.format(MESSAGE_FAILURE, e.getMessage()));
            }
            
        }
        
        // Re-render
        ConfigView view = UiManager.loadView(ConfigView.class);
        view.render();
        
        // Update console message
        UiManager.updateConsoleMessage(MESSAGE_SHOWING);
    }
    
    private void failWithMessage(String message) {
        ConfigView view = UiManager.loadView(ConfigView.class);
        view.render();
        UiManager.updateConsoleMessage(message);
    }
    
    private Config updateConfigByName(Config config, String configName, String configValue) {
        switch (configName) {
        case "appTitle":
            config.setAppTitle(configValue);
            break;
        case "databaseFilePath":
            config.setDatabaseFilePath(configValue);
            break;
        }
        
        return config;
    }

}
