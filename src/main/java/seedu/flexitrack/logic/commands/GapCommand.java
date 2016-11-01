//@@author A0127686R
package seedu.flexitrack.logic.commands;

import java.util.List;

import seedu.flexitrack.model.task.DateTimeInfo;

/**
 * Find a gap with minimum time specified by the user. 
 */
public class GapCommand extends Command {

    public static final String COMMAND_WORD = "gap";
    public static final String COMMAND_SHORTCUT = "g";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ", shortcut [" + COMMAND_SHORTCUT + "]" + 
            ": Find the earliest specified timing available.\n" 
            + "1. To find a gap with default number of gap searched (3) - Parameters: [specified timing] \n"
            + "   Example: " + COMMAND_WORD + " 3 hours \n"
            + "2. To find a specific number of gap - Parameters: [specified timing] n/[number of gap to find] \n"
            + "   Example: " + COMMAND_WORD + " 30 minutes n/4\n"
            + "Note that :\n"
            + "   1. The specified timing and number of gap need to be in digit.\n"
            + "   2. Timing for minutes can't be longer than 59 minutes, for hour it can't be longer than 23 hours"
            + "and for days it can't be more than 29 days\n"
            + "Example of accepted specified timing: 20m, 30 minutes, 2 hours, 12 h, 2 days.\n"
            + "Example of rejected specified timing: 70 minutes, 2 hours 30 minutes";
            
    public static final String MESSAGE_SUCCESS = "The earliest %1$s free time are found... ";
    public static final String WORD_DAY = "day";
    public static final String WORD_HOUR = "hour";
    public static final String WORD_MINUTE = "minute";
    public static final String INITIAL_DAY = "d";
    public static final String INITIAL_HOUR = "h";
    public static final String INITIAL_MINUTE = "m";
    public static final int DEFAULT_NUMBER_OF_SLOT = 3;
    public static final int REF_NO_DAY = 2;
    public static final int REF_NO_HOUR = 1;
    public static final int REF_NO_MINUTE = 0;

    private final int keyword;
    private final int length; 
    private final int numberOfSlot; 

    public GapCommand(int keyword, int length, int numberOfSlot) {
        this.keyword = keyword; 
        this.length = length; 
        this.numberOfSlot = numberOfSlot; 
    }

    @Override
    public CommandResult execute() {
        List<DateTimeInfo> listOfTiming = model.findSpecifiedGapTiming(keyword,length,numberOfSlot);
        String theListOfDates = putResultIntoString(listOfTiming);
        String keywordString = putKeywordIntoString(); 
        return new CommandResult((String.format(MESSAGE_SUCCESS, keywordString) + theListOfDates));
    }

    /**
     * Put the user specified timing back into string
     * @return the specified timing by the users 
     */
    private String putKeywordIntoString() {
        String keywordString = ""; 
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
        return keywordString;
    }

    /**
     * Rearrange and put into String the starting and ending time of the gap 
     * @param listOfTiming
     * @return The timing of the gap in string, ready to be shown to the user 
     */
    private String putResultIntoString(List<DateTimeInfo> listOfTiming) {
        String theListOfDates = ""; 
        int iter = 0; 
        for (; iter<listOfTiming.size()-1 ; iter++){
            if ( iter == 0 && listOfTiming.get(iter).toString().equals(DateTimeInfo.getCurrentTime().toString())){
                theListOfDates = theListOfDates + "\nBetween:  now                        ";
            } else {
                theListOfDates = theListOfDates + "\nBetween:  " + listOfTiming.get(iter).toString();
            }iter=iter+1;
            theListOfDates = theListOfDates + "  to: " + listOfTiming.get(iter).toString();

        }
        if ((iter+1)/2 <numberOfSlot){
            theListOfDates = theListOfDates + "\nFree from: " + listOfTiming.get(iter).toString()+ " onwards. ";
        }
        return theListOfDates;
    }

}
