package harmony.mastermind.logic;

/**
 * @@author A0139194X
 * Class to store entries into the Help Popup table.
 */
public class HelpPopupEntry {
    private String commandWord;
    private String format;
    private String description;
    
    public HelpPopupEntry(String commandWord, String format, String description) {
        this.commandWord = commandWord;
        this.format = format;
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getDescription() {
        return description;
    }
}