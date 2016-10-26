package seedu.address.model;

import seedu.address.commons.core.Config;

//@@author A0147890U
public class SaveState {
    private final TaskBook taskBook;
    private final Config config;
    
    public SaveState() {
        this.taskBook = new TaskBook();
        this.config = new Config();
    }
    
    public SaveState(TaskBook taskBook, Config config) {
        this.taskBook = taskBook;
        this.config = config;
    }
    
    public Config getSaveStateConfig() {
        return this.config;
    }
    
    public TaskBook getSaveStateTaskBook() {
        return this.taskBook;
    }
}
