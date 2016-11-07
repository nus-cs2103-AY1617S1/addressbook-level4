//@@author A0139772U
package seedu.whatnow.logic.commands;

import java.util.ArrayList;
import seedu.whatnow.model.freetime.Period;

/**
 * Fetch and display the free periods of a date
 */
public class FreeTimeCommand extends Command {

    public static final String COMMAND_WORD = "freetime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the freetime of a particular date\n"
            + "Parameters: date\n" + "Example: [" + COMMAND_WORD + " 12/12/2016] or [" + COMMAND_WORD + " today or ["
            + COMMAND_WORD + " tuesday]\n";

    public static final String MESSAGE_SUCCESS = "Freetime found for: ";

    public static final String MESSAGE_NO_FREE_TIME_FOUND = "No freetime was found for: ";

    public final String date;

    public FreeTimeCommand(String date) {
        this.date = date;
    }
    
    /**
     * Execute the FreeTimeCommand to display the period of free time of a date
     */
    @Override
    public CommandResult execute() {

        ArrayList<Period> freePeriods = model.getFreeTime(date.trim()).getList();
        if (freePeriods.size() == 0) {
            String dateWithoutTrail = date.trim();
            return new CommandResult(MESSAGE_NO_FREE_TIME_FOUND + dateWithoutTrail);
        }
        return new CommandResult(MESSAGE_SUCCESS + date.trim() + "\n" + freePeriods.toString());
    }

}