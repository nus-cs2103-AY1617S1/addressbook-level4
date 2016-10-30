package seedu.menion.background;

import seedu.menion.logic.commands.Command;
import seedu.menion.logic.commands.CommandResult;

//@@author A0139164A
public class SetEmailSent extends Command {
    
    private int targetIndex;
    private boolean isSent;
    
    public SetEmailSent(boolean isSent, int targetIndex) {
        this.targetIndex = targetIndex;
        this.isSent = isSent;
        System.out.println("This is index: " + targetIndex);
        System.out.println("This is isSent: " + isSent);
    }
    
    @Override
    public CommandResult execute() {
        // THE PROBLEM HERE IS, MODEL IS NULL!
        System.out.println("I'm here and : " + model.toString());
        model.setEmailSent(isSent, targetIndex);
        return null;
    }

}
