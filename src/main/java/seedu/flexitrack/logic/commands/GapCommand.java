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
    public static final String MESSAGE_SUCCESS = "The earliest %1$s gap are found... ";
    public static final String DAY_WORD = "day";
    public static final String HOUR_WORD = "hour";
    public static final String MINUTE_WORD = "minute";

    public final int keyword;
    public final int length; 
//    public final int numberOfSlot; 

    public GapCommand(int keyword, int length) {
        this.keyword = keyword; 
        this.length = length; 
//        this.numberOfSlot =numberOfSlot; 
    }

    @Override
    public CommandResult execute() {
        System.out.println("keyword: " + keyword);
        System.out.println("length: " + length);
        
        List<DateTimeInfo> listOfTiming = model.findSpecifiedGapTiming(keyword,length);

        String theListOfDates = "";
        for (int i = 0; i<listOfTiming.size() ; i++){
            System.out.println("END LIST: " + listOfTiming.get(i).toString());
            theListOfDates = theListOfDates + "\nBetween: " + listOfTiming.get(i).toString();
            i=i+1;
            theListOfDates = theListOfDates + "     to: " + listOfTiming.get(i).toString();

        }
        
        String keywordString="";
        switch (keyword){
        case 0: 
            keywordString = "minute";
            break; 
        case 1: 
            keywordString = "hour"; 
            break; 
        case 2: 
            keywordString = "day"; 
            break;
        }
        keywordString = length + " " + keywordString; 
        if (length>1){
            keywordString = keywordString +"s";
        }
        return new CommandResult((String.format(MESSAGE_SUCCESS, keywordString) + theListOfDates));
    }

}
