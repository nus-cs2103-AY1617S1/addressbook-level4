package seedu.todo.logic.commands;


import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import seedu.todo.model.property.TaskViewFilter;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.testutil.TaskBuilder;

import static org.junit.Assert.*;

//@@author A0092382A
public class FindCommandTest extends CommandTest {
    
    @Override
    protected BaseCommand commandUnderTest() {
        return new FindCommand();
    }
    
    @Before
    public void setUp() throws Exception {
        todolist.setTasks(ImmutableList.of(
            TaskBuilder.name("CS2101 Project Task")
                .tagged("Alpha", "Beta")
                .build(),
            TaskBuilder.name("CS2103T project")
                .tagged("Beta")
                .build(),
            TaskBuilder.name("Unrelated task")
                .tagged("Gamma")
                .build(),
            TaskBuilder.name("Unrelated CS2101 that expands").build()
        ));
    }
    
    @Test
    public void testFindSuccessful() throws ValidationException {
        assertNull(model.getSearchStatus().getValue());
        assertVisibleTaskCount(4);
        setParameter("CS2101");
        execute(true);
        assertVisibleTaskCount(2);
        assertNotNull(model.getSearchStatus().getValue());
    }
    
    @Test
    public void testCaseInsensitive() throws ValidationException {
        setParameter("project");
        execute(true);
        assertVisibleTaskCount(2);
    }
    
    @Test
    public void testMultipleParameters() throws ValidationException {
        setParameter("task expands");
        execute(true);
        assertVisibleTaskCount(3);
    }
    
    @Test
    public void testUnsuccessfulFind() throws ValidationException {
        setParameter("team");
        execute(true);
        assertVisibleTaskCount(0);
    }
    
    //@@author A0135817B
    @Test
    public void testFindWithFilter() throws ValidationException {
        TaskViewFilter filter = new TaskViewFilter("test", t -> t.getTitle().contains("CS2101"), null);
        model.view(filter);
        
        setParameter("Task");
        execute(true);
        assertVisibleTaskCount(1);
    }
    
    @Test
    public void testFindTag() throws ValidationException {
        setParameter("t", "Gamma");
        execute(true);
        assertVisibleTaskCount(1);
    }

    @Test
    public void testFindMultipleTag() throws ValidationException {
        setParameter("t", "Gamma, Beta");
        execute(true);
        assertVisibleTaskCount(3);
    }

    @Test
    public void testPartialFindTag() throws ValidationException {
        setParameter("t", "Ga");
        execute(true);
        assertVisibleTaskCount(0);
    }

    @Test
    public void testKeywordAndTag() throws ValidationException {
        setParameter("t", "Gamma");
        setParameter("CS2101");
        execute(true);
        assertVisibleTaskCount(3);
    }

    @Test
    public void testDismissFind() throws ValidationException {
        setParameter("project");
        execute(true);
        assertVisibleTaskCount(2);
        assertNotNull(model.getSearchStatus().getValue());

        setParameter("");
        execute(true);
        assertVisibleTaskCount(4);
        assertNull(model.getSearchStatus().getValue());
    }
}
