package seedu.address.testutil;


import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.todo.ToDo;

import java.time.LocalDateTime;

/**
 * Helps build a to-do list in 1 line
 */
public class ToDoListBuilder {
    private ToDoList toDoList = new ToDoList();

    public ToDoListBuilder add(ToDo toDo) {
        toDoList.add(toDo);

        return this;
    }

    public ToDoList build() {
        return toDoList;
    }

    public static ToDoList getSample() throws IllegalValueException {
        return new ToDoListBuilder()
            .add(
                new ToDoBuilder("valid title")
                    .withTags(
                        "tag1", "tag2"
                    )
                    .withDueDate(
                        LocalDateTime.of(2016, 5, 1, 20, 1)
                    )
                    .withDateRange(
                        LocalDateTime.of(2016, 3, 1, 20, 1),
                        LocalDateTime.of(2016, 4, 1, 20, 1)
                    )
                    .build()
            )
            .add(
                new ToDoBuilder("valid title 2")
                    .build()
            ).build();
    }
}
