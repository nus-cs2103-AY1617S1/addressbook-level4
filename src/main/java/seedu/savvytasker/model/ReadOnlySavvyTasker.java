package seedu.savvytasker.model;

import java.util.List;

import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.alias.AliasSymbolList;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.TaskList;

/**
 * Unmodifiable view of a task list
 */
public interface ReadOnlySavvyTasker {
    //@@author A0139915W
    /**
     * Returns a defensively copied task list.
     */
    TaskList getTaskList();
    //@@author
    //@@author A0139916U

    /**
     * Returns a defensively copied alias symbol list.
     */
    
    AliasSymbolList getAliasSymbolList();
    //@@author
    //@@author A0139915W
    /**
     * Returns an unmodifiable view of task list
     */
    List<ReadOnlyTask> getReadOnlyListOfTasks();
    //@@author
    //@@author A0139916U

    /**
     * Returns unmodifiable view of symbols list
     */
    List<AliasSymbol> getReadOnlyListOfAliasSymbols();
    //@@author
}
