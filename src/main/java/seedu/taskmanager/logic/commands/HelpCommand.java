package seedu.taskmanager.logic.commands;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "h";
    //@@author 
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String HELP_UNAVAILABLE_MESSAGE = "Unable to conenct to User Guide";

    @Override
    public CommandResult execute() {
        if (isHelpAvailable(Config.USERGUIDE_URL)) {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(HELP_UNAVAILABLE_MESSAGE);
        }
    }
    
    //reused
    private static boolean isHelpAvailable(String helpUrl) {                                                                                                                                                                                                 
        try {                                                                                                                                                                                                                                 
            final URL url = new URL(helpUrl);                                                                                                                                                                                 
            final URLConnection connection = url.openConnection();                                                                                                                                                                                  
            connection.connect();                                                                                                                                                                                                                   
            return true;                                                                                                                                                                                                                      
        } catch (MalformedURLException e) {                                                                                                                                                                                                   
            throw new RuntimeException(e);                                                                                                                                                                                                    
        } catch (IOException e) {                                                                                                                                                                                                             
            return false;                                                                                                                                                                                                                     
        }                                                                                                                                                                                                                                     
    }   
}
