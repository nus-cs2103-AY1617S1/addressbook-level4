package seedu.jimi.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Set;

import seedu.jimi.model.datetime.DateTime;

/**
 * Finds and lists all tasks in Jimi whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " \"add water\"";

    private final Set<String> keywords;
    private final DateTime fromDate;
    private final DateTime toDate;
    
    
    public FindCommand() {
        keywords = null;
        toDate = null;
        fromDate = null;
    }
    
    public FindCommand(Set<String> keywords, List<Date> fromDate, List<Date> toDate) {
        this.keywords = keywords;
        if(fromDate != null && fromDate.size() != 0) {
            this.fromDate = new DateTime(fromDate.get(0));
        } else {
            this.fromDate = null;
        }
            
        if(toDate != null && toDate.size() != 0) {
            this.toDate = new DateTime(toDate.get(0));
        } else {
            this.toDate = null;
        }
    }
    //@@author A0138915X

    @Override
    public CommandResult execute() {
        if(keywords != null && keywords.size() > 0) {
            if(fromDate != null) {
                model.updateFilteredAgendaTaskList(keywords, fromDate, toDate);
                model.updateFilteredAgendaEventList(keywords, fromDate, toDate);
            } else {
                model.updateFilteredAgendaTaskList(keywords);
                model.updateFilteredAgendaEventList(keywords);
            }
        } else {
            model.updateFilteredAgendaTaskList(fromDate, toDate);
            model.updateFilteredAgendaEventList(fromDate, toDate);
        }
        
        
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredAgendaTaskList().size()
                + model.getFilteredAgendaEventList().size()));
    }
    
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.toLowerCase().equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
}
