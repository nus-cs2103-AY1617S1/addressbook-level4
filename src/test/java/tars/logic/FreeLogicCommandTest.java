package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tars.logic.commands.FreeCommand;
import tars.model.Tars;
import tars.model.task.DateTime;
import tars.model.task.Task;

/**
 * Logic command test for free
 * 
 * @@author A0124333U
 */
public class FreeLogicCommandTest extends LogicCommandTest {
  @Test
  public void execute_free_incorrectArgsFormat_errorMessageShown() throws Exception {
    assertCommandBehavior("free ",
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
    assertCommandBehavior("free invalidargs",
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
    assertCommandBehavior("free 29/10/2016 to 30/10/2016", FreeCommand.MESSAGE_DATE_RANGE_DETECTED);
  }

  @Test
  public void execute_free_noFreeTimeSlotResult() throws Exception {
    TypicalTestDataHelper helper = new TypicalTestDataHelper();
    Tars expectedTars = helper.fillModelAndTarsForFreeCommand(model);

    Task task4 = helper.generateTaskWithNameAndDate("Task 4",
        new DateTime("10/10/2016 1500", "12/10/2016 1400"));
    List<Task> expectedShownTaskList = helper.generateTaskList(task4);

    assertCommandBehavior("free 11/10/2016",
        String.format(FreeCommand.MESSAGE_NO_FREE_TIMESLOTS, "Tuesday, 11/10/2016"), expectedTars,
        expectedShownTaskList);

    // Case where the user types in a time should still be allowed to pass. Programme will
    // extract the date
    assertCommandBehavior("free 11/10/2016 0900",
        String.format(FreeCommand.MESSAGE_NO_FREE_TIMESLOTS, "Tuesday, 11/10/2016"), expectedTars,
        expectedShownTaskList);
  }

  @Test
  public void execute_free_freeDayResult() throws Exception {
    TypicalTestDataHelper helper = new TypicalTestDataHelper();
    Tars expectedTars = helper.fillModelAndTarsForFreeCommand(model);

    // Create expected empty list
    List<Task> expectedShownTaskList = helper.generateTaskList();

    assertCommandBehavior("free 01/11/2016",
        String.format(FreeCommand.MESSAGE_FREE_DAY, "Tuesday, 01/11/2016"), expectedTars,
        expectedShownTaskList);
  }

  @Test
  public void execute_free_freeTimeSlotsFound() throws Exception {
    TypicalTestDataHelper helper = new TypicalTestDataHelper();
    Tars expectedTars = helper.fillModelAndTarsForFreeCommand(model);


    // Fill up expected shown task list
    Task taskWithoutStartDate = helper.generateTaskWithNameAndDate("Task without startdate",
        new DateTime("", "29/10/2016 1500"));
    Task task1 = helper.generateTaskWithNameAndDate("Task 1",
        new DateTime("28/10/2016 2200", "29/10/2016 0100"));
    Task task2 = helper.generateTaskWithNameAndDate("Task 2",
        new DateTime("29/10/2016 1430", "29/10/2016 1800"));
    List<Task> expectedShownTaskList = helper.generateTaskList(taskWithoutStartDate,task1, task2);

    StringBuilder sb = new StringBuilder();

    sb.append("Saturday, 29/10/2016").append(": \n").append("1. 0100hrs to 1400hrs (13 hr 0 min)\n")
        .append("2. 1800hrs to 2359hrs (5 hr 59 min)");

    assertCommandBehavior("free 29/10/2016",
        String.format(FreeCommand.MESSAGE_SUCCESS, sb.toString()), expectedTars,
        expectedShownTaskList);
  }
}
