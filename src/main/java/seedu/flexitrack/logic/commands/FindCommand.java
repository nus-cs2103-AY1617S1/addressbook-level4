package seedu.flexitrack.logic.commands;

import java.util.HashSet;
import java.util.Set;

/**
 * Finds and lists all tasks in FlexiTrack whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_SHORTCUT = "f";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD  + ", Shortcut [" + COMMAND_SHORTCUT + "]" + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "1. Default Search - " + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t" + "Example: " + COMMAND_WORD
            + "  CS2103\n" + "2. Search by exact task name - " + "Parameters: f/ KEYWORD [MORE KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + "f/ attend CS2103 lecture";

    private final Set<String> keywords;
    private final String arguments; 

    public FindCommand(Set<String> keywords, String args) {
        this.keywords = keywords;
        this.arguments = args; 
    }

    public FindCommand(String keywords) {
        Set<String> keyword2 = new HashSet<String>();
        keyword2.add(keywords);
        this.keywords = keyword2;
        this.arguments = keywords; 
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        
        UndoCommand.argumentsRecord.add(UndoCommand.argumentsTemp);
        UndoCommand.argumentsTemp = arguments; 
        UndoCommand.uiCommandRecord.add(UndoCommand.uiCommandRecordTemp);
        UndoCommand.uiCommandRecordTemp = "find";
        recordCommand("find"); 
        
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
    
    @Override
    public void executeUndo() {

        model.updateFilteredTaskList(keywords);

        UndoCommand.argumentsTemp = UndoCommand.argumentsRecord.pop();
        UndoCommand.uiCommandRecordTemp = UndoCommand.uiCommandRecord.pop();
    }

}
