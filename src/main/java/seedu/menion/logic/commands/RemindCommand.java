package seedu.menion.logic.commands;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import seedu.menion.commons.core.Messages;

public class RemindCommand extends Command{
    
    public static final String COMMAND_WORD = "remind";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Menion will automatically send you an email when you miss the deadline of a task!\n"
                                                            + "Example: " + COMMAND_WORD + " [Your email]";
    public static final String MESSAGE_REMINDER_SET_SUCCESS = "Reminders set!";
    public static final String MESSAGE_FILE = "Email.txt"; // Name of file to store User email.
    public static final String MESSAGE_PROBLEM_ENCOUNTERED = "Oh no! Menion has encountered a problem, try restarting it";
    
    private String userEmail;
    
    public RemindCommand(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public CommandResult execute() {
        // Writes userEmail to a file.
        // Saves file
        PrintStream out;
        try {
            out = new PrintStream(new FileOutputStream(MESSAGE_FILE));
            out.print(this.userEmail); // puts the UserEmail into the txt file.
        } catch (FileNotFoundException e) {
            return new CommandResult(MESSAGE_PROBLEM_ENCOUNTERED);
        }
        return new CommandResult(MESSAGE_REMINDER_SET_SUCCESS);
    }
    
}
