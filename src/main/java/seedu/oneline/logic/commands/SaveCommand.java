package seedu.oneline.logic.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.events.storage.StorageLocationChangedEvent;

public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Sets the folder to be used for storage\n" 
            + "Parameters: FOLDERPATH\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Bob/Desktop/";

    public static final String MESSAGE_SET_STORAGE_SUCCESS = "Storage location succesfully set to %1$s.";
    public static final String MESSAGE_SET_STORAGE_FAILURE_PATH_INVALID = "Cannot set storage location to \"%1$s\", path is invalid!";
    public static final String MESSAGE_SET_STORAGE_FAILURE_NOT_DIRECTORY = "Cannot set storage location to \"%1$s\", this is not a directory!";
    public static final String MESSAGE_SET_STORAGE_FAILURE_CANNOT_READ = "Cannot set storage location to \"%1$s\", cannot read from here!"; 
    public static final String MESSAGE_SET_STORAGE_FAILURE_CANNOT_WRITE = "Cannot set storage location to \"%1$s\", cannot write to here!"; 

    String storageLocation;

    public SaveCommand(String storageLocation) {
        this.storageLocation = storageLocation;
    }


    @Override
    public CommandResult execute() {
        Optional<Path> path = getValidPath(storageLocation);
        if (!path.isPresent()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_PATH_INVALID, storageLocation));
        } else {
            Path actualPath = path.get();
            if (!isDirectory(actualPath)) {
                return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_NOT_DIRECTORY, actualPath.toAbsolutePath()));
            } else if (!isReadable(actualPath)) {
                return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_CANNOT_READ, actualPath.toAbsolutePath()));
            } else if (!isWritable(actualPath)) {
                return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE_CANNOT_WRITE, actualPath.toAbsolutePath()));
            }
        }
        Path actualPath = path.get();
        EventsCenter.getInstance().post(new StorageLocationChangedEvent(storageLocation));
        return new CommandResult(String.format(MESSAGE_SET_STORAGE_SUCCESS, actualPath.toAbsolutePath()));    
    }

    private Optional<Path> getValidPath(String folderpath) {
        if (folderpath == null || folderpath.isEmpty()) {
            return Optional.empty();
        }

        try {
            Path path = Paths.get(folderpath);
            return Optional.of(path);
        } catch (InvalidPathException ipe) {
            return Optional.empty();
        } catch (SecurityException sece) {
            return Optional.empty();
        }

    }


    private boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    private boolean isWritable(Path path) {
        return Files.isWritable(path);
    }

    private boolean isReadable(Path path) {
        return Files.isReadable(path);
    }
}
