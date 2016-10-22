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
    /**
     * Returns a defensively copied task list.
     */
    TaskList getTaskList();

    /**
     * Returns a defensively copied alias symbol list.
     */
    
    AliasSymbolList getAliasSymbolList();
    /**
     * Returns an unmodifiable view of task list
     */
    List<ReadOnlyTask> getReadOnlyListOfTasks();

    /**
     * Returns unmodifiable view of symbols list
     */
    List<AliasSymbol> getReadOnlyListOfAliasSymbols();
}
