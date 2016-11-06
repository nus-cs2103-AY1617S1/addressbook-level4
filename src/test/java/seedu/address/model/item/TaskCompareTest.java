package seedu.address.model.item;

import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

//@@author A0093960X
public class TaskCompareTest {

    // Tests that involve name and priority
    // Both no dates
    @Test
    public void taskCompareTo_bothNoDatesThisHigherPriority_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), null, null, null, Priority.HIGH);
        Task otherTask = new Task(new Name("a"), null, null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }

    @Test
    public void taskCompareTo_bothNoDatesThisLexiSmallerName_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), null, null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("b"), null, null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    // Both have start date only and equal
    @Test
    public void taskCompareTo_bothHaveStartDateOnlyAndSameButThisHigherPriority_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), new Date(2016, 1, 1), null, null, Priority.HIGH);
        Task otherTask = new Task(new Name("a"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_bothHaveStartDateOnlyAndSameLexiThisLexiSmallerName_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("b"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }      
    
    // Both have end date only and equal
    @Test
    public void taskCompareTo_bothHaveEndDateOnlyAndSameButThisHigherPriority_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), null, new Date(2016, 1, 1), null, Priority.HIGH);
        Task otherTask = new Task(new Name("a"), null, new Date(2016, 1, 1), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_bothHaveEndDateOnlyAndSameLexiThisLexiSmallerName_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), null, new Date(2016, 1, 1), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("b"), null, new Date(2016, 1, 1), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    // Both have start and end date but equal
    @Test
    public void taskCompareTo_bothHaveAllDatesThisHigherPriority_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), new Date(2016, 1, 1), new Date(2016, 1, 1), null, Priority.HIGH);
        Task otherTask = new Task(new Name("a"), new Date(2016, 1, 1), new Date(2016, 1, 1), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_bothHaveAllDatesThisLexiSmallerName_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), new Date(2016, 1, 1), new Date(2016, 1, 1), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("b"), new Date(2016, 1, 1), new Date(2016, 1, 1), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    

    // Tests that involve dates only
    // This task has one date, other task has zero dates
    @Test
    public void taskCompareTo_thisHasStartDateOtherNoDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), null, null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }

    @Test
    public void taskCompareTo_thisHasEndDateOtherNoDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), null, new Date(2016, 1, 1), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), null, null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }

    @Test
    public void taskCompareTo_thisbothDatesOtherNoDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 1), new Date(2016, 1, 2), null,
                Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), null, null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }

    // This task has one date, other task has one date
    @Test
    public void taskCompareTo_thisHasEarlierStartDateOtherStartDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 2), null, null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }

    @Test
    public void taskCompareTo_thisHasEarlierEndDateOtherEndDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), null, new Date(2016, 1, 1), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), null, new Date(2016, 1, 2), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }

    @Test
    public void taskCompareTo_thisHasEarlierStartDateOtherEndDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), null, new Date(2016, 1, 2), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    // This task has one date, other has two dates
    @Test
    public void taskCompareTo_thisHasEarlierStartDateThanOtherTaskBothDates_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 2), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_thisHasEarlierStartDateThanOtherTaskEndButNotStartDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 2), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 1), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisAfterOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_thisHasLaterStartDateThanOtherTaskBothDates_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 4), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 2), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisAfterOther(thisTask.compareTo(otherTask));
    }
    
    // This task has only start date and equal to other both start and end date
    @Test
    public void taskCompareTo_thisStartDateEqualToOtherStartAndEndDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), new Date(2016, 1, 1), null, null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("a"), new Date(2016, 1, 1), new Date(2016, 1, 1), null, Priority.MEDIUM);
        System.out.println(thisTask.compareTo(otherTask));
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    // This task has only end date and equal to other both start and end date
    @Test
    public void taskCompareTo_thisEndDateEqualToOtherStartAndEndDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("a"), null, new Date(2016, 1, 1), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("a"), new Date(2016, 1, 1), new Date(2016, 1, 1), null, Priority.MEDIUM);
        assertThisAfterOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_thisHasEarlierEndDateThanOtherTaskBothDates_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), null, new Date(2016, 1, 1), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 2), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_thisHasEarlierEndDateThanOtherTaskEndButNotStartDate_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), null, new Date(2016, 1, 2), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 1), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisAfterOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_thisHasLaterEndDateThanOtherTaskBothDates_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), null, new Date(2016, 1, 4), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 2), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisAfterOther(thisTask.compareTo(otherTask));
    }
    
    // Both tasks have start and end dates
    @Test
    public void taskCompareTo_thisHasEarlierStartDateThanOtherTask_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 1), new Date(2016, 1, 1), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 2), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    @Test
    public void taskCompareTo_thisHasSameStartDateButEarlierEndDateThanOtherTask_thisBeforeOther() {
        Task thisTask = new Task(new Name("same name"), new Date(2016, 1, 1), new Date(2016, 1, 2), null, Priority.MEDIUM);
        Task otherTask = new Task(new Name("same name"), new Date(2016, 1, 1), new Date(2016, 1, 3), null, Priority.MEDIUM);
        assertThisBeforeOther(thisTask.compareTo(otherTask));
    }
    
    
    /**
     * Asserts that this task is before the other task being compared, assuming
     * that the compareNumber specified is the return value of this Task
     * compareTo the other Task.
     * 
     * @param compareNumber the return value for this.compareTo(other)
     */
    private void assertThisBeforeOther(int compareNumber) {
        assertTrue(compareNumber < 0);
    }

    /**
     * Asserts that this task is after the other task being compared, assuming
     * that the compareNumber specified is the return value of this Task
     * compareTo the other Task.
     * 
     * @param compareNumber the return value for this.compareTo(other)
     */
    private void assertThisAfterOther(int compareNumber) {
        assertTrue(compareNumber > 0);
    }
}
