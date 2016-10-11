package seedu.address.model.filter;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;

public class NameContainsKeywordsPredicate implements Predicate<Task> {
    private final Set<String> keywords;

    public NameContainsKeywordsPredicate(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .filter(keyword -> StringUtil.containsIgnoreCase(task.name.toString(), keyword))
                .findAny()
                .isPresent();
    }

}
