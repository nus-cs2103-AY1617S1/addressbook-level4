package harmony.mastermind.logic.commands;

import java.util.regex.Pattern;

//@@author A0124797R
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
    public static final String MESSAGE_SUCCESS_UPCOMING_DEADLINE = "Listed all upcoming deadlines";
    public static final String MESSAGE_SUCCESS_UPCOMING_EVENT = "Listed all upcoming events";
    public static final String COMMAND_SUMMARY = "Upcoming tasks: \n" + COMMAND_WORD;
    
    private String taskType;
    
    public UpcomingCommand() {}
    
    public UpcomingCommand(String type) {
        this.taskType = type;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowUpcoming(prettyTimeParser.parse(TIME_TODAY).get(0).getTime(), taskType);
        
        switch (taskType) {
            case "deadlines" :
                return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS_UPCOMING_DEADLINE);
            case "events" :
                return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS_UPCOMING_EVENT);
            default :
                return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS_UPCOMING);
        }
    }
    

}
