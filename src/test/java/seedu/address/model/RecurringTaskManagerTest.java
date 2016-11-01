package seedu.address.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.RecurringTaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0135782Y
public class RecurringTaskManagerTest {
    private RecurringTaskManager recurringManager;
    private TaskMaster taskMaster;
    
    @Before
    public void setUp() {
        recurringManager = RecurringTaskManager.getInstance();
        taskMaster = new TaskMaster();
        recurringManager.setTaskList(taskMaster.getUniqueTaskList());
    }
    
    @Test(expected=AssertionError.class)
    public void setNullTasklist_throwAssert() {
        recurringManager.setTaskList(null);
    }
    
    @Test(expected=AssertionError.class)
    public void correctAddingOverdueTasks_usingNullTask_throwAssert() {
        recurringManager.correctAddingOverdueTasks(null);
    }
    
    @Test
    public void updateRecurringTask_successful() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask testData = helper.buildRecurringTask(RecurringType.DAILY);
        Task tryUpdate = new Task(testData);
        taskMaster.addTask(tryUpdate);
        recurringManager.updateAnyRecurringTasks(helper.getLocalDateByString("2016-10-12"));
        
        assertEquals("Recurring task should be updated to append 1 more task occurrence", taskMaster.getTaskComponentList().size(), 2);
        assertEquals("Recurring task should be have unique tasks", taskMaster.getTaskList().size(), 1);
    }
    
    @Test
    public void correctAssingOverdueTasks_nonRecurringTask() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = helper.buildNonRecurringTask();
        TestTask expectedTask = helper.buildNonRecurringTask();

        recurringManager.correctAddingOverdueTasks(tryCorrect);
        assertEquals("Non recurring tasks should not be corrected", tryCorrect, expectedTask);
    }
    
    @Test
    public void correctAssignOverdueTasks_dailyRecurring() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = helper.buildRecurringTask(RecurringType.DAILY);
        TestTask expectedTask = helper.buildRecurringTask(RecurringType.DAILY);
        
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);        
    }
    
    @Test
    public void correctAssignOverdueTasks_weeklyRecurring() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = helper.buildRecurringTask(RecurringType.WEEKLY);
        TestTask expectedTask = helper.buildRecurringTask(RecurringType.WEEKLY);
        
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);        
    }            
    
    @Test
    public void correctAssignOverdueTasks_monthlyRecurring() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = helper.buildRecurringTask(RecurringType.MONTHLY);
        TestTask expectedTask = helper.buildRecurringTask(RecurringType.MONTHLY);

        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-11-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);        
    }        

    @Test
    public void correctAssignOverdueTasks_yearlyRecurring() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = helper.buildRecurringTask(RecurringType.YEARLY);
        TestTask expectedTask = helper.buildRecurringTask(RecurringType.YEARLY);
        
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);        
    }
    
    @Test
    public void updateRecurringTask_daily() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = helper.buildRecurringTask(RecurringType.DAILY);
        
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);        
    }

    @Test
    public void updateRecurringTask_weekly() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = helper.buildRecurringTask(RecurringType.WEEKLY);

        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-17"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);        
    }

    @Test
    public void updateRecurringTask_monthly() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = helper.buildRecurringTask(RecurringType.MONTHLY);

        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-11-12"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);        
    }    

    @Test
    public void updateRecurringTask_yearly() throws Exception {
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = helper.buildRecurringTask(RecurringType.YEARLY);

        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2017-10-11"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);        
    }
        
    class RecurringTaskHelper {
        private TaskBuilder builder;
        public RecurringTaskHelper() {
            builder = new TaskBuilder();
        }
        
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
            return builder.withName("recurring").withStartDate("11 oct 11pm")
                    .withEndDate("12 oct 11pm").withRecurringType(type).build();
        }
        
        public TestTask buildNonRecurringTask() throws IllegalValueException {
            return builder.withName("non recurring").withStartDate("11 oct 11pm")
                    .withEndDate("12 oct 11pm").build();
        }
    }
}
