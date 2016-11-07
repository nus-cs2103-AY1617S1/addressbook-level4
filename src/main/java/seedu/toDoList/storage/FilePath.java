package seedu.toDoList.storage;

//@@author A0146123R
/**
 * Represents the file path of the task manager.
 */
public class FilePath {

    private final String path;
    private final boolean isToClear;

    public FilePath(String path, boolean isToClear) {
        this.path = path;
        this.isToClear = isToClear;
    }

    public String getPath() {
        return path;
    }

    public boolean isToClear() {
        return isToClear;
    }

}