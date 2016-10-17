package seedu.todo.logic.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.util.DateTimeUtil;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Search all tasks whose names contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " birthday homework friday";

    private final String data;
    private final int whichSearch;
    
    public SearchCommand(String data, int whichSearch) {
        this.data = data;
        this.whichSearch = whichSearch;
    }

    @Override
    public CommandResult execute() {
        switch (whichSearch) {
        case 0 : //on date search
            try {
                LocalDateTime datetime = DateTimeUtil.parseDateTimeString(data);
                model.updateFilteredTaskListOnDate(datetime);
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
            } catch (DateTimeParseException e) {
                return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
        case 1 : //before date search
            try {
                LocalDateTime datetime = DateTimeUtil.parseDateTimeString(data);
                model.updateFilteredTaskListBeforeDate(datetime);
                    
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
                    
            } catch (DateTimeParseException e) {
                return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
                
        case 2 : //after date search
            try {
                LocalDateTime datetime = DateTimeUtil.parseDateTimeString(data);
                model.updateFilteredTaskListAfterDate(datetime);
                    
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
                    
            } catch (DateTimeParseException e) {
                return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
                
        case 3 : //from till date search
            try {
                String fromDateString = data.split("@")[0].trim();
                LocalDateTime fromDateTime = DateTimeUtil.parseDateTimeString(fromDateString);
                    
                String tillDateString = data.split("@")[0].trim();
                LocalDateTime tillDateTime = DateTimeUtil.parseDateTimeString(tillDateString);
                    
                model.updateFilteredTaskListFromTillDate(fromDateTime, tillDateTime);
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
                    
            } catch (DateTimeParseException e) {
                return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
                
        case 4 : //keyword search
            final String[] keywords = data.split("\\s+");
            final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            model.updateFilteredTaskListByKeywords(keywordSet);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
                
        case 5 : //tag search
            String tag = data.split("tag")[1].trim();
            model.updateFilteredTaskListByTag(tag);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
                
        case 6 : //done search
            model.updateFilteredListToShowAllCompleted();
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
                
        case 7 : //undone search
            model.updateFilteredListToShowAllNotCompleted();
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
                
        default :
            return new CommandResult(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        
        
        
        ///
        
        
        
    }

}
