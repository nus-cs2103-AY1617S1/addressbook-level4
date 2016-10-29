package tars.logic.commands;

import java.util.ArrayList;
import tars.commons.util.DateTimeUtil;
import tars.model.task.DateTime;

/**
 * @@author A0124333U
 * 
 *          Suggests free time slots on a specified date
 */
public class FreeCommand extends Command {

    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Suggests free timeslots in a specified day.\n"
            + "Parameters: <DATETIME>\n" + "Example: free 29/10/2016";

    public static final String MESSAGE_DATE_RANGE_DETECTED = "Range of datetime detected. Please only input a single datetime";
    public static final String MESSAGE_SUCCESS = "Free timeslots on %1$s";
    public static final String MESSAGE_FREE_DAY = "You have no tasks or reserved tasks on %1$s";
    public static final String MESSAGE_NO_FREE_TIMESLOTS = "You have no free time slots on %1$s";

    private DateTime dateToCheck;

    public FreeCommand(DateTime dateToCheck) {
        this.dateToCheck = dateToCheck;
        this.dateToCheck.setStartDateTime(dateToCheck.getEndDate().withHour(0).withMinute(0).withSecond(0));
    }

    @Override
    public CommandResult execute() {
        ArrayList<DateTime> listOfFilledTimeSlots = model.getListOfFilledTimeSlotsInDate(dateToCheck);
        ArrayList<DateTime> listOfFreeTimeSlots = DateTimeUtil.getListOfFreeTimeSlotsInDate(dateToCheck,
                listOfFilledTimeSlots);

        if (listOfFilledTimeSlots.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FREE_DAY, DateTimeUtil.getDayAndDateString(dateToCheck)));
        } else if (listOfFreeTimeSlots.isEmpty()) {
            return new CommandResult(
                    String.format(MESSAGE_NO_FREE_TIMESLOTS, DateTimeUtil.getDayAndDateString(dateToCheck)));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    DateTimeUtil.getStringOfFreeDateTimeInDate(dateToCheck, listOfFreeTimeSlots)));
        }
    }

}
