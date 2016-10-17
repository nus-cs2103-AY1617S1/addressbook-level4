package teamfour.tasc.model.task;

import java.util.Objects;

import teamfour.tasc.commons.exceptions.IllegalValueException;

/**
 * Represents a task's name in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    private final String name;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.getName().equals(((Name) other).getName())); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public String getName() {
        return name;
    }

}
