package seedu.todo.controllers;

import java.io.IOException;
import java.util.Map;

import seedu.todo.MainApp;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.TodoListDB;

/**
 * Controller to declare aliases
 * 
 * @author louietyj
 *
 */
public class AliasController implements Controller {
    
    private static final String NAME = "Alias";
    private static final String DESCRIPTION = "Shows current aliases or updates them.";
    private static final String COMMAND_SYNTAX = "alias [<alias key> <alias value>]";
    
    private static final String SPACE = " ";
    private static final int ARGS_LENGTH = 2;
    private static final String MESSAGE_SHOWING = "Showing all aliases.";
    private static final String MESSAGE_SAVE_SUCCESS = "Successfully saved alias!";
    private static final String INVALID_NUM_PARAMS = "Seems like you have provided an invalid number of parameters!";
    private static final String MESSAGE_INVALID_INPUT = "Invalid alias parameters! Alias inputs must consist solely "
                                                      + "of alphabetical characters.";
    private static final String SAVE_ERROR = "There was an error saving your aliases. Please try again.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }
    
    @Override
    public float inputConfidence(String input) {
        // TODO
        return input.toLowerCase().startsWith("alias") ? 1 : 0;
    }

    @Override
    public void process(String input) {
        String params = input.replaceFirst("alias", "").trim();

        if (params.length() <= 0) {
            Renderer.renderAlias(MESSAGE_SHOWING);
            return;
        }
        
        String[] args = params.split(SPACE, ARGS_LENGTH);
        
        String aliasKey = null;
        String aliasValue = null;
        
        // Best-effort matching, disambiguate if wrong.
        validate: {
            switch (args.length) {
                case 0:
                    break;
                case 1:
                    aliasKey = args[0];
                    break;
                case 2: // All good!
                    aliasKey = args[0];
                    aliasValue = args[1];
                    break validate;
                default:
                    aliasKey = args[0];
                    aliasValue = args[0];
                    break;
            }
            renderDisambiguation(aliasKey, aliasValue, INVALID_NUM_PARAMS);
            return;
        }
        
        if (!validateAlias(aliasKey) || !validateAlias(aliasValue)) {
            renderDisambiguation(aliasKey, aliasValue, MESSAGE_INVALID_INPUT);
            return;
        }
        
        // Persist alias mapping
        try {
            saveAlias(aliasKey, aliasValue);
        } catch (IOException e) {
            Renderer.renderAlias(SAVE_ERROR);
            return;
        }
        
        Renderer.renderAlias(MESSAGE_SAVE_SUCCESS);
    }
    
    /**
     * Persists an alias mapping to the database.
     * 
     * @param db    TodoListDB singleton
     * @param aliasKey
     * @param aliasValue
     * @throws IOException 
     */
    private static void saveAlias(String aliasKey, String aliasValue) throws IOException {
        Config config = MainApp.getConfig();
        Map<String, String> aliases = config.getAliases();
        aliases.put(aliasKey, aliasValue);
        ConfigUtil.saveConfig(config, MainApp.getConfigFilePath());
        
    }
    
    /**
     * Validates that string is sanitized and safe for aliasing.
     * 
     * @param alias     string to check
     * @return          true if string is sanitized, false otherwise
     */
    private static boolean validateAlias(String alias) {
        return alias.chars().allMatch(Character::isLetter);
    }
    
    /**
     * Makes a best effort to sanitize input string.
     * 
     * @param alias     string to sanitize
     * @return          sanitized string
     */
    private static String sanitize(String alias) {
        return (alias == null) ? null : alias.replaceAll("[^A-Za-z]+", "");
    }
    
    private static void renderDisambiguation(String aliasKey, String aliasValue, String message) {
        String sanitizedAliasKey = sanitize(aliasKey);
        if (sanitizedAliasKey == null || sanitizedAliasKey.length() == 0) {
            sanitizedAliasKey = "<alias key>";
        }
        String sanitizedAliasValue = sanitize(aliasValue);
        if (sanitizedAliasValue == null || sanitizedAliasValue.length() == 0) {
            sanitizedAliasValue = "<alias value>";
        }
        Renderer.renderDisambiguation(String.format("alias %s %s",
                sanitizedAliasKey, sanitizedAliasValue), message);
    }

}
