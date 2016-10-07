package seedu.address.model.todo;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a to-do
 * Guarantees: details are present and not null, field values are validated.
 */
public class ToDo implements ReadOnlyToDo {

    private Title title;
    private Optional<DueDate> dueDate;
    private Optional<DateRange> dateRange;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public ToDo(Title title) {
        assert !CollectionUtil.isAnyNull(title);
        this.title = title;
    }

    /**
     * Copy constructor.
     */
    public ToDo(ReadOnlyToDo source) {
        this(source.getTitle());
    }
    
    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDueDate(Optional<DueDate> dueDate) {
        this.dueDate = dueDate;
    }

    public void setDateRange(Optional<DateRange> dateRange) {
        this.dateRange = dateRange;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    public Optional<DueDate> getDueDate() {
        return dueDate;
    }

    public Optional<DateRange> getDateRange() {
        return dateRange;
    }

    public UniqueTagList getTags() {
        return tags;
    }
    
    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyToDo // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyToDo) other));
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
