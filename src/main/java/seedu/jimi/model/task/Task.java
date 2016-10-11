package seedu.jimi.model.task;

import seedu.jimi.model.tag.UniqueTagList;

public class Task implements ReadOnlyTask {

    private Name name;
    private DateTime deadline;
    private UniqueTagList tags;
    private boolean isCompleted;
    
    public Task(Name name, DateTime deadline, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, deadline, tags);
        this.isCompleted = false;
        this.name = name;
        this.deadline = deadline;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    @Override
    public Name getName() {
        return name;
    }
    
    public DateTime getDeadline() {
        return deadline;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(getDeadline())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }


}
