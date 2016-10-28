package seedu.todo.storage;

import java.io.IOException;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.models.TodoListDB;

/**
 * Storage interface for persisting and loading from disk.
 * 
 * @@author A0093907W
 */
public interface Storage {

    /**
     * Persists a TodoListDB object to disk.
     * @param db    TodoListDB object
     * @throws IOException  If there is an error writing to disk.
     */
    public void save(TodoListDB db) throws IOException;

    /**
     * Loads a TodoListDB object from disk
     * @return  TodoListDB object
     * @throws IOException  If there is an error reading from disk.
     */
    public TodoListDB load() throws IOException;

    public void move(String newPath) throws IOException;

    /**
     * Rolls back the DB by one commit, persists the DB, and returns a
     * TodoListDB object.
     * 
     * Undo information is on a per-session basis, and should not be persisted
     * to disk.
     * 
     * @return TodoListDB object
     * @throws CannotUndoException
     *             If there is nothing to undo.
     * @throws IOException
     *             If there is an error writing to disk.
     */
    public TodoListDB undo() throws CannotUndoException, IOException;

    /**
     * Rolls forward the DB by one undo commit, persists the DB, and returns a
     * TodoListDB object.
     * 
     * @return TodoListDB object
     * @throws CannotRedoException
     *             If there is nothing to redo.
     * @throws IOException
     *             If there is an error writing to disk.
     */
    public TodoListDB redo() throws CannotRedoException, IOException;
    
    /**
     * Returns the maximum possible number of undos.
     * @return undoSize
     */
    public int undoSize();
    
    /**
     * Returns the maximum possible number of redos.
     * @return
     */
    public int redoSize();

}
