package seedu.todo.storage;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.models.TodoListDB;

public interface Storage {

    public boolean save(TodoListDB db);

    public TodoListDB load();

    /**
     * Undo information is on a per-session basis, and should not be
     * persisted to disk.
     * 
     * @return TodoListDB
     * @throws CannotUndoException
     */
    public TodoListDB undo() throws CannotUndoException;

    public TodoListDB redo() throws CannotRedoException;

}
