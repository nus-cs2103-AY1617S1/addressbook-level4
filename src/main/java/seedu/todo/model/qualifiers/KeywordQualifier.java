package seedu.todo.model.qualifiers;

import java.util.Set;

import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * A qualifier that filter tasks based on whether their names
 * matches the keywords
 */
public class KeywordQualifier implements Qualifier{
    private Set<String> nameKeyWords;
    private SearchCompletedOption option;
    
    public KeywordQualifier(Set<String> nameKeyWords, SearchCompletedOption option) {
        this.nameKeyWords = nameKeyWords;
        this.option = option;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        boolean taskHasKeywords =  nameKeyWords.stream()
                .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName 
                        + " " 
                        + task.getDetail().value, keyword))
                .findAny()
                .isPresent();
        
        if (option == SearchCompletedOption.ALL) {
            return taskHasKeywords;
        } else if (option == SearchCompletedOption.DONE) {
            return taskHasKeywords && task.getCompletion().isCompleted();
        } else {
            return taskHasKeywords && !task.getCompletion().isCompleted();
        }
        
    }

    @Override
    public String toString() {
        return "name=" + String.join(", ", nameKeyWords);
    }
}
