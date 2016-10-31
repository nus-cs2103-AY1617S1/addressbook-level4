//@@author A0139772U
package seedu.whatnow.logic.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import seedu.whatnow.model.freetime.FreePeriod;
import seedu.whatnow.model.freetime.Period;

/**
 * Fetch and display the free periods of a date
 */
public class FreeTimeCommand extends Command {

    public static final String COMMAND_WORD = "freetime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the freetime of a particular date "
            + "Parameters: date"
            + "Example: " + COMMAND_WORD
            + "12/12/2016\n";

    public static final String MESSAGE_SUCCESS = "Freetime found for: "; 

    public final String date;
    
    public FreeTimeCommand(String date) {
        this.date = date;
    }

    @Override
    public CommandResult execute() {
        ArrayList<Period> freePeriods = model.getFreeTime(date.trim()).getList();
        return new CommandResult(MESSAGE_SUCCESS + date.trim() + "\n" + freePeriods.toString());
    }

}