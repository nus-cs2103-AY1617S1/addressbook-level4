package seedu.todo.controllers;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;

/**
 * Controller to exit gracefully from the app.
 * 
 * @author louietyj
 *
 */
public class ExitController extends Controller {

    private static final String NAME = "Exit";
    private static final String DESCRIPTION = "Exit from GetShitDone!";
    private static final String COMMAND_SYNTAX = "exit";
    private static final String COMMAND_KEYWORD = "exit";

    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX, COMMAND_KEYWORD); 

    @Override
    public CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public void process(String input) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
    }
    
}