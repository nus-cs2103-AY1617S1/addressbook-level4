package harmony.mastermind.logic.commands;

/**
 * Lists all upcoming tasks in the task manager to the user.
 */
public class UpcomingCommand extends Command {

    public static final String COMMAND_WORD = "upcoming";
    public final String TIME_TODAY = "today 2359";

    public static final String MESSAGE_USAGE = "[Examples] \nupcoming";
    public static final String MESSAGE_SUCCESS_UPCOMING = "Listed all upcoming tasks";
    
    public UpcomingCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowUpcoming(prettyTimeParser.parse(TIME_TODAY).get(0).getTime());
        
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS_UPCOMING);
    }
    

}
