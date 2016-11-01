package seedu.todo.logic.commands;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.atteo.evo.inflector.English;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.FlagArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;

public class FindCommand extends BaseCommand {
    private static final String TASK_FOUND_FORMAT = "%d %s found!";
    
    private Argument<String> keywords = new StringArgument("keywords");

    private Argument<Boolean> tags = new FlagArgument("tags")
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
        if (!keywords.hasBoundValue() || StringUtil.isEmpty(keywords.getValue())) {
            model.find(t -> true);
            return new CommandResult();
        }

        List<String> keywordList = Lists.newArrayList(Splitter.on(" ")
            .trimResults()
            .omitEmptyStrings()
            .split(keywords.getValue().toLowerCase()));


        Predicate<ImmutableTask> filter = task -> {
            if (tags.getValue()) {
                Collection<String> tagNames = Tag.getTagNames(task.getTags());
                String tags = String.join("", tagNames);
                return keywordList.stream().anyMatch(tags::contains);
            } else {
                String title = task.getTitle().toLowerCase();
                return keywordList.stream().anyMatch(title::contains);
            }
        };

        model.find(filter, keywordList);

        int resultSize = model.getObservableList().size();
        String feedback = String.format(TASK_FOUND_FORMAT, resultSize, English.plural("result", resultSize));

        return new CommandResult(feedback);
    }
}
