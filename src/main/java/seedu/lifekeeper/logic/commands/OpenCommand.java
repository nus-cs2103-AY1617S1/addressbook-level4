package seedu.lifekeeper.logic.commands;

import java.io.File;

import seedu.lifekeeper.commons.core.EventsCenter;
import seedu.lifekeeper.commons.events.ui.OpenFileChooserEvent;
import seedu.lifekeeper.commons.util.FileUtil;
import seedu.lifekeeper.storage.XmlAddressBookStorage;

//@@author A0125680H
/**
* Loads data from the specified file into the Lifekeeper App.
*/
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
        
        if (!filePath.endsWith(".xml")) {
            filePath += ".xml";
        }
        
        this.openPath = filePath;
    }

    @Override
    public CommandResult execute() {
        String oldPath = XmlAddressBookStorage.getFilePathForSaveCommand();
        File openFile = new File(openPath);
        String filePath = openFile.getAbsolutePath();
        
        if (FileUtil.isValidFilePath(openPath) && openFile.exists()) {
            EventsCenter.getInstance().post(new OpenFileChooserEvent(filePath));
        } else {
            EventsCenter.getInstance().post(new OpenFileChooserEvent(""));
        }
        
        if (oldPath.equals(XmlAddressBookStorage.getFilePathForSaveCommand())) {
            return new CommandResult(NO_SUCH_FILE_MESSAGE);
        } else {
            return new CommandResult(String.format(OPEN_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        }
    }

}
