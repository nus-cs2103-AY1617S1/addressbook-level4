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
    
    private void pruneHistory() {
        // Don't need to worry about future because it cannot exceed limit.
        while (historyPatch.size() > HISTORY_SIZE)
            historyPatch.removeFirst();
    }

    @Override
    public void save(TodoListDB db) throws JsonProcessingException, IOException {
        String newJson = JsonUtil.toJsonString(db);
        
        // Store the undo patch.
        historyPatch.addLast(dmp.patchMake(newJson, this.currJson));
        pruneHistory();
        futurePatch.clear(); // A forward move nullifies all future patches.
        
        // Update currJson and persist to disk.
        this.currJson = newJson;
        FileUtil.writeToFile(getStorageFile(), this.currJson);
    }

    @Override
    public TodoListDB load() throws IOException {
        this.currJson = FileUtil.readFromFile(getStorageFile());
        historyPatch.clear(); // It does not make sense to preserve history on load.
        return JsonUtil.fromJsonString(this.currJson, TodoListDB.class);
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
