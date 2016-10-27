package seedu.todo.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Patch;

import com.fasterxml.jackson.core.JsonProcessingException;

import seedu.todo.MainApp;
import seedu.todo.commons.exceptions.CannotRedoException;
import seedu.todo.commons.exceptions.CannotUndoException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.commons.util.JsonUtil;
import seedu.todo.models.TodoListDB;

/**
 * JSON Storage for persisting and loading from disk.
 * 
 * @@author A0093907W
 */
public class JsonStorage implements Storage {
    
    // Ideally this would be a single circular-queue, but there is no such built-in
    // mechanism, and we would really really like to keep this operation O(1).
    private Deque<LinkedList<Patch>> historyPatch = new ArrayDeque<LinkedList<Patch>>();
    private Deque<LinkedList<Patch>> futurePatch = new ArrayDeque<LinkedList<Patch>>();
    private String currJson;
    private final static int HISTORY_SIZE = 1000;
    private final DiffMatchPatch dmp = new DiffMatchPatch();

    private File getStorageFile() {
        String filePath = MainApp.getConfig().getDatabaseFilePath();
        return new File(filePath);
    }
    
    /**
     * Internal function to prune the history patches stored to ensure it does
     * not exit HISTORY_SIZE.
     */
    private void pruneHistory() {
        // Don't need to worry about future because it cannot exceed limit.
        while (historyPatch.size() > HISTORY_SIZE) {
            historyPatch.removeFirst();
        }
    }
    
    @Override
    public void move(String newPath) throws IOException {
        boolean hasMoved = false;
        
        try {
            FileUtil.createParentDirsOfFile(new File(newPath));
        } catch (IOException e) {
            throw e;
        }
        
        try {
            hasMoved = getStorageFile().renameTo(new File(newPath));
        } catch (SecurityException e) {
            throw new IOException(e.getMessage(), e.getCause());
        }
        
        if (!hasMoved) {
            throw new IOException(String.format("Could not move file to \"%s\".", newPath));
        }
    }

    @Override
    public void save(TodoListDB db) throws JsonProcessingException, IOException {
        String newJson = JsonUtil.toJsonString(db);
        
        // Store the undo patch.
        if (this.currJson != null) {
            historyPatch.addLast(dmp.patchMake(newJson, this.currJson));
            pruneHistory();
            futurePatch.clear(); // A forward move nullifies all future patches.
        }
        
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
    public TodoListDB undo() throws CannotUndoException, IOException {
        // Get undo
        LinkedList<Patch> undoPatch;
        try {
            undoPatch = historyPatch.removeLast();
        } catch (NoSuchElementException e) {
            throw new CannotUndoException(e);
        }
        
        String newJson = (String)dmp.patchApply(undoPatch, this.currJson)[0];
        
        // Create redo
        LinkedList<Patch> redoPatch = dmp.patchMake(newJson, this.currJson);
        futurePatch.addLast(redoPatch);
        
        // Apply undo
        this.currJson = newJson;
        FileUtil.writeToFile(getStorageFile(), this.currJson);
        return JsonUtil.fromJsonString(this.currJson, TodoListDB.class);
    }
    
    @Override
    public int undoSize() {
        return historyPatch.size();
    }

    @Override
    public TodoListDB redo() throws CannotRedoException, IOException {
        // Get redo
        LinkedList<Patch> redoPatch;
        try {
            redoPatch = futurePatch.removeLast();
        } catch (NoSuchElementException e) {
            throw new CannotRedoException(e);
        }
        
        String newJson = (String)dmp.patchApply(redoPatch, this.currJson)[0];
        
        // Create undo
        LinkedList<Patch> undoPatch = dmp.patchMake(newJson, this.currJson);
        historyPatch.addLast(undoPatch);
        
        // Apply redo
        this.currJson = newJson;
        FileUtil.writeToFile(getStorageFile(), this.currJson);
        return JsonUtil.fromJsonString(this.currJson, TodoListDB.class);
    }
    
    @Override
    public int redoSize() {
        return futurePatch.size();
    }

}
