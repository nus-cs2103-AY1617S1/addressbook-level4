package seedu.todo.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.Task;

/**
 * Builds a task for testing purposes.
 */
public class TestTaskBuilder {

    private Task task;

    public TestTaskBuilder(String title) {
        task = new Task(title);
    }

    public TestTaskBuilder withDescription(String description) {
        task.setDescription(description);
        return this;
    }
    
    public TestTaskBuilder withLocation(String location) {
        task.setLocation(location);
        return this;
    }

    public TestTaskBuilder withLastUpdated(LocalDateTime lastUpdated) throws IllegalValueException {
        task.setLastUpdated(lastUpdated);
        return this;
    }

    public TestTaskBuilder withCompleted() {
        task.setCompleted(true);
        return this;
    }

    public TestTaskBuilder withPinned() {
        task.setPinned(true);
        return this;
    }

    public TestTaskBuilder withTime() {
        return withTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1l));
    }

    public TestTaskBuilder withTime(LocalDateTime start, LocalDateTime end) {
        task.setStartTime(start);
        task.setEndTime(end);
        return this;
    }

    public TestTaskBuilder withTags(String ... tags) throws IllegalValueException {
        Set<Tag> setOfTags = new HashSet<>();
        for (String tag: tags) {
            setOfTags.add(new Tag(tag));
        }
        task.setTags(setOfTags);
        return this;
    }
    
    public Task build() {
        return task;
    }

}
