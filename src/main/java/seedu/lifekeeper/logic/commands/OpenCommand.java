package seedu.lifekeeper.logic.commands;

import java.io.File;

import seedu.lifekeeper.commons.core.EventsCenter;
import seedu.lifekeeper.commons.events.ui.OpenFileChooserEvent;
import seedu.lifekeeper.commons.util.FileUtil;
import seedu.lifekeeper.storage.XmlAddressBookStorage;

public class OpenCommand extends Command {
    public static final String COMMAND_WORD = "open";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows the save file location to be specified.\n"
            + "File name cannot consist of any of the following characters: '<', '>', ':', '\"', '|', '?', '*'\n"
            + "Example: " + COMMAND_WORD + " folder" + System.getProperty("file.separator") + "file.xml";

    public static final String OPEN_MESSAGE = "Loaded data from file: %1$s";
    
    private final String openPath;

    public OpenCommand(String filePath) {
        filePath.replace("/", System.getProperty("file.separator"));
        filePath.replace("\\", System.getProperty("file.separator"));
        this.openPath = filePath;
    }

    @Override
    public CommandResult execute() {
        File openFile = new File(openPath);
        if (FileUtil.isValidFilePath(openPath)) {
            String filePath = openFile.getAbsolutePath();
            
            if (!filePath.endsWith(".xml")) {
                filePath += ".xml";
            }
            
            EventsCenter.getInstance().post(new OpenFileChooserEvent(filePath));
            return new CommandResult(String.format(OPEN_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        } else {
            EventsCenter.getInstance().post(new OpenFileChooserEvent(""));
            return new CommandResult(String.format(OPEN_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        }
    }

}
