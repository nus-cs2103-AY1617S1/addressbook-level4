package seedu.todo.model.qualifiers;

import seedu.todo.model.task.ReadOnlyTask;

public class TagQualifier implements Qualifier{
    private String tagName;

    public TagQualifier(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return task.getTags().getInternalList().stream()
                .filter(tag -> tag.tagName.equals(tagName))
                .findAny()
                .isPresent();
    }

    @Override
    public String toString() {
        return "tag name=" + tagName;
    }
}
