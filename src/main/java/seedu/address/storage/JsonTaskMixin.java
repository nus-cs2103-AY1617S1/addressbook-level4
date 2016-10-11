package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.task.Name;

public abstract class JsonTaskMixin {
    JsonTaskMixin(@JsonProperty("name") Name name) {}
}
