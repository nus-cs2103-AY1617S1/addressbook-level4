package seedu.address.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.RecurringTaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0135782Y
public class RecurringTaskManagerTest {
    private RecurringTaskManager recurringManager;
    private RecurringTaskHelper helper;
    private TaskMaster taskMaster;
    private Model model;
    
    @Before
    public void setUp() {
        recurringManager = RecurringTaskManager.getInstance();
        helper = new RecurringTaskHelper();
        model = new ModelManager();
        taskMaster = (TaskMaster) model.getTaskMaster();
        recurringManager.setTaskList(taskMaster.getUniqueTaskList());
    }
    
    @Test(expected = AssertionError.class)
    public void setTasklist_nullTaskList_throwAssert() {
        recurringManager.setTaskList(null);
    }
    
    @Test(expected = AssertionError.class)
    public void correctAddingOverdueTasks_nullTaskList_throwAssert() {
        recurringManager.correctOverdueNonRepeatingTasks(null);
    }
    
//    @Test(expected = AssertionError.class)
//    public void appendAnyRecurringTasks_nullTaskList_throwAssert() {
//        recurringManager.appendAnyRecurringTasks();
//    }
    
    @Test
    public void updateRecurringTask_successful() throws Exception {
        TestTask testData = helper.buildRecurringTask(RecurringType.DAILY);
        Task tryUpdate = new Task(testData);
        taskMaster.addTask(tryUpdate);
        recurringManager.appendAnyRecurringTasks(helper.getLocalDateByString("2016-10-12"));
        
        assertEquals("Recurring task should be updated to append 1 more task occurrence", taskMaster.getTaskComponentList().size(), 2);
        assertEquals("Recurring task should be have unique tasks", taskMaster.getTaskList().size(), 1);
    }
    
    @Test
    public void correctAssingOverdueTasks_nonRecurringTask() throws Exception {
        TestTask tryCorrect = helper.buildNonRecurringTask_withStartDate();
        TestTask expectedTask = helper.buildNonRecurringTask_withStartDate();
        recurringManager.setTestMode(false);
        recurringManager.correctOverdueNonRepeatingTasks(tryCorrect);
        assertEquals("Non recurring tasks should not be corrected", tryCorrect, expectedTask);
    }
    
    @Test
    public void correctAssignOverdueTasks_recurringTask() throws Exception {
        recurringManager.setTestMode(false);
        correctAssignOverdueTasks(RecurringType.DAILY);
        correctAssignOverdueTasks(RecurringType.WEEKLY);
        correctAssignOverdueTasks(RecurringType.MONTHLY);
        correctAssignOverdueTasks(RecurringType.YEARLY);
    }
    
    private void correctAssignOverdueTasks(RecurringType type) throws Exception {
        TestTask tryCorrect = helper.buildRecurringTask(type);
        TestTask expectedTask = helper.buildRecurringTask(type);
        assert_correctAssignOverdueTasks(tryCorrect, expectedTask, type);      
    }
    
    private void assert_correctAssignOverdueTasks(TestTask tryCorrect, TestTask expectedTask, RecurringType type)
            throws IllegalValueException {
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-12"));
        assertThat("Recurring tasks should be corrected", 
                helper.getLastAppendedOccurrence(tryCorrect), is(not(helper.getLastAppendedOccurrence(expectedTask))));
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2017-11-12"));
        assertThat("Recurring tasks should be corrected",
                helper.getLastAppendedOccurrence(tryCorrect), is(not(helper.getLastAppendedOccurrence(expectedTask))));
        tryCorrect = helper.buildRecurringTask(type);
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected",
                helper.getLastAppendedOccurrence(tryCorrect), helper.getLastAppendedOccurrence(expectedTask));
    }
    
    @Test
    public void updateRecurringTask_noRecurringPeriod() throws Exception {
        recurringManager.setTestMode(true);
        updateRecurringTask(RecurringType.DAILY, "2016-10-12");
        updateRecurringTask(RecurringType.WEEKLY, "2016-10-17");
        updateRecurringTask(RecurringType.MONTHLY, "2016-11-12");
        updateRecurringTask(RecurringType.YEARLY, "2017-10-11");
    }
    
    public void updateRecurringTask(RecurringType type, String dateToAppendTask) throws Exception {
        TestTask tryAppend = helper.buildRecurringTask(type);
        assert_updateRecurringTask(tryAppend, dateToAppendTask);        
    }
    
    private void assert_updateRecurringTask(TestTask tryAppend, String dateToAppendTask) {
        recurringManager.attemptAppendRecurringTasks(tryAppend, 
                helper.getLastAppendedStartDate(tryAppend), helper.getLastAppendedEndDate(tryAppend), 
                helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
        recurringManager.attemptAppendRecurringTasks(tryAppend, 
                helper.getLastAppendedStartDate(tryAppend), helper.getLastAppendedEndDate(tryAppend), 
                helper.getLocalDateByString(dateToAppendTask));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);                
    }    
    
    @Test
    public void updateRecurringTasks_withoutRepeatLimit() throws Exception {
        recurringManager.setTestMode(true);
        updateRecurringTask(RecurringType.DAILY, "12 oct 2016 11pm", "13 oct 2016 11pm");
        updateRecurringTask(RecurringType.WEEKLY, "18 oct 2016 11pm", "19 oct 2016 11pm");
        updateRecurringTask(RecurringType.MONTHLY, "11 nov 2016 11pm", "12 nov 2016 11pm");
        updateRecurringTask(RecurringType.YEARLY, "11 oct 2017 11pm", "12 oct 2017 11pm");
    }
    
    private void updateRecurringTask(RecurringType type, String nextStartDate, String nextEndDate) throws Exception {
        TestTask testData = helper.buildRecurringTask(type);
        Task tryUpdate = new Task(testData);
        execute_updatingOfRecurringTask(tryUpdate);
        TaskOccurrence nextDayTaskOccurrence = helper.buildTaskOccurrenceFromTask(tryUpdate, nextStartDate, nextEndDate);
        assert_updateRecurringTasks(tryUpdate, nextDayTaskOccurrence, 2);
    }

    private void execute_updatingOfRecurringTask(Task tryUpdate)
            throws Exception {
        model.addTask(tryUpdate);
        model.archiveTask(helper.getLastAppendedOccurrence(tryUpdate));
    }
    
    private void assert_updateRecurringTasks(Task tryUpdate, TaskOccurrence nextDayTaskOccurrence, final int numOfOccurrences) {
        assertEquals("The following daily task should have been created", tryUpdate.getTaskDateComponent().size(), numOfOccurrences);
        assertEquals("Daily task should match in task occurrence", tryUpdate.getLastAppendedComponent(), nextDayTaskOccurrence);
    }
    
    @Test
    public void updateRecurringTask_withRecurringPeriod() throws Exception {
        final int recurringPeriod = 3;
        updateRecurringTask_withRecurringPeriod(RecurringType.DAILY, recurringPeriod, null, "14 oct 2016 11pm");
        updateRecurringTask_withRecurringPeriod(RecurringType.WEEKLY, recurringPeriod, null, "26 oct 2016 11pm");
        updateRecurringTask_withRecurringPeriod(RecurringType.MONTHLY, recurringPeriod, null, "12 dec 2016 11pm");
        updateRecurringTask_withRecurringPeriod(RecurringType.YEARLY, recurringPeriod, null, "12 oct 2018 11pm");
    }


    private void updateRecurringTask_withRecurringPeriod(RecurringType type, int recurringPeriod,
            String nextStartDate, String nextEndDate) throws Exception {
        TestTask testData = helper.buildRecurringTask_withoutStartDate(type, recurringPeriod);
        Task tryUpdate = new Task(testData);
        execute_updatingOfRecurringTask(tryUpdate);
        TaskOccurrence nextDayTaskOccurrence = helper.buildTaskOccurrenceFromTask(tryUpdate, nextStartDate, nextEndDate);
        assert_updateRecurringTasks(tryUpdate, nextDayTaskOccurrence, recurringPeriod);
    }


    class RecurringTaskHelper {
        private static final int RECURRING_PERIOD_OFFSET = 1;
        private TaskBuilder builder;
        
        public LocalDate getLocalDateByString(String dateToConsider) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale(Locale.getDefault());  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
            LocalDate date = LocalDate.parse(dateToConsider, formatter);
            return date;
        }
        
        public Calendar getLastAppendedStartDate(ReadOnlyTask task) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(task.getLastAppendedComponent().getStartDate().getDate());
            return cal;
        }
        
        public Calendar getLastAppendedEndDate(ReadOnlyTask task) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(task.getLastAppendedComponent().getEndDate().getDate());
            return cal;
        }
        
        public TestTask buildRecurringTask(RecurringType type) throws IllegalValueException {
            return buildRecurringTask(type, 0);
        }
        
        public TestTask buildRecurringTask(RecurringType type, int recurringPeriod) throws IllegalValueException {
            builder = new TaskBuilder();
            return builder.withName("recurring").withStartDate("11 oct 2016 11pm").withEndDate("12 oct 2016 11pm")
                    .withRecurringType(type).withRecurringPeriod(recurringPeriod - RECURRING_PERIOD_OFFSET).build();
        }
        
        public TestTask buildNonRecurringTask_withStartDate() throws IllegalValueException {
            builder = new TaskBuilder();
            return builder.withName("non recurring").withStartDate("11 oct 2016 11pm")
                    .withEndDate("12 oct 2016 11pm").build();
        }
        
        public TestTask buildRecurringTask_withoutStartDate(RecurringType type, int recurringPeriod) throws IllegalValueException {
            builder = new TaskBuilder();
            return builder.withName("recurring").withEndDate("12 oct 2016 11pm")
                    .withRecurringType(type).withRecurringPeriod(recurringPeriod - 1).build();
        }                
        
        public TaskOccurrence getLastAppendedOccurrence(ReadOnlyTask task) {
            int listLen = task.getTaskDateComponent().size();
            TaskOccurrence toReturn = task.getTaskDateComponent().get(listLen-1);
            toReturn.setTaskReferrence((Task) task);
            return toReturn;
        }
        
        public TaskOccurrence buildTaskOccurrenceFromTask(ReadOnlyTask task, String startDate, String endDate) {
            TaskOccurrence toBuild = new TaskOccurrence(task.getLastAppendedComponent());
            toBuild = changeStartDate(toBuild, startDate);
            toBuild = changeEndDate(toBuild, endDate);
            return toBuild;
        }
        
        public TaskOccurrence changeStartDate(TaskOccurrence occurrence, String startDate) {
            TaskOccurrence toChange = new TaskOccurrence(occurrence);
            if (startDate == null) {
                toChange.setStartDate(new TaskDate(TaskDate.DATE_NOT_PRESENT));
                return toChange;
            }
            toChange.setStartDate(new TaskDate(startDate));
            return toChange;
        }
        
        public TaskOccurrence changeEndDate(TaskOccurrence occurrence, String endDate) {
            TaskOccurrence toChange = new TaskOccurrence(occurrence);
            toChange.setEndDate(new TaskDate(endDate));
            return toChange;
        }
    }
}
