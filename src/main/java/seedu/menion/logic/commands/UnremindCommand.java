package seedu.menion.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import seedu.menion.commons.core.Messages;

public class UnremindCommand extends Command {

    public static final String COMMAND_WORD = "unremind";
    public static final String REMINDER_OFF = "false";
    public static final String MESSAGE_REMINDER_UNSET_SUCCESS = "Reminders are unset! You will not be receiving anymore emails from Menion";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Menion will stop sending you notifications!\n"
            + "Example: " + COMMAND_WORD;
    public static final String REMIND_NOT_SET_ERROR = "Error! Menion cannot help you unset reminders if you have yet to set any!";

    @Override
    public CommandResult execute() {

        try {
            // Retrieve the email of the user from the txt file.
            Scanner fromFile = new Scanner(new File(Messages.MESSAGE_FILE));
            String isRemindOn = fromFile.next();
            if (isRemindOn.equals(REMINDER_OFF)) {
                return new CommandResult(REMIND_NOT_SET_ERROR);
            }
            fromFile.nextLine(); // Skips a line.
            String userEmail = fromFile.nextLine();
            
            // Writes userEmail to a file.
            // Saves file
            PrintStream out;
            StringBuilder build = new StringBuilder();
            build.append(REMINDER_OFF); // Turns off reminder
            build.append("\n");

            out = new PrintStream(new FileOutputStream(Messages.MESSAGE_FILE));
            build.append(userEmail);
            out.print(build.toString()); // puts the UserEmail into the txt file
        } catch (FileNotFoundException e) {
            return new CommandResult(REMIND_NOT_SET_ERROR);
        }
        return new CommandResult(MESSAGE_REMINDER_UNSET_SUCCESS);
    }

}
