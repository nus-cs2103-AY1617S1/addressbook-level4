package seedu.todo.controllers;

import java.io.IOException;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.ConfigCenter;
import seedu.todo.commons.exceptions.CannotConfigureException;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.TodoListDB;
import seedu.todo.ui.UiManager;

// @@author A0139812A
/**
 * Controller to configure app settings.
 * Has side effects, since it has to perform
 * updates on the UI or file sources on update.
 */
public class ConfigController extends Controller {

    private static final String NAME = "Configure";
    private static final String DESCRIPTION = "Shows current configuration settings or updates them.";
    private static final String COMMAND_SYNTAX = "config [<setting> <value>]";
    private static final String COMMAND_KEYWORD = "config";

    private static final String MESSAGE_SHOWING = "Showing all settings.";
    private static final String MESSAGE_SUCCESS = "Successfully updated %s.";
    private static final String MESSAGE_FAILURE = "Could not update settings: %s";
    private static final String MESSAGE_INVALID_INPUT = "Invalid config setting provided!";
    private static final String MESSAGE_WRONG_EXTENSION = "Could not change storage path: File must end with %s";
    public static final String TEMPLATE_SET_CONFIG = "config <setting> <value>";
    
    private static final String STRING_SPACE = " ";
    private static final int ARGS_LENGTH = 2;
    private static final String DB_FILE_EXTENSION = ".json";

    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX, COMMAND_KEYWORD); 

    @Override
    public CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public void process(String input) {
        String params = input.replaceFirst("config", "").trim();

        // Check for basic command.
        if (params.length() <= 0) {
            Renderer.renderConfig(MESSAGE_SHOWING);
            return;
        }
        
        // Check args length
        String[] args = params.split(STRING_SPACE, ARGS_LENGTH);
        if (args.length != ARGS_LENGTH) {
            Renderer.renderDisambiguation(TEMPLATE_SET_CONFIG, MESSAGE_INVALID_INPUT);
            return;
        }
        
        String configName = args[0];
        String configValue = args[1];
        Config config = ConfigCenter.getInstance().getConfig();

        // Check if configName is a valid name.
        if (!config.getDefinitionsNames().contains(configName)) {
            Renderer.renderDisambiguation(TEMPLATE_SET_CONFIG, MESSAGE_INVALID_INPUT);
            return;
        }

        try {
            // Update config value
            config = updateConfigByName(config, configName, configValue);
            
            // Save config to file
            ConfigCenter.getInstance().saveConfig(config);
        } catch (CannotConfigureException | IOException e) {
            Renderer.renderConfig(String.format(MESSAGE_FAILURE, e.getMessage()));
            return;
        }

        // Update console for success
        Renderer.renderConfig(String.format(MESSAGE_SUCCESS, configName));
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
            // Move the DB file to the new location
            moveDatabaseFile(configValue);

            // Update config
            config.setDatabaseFilePath(configValue);

            break;

        default :
            break;
        }

        return config;
    }
    
    /**
     * Moves the database file to the new location.
     * Throws an exception if the new path does not exist, or if it has the wrong extension.
     */
    private void moveDatabaseFile(String newPath) throws CannotConfigureException {
        // Make sure the new path has a .json extension
        if (!newPath.endsWith(DB_FILE_EXTENSION)) {
            throw new CannotConfigureException(String.format(MESSAGE_WRONG_EXTENSION, DB_FILE_EXTENSION));
        }

        try {
            TodoListDB.getInstance().move(newPath);
        } catch (IOException e) {
            throw new CannotConfigureException(e.getMessage());
        }
    }

}
