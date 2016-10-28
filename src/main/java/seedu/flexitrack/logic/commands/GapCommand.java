//@@author A0127686R
package seedu.flexitrack.logic.commands;

import java.util.List;

import seedu.flexitrack.model.task.DateTimeInfo;

/**
 * Lists all task in the FlexiTrack to the user.
 */
public class GapCommand extends Command {

    public static final String COMMAND_WORD = "gap";
    public static final String COMMAND_SHORTCUT = "g";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]" + 
            ": Find the earliest specified timing available.\n" 
            + COMMAND_WORD + " [specified timing]\n"
            + "Example: " + COMMAND_WORD + " 3 hours \n";
    public static final String MESSAGE_SUCCESS = "The earliest %1%s gap is on: ";
    public static final String DAY_WORD = "day";
    public static final String HOUR_WORD = "hour";
    public static final String MINUTE_WORD = "minute";

    public final int keyword;
    public final int length; 

    public GapCommand(int keyword, int length) {
        this.keyword = keyword; 
        this.length = length; 
    }

    @Override
    public CommandResult execute() {
        System.out.println("keyword: " + keyword);
        System.out.println("length: " + length);
        
        List<DateTimeInfo> listOfTiming = model.findSpecifiedGapTiming(keyword,length);

        for (DateTimeInfo time: listOfTiming){
            System.out.println(time.toString());
        }
        
        return new CommandResult((MESSAGE_SUCCESS));
    }

}
