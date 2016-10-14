package seedu.todo.storage;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.commons.util.JsonUtil;
import seedu.todo.models.TodoListDB;

public class JsonStorage implements Storage {

    private File getStorageFile() {
        return new File("database.json");
    }

    @Override
    public void save(TodoListDB db) throws JsonProcessingException, IOException {
        FileUtil.writeToFile(getStorageFile(), JsonUtil.toJsonString(db));
    }

    @Override
    public TodoListDB load() throws IOException {
        return JsonUtil.fromJsonString(FileUtil.readFromFile(getStorageFile()), TodoListDB.class);
    }

    @Override
    public TodoListDB undo() throws CannotUndoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TodoListDB redo() throws CannotRedoException {
        // TODO Auto-generated method stub
        return null;
    }

}
