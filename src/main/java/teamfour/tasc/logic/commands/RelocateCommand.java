package teamfour.tasc.logic.commands;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import teamfour.tasc.MainApp;

/**
 * Moves the data storage file to a new directory.
 */
public class RelocateCommand extends Command {

    public static final String COMMAND_WORD = "relocate";

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Designates a new data storage location (relative to current location). \n"
            + "Parameters: [RELATIVE_PATH] (Enter no parameter for relocating to original path)\n"
            + "Example: " + COMMAND_WORD
            + " ../../relative/path/to/storage/location";

    
    public static final String MESSAGE_SUCCESS = 
            "File Relocated: %1$s \nWill take effect after restarting the app.";
    public static final String MESSAGE_UNDO_SUCCESS = 
            "File Relocation cancelled. Data will be stored in %1$s.";
    public static final String MESSAGE_FILE_OPERATION_FAILURE = 
            "Error occured while transfering data. ";
    
    private final String destination;
    private final String originalDestination = MainApp.getDataStorageFilePath();
    
    private boolean undoable = false;

    /**
     * Relocate Command for changing storage path to new directory.
     */
    public RelocateCommand(String destination) {
        this.destination = "data/" + destination;
    }
    
    /**
     * Relocate Command for changing back storage to original directory.
     * */
    public RelocateCommand() {
        this.destination = "data";
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            MainApp.setDataStorageFilePath(destination);
            undoable = true;
            return new CommandResult(String.format(MESSAGE_SUCCESS, 
                    destination + "/tasklist.xml"));
        } catch (IOException | JAXBException e) {
            return new CommandResult(MESSAGE_FILE_OPERATION_FAILURE);
        }
    }

    @Override
    public boolean canUndo() {
        return undoable;
    }

}
