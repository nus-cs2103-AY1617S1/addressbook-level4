package harmony.mastermind.logic.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.exceptions.FolderDoesNotExistException;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.UnwrittableFolderException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;
import harmony.mastermind.storage.StorageManager;
import harmony.mastermind.storage.*;

/**
 * @@author A0139194X
 * Relocates save location
 */
public class RelocateCommand extends Command{

    public static final String COMMAND_WORD = "relocate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes save location in MasterMind. "
            + "Parameters: FILE_PATH\n"
            + "Example: " + COMMAND_WORD
            + "Desktop";

    public static final String MESSAGE_SUCCESS = "Relocated save location to %1$s";
    public static final String MESSAGE_INVALID_INPUT = "%1$s is not valid.";
    public static final String MESSAGE_UNWRITTABLE_FOLDER = "%1$s is not writtable.";

    private final String newFilePath;

    /** 
     * @@author A0139194X
     * Convenience constructor using raw values.
     */
    public RelocateCommand(String newFilePath) {
        this.newFilePath = newFilePath.trim();
    }

    //@@author A0139194X
    @Override
    public CommandResult execute() {
        assert model != null;
        assert storage != null;
        assert newFilePath != null;
        try {
            storage.checkSaveLocation(newFilePath);
            storage.checkWrittableDirectory(newFilePath);
            model.relocateSaveLocation(newFilePath);
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_SUCCESS, newFilePath));
        } catch (FolderDoesNotExistException fdnee) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_INVALID_INPUT, newFilePath));
        } catch (UnwrittableFolderException ufe) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_UNWRITTABLE_FOLDER, newFilePath));
        }
    }
}
