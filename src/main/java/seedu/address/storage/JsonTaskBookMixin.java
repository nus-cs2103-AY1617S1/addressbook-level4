package seedu.address.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.task.Task;

public abstract class JsonTaskBookMixin {
    JsonTaskBookMixin(@JsonProperty("tasks") List<Task> tasks) {}
}
