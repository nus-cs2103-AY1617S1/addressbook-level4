package seedu.todo.logic.commands;

import com.google.common.collect.ImmutableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;

import java.util.*;
import java.util.function.Predicate;

public class FindCommand extends BaseCommand {
    private static final String FEEDBACK = "Type 'find' again to close find";
    
    private Argument<String> keywords = new StringArgument("keywords");

    private Argument<String> tags = new StringArgument("tags")
            .flag("t");
    
    @Override
    protected Parameter[] getArguments() {
        return new Parameter[] { keywords, tags };
    }

    @Override
    public String getCommandName() {
        return "find";
        }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(
                new CommandSummary("Find tasks", getCommandName(), getArgumentSummary()));
    }

    @Override
    public CommandResult execute() throws ValidationException {
        // Dismissing search results if there are no keywords
        if (isEmpty(keywords) && isEmpty(tags)) {
            model.find(t -> true);
            return new CommandResult();
        }
        
        // Construct the predicate and list of search terms 
        Predicate<ImmutableTask> filter = t -> false;
        List<String> terms = new ArrayList<>();
        
        if (!isEmpty(keywords)) {
            List<String> keywordList = getSearchTerms(keywords);
            terms.addAll(keywordList);
            filter = filter.or(task -> {
                String title = task.getTitle().toLowerCase();
                return keywordList.stream().anyMatch(title::contains);
            });
        }
        
        if (!isEmpty(tags)) {
            List<String> tagList = getSearchTerms(tags);
            terms.addAll(tagList);
            filter = filter.or(task -> {
                Set<String> taskTagNames = Tag.getLowerCaseNames(task.getTags());
                return !Collections.disjoint(taskTagNames, tagList);
            });
        }

        model.find(filter, terms);
        return new CommandResult(FEEDBACK);
    }

    /**
     * Splits the argument's value into lowercase space or comma delimited terms 
     */
    private List<String> getSearchTerms(Argument<String> argument) {
        return Arrays.asList(StringUtil.split(argument.getValue().toLowerCase()));
    }
}
