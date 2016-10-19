package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a DatedTask in the to-do-list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private Name name;
    private Description description;
    private Date date;
    private Time time;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Description phone, Date email, Time address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        this.name = name;
        this.description = phone;
        this.date = email;
        this.time = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDescription(), source.getDate(), source.getTime(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, date, time, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public int compareTo(Task other) {
        return Comparators.NAME.compare(this, other);
    }

    public static class Comparators {

        public static Comparator<Task> NAME = new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getName().toString().compareTo(t2.getName().toString());
            }
        };
        public static Comparator<Task> DATE = new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getDate().startDate.compareTo(t2.getDate().startDate);
            }
        };
    }
}
