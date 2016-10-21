package seedu.savvytasker.logic;

import javafx.collections.ObservableList;
import seedu.savvytasker.logic.commands.CommandResult;
import seedu.savvytasker.logic.parser.MasterParser;
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

    /**
     * Undoes the previous command. Does nothing but return false if there is 
     * no command to undo.
     * 
     * @return true if a command was undone, false otherwise
     */
    public boolean undo();
    
    /**
     * Redoes the previous undone command. Does nothing if there is nothing to redo.
     * 
     * @return true if a command was redone, false otherwise
     */
    public boolean redo();
    
    /**
     * Refer to {@link MasterParser#addPreprocessSymbol(String,String)}
     */
    public boolean addPreprocessSymbol(String symbol, String representation);

    /**
     * Refer to {@link MasterParser#removePreprocessingSymbol(String)}
     */
    public boolean removePreprocessingSymbol(String symbol);
    
    /**
     * Refer to {@link MasterParser#doesPreprocessingSymbolExist(String)}
     */
    public boolean doesPreprocessingSymbolExist(String symbol);
}
