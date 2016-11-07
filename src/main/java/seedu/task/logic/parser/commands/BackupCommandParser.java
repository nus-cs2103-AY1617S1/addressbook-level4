package seedu.task.logic.parser.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.util.FilePickerUtil;
import seedu.task.logic.commands.BackupCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

// @@author A0147944U
public class BackupCommandParser {

    private static final Pattern DIRECTORY_ARGS_FORMAT = Pattern.compile("(?<directory>[^<>|]+)");

    /**
     * Parses arguments in the context of the backup command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    public static Command prepareBackup(String args) {
        final Matcher matcher = DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            String directory = FilePickerUtil.saveXMLFile();
            if (directory == null || directory.equals("")) {
                return new IncorrectCommand("Aborted backup command");
            }
            return new BackupCommand(directory);
        }
        return new BackupCommand(matcher.group("directory"));
    }
}
