package seedu.todo.model.task;

import java.util.UUID;

//@@author A0135817B
public abstract class BaseTask implements ImmutableTask {
    protected UUID uuid = UUID.randomUUID();

    @Override
    public UUID getUUID() {
        return uuid;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ImmutableTask)) {
            return false;
        }

        return getUUID().equals(((ImmutableTask) o).getUUID());
    }

    @Override
    public int hashCode() {
        return getUUID().hashCode();
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
