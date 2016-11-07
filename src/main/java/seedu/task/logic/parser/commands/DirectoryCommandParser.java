package seedu.task.logic.parser.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.util.FilePickerUtil;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DirectoryCommand;
import seedu.task.logic.commands.IncorrectCommand;

// @@author A0147944U
public class DirectoryCommandParser {

    private static final Pattern DIRECTORY_ARGS_FORMAT = Pattern.compile("(?<directory>[^<>|]+)");

    /**
     * Parses arguments in the context of the directory command.
     * 
     * @param args
     *            full command args string
     * @return the prepared command
     */
    public static Command prepareDirectory(String args) {
        final Matcher matcher = DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            String directory = FilePickerUtil.openXMLFile();
            if (directory == null || "".equals(directory)) {
                return new IncorrectCommand("Aborted directory command");
            }
            return new DirectoryCommand(directory);
        }
        return new DirectoryCommand(matcher.group("directory"));
    }
}
