package seedu.todo.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.task.ImmutableTask;

public class TestTodoList implements ImmutableTodoList {

    @Override
    public List<ImmutableTask> getTasks() {
        try {
            List<ImmutableTask> list = new ArrayList<>();
            
            list.add(new TestTaskBuilder("test1").build());
            list.add(new TestTaskBuilder("test2")
                    .withCompleted()
                    .withPinned()
                    .build());
            list.add(new TestTaskBuilder("test3")
                    .withDescription("description")
                    .withLocation("location")
                    .build());
            list.add(new TestTaskBuilder("test4")
                    .withTime()
                    .build());
            list.add(new TestTaskBuilder("test5")
                    .withTags("tag", "tag1")
                    .build());
            
            return list;
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }

}
