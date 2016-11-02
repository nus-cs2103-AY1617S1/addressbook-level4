package guitests;

//import static org.junit.Assert.*;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import seedu.address.logic.commands.EditCommand;
//
//import org.junit.Test;
//
//import edu.emory.mathcs.backport.java.util.Arrays;
//import guitests.guihandles.DeadlineTaskCardHandle;
//import guitests.guihandles.EventTaskCardHandle;
//import guitests.guihandles.SomedayTaskCardHandle;
//import seedu.address.model.task.TaskType;
//import seedu.address.testutil.TestTask;
//import seedu.address.testutil.TestUtil;
//import seedu.address.testutil.TypicalTestTasks;

//TODO: Change arguments for postEdit for //Edits the first task in the list to //Edits an in-30-days task from the list
//TODO: Change result message for //Edits task, new startDateTime given, new startDateTime but no endDateTime

public class EditCommandTest extends TaskManagerGuiTest{
//	@Test
//	public void edit() {
//		//Edits the first task in the list
//        TestTask[] currentList = td.getTypicalTasks();
//        
//        int targetIndex = 0; 
//        TestTask preEdit = currentList[targetIndex];
//        TestTask postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        //Edits the last task in the list
//        targetIndex = currentList.length - 1;
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate()); 
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//
//        //Edits from the middle of the list
//        targetIndex = currentList.length/2 - 1; 
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate()); 
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        //Edits a someday task from the list
//        TestTask[] somedayList = td.getSomedayTasks();
//        
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.someday2);
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        targetIndex = Arrays.asList(somedayList).indexOf(TypicalTestTasks.someday2);        
//        preEdit = somedayList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "someday", somedayList);
//        
//        //Edits a deadline task from the list
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadline1);        
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        //Edits an event task from the list
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.event2);        
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        //Edits a today task from the list
//        TestTask[] todayList = td.getTodayTasks();
//        
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.eventToday);
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        targetIndex = Arrays.asList(todayList).indexOf(TypicalTestTasks.eventToday);        
//        preEdit = todayList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "today", todayList);
//        
//        //Edits a tomorrow task from the list
//        TestTask[] tomorrowList = td.getTomorrowTasks();
//        
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadlineTomorrow);
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        targetIndex = Arrays.asList(tomorrowList).indexOf(TypicalTestTasks.deadlineTomorrow);        
//        preEdit = tomorrowList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "tomorrow", tomorrowList);
//        
//        //Edits an in-7-days task from the list
//        TestTask[] in7DaysList = td.getIn7DaysTasks();
//        
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadlineIn7Days);
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        targetIndex = Arrays.asList(in7DaysList).indexOf(TypicalTestTasks.deadlineIn7Days);        
//        preEdit = in7DaysList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "in 7 days", in7DaysList);
//        
//        //Edits an in-30-days task from the list
//        TestTask[] in30DaysList = td.getIn30DaysTasks();
//        
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadlineIn30Days);
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "typical", currentList);
//        
//        targetIndex = Arrays.asList(in30DaysList).indexOf(TypicalTestTasks.deadlineIn30Days);        
//        preEdit = in30DaysList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getEndDate());
//        assertEditSuccess(targetIndex, postEdit, "in 30 days", in30DaysList);
//        
//        //Edits a task, startDateTime and new endDateTime given, new startDateTime after new endDateTime 
//        targetIndex = 0; 
//        commandBox.runCommand(TypicalTestTasks.eventStartDateTimeAfterEndDateTime.getEditCommand(targetIndex + 1)); //+1 to correspond to 1-indexed list display
//        assertResultMessage(EditCommand.MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME);
//        
//        //Edits task, startDateTime and new endDateTime given, new startDateTime equals new endDateTime
//        targetIndex = 0; 
//        commandBox.runCommand(TypicalTestTasks.eventStartDateTimeEqualsEndDateTime.getEditCommand(targetIndex + 1)); //+1 to correspond to 1-indexed list display
//        assertResultMessage(EditCommand.MESSAGE_START_DATE_TIME_EQUALS_END_DATE_TIME);
//       
//        //Edits task, new startDateTime given, new startDateTime after existing endDateTime
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadlineToday); //Task has to be either deadline/ event task to have existing endDateTime
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), Optional.of(preEdit.getEndDate().get().plusDays(2)), preEdit.getEndDate());
//        assertResultMessage(EditCommand.MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME);
//        
//        //Edits task, new startDateTime given, new startDateTime equals existing endDateTime
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadlineToday); //Task has to be either deadline/ event task to have existing endDateTime
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getEndDate(), preEdit.getEndDate());
//        assertResultMessage(EditCommand.MESSAGE_START_DATE_TIME_EQUALS_END_DATE_TIME);
//        
//        //Edits task, new endDateTime given, new endDateTime before existing startDateTime 
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadlineToday); //Task has to be event task to have existing startDateTime
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), Optional.of(preEdit.getStartDate().get().minusDays(2)));
//        assertResultMessage(EditCommand.MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME);
//        
//        //Edits task, new endDateTime given, new endDateTime equals existing startDateTime
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.deadlineToday); //Task has to be event task to have existing startDateTime
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), preEdit.getStartDate(), preEdit.getStartDate());
//        assertResultMessage(EditCommand.MESSAGE_START_DATE_TIME_EQUALS_END_DATE_TIME);
//        
//        //Edits task, new startDateTime given, new startDateTime but no endDateTime
//        targetIndex = Arrays.asList(currentList).indexOf(TypicalTestTasks.someday3); //Task has to be someday task to have no existing endDateTime
//        preEdit = currentList[targetIndex];
//        postEdit = preEdit.convertoToPostEditTestTask(preEdit.getTaskType(), preEdit.getName(), Optional.of(LocalDateTime.of(2016, 12, 1, 12, 40)), preEdit.getEndDate());
//        assertResultMessage(EditCommand.MESSAGE_START_DATE_TIME_EQUALS_END_DATE_TIME); 
//        
//        //Invalid index
//        commandBox.runCommand("edit " + currentList.length + 1);
//        assertResultMessage("The task index provided is invalid");
//	}
//	
//    /**
//     * Runs the edit command to edit the task at specified index and confirms the result is correct.
//     * @param targetIndexOneIndexed e.g. to edit the first task in the list, 1 should be given as the target index.
//     * @param currentList A copy of the current list of tasks (before editing).
//     */
//    private void assertEditSuccess(int targetIndex, TestTask postEdit, String listType, TestTask[] list) {
//        TestTask taskToEdit = list[targetIndex];
//
//        commandBox.runCommand(postEdit.getEditCommand(targetIndex + 1)); //+1 to correspond to 1-indexed list display
//
//        //Confirms that the new task card contains the right data
//        if (postEdit.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
//        	SomedayTaskCardHandle EditedCard = taskListPanel.navigateToSomedayTask(postEdit.getName().value);
//        	assertSomedayTaskMatching(postEdit, EditedCard);
//        } else if (postEdit.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
//        	DeadlineTaskCardHandle EditedCard = taskListPanel.navigateToDeadlineTask(postEdit.getName().value);
//        	assertDeadlineTaskMatching(postEdit, EditedCard);
//        } else if (postEdit.getTaskType().value.equals(TaskType.Type.EVENT)) {
//        	EventTaskCardHandle EditedCard = taskListPanel.navigateToEventTask(postEdit.getName().value);
//        	assertEventTaskMatching(postEdit, EditedCard);
//        } 
//
//        //Confirms that the list now contains all previous tasks and the new task
//        TestTask[] expectedList = TestUtil.replaceTaskFromList(list, postEdit, targetIndex);
//        System.out.println("expectedList[0]: " + expectedList[0]);
//        switch (listType) {
//        case "typical":
//        	assertTrue(taskListPanel.isListMatching(expectedList));
//        case "today":
//            assertTrue(todayTaskListTabPanel.isListMatching(expectedList));
//        case "tomorrow":
//            assertTrue(tomorrowTaskListTabPanel.isListMatching(expectedList));
//        case "in 7 days":
//            assertTrue(in7DaysTaskListTabPanel.isListMatching(expectedList));
//        case "in 30 days":
//            assertTrue(in30DaysTaskListTabPanel.isListMatching(expectedList));
//        case "someday":
//            assertTrue(somedayTaskListTabPanel.isListMatching(expectedList));
//        default:
//        }
//        
//        //Confirms that the result message is correct
//        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
//    }
}
