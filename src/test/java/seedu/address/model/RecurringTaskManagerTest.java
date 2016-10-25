package seedu.address.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.RecurringTaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0135782Y
public class RecurringTaskManagerTest {
    private RecurringTaskManager recurringManager;
    private TaskMaster taskMaster;
    
    @Before
    public void setup() {
        recurringManager = RecurringTaskManager.getInstance();
        taskMaster = new TaskMaster();
        recurringManager.setTaskList(taskMaster.getUniqueTaskList());
    }
    
    @Test
    public void set_null_tasklist_throwAssert() {
        try{
            recurringManager.setTaskList(null);
            fail();
        } catch (AssertionError ae) {
            assertTrue(true);
        }
    }
    
    @Test
    public void set_null_taskList_updateRecurringTask_throwAssert() {
        try {
            recurringManager.setTaskList(null);
            recurringManager.updateAnyRecurringTasks();
            fail();
        } catch (AssertionError ae) {
            assertTrue(true);
        }        
    }
    
    @Test
    public void correctAddingOverdueTasks_usingNullTask_throwAssert() {
        try {
            recurringManager.correctAddingOverdueTasks(null);
            fail();
        } catch (AssertionError ae) {
            assertTrue(true);
        }
    }
    
    @Test
    public void correctAssingOverdueTasks_nonRecurringTask_notCorrected() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        TestTask tryCorrect = builder.withName("non recurring").withStartDate("11 oct 11pm")
                .withEndDate("12 oct 11pm").build();
        TestTask expectedTask = builder.withName("non recurring").withStartDate("11 oct 11pm")
                .withEndDate("12 oct 11pm").build();
        recurringManager.correctAddingOverdueTasks(tryCorrect);
        assertEquals("Non recurring tasks should not be corrected", tryCorrect, expectedTask);
    }
    
    // Boundary test 
    // Test lower bounds 
    @Test
    public void correctAssignOverdueTasks_dailyRecurring_corrected_lowerBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.DAILY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("12 oct 2016 11pm")
                .withEndDate("13 oct 2016 11pm").withRecurringType(RecurringType.DAILY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }
    
    @Test
    public void correctAssignOverdueTasks_weeklyRecurring_corrected_lowerBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.WEEKLY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("18 oct 2016 11pm")
                .withEndDate("19 oct 2016 11pm").withRecurringType(RecurringType.WEEKLY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }            
    
    @Test
    public void correctAssignOverdueTasks_monthlyRecurring_corrected_lowerBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("11 nov 2016 11pm")
                .withEndDate("12 nov 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-11-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }        

    @Test
    public void correctAssignOverdueTasks_yearlyRecurring_corrected_lowerBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.YEARLY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("11 oct 2017 11pm")
                .withEndDate("12 oct 2017 11pm").withRecurringType(RecurringType.YEARLY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }
    
    // Boundary test
    // Test upper bounds
    @Test
    public void correctAssignOverdueTasks_dailyRecurring_corrected_upperBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.DAILY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.DAILY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }
    
    @Test
    public void correctAssignOverdueTasks_weeklyRecurring_corrected_upperBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.WEEKLY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.WEEKLY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }

    @Test
    public void correctAssignOverdueTasks_monthlyRecurring_corrected_upperBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }    

    @Test
    public void correctAssignOverdueTasks_yearlyRecurring_corrected_upperBound() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryCorrect = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        builder = new TaskBuilder();
        TestTask expectedTask = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        recurringManager.correctAddingOverdueTasks(tryCorrect, helper.getLocalDateByString("2016-10-09"));
        assertEquals("Recurring tasks should be corrected", tryCorrect, expectedTask);
    }
    
    @Test
    public void updateRecurringTask_daily_noTaskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.DAILY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
    }

    @Test
    public void updateRecurringTask_weekly_noTaskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.WEEKLY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
    }

    @Test
    public void updateRecurringTask_monthly_noTaskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
    }    

    @Test
    public void updateRecurringTask_yearly_noTaskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.YEARLY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-11"));
        assertEquals("Recurring tasks should not append until their date has been elapsed", tryAppend.getTaskDateComponent().size(), 1);
    }
    
    @Test
    public void updateRecurringTask_daily_taskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.DAILY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-12"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);
    }

    @Test
    public void updateRecurringTask_weekly_taskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.WEEKLY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-10-17"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);
    }    

    @Test
    public void updateRecurringTask_monthly_taskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.MONTHLY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2016-11-12"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);
    }        

    @Test
    public void updateRecurringTask_yearly_taskAppended() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        RecurringTaskHelper helper = new RecurringTaskHelper();
        TestTask tryAppend = builder.withName("recurring").withStartDate("11 oct 2016 11pm")
                .withEndDate("12 oct 2016 11pm").withRecurringType(RecurringType.YEARLY).build();
        recurringManager.appendRecurringTasks(tryAppend, helper.getLastAppendedStartDate(tryAppend), 
                helper.getLastAppendedEndDate(tryAppend), helper.getLocalDateByString("2017-10-11"));
        assertEquals("Recurring tasks should be appended when it is time", tryAppend.getTaskDateComponent().size(), 2);
    }    
    
    class RecurringTaskHelper {
        public RecurringTaskHelper() {}
        
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
    }
}
