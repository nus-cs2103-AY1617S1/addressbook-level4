package seedu.todo.model.qualifiers;

import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * A qualifier that filter tasks based on the tags
 * that the tasks have
 */
public class TagQualifier implements Qualifier{
    private String tagName;
    private SearchCompletedOption option;
    
    public TagQualifier(String tagName, SearchCompletedOption option) {
        this.tagName = tagName;
        this.option = option;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
<<<<<<< Updated upstream
        return task.getTags().getInternalList().stream()
                .filter(tag -> tag.getName().equals(tagName))
=======
        boolean taskHasTags = task.getTags().getInternalList().stream()
                .filter(tag -> tag.tagName.equals(tagName))
>>>>>>> Stashed changes
                .findAny()
                .isPresent();
        
        if (option == SearchCompletedOption.ALL) {
            return taskHasTags;
        } else if (option == SearchCompletedOption.DONE) {
            return taskHasTags && task.getCompletion().isCompleted();
        } else {
            return taskHasTags && !task.getCompletion().isCompleted();
        }
        
        
    }

    @Override
    public String toString() {
        return "tag name=" + tagName;
    }
}
