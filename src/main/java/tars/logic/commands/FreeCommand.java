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

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Suggests free timeslots in a specified day.\n"
                    + "Parameters: <DATETIME>\n" + "Example: free 29/10/2016";

    public static final String MESSAGE_DATE_RANGE_DETECTED =
            "Range of datetime detected. Please only input a single datetime";
    public static final String MESSAGE_SUCCESS = "Free timeslots on %1$s";
    public static final String MESSAGE_FREE_DAY =
            "You have no event tasks or reserved event tasks on %1$s";
    public static final String MESSAGE_NO_FREE_TIMESLOTS =
            "You have no free time slots on %1$s";

    private DateTime dateToCheck;

    public FreeCommand(DateTime dateToCheck) {
        this.dateToCheck = dateToCheck;

        // Ensure that dateToCheck covers the whole day
        this.dateToCheck.setStartDateTime(dateToCheck.getEndDate()
                .withHour(DateTimeUtil.DATETIME_FIRST_HOUR_OF_DAY)
                .withMinute(DateTimeUtil.DATETIME_FIRST_MINUTE_OF_DAY)
                .withSecond(DateTimeUtil.DATETIME_FIRST_SECOND_OF_DAY));

        this.dateToCheck.setEndDateTime(dateToCheck.getEndDate()
                .withHour(DateTimeUtil.DATETIME_LAST_HOUR_OF_DAY)
                .withMinute(DateTimeUtil.DATETIME_LAST_MINUTE_OF_DAY)
                .withSecond(DateTimeUtil.DATETIME_FIRST_SECOND_OF_DAY));
    }

    @Override
    public CommandResult execute() {
        ArrayList<DateTime> listOfFilledTimeSlots =
                model.getListOfFilledTimeSlotsInDate(dateToCheck);
        ArrayList<DateTime> listOfFreeTimeSlots =
                DateTimeUtil.getListOfFreeTimeSlotsInDate(dateToCheck,
                        listOfFilledTimeSlots);

        model.updateFilteredTaskListUsingDate(dateToCheck);

        if (listOfFilledTimeSlots.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FREE_DAY,
                    DateTimeUtil.getDayAndDateString(dateToCheck)));
        } else if (listOfFreeTimeSlots.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_FREE_TIMESLOTS,
                    DateTimeUtil.getDayAndDateString(dateToCheck)));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    DateTimeUtil.getStringOfFreeDateTimeInDate(dateToCheck,
                            listOfFreeTimeSlots)));
        }
    }

}
