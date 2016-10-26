package seedu.todo.model.task;

import seedu.todo.testutil.TaskFactory;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

//@@author A0135817B
/**
 * This set of tests check that every implementation of ImmutableTask are equal and 
 * generates the same hashCode as each other when their copy constructor is used. 
 */
public class ImmutableTaskTest {
    private ImmutableTask reference = TaskFactory.fullEvent();
    
    private List<ImmutableTask> tasks = ImmutableList.of(
        new ValidationTask(reference), 
        new Task(reference)
    );
    
    private void permuteTasks(Function<ImmutableTask, Object> mapper) {
        Object referenceProperty = mapper.apply(reference);
        
        for (int i = 0; i < tasks.size(); i++) {
            ImmutableTask subject = tasks.get(i);
            Object subjectProperty = mapper.apply(subject);
            
            assertEquals(subjectProperty, referenceProperty);
            assertEquals(referenceProperty, subjectProperty);

            for (int j = i; j < tasks.size(); j++) {
                assertEquals(subjectProperty, mapper.apply(tasks.get(i)));
                assertEquals(mapper.apply(tasks.get(i)), subjectProperty);
            }
        }
    }
    
    @Test
    public void testEquality() {
        permuteTasks(task -> task);
    }

    @Test
    public void testHashing() {
        permuteTasks(Object::hashCode);
    }
    
    @Test
    public void testProperties() {
        permuteTasks(ImmutableTask::getTitle);
        permuteTasks(ImmutableTask::getTags);
        permuteTasks(ImmutableTask::getDescription);
        permuteTasks(ImmutableTask::getStartTime);
        permuteTasks(ImmutableTask::getEndTime);
        permuteTasks(ImmutableTask::getLocation);
        permuteTasks(ImmutableTask::isCompleted);
        permuteTasks(ImmutableTask::isEvent);
        permuteTasks(ImmutableTask::isPinned);
        permuteTasks(ImmutableTask::getCreatedAt);
    }
}
