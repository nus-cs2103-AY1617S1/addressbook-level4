package seedu.todo.controllers;

import java.io.IOException;
import java.util.List;

import seedu.todo.MainApp;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.exceptions.CannotConfigureException;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;

/**
 * Controller to configure app settings.
 * Has side effects, since it has to perform
 * updates on the UI or file sources on update.
 * 
 * @@author A0139812A
 */
public class ConfigController implements Controller {

    private static final String NAME = "Configure";
    private static final String DESCRIPTION = "Shows current configuration settings or updates them.";
    private static final String COMMAND_SYNTAX = "config [<setting> <value>]";

    private static final String MESSAGE_SHOWING = "Showing all settings.";
    private static final String MESSAGE_SUCCESS = "Successfully updated %s.";
    private static final String MESSAGE_FAILURE = "Could not update settings: %s";
    private static final String MESSAGE_INVALID_INPUT = "Invalid config setting provided!";
    private static final String MESSAGE_WRONG_EXTENSION = "Could not change storage path: File must end with %s";
    private static final String SPACE = " ";
    private static final int ARGS_LENGTH = 2;
    private static final String DB_FILE_EXTENSION = ".json";

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
        String params = input.replaceFirst("config", "").trim();

        if (params.length() <= 0) {

            // Showing all configs
            Renderer.renderConfig(MESSAGE_SHOWING);

        } else {

            String[] args = params.split(SPACE, ARGS_LENGTH);

            // Check args length
            if (args.length != ARGS_LENGTH) {
                Renderer.renderConfig(MESSAGE_INVALID_INPUT);
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
                Renderer.renderConfig(MESSAGE_INVALID_INPUT);
                return;
            }

            assert validConfigDefinitions.contains(configName);

            // Update config value
            try {
                config = updateConfigByName(config, configName, configValue);
            } catch (CannotConfigureException e) {
                Renderer.renderConfig(e.getMessage());
                return;
            }

            // Save config to file
            try {
                ConfigUtil.saveConfig(config, MainApp.getConfigFilePath());
            } catch (IOException e) {
                Renderer.renderConfig(String.format(MESSAGE_FAILURE, e.getMessage()));
                return;
            }

            // Update console for success
            Renderer.renderConfig(String.format(MESSAGE_SUCCESS, configName));
        }
    }

    /**
     * Updates a config value and performs the necessary actions for the configuration.
     * Throws a {@code CannotConfigureException} if an error was encountered while performing configuration actions.
     * 
     * @param config         Config object which will be updated.
     * @param configName     Config setting name to update.
     * @param configValue    New value to set for the config setting.
     * @return               Config object after setting values.
     * @throws CannotConfigureException if an error was encountered during configuration.
     */
    private Config updateConfigByName(Config config, String configName, String configValue) throws CannotConfigureException {
        switch (configName) {
        case "appTitle" :
            // Updates MainWindow title
            UiManager.getInstance().getMainWindow().setTitle(configValue);

            // Update config
            config.setAppTitle(configValue);

            break;

        case "databaseFilePath" :
            // Make sure the new path has a .json extension
            if (!configValue.endsWith(DB_FILE_EXTENSION)) {
                throw new CannotConfigureException(String.format(MESSAGE_WRONG_EXTENSION, DB_FILE_EXTENSION));
            }

            // Move the DB file to the new location
            try {
                TodoListDB.getInstance().move(configValue);
            } catch (IOException e) {
                throw new CannotConfigureException(e.getMessage());
            }

            // Update config
            config.setDatabaseFilePath(configValue);

            break;

        default :
            break;
        }

        return config;
    }

}
