package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.DateRange;
import seedu.address.model.todo.DueDate;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.ToDo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Helps build a to-do in 1 line
 */
public class ToDoBuilder {
    private ToDo toDo;

    public ToDoBuilder(String title) throws IllegalValueException {
        toDo = new ToDo(new Title(title));
    }

    public ToDoBuilder withDateRange(LocalDateTime startDate, LocalDateTime endDate)
        throws IllegalValueException {
        toDo.setDateRange(new DateRange(
            startDate, endDate
        ));

        return this;
    }

    public ToDoBuilder withDueDate(LocalDateTime dueDate) throws IllegalValueException {
        toDo.setDueDate(
            new DueDate(dueDate)
        );

        return this;
    }

    public ToDoBuilder withTags(String... tags) throws IllegalValueException {
        Set<Tag> tagsSet = new HashSet<>();
        for (String tag : tags) {
            tagsSet.add(new Tag(tag));
        }

        toDo.setTags(tagsSet);

        return this;
    }

    public ToDo build() {
        return toDo;
    }
}
