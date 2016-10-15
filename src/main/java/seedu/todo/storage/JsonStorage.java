package seedu.todo.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Patch;

import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.commons.util.JsonUtil;
import seedu.todo.models.TodoListDB;

public class JsonStorage implements Storage {
    
    // Ideally this would be a single circular-queue, but there is such built-in
    // mechanism, and we would really really like to keep this operation O(1).
    private Deque<LinkedList<Patch>> historyPatch = new ArrayDeque<LinkedList<Patch>>();
    private Deque<LinkedList<Patch>> futurePatch = new ArrayDeque<LinkedList<Patch>>();
    private String currJson;
    private final int HISTORY_SIZE = 1000;
    private final DiffMatchPatch dmp = new DiffMatchPatch();

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
