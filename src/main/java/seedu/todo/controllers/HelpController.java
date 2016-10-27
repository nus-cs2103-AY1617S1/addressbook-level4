package seedu.todo.controllers;

import java.util.Arrays;

import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.HelpView;

/**
 * Controller to show commands help.
 * 
 * @author louietyj
 *
 */
public class HelpController implements Controller {

    private static final String NAME = "Help";
    private static final String DESCRIPTION = "Shows documentation for all valid commands.";
    private static final String COMMAND_SYNTAX = "help";
    
    private static final String MESSAGE_HELP_SUCCESS = "Showing all commands.";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith("help")) ? 1 : 0;
    }

    @Override
    public void process(String input) {
        HelpView view = UiManager.loadView(HelpView.class);
        view.commandDefinitions = Arrays.asList(getAllCommandDefinitions());
        UiManager.renderView(view);
        
        UiManager.updateConsoleMessage(MESSAGE_HELP_SUCCESS);
    }
    
    private CommandDefinition[] getAllCommandDefinitions() {
        return new CommandDefinition[] { HelpController.getCommandDefinition(),
                                         AddController.getCommandDefinition(),
                                         ListController.getCommandDefinition(),
                                         UpdateController.getCommandDefinition(),
                                         CompleteTaskController.getCommandDefinition(),
                                         UncompleteTaskController.getCommandDefinition(),
                                         DestroyController.getCommandDefinition(),
                                         ConfigController.getCommandDefinition(),
                                         DestroyController.getCommandDefinition(),
                                         ClearController.getCommandDefinition(),
                                         FindController.getCommandDefinition(),
                                         TagController.getCommandDefinition(),
                                         UntagController.getCommandDefinition(),
                                         ExitController.getCommandDefinition() };
    }
}
