package seedu.savvytasker.logic;

import java.util.Date;

import javafx.collections.ObservableList;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.logic.commands.CommandResult;
import seedu.savvytasker.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered list of alias symbol */
    ObservableList<AliasSymbol> getAliasSymbolList();
    
    /** */
    boolean canParseHeader(String keyword);

	/** Returns the list of floating tasks */
	ObservableList<ReadOnlyTask> getFilteredFloatingTasks();

	/** Returns the list of tasks on a specific day */
	ObservableList<ReadOnlyTask> getFilteredDailyTasks(int i, Date date);

	/** Returns the list of tasks that occur after the selected week */
	ObservableList<ReadOnlyTask> getFilteredUpcomingTasks(Date date);
	
}
