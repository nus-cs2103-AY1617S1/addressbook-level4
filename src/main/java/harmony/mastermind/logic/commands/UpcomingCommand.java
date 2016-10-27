package harmony.mastermind.logic.commands;

import java.util.regex.Pattern;

/**
 * Lists all upcoming tasks in the task manager to the user.
 */
public class UpcomingCommand extends Command {

    public static final String COMMAND_WORD = "upcoming";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": List upcoming deadlines and events.\n"
                                               + "Parameters: TASKTYPE (Optional)\n"
                                               + "Example: \n"
                                               + COMMAND_WORD + "\n"
                                               + COMMAND_WORD + " events\n"
                                               + COMMAND_WORD + " deadlines";
            
    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile("(?<taskType>deadlines|events)");

    public final String TIME_TODAY = "today 2359";

    public static final String MESSAGE_SUCCESS_UPCOMING = "Listed all upcoming tasks";
    
    private String taskType;
    
    public UpcomingCommand() {}
    
    public UpcomingCommand(String type) {
        this.taskType = type;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowUpcoming(prettyTimeParser.parse(TIME_TODAY).get(0).getTime(), taskType);
        
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS_UPCOMING);
    }
    

}
