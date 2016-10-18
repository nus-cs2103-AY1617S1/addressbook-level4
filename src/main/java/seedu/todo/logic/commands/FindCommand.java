package seedu.todo.logic.commands;

import java.util.List;


import org.atteo.evo.inflector.English;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.arguments.StringArgument;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
public class FindCommand extends BaseCommand {
    private static final String VERB= "%d %s found!";
    
    private Argument<String> keywords = new StringArgument("keywords").required();
    

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
        return ImmutableList.of(new CommandSummary("Find a task", getCommandName(), 
                getArgumentSummary()));
    }
    @Override
    public CommandResult execute() throws ValidationException {
        List<String> keywordList = Lists.newArrayList(Splitter
                                    .on(" ")
                                    .trimResults()
                                    .omitEmptyStrings()
                                    .split(keywords.getValue().toLowerCase()));
        
        model.view( task -> {
           for (String keyword : keywordList) {
               if (task.getTitle().toLowerCase().contains(keyword)) {
                   return true;
                   }
               }
           return false;
           }, null );
        int resultSize = model.getObserveableList().size();
        String feedback = String.format(VERB, resultSize, English.plural("result", resultSize));
        return new CommandResult(feedback);
    }



}
