package seedu.address.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.RecurringTaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0135782Y
public class RecurringTaskManagerTest {
    private static final int UPDATE_OCCURRENCE_SIZE = 2;
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
    
    @Test
    public void setTaskList_successful() {
        recurringManager.setTaskList(taskMaster.getUniqueTaskList());
        UniqueTaskList toCompare = recurringManager.getTaskList();
        assertEquals(toCompare, taskMaster.getUniqueTaskList());
    }    
    
    @Test(expected = AssertionError.class)
    public void correctAddingOverdueTasks_nullTaskList_throwAssert() {
        recurringManager.correctOverdueNonRepeatingTasks(null);
    }
    
    @Test
    public void correctAssingOverdueTasks_nonRecurringTask() throws Exception {
        TestTask tryCorrect = helper.buildNonRecurringTaskWithStartDate();
        TestTask expectedTask = helper.buildNonRecurringTaskWithStartDate();
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
    
    @Test
    public void updateRecurringTask_noRecurringPeriod() throws Exception {
        recurringManager.setTestMode(true);
        // add XXXX by XXXX
        updateRecurringTask(RecurringType.DAILY, null, "13 oct 2016 11pm");
        updateRecurringTask(RecurringType.WEEKLY, null, "19 oct 2016 11pm");
        updateRecurringTask(RecurringType.MONTHLY, null, "12 nov 2016 11pm");
        updateRecurringTask(RecurringType.YEARLY, null, "12 oct 2017 11pm");

        // add XXXX from XXXX to XXXX
        updateRecurringTask(RecurringType.DAILY, "12 oct 2016 11pm", "13 oct 2016 11pm");
        updateRecurringTask(RecurringType.WEEKLY, "18 oct 2016 11pm", "19 oct 2016 11pm");
        updateRecurringTask(RecurringType.MONTHLY, "11 nov 2016 11pm", "12 nov 2016 11pm");
        updateRecurringTask(RecurringType.YEARLY, "11 oct 2017 11pm", "12 oct 2017 11pm");
    }
    
    @Test
    public void updateRecurringTask_withRecurringPeriod() throws Exception {
        final int recurringPeriod = 3;
        updateRecurringTaskWithRecurringPeriod(RecurringType.DAILY, recurringPeriod, null, "14 oct 2016 11pm");
        updateRecurringTaskWithRecurringPeriod(RecurringType.WEEKLY, recurringPeriod, null, "26 oct 2016 11pm");
        updateRecurringTaskWithRecurringPeriod(RecurringType.MONTHLY, recurringPeriod, null, "12 dec 2016 11pm");
        updateRecurringTaskWithRecurringPeriod(RecurringType.YEARLY, recurringPeriod, null, "12 oct 2018 11pm");
    }

    /**
     * Helps to test for correcting of overdue tasks based on recurring type
     */
    private void correctAssignOverdueTasks(RecurringType type) throws Exception {
        TestTask tryCorrect = helper.buildRecurringTask(type);
        TestTask expectedTask = helper.buildRecurringTask(type);
        assertCorrectAssignOverdueTasks(tryCorrect, expectedTask, type);      
    }

    /**
     * Helps to assert using boundary test for the task.
     */    
    private void assertCorrectAssignOverdueTasks(TestTask tryCorrect, TestTask expectedTask, RecurringType type)
            throws IllegalValueException {
        correctAssignOverDueTasks(tryCorrect, expectedTask, "2016-10-12");
        assertThat("Recurring tasks should be corrected", helper.getLastAppendedOccurrence(tryCorrect), 
                is(not(helper.getLastAppendedOccurrence(expectedTask))));
        correctAssignOverDueTasks(tryCorrect, expectedTask, "2017-11-12");
        assertThat("Recurring tasks should be corrected", helper.getLastAppendedOccurrence(tryCorrect), 
                   is(not(helper.getLastAppendedOccurrence(expectedTask))));
        
        tryCorrect = helper.buildRecurringTask(type);
        correctAssignOverDueTasks(tryCorrect, expectedTask, "2016-10-09");
        assertEquals("Recurring tasks should be corrected", helper.getLastAppendedOccurrence(tryCorrect),
                     helper.getLastAppendedOccurrence(expectedTask));
    }

    /**
     * Helps to keep the method call shorter.
     */
    private void correctAssignOverDueTasks(TestTask tryCorrect, TestTask expectedTask, String currentDate) {
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString(currentDate));
    }

    /**
     * Helps to test if task has been updated based on recurring type and the date
     * Start date of the task is present
     * 
     * @param type Recurring type of the task
     * @param dateToAppendTask The date which RecurringTaskManager will use to decide if task occurrence should be appended
     */    
    private void updateRecurringTask(RecurringType type, String nextStartDate, String nextEndDate) throws Exception {
        TestTask testData;
        if (nextStartDate == null) {
            testData = helper.buildRecurringTaskWithoutStartDate(type);
        } else {
            testData = helper.buildRecurringTask(type);
        }
        Task tryUpdate = new Task(testData);
        executeAddAndArchive(tryUpdate);
        TaskOccurrence nextDayTaskOccurrence = helper.buildTaskOccurrenceFromTask(tryUpdate, nextStartDate, 
                                                                                  nextEndDate);
        assertUpdateRecurringTasks(tryUpdate, nextDayTaskOccurrence, UPDATE_OCCURRENCE_SIZE);
    }    

    /**
     * Helps to add task and immediately archive it.
     */
    private void executeAddAndArchive(Task tryUpdate)
            throws Exception {
        model.addTask(tryUpdate);
        model.archiveTask(helper.getLastAppendedOccurrence(tryUpdate));
    }
    
    /**
     * Helps to test if the task has been updated properly.
     * 
     * @param tryUpdate The task we are trying to update
     * @param nextTaskOccurrence The task occurrence that will be in the next time slot
     * @param numOfOccurrences The number of occurrence expected
     */
    private void assertUpdateRecurringTasks(Task tryUpdate, TaskOccurrence nextTaskOccurrence, final int numOfOccurrences) {
        assertEquals("The following daily task should have been created", tryUpdate.getTaskDateComponent().size(), numOfOccurrences);
        assertEquals("Daily task should match in task occurrence", tryUpdate.getLastAppendedComponent(), nextTaskOccurrence);
    }    
    
    /**
     * Helps to test for updating of Tasks with recurring period
     */
    private void updateRecurringTaskWithRecurringPeriod(RecurringType type, int recurringPeriod,
            String nextStartDate, String nextEndDate) throws Exception {
        TestTask testData = helper.buildRecurringTaskWithoutStartDate(type, recurringPeriod);
        Task tryUpdate = new Task(testData);
        executeAddAndArchive(tryUpdate);
        TaskOccurrence nextDayTaskOccurrence = helper.buildTaskOccurrenceFromTask(tryUpdate, nextStartDate, nextEndDate);
        assertUpdateRecurringTasks(tryUpdate, nextDayTaskOccurrence, recurringPeriod);
    }    

    /**
     * A utility class to create recurring tasks
     */
    class RecurringTaskHelper {
        private static final int INDEX_OFFSET = 1;
        private static final int NO_RECURRING_PERIOD = 0;
        private static final int RECURRING_PERIOD_OFFSET = 1;
        private TaskBuilder builder;
        
        /**
         * Converts a date in string to a LocalDate object
         */
        public LocalDate getLocalDateByString(String dateToConsider) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale(Locale.getDefault());  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
            LocalDate date = LocalDate.parse(dateToConsider, formatter);
            return date;
        }
        
        /**
         * Returns the start date of last appended occurrence
         */
        public Calendar getLastAppendedStartDate(ReadOnlyTask task) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(task.getLastAppendedComponent().getStartDate().getDate());
            return cal;
        }

        /**
         * Returns the end date of last appended occurrence
         */
        public Calendar getLastAppendedEndDate(ReadOnlyTask task) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(task.getLastAppendedComponent().getEndDate().getDate());
            return cal;
        }
        
        /**
         * Builds a recurring task without any recurring period
         */
        public TestTask buildRecurringTask(RecurringType type) throws IllegalValueException {
            return buildRecurringTask(type, NO_RECURRING_PERIOD);
        }
        
        /**
         * Builds a recurring task with recurring period
         */
        public TestTask buildRecurringTask(RecurringType type, int recurringPeriod) throws IllegalValueException {
            builder = new TaskBuilder();
            return builder.withName("recurring").withStartDate("11 oct 2016 11pm").withEndDate("12 oct 2016 11pm")
                    .withRecurringType(type).withRecurringPeriod(recurringPeriod - RECURRING_PERIOD_OFFSET).build();
        }
        
        /**
         * Builds a recurring task with start date stated
         */
        public TestTask buildNonRecurringTaskWithStartDate() throws IllegalValueException {
            builder = new TaskBuilder();
            return builder.withName("non recurring").withStartDate("11 oct 2016 11pm")
                    .withEndDate("12 oct 2016 11pm").build();
        }

        /**
         * Builds a recurring task without start date
         */
        public TestTask buildRecurringTaskWithoutStartDate(RecurringType type) throws IllegalValueException {
            return buildRecurringTaskWithoutStartDate(type, NO_RECURRING_PERIOD);
        }        
        
        /**
         * Builds a recurring task without start date
         */
        public TestTask buildRecurringTaskWithoutStartDate(RecurringType type, int recurringPeriod) throws IllegalValueException {
            builder = new TaskBuilder();
            return builder.withName("recurring").withEndDate("12 oct 2016 11pm")
                    .withRecurringType(type).withRecurringPeriod(recurringPeriod - RECURRING_PERIOD_OFFSET).build();
        }                
        
        /**
         * Returns the last appended TaskOccurence using ReadOnlyTask
         */
        public TaskOccurrence getLastAppendedOccurrence(ReadOnlyTask task) {
            int listLen = task.getTaskDateComponent().size();
            TaskOccurrence toReturn = task.getTaskDateComponent().get(listLen - INDEX_OFFSET);
            toReturn.setTaskReferrence((Task) task);
            return toReturn;
        }
        
        /**
         * Builds TaskOccurrence from a ReadOnlyTask.
         * Start date and end date must be specified.
         */
        public TaskOccurrence buildTaskOccurrenceFromTask(ReadOnlyTask task, String startDate, String endDate) {
            TaskOccurrence toBuild = new TaskOccurrence(task.getLastAppendedComponent());
            toBuild = changeStartDate(toBuild, startDate);
            toBuild = changeEndDate(toBuild, endDate);
            return toBuild;
        }
        
        /**
         * Changes the start date of the task occurrence
         */
        public TaskOccurrence changeStartDate(TaskOccurrence occurrence, String startDate) {
            TaskOccurrence toChange = new TaskOccurrence(occurrence);
            if (startDate == null) {
                toChange.setStartDate(new TaskDate(TaskDate.DATE_NOT_PRESENT));
                return toChange;
            }
            toChange.setStartDate(new TaskDate(startDate));
            return toChange;
        }
        
        /**
         * Changes the end date of the task occurrence
         */
        public TaskOccurrence changeEndDate(TaskOccurrence occurrence, String endDate) {
            TaskOccurrence toChange = new TaskOccurrence(occurrence);
            toChange.setEndDate(new TaskDate(endDate));
            return toChange;
        }
    }
}
