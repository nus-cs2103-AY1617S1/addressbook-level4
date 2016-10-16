package seedu.todo.logic.commands;




import java.util.List;
import java.util.function.Predicate;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import seedu.todo.model.task.ImmutableTask;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
public class FindCommand extends BaseCommand {
    private static final String VERB = " result(s) found";
    
    private Argument<String> keywords = new StringArgument("keywords").required();
    

    @Override
    protected Parameter[] getArguments() {
        
        return new Parameter[] { keywords };
    }

    @Override
    public String getCommandName() {
        return null;
}

    @Override
    public List<CommandSummary> getCommandSummary() {
        return null;
    }
    @Override
    public CommandResult execute() throws ValidationException {
        List<String> keywordArray = Lists.newArrayList(Splitter
                                    .on(" ")
                                    .trimResults()
                                    .omitEmptyStrings()
                                    .split(keywords.getValue()));;
        Predicate<ImmutableTask> p = (task)-> task.getTitle().contains(keywordArray.get(0));
        for (String keyword: keywordArray){
            Predicate<ImmutableTask> q = (task) -> task.getTitle().contains(keyword);
            p = p.or(q);
        }
        model.view( p , null );
        int results = model.getObserveableList().size();
        return taskSuccessfulResult(Integer.toString(results), VERB);
    }



}
