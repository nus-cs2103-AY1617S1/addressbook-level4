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
    private static final String MESSAGE_INVALID_INPUT = "Invalid alias command!";
    
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
        } else {
            String[] args = params.split(SPACE, ARGS_LENGTH);
            
            // Check args length
            if (args.length != ARGS_LENGTH) {
                Renderer.renderConfig(MESSAGE_INVALID_INPUT);
                return;
            }

            // Split by args
            String configName = args[0];
            String configValue = args[1];
        }
    }

}
