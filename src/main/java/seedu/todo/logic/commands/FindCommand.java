package seedu.todo.logic.commands;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.atteo.evo.inflector.English;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.task.ImmutableTask;

import java.util.List;
import java.util.function.Predicate;

public class FindCommand extends BaseCommand {
    private static final String TASK_FOUND_FORMAT = "%d %s found!";
    
    private Argument<String> keywords = new StringArgument("keywords");
    
    @Override
    protected Parameter[] getArguments() {
        return new Parameter[] { keywords };
    }

    @Override
    public String getCommandName() {
        return "find";
        }

    @Override
    public List<CommandSummary> getCommandSummary() {
        return ImmutableList.of(new CommandSummary("Find tasks", getCommandName(), 
                getArgumentSummary()));
    }
    @Override
    public CommandResult execute() throws ValidationException {
        // Dismissing search results 
        if (!keywords.hasBoundValue()) {
            model.find(null);
            return new CommandResult();
        }
        
        List<String> keywordList = Lists.newArrayList(Splitter.on(" ")
            .trimResults()
            .omitEmptyStrings()
            .split(keywords.getValue().toLowerCase()));

        Predicate<ImmutableTask> filter = task -> {
            String title = task.getTitle().toLowerCase();
            return keywordList.stream().anyMatch(title::contains);
        };
        
        model.find(filter, keywordList);
        
        int resultSize = model.getObservableList().size();
        String feedback = String.format(TASK_FOUND_FORMAT, resultSize, English.plural("result", resultSize));
        
        return new CommandResult(feedback);
    }



}
