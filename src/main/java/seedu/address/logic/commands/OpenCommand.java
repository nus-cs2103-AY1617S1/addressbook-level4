package seedu.address.logic.commands;

import java.io.File;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.OpenFileChooserEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.storage.XmlAddressBookStorage;

public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows the save file location to be specified.\n"
            + "File name cannot consist of any of the following characters: '<', '>', ':', '\"', '|', '?', '*'\n"
            + "Example: " + COMMAND_WORD + " folder" + System.getProperty("file.separator") + "file.xml";

    public static final String OPEN_MESSAGE = "Loaded data from file: %1$s";
    public static final String NO_SUCH_FILE_MESSAGE = "The specified file doesn't exist";
    
    private final String openPath;

    public OpenCommand(String filePath) {
        filePath.replace("/", System.getProperty("file.separator"));
        filePath.replace("\\", System.getProperty("file.separator"));
        this.openPath = filePath;
    }

    @Override
    public CommandResult execute() {
        File openFile = new File(openPath);
        String filePath = openFile.getAbsolutePath();
        if (FileUtil.isValidFilePath(openPath) && openFile.exists()) {
            if (!filePath.endsWith(".xml")) {
                filePath += ".xml";
            }
            
            EventsCenter.getInstance().post(new OpenFileChooserEvent(filePath));
        } else {
            EventsCenter.getInstance().post(new OpenFileChooserEvent(""));
        }
        
        if (filePath.equals(XmlAddressBookStorage.getFilePathForSaveCommand())) {
            return new CommandResult(String.format(OPEN_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        } else {
            return new CommandResult(NO_SUCH_FILE_MESSAGE);
        }
    }

}
