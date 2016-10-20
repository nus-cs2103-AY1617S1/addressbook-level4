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
 * Controller to declare aliases
 * 
 * @author louietyj
 *
 */
public class AliasController implements Controller {
    
    private static final String NAME = "Alias";
    private static final String DESCRIPTION = "Shows current aliases or updates them.";
    private static final String COMMAND_SYNTAX = "alias [<setting> <value>]";
    
    private static final String SPACE = " ";
    private static final int ARGS_LENGTH = 2;
    private static final String INVALID_NUM_PARAMS = "Seems like you have provided an invalid number of parameters!";
    private static final String MESSAGE_INVALID_INPUT = "Invalid alias parameters! Alias inputs must consist solely "
                                                      + "of alphabetical characters.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }
    
    @Override
    public float inputConfidence(String input) {
        // TODO
        return input.startsWith("alias") ? 1 : 0;
    }

    @Override
    public void process(String input) {
        String params = input.replaceFirst("alias", "").trim();

        if (params.length() <= 0) {
            // TODO: Render aliases
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
            }
            renderDisambiguation(aliasKey, aliasValue, INVALID_NUM_PARAMS);
            return;
        }
        
        if (!validateAlias(aliasKey) || !validateAlias(aliasValue)) {
            renderDisambiguation(aliasKey, aliasValue, MESSAGE_INVALID_INPUT);
            return;
        }
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
