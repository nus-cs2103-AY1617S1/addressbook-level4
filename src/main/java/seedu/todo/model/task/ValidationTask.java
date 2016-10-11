package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.tag.Tag;

public class ValidationTask implements ImmutableTask {
    private static final String TITLE = "title";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";

    private static final String ONLY_ONE_TIME_ERROR_MESSAGE = "Only field=%s is defined";
    private static final String TITLE_EMPTY_ERROR_MESSAGE = "Title should not be a empty string.";
    private static final String VALIDATION_ERROR_MESSAGE = "Model validation failed";
    private static final String START_AFTER_END_ERROR_MESSAGE = "startTime is after endTime";

    private ErrorBag errors;

    private String title;
    private String description;
    private String location;

    private boolean pinned;
    private boolean completed;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Set<Tag> tags = new HashSet<Tag>();
    private UUID uuid;

    public ValidationTask(String title) {
        this.setTitle(title);
        this.uuid = UUID.randomUUID();
    }

    /**
     * Constructs a Task from a ReadOnlyTask
     */
    public ValidationTask(ImmutableTask task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription().orElse(null));
        this.setLocation(task.getLocation().orElse(null));
        this.setStartTime(task.getStartTime().orElse(null));
        this.setEndTime(task.getEndTime().orElse(null));
        this.setCompleted(task.isCompleted());
        this.setPinned(task.isPinned());
        this.uuid = task.getUUID();
    }

    /**
     * Validates the task by checking the individuals are valid.
     * 
     * @return whether the task is valid or not
     */
    public void isValidTask() throws ValidationException {
        isValidTime();
        isValidTitle();
        errors.validate(VALIDATION_ERROR_MESSAGE);
    }

    private void isValidTitle() {
        if (title.equals("")) {
            errors.put(TITLE, TITLE_EMPTY_ERROR_MESSAGE);
        }
    }

    private void isValidTime() {
        if (startTime == null && endTime == null) {
            return;
        }
        // Both time fields must be declared
        if (startTime != null) {
            String field = START_TIME;
            errors.put(field, String.format(ONLY_ONE_TIME_ERROR_MESSAGE, field));
        }
        if (endTime != null) {
            String field = END_TIME;
            errors.put(field, String.format(ONLY_ONE_TIME_ERROR_MESSAGE, field));
        }

        if (startTime.isAfter(endTime)) {
            errors.put(START_TIME, START_AFTER_END_ERROR_MESSAGE);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.ofNullable(location);
    }

    @Override
    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    @Override
    public boolean isPinned() {
        return pinned;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
