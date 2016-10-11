package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public abstract class JsonNameMixin {
    @JsonCreator
    JsonNameMixin(String name) {}

    @JsonValue
    public abstract String toString();
}
