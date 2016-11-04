package seedu.address.logic.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SaveEvent;
import seedu.address.storage.XmlAddressBookStorage;

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows the save file location to be specified.\n"
            + "File name cannot consist of any of the following characters: '<', '>', ':', '\"', '|', '?', '*'\n"
            + "Example: " + COMMAND_WORD + " folder" + System.getProperty("file.separator") + "file.xml";

    public static final String SHOWING_HELP_MESSAGE = "New save location: %1$s";
    
    private static final ArrayList<Character> FORBIDDEN_CHARACTERS = new ArrayList<>(
            Arrays.asList('<', '>', ':', '"', '|', '?', '*'));
    
    private final String savePath;

    public SaveCommand(String filePath) {
        filePath.replace("/", System.getProperty("file.separator"));
        filePath.replace("\\", System.getProperty("file.separator"));
        this.savePath = filePath;
    }

    @Override
    public CommandResult execute() {
        File saveFile = new File(savePath);
        System.out.println(saveFile.getAbsolutePath());
        if (isValidPath()) {
            String filePath = saveFile.getAbsolutePath();
            
            if (!filePath.endsWith(".xml")) {
                filePath.concat(".xml");
            }
            
            EventsCenter.getInstance().post(new SaveEvent(filePath));
            return new CommandResult(String.format(SHOWING_HELP_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        } else {
            EventsCenter.getInstance().post(new SaveEvent(""));
            return new CommandResult(String.format(SHOWING_HELP_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        }
    }
    
    private boolean isValidPath() {
        if (savePath.equals("")) {
            return false;
        } else {
            for (char forbidden : FORBIDDEN_CHARACTERS) {
                if (savePath.contains(String.valueOf(forbidden))) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
