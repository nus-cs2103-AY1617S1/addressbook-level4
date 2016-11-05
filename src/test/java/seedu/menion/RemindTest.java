package seedu.menion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.commons.core.Messages;
import seedu.menion.logic.commands.Command;
import seedu.menion.logic.commands.RemindCommand;
import seedu.menion.logic.commands.UnremindCommand;
import seedu.menion.model.Model;
import seedu.menion.model.ReadOnlyActivityManager;

//@@author A0139164A
public class RemindTest {
    Model model;
    ReadOnlyActivityManager testManager;

    @Test
    public void remind() {
        // For the purpose of testing we will store the existing data of email.txt file (If any)
        // If there is no email.txt, we will create a new one. And delete it after.
        
        String testEmail = "ThisIsATestEmail@gmail.com";
        String testRemindOn = RemindCommand.REMINDER_ON;
        String testRemindOff = UnremindCommand.REMINDER_OFF;

        String userEmail = "";
        String remind = "";
        boolean needRefresh = false;
        Scanner fromFile;
        try {
            fromFile = new Scanner(new File(Messages.MESSAGE_FILE));
            remind = fromFile.next();
            fromFile.nextLine(); // Skips a line.
            userEmail = fromFile.nextLine();
            fromFile.close(); // close input file stream
            needRefresh = true;
        } catch (FileNotFoundException e) {
            needRefresh = false;
        }
        // Start testing
        assertRemindSuccess(testEmail, testRemindOn);
        assertUnremindSuccess(testEmail, testRemindOff);

        // Testing done, reverting back to previous state
        if (needRefresh) {
            resetData(userEmail, remind);
        } else {
            // delete file
            File file = new File(Messages.MESSAGE_FILE);
            file.delete();
        }
    }

    private void assertRemindSuccess(String testEmail, String testRemindOn) {
        Command remindCommand = new RemindCommand(testEmail); // Calls the
                                                              // remind command.
        remindCommand.execute();
        Scanner fromTestFile;
        try {
            fromTestFile = new Scanner(new File(Messages.MESSAGE_FILE));
            String remindRead = fromTestFile.next();
            fromTestFile.nextLine(); // Skips a line.
            String emailRead = fromTestFile.nextLine();
            fromTestFile.close(); // close input file stream
            boolean sameEmail = testEmail.equals(emailRead);
            boolean sameRemind = testRemindOn.equals(remindRead);
            assertEquals(true, sameEmail);
            assertEquals(true, sameRemind);
        } catch (FileNotFoundException e) {
            // Should not get here.
            e.printStackTrace();
        }

    }

    private void assertUnremindSuccess(String testEmail, String testRemindOff) {
        Command unremindCommand = new UnremindCommand();
        unremindCommand.execute();
        Scanner fromTestFile;
        try {
            fromTestFile = new Scanner(new File(Messages.MESSAGE_FILE));
            String remindRead = fromTestFile.next();
            fromTestFile.nextLine(); // Skips a line.
            String emailRead = fromTestFile.nextLine();
            fromTestFile.close(); // close input file stream
            boolean sameEmail = testEmail.equals(emailRead);
            boolean sameRemind = testRemindOff.equals(remindRead);
            assertEquals(true, sameEmail);
            assertEquals(true, sameRemind);
        } catch (FileNotFoundException e) {
            // Should not get here.
            e.printStackTrace();
        }

    }

    private void resetData(String email, String remind) {
        // Writes userEmail to a file.
        // Saves file
        PrintStream out;
        StringBuilder build = new StringBuilder();
        build.append(remind);
        build.append("\n");
      
        try {
            out = new PrintStream(new FileOutputStream(Messages.MESSAGE_FILE));
            build.append(email);
            out.print(build.toString()); // puts the UserEmail into the txt file.
        } catch (Exception e) {
            // Should not get here.
        }
    }
}
