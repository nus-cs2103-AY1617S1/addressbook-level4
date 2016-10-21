package seedu.taskitty.model;

import javafx.collections.FXCollections;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.exceptions.DataConversionException;
import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static seedu.taskitty.testutil.TestUtil.assertThrows;

public class TaskTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void invalidDateFormat_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskDate date = new TaskDate("25-12-2016");
    }
    
    @Test
    public void invalidDate_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskDate date = new TaskDate("29/2/2015");
    }
    
    @Test
    public void invalidTimeFormat_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskTime date = new TaskTime("3pm");
    }
    
    @Test
    public void invalidTime_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskDate date = new TaskDate("25:00");
    }
}
