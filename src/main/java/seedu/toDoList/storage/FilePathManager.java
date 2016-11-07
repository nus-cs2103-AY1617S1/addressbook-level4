package seedu.toDoList.storage;

import java.util.Stack;

//@@author A0146123R
/**
 * Saves the file path of the task manager.
 */
public class FilePathManager {

    private static final int MIN = 1;

    private FilePath currentFilePath;
    private Stack<FilePath> undoFilePath;
    private Stack<FilePath> redoFilePath;

    /**
     * Initializes a FilePathManager with the given FilePath. FilePath should
     * not be null.
     */
    public FilePathManager(FilePath initialFilePath) {
        currentFilePath = initialFilePath;
        undoFilePath = new Stack<FilePath>();
        redoFilePath = new Stack<FilePath>();
    }

    /** Saves the current file path of the task manager. */
    public void saveFilePath(FilePath filePath) {
        undoFilePath.push(currentFilePath);
        currentFilePath = filePath;
        redoFilePath.clear();
    }

    /** Gets the previous file path of the task manager. */
    public FilePath getPreviousFilePath() {
        assert undoFilePath.size() >= MIN;
        redoFilePath.push(currentFilePath);
        currentFilePath = undoFilePath.pop();
        return currentFilePath;
    }

    /** Gets the next file path of the task manager. */
    public FilePath getNextFilePath() {
        assert redoFilePath.size() >= MIN;
        undoFilePath.push(currentFilePath);
        currentFilePath = redoFilePath.pop();
        return currentFilePath;
    }

}