package seedu.address.logic.commands;

import seedu.address.commons.core.Config;

public class SpecifyStorageCommand extends Command {
    
    public static final String COMMAND_WORD = "storage";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets your storage folder for your data files in Simply."
            + "Parameters: storage folder path";
    
    public static final String SPECIFY_STORAGE_SUCCESS = "storage folder changed: %1$s";
    
    private final String folderPath;
    
    public SpecifyStorageCommand(String folderPath) {
        this.folderPath = folderPath;
    }
    
    @Override
    public CommandResult execute() {
        try {
            Files.move
            Config.setUserPrefsFilePath();
            Config.setAddressBookFilePath();
            
        } catch (PathNotFoundException e) {
            
        }
    }
}
