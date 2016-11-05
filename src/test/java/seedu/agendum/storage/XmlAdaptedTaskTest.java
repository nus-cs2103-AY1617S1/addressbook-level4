package seedu.agendum.storage;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.Name;
import seedu.agendum.model.task.Task;

//@@author A0148095X
public class XmlAdaptedTaskTest {
    
    private Optional<LocalDateTime> optionalStartDateTime;
    private Optional<LocalDateTime> optionalEndDateTime;
    
    private XmlAdaptedTask xmlAdaptedTaskAllFields;
    private XmlAdaptedTask xmlAdaptedTaskUncompleted;
    private XmlAdaptedTask xmlAdaptedTaskNoStartDateTime;
    private XmlAdaptedTask xmlAdaptedTaskNoEndDateTime;
    
    @Before
    public void setUp() throws IllegalValueException {
        LocalDate date = LocalDate.now();
        
        LocalTime startTime = LocalTime.of(12, 0); // 12pm
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        optionalStartDateTime = Optional.of(startDateTime);

        LocalTime endTime = LocalTime.of(13, 0); // 1pm
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
        optionalEndDateTime = Optional.of(endDateTime);
        
        Task taskWithAllFields = new Task(new Name("taskWithStartAndEndDateTimeCompleted"), optionalStartDateTime, optionalEndDateTime);
        taskWithAllFields.markAsCompleted();
        xmlAdaptedTaskAllFields = new XmlAdaptedTask(taskWithAllFields);

        Task taskMarkedAsUncompleted = new Task(new Name("taskWithStartAndEndDateTimeUncompleted"), optionalStartDateTime, optionalEndDateTime);
        xmlAdaptedTaskUncompleted = new XmlAdaptedTask(taskMarkedAsUncompleted);
        
        Task taskWithNoStartDateTime = new Task(new Name("taskWithNoStartDateTime"), Optional.ofNullable(null), optionalEndDateTime);
        taskWithNoStartDateTime.markAsCompleted();
        xmlAdaptedTaskNoStartDateTime = new XmlAdaptedTask(taskWithNoStartDateTime);
        
        Task taskWithNoEndDateTime = new Task(new Name("taskWithNoEndDateTime"), optionalStartDateTime, Optional.ofNullable(null));
        taskWithNoEndDateTime.markAsCompleted();
        xmlAdaptedTaskNoEndDateTime = new XmlAdaptedTask(taskWithNoEndDateTime);
    }
    
    public void assertTaskEqual(Task task, Optional<LocalDateTime> startDateTime, Optional<LocalDateTime> endDateTime, boolean isCompleted) {
        assertTrue(task.getStartDateTime().equals(startDateTime));
        assertTrue(task.getEndDateTime().equals(endDateTime));
        assertTrue(task.isCompleted() == isCompleted);
    }
    
    @Test
    public void toModelType() throws IllegalValueException {
        // Task with start date time, end date time, completed
        Task taskWithAllFields = xmlAdaptedTaskAllFields.toModelType();
        assertTaskEqual(taskWithAllFields, optionalStartDateTime, optionalEndDateTime, true);
        
        // Task with start date time, end date time, not completed
        Task taskMarkedAsUncompleted = xmlAdaptedTaskUncompleted.toModelType();
        assertTaskEqual(taskMarkedAsUncompleted, optionalStartDateTime, optionalEndDateTime, false);
        
        // Task with no start date time, is completed
        Task taskWithNoStartDateTime = xmlAdaptedTaskNoStartDateTime.toModelType();
        assertTaskEqual(taskWithNoStartDateTime, Optional.empty(), optionalEndDateTime, true);
        
        // Task with no end date time, is completed
        Task taskWithNoEndDateTime = xmlAdaptedTaskNoEndDateTime.toModelType();
        assertTaskEqual(taskWithNoEndDateTime, optionalStartDateTime, Optional.empty(), true);
    }
}
