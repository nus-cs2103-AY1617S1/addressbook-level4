package seedu.todo.storage;

import java.io.IOException;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.models.TodoListDB;

public interface Storage {

    public void save(TodoListDB db) throws IOException;

    public TodoListDB load() throws IOException;

    public void move(String newPath) throws IOException;

    /**
     * Undo information is on a per-session basis, and should not be
     * persisted to disk.
     * 
     * @return TodoListDB
     * @throws CannotUndoException
     */
    public TodoListDB undo() throws CannotUndoException, IOException;

    public TodoListDB redo() throws CannotRedoException, IOException;
    
    public int undoSize();
    
    public int redoSize();

}
