package seedu.todo.model.task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.tag.Tag;

//@@author A0139021U
public class ValidationTask extends BaseTask implements MutableTask {
    private static final String END_TIME = "endTime";
    private static final String TITLE = "title";
    private static final String ONLY_START_TIME_ERROR_MESSAGE = "You must define an ending time.";
    private static final String TITLE_EMPTY_ERROR_MESSAGE = "Your title should not be empty.";
    private static final String VALIDATION_ERROR_MESSAGE = "Your task is not in the correct format.";
    private static final String START_AFTER_END_ERROR_MESSAGE = "No time travelling allowed! You've finished before you even start.";

    private ErrorBag errors = new ErrorBag();

    private String title;
    private String description;
    private String location;

    private boolean pinned;
    private boolean completed;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Set<Tag> tags = new HashSet<>();
    private LocalDateTime createdAt;

    public ValidationTask(String title) {
        this.setTitle(title);
        this.setCreatedAt();
    }

    /**
     * Constructs a ValidationTask from an ImmutableTask
     */
    public ValidationTask(ImmutableTask task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription().orElse(null));
        this.setLocation(task.getLocation().orElse(null));
        this.setStartTime(task.getStartTime().orElse(null));
        this.setEndTime(task.getEndTime().orElse(null));
        this.setCompleted(task.isCompleted());
        this.setPinned(task.isPinned());
        
        this.createdAt = task.getCreatedAt();
        this.uuid = task.getUUID();
    }

    /**
     * Validates the task by checking the individual fields are valid.
     */
    public void validate() throws ValidationException {
        isValidTime();
        isValidTitle();
        errors.validate(VALIDATION_ERROR_MESSAGE);
    }

    private void isValidTitle() {
        if (StringUtil.isEmpty(title)) {
            errors.put(TITLE, TITLE_EMPTY_ERROR_MESSAGE);
        }
    }

    /**
     * Validates time. Only valid when
     * 1) both time fields are not declared
     * 2) end time is present
     * 3) start time is before end time
     */
    private void isValidTime() {
        if (startTime == null && endTime == null) {
            return;
        } else if (endTime == null) {
            errors.put(END_TIME, ONLY_START_TIME_ERROR_MESSAGE);
        } else if (startTime != null && startTime.isAfter(endTime)) {
            errors.put(END_TIME, START_AFTER_END_ERROR_MESSAGE);
        }
    }

    /**
     * Converts the validation task into an actual task for consumption.
     * 
     * @return A task with observable properties
     */
    public Task convertToTask() throws ValidationException {
        validate();
        return new Task(this);
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

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setCreatedAt() { this.createdAt = LocalDateTime.now(); }
}
