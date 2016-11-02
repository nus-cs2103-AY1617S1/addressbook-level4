package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import tars.logic.commands.FreeCommand;
import tars.model.Tars;

/**
 * Logic command test for free
 * 
 * @@author A0124333U
 */
public class FreeLogicCommandTest extends LogicCommandTest {
    @Test
    public void execute_free_incorrectArgsFormat_errorMessageShown()
            throws Exception {
        assertCommandBehavior("free ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        assertCommandBehavior("free invalidargs", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        assertCommandBehavior("free 29/10/2016 to 30/10/2016",
                FreeCommand.MESSAGE_DATE_RANGE_DETECTED);
    }

    @Test
    public void execute_free_noFreeTimeSlotResult() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Tars expectedTars = helper.fillModelAndTarsForFreeCommand(model);

        assertCommandBehavior("free 11/10/2016",
                String.format(FreeCommand.MESSAGE_NO_FREE_TIMESLOTS,
                        "Tuesday, 11/10/2016"),
                expectedTars, expectedTars.getTaskList());

        // Case where the user types in a time should still be allowed to pass. Programme will
        // extract the date
        assertCommandBehavior("free 11/10/2016 0900",
                String.format(FreeCommand.MESSAGE_NO_FREE_TIMESLOTS,
                        "Tuesday, 11/10/2016"),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_free_freeDayResult() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Tars expectedTars = helper.fillModelAndTarsForFreeCommand(model);

        assertCommandBehavior("free 01/11/2016",
                String.format(FreeCommand.MESSAGE_FREE_DAY,
                        "Tuesday, 01/11/2016"),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_free_freeTimeSlotsFound() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Tars expectedTars = helper.fillModelAndTarsForFreeCommand(model);

        StringBuilder sb = new StringBuilder();

        sb.append("Saturday, 29/10/2016").append(": \n")
                .append("1. 0100hrs to 1400hrs (13 hr 0 min)\n")
                .append("2. 1800hrs to 2359hrs (5 hr 59 min)");

        assertCommandBehavior("free 29/10/2016",
                String.format(FreeCommand.MESSAGE_SUCCESS, sb.toString()),
                expectedTars, expectedTars.getTaskList());
    }
}
