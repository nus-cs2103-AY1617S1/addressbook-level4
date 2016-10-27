package seedu.todo.controllers;

import java.io.IOException;
import java.util.Map;

import seedu.todo.MainApp;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Renderer;

/**
 * Controller to unalias an existing alias.
 * 
 * @author louietyj
 *
 */

public class UnaliasController implements Controller {
    
    private static final String NAME = "Unalias";
    private static final String DESCRIPTION = "Deletes an existing alias pair.";
    private static final String COMMAND_SYNTAX = "unalias <alias key>";
    
    private static final String MESSAGE_DESTROY_SUCCESS = "Successfully destroyed alias!";
    private static final String MESSAGE_INVALID_INPUT = "Invalid alias parameters! Alias inputs must consist solely "
            + "of alphabetical characters.";
    private static final String MESSAGE_ALIAS_NOT_EXISTS = "Specified alias key doesn't exist!";
    private static final String SAVE_ERROR = "There was an error saving your aliases. Please try again.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return input.toLowerCase().startsWith("unalias") ? 1 : 0;
    }

    @Override
    public void process(String input) throws ParseException {
        String aliasKey = input.replaceFirst("unalias", "").trim();
        
        if (aliasKey.isEmpty() || !validateAlias(aliasKey)) {
            renderDisambiguation(aliasKey, MESSAGE_INVALID_INPUT);
            return;
        }
        
        // Persist alias mapping
        try {
            if (destroyAlias(aliasKey)) {
                Renderer.renderAlias(MESSAGE_DESTROY_SUCCESS);
            } else {
                Renderer.renderAlias(MESSAGE_ALIAS_NOT_EXISTS);
            }
        } catch (IOException e) {
            Renderer.renderAlias(SAVE_ERROR);
            return;
        }
    }
    
    private static boolean destroyAlias(String aliasKey) throws IOException {
        Config config = MainApp.getConfig();
        Map<String, String> aliases = config.getAliases();
        if (aliases.remove(aliasKey) == null) {
            return false;
        }
        ConfigUtil.saveConfig(config, MainApp.getConfigFilePath());
        return true;
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
    
    private static void renderDisambiguation(String aliasKey, String message) {
        String sanitizedAliasKey = StringUtil.sanitize(aliasKey);
        sanitizedAliasKey = StringUtil.replaceEmpty(sanitizedAliasKey, "<alias key>");
        Renderer.renderDisambiguation(String.format("unalias \"%s\"", sanitizedAliasKey), message);
    }

}
