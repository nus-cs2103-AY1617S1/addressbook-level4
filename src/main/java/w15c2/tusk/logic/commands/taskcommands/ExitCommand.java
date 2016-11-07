package w15c2.tusk.logic.commands.taskcommands;

import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.ExitAppRequestEvent;
import w15c2.tusk.logic.commands.CommandResult;

//@@author A0143107U
/**
 * Terminates the program.
 */
public class ExitCommand extends TaskCommand {

    public static final String COMMAND_WORD = "exit";
    public static final String ALTERNATE_COMMAND_WORD = null;
    
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String COMMAND_DESCRIPTION = "Exit Tusk"; 

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Tusk Manager as requested ...";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits Tusk Manager.\n"
            + "Example: " + COMMAND_WORD;
    
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }
    
    @Override
    public String toString(){
        return MESSAGE_EXIT_ACKNOWLEDGEMENT;
    }

}