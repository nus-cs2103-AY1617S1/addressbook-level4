package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.model.SaveState;
import seedu.address.model.TaskBook;


//@@author A0147890U
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undid Task";

    private int numTimes;

    public UndoCommand() {
    }

    public UndoCommand(int numTimes) {
        this.numTimes = numTimes;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        for (int i = 0; i < numTimes; i++) {
            TaskBook currentTaskBook = new TaskBook(model.getAddressBook());
            
            SaveState saveToResetTo = model.getUndoStack().pop();
            TaskBook taskToResetTo = saveToResetTo.getSaveStateTaskBook();
            model.resetData(taskToResetTo);
            
            Config currentConfig = new Config(model.getConfig());
            Config config = saveToResetTo.getSaveStateConfig();
            model.setConfig(config);
            
     //       System.out.println(config.getAddressBookFilePath());
            try {
                ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
               // System.out.println("This is supposed to print");
            } catch (IOException e) {
               // System.out.println("oops i did it again");
            }
         //   System.out.println(config.getAddressBookFilePath());
            SaveState saveToBeAdded = new SaveState(currentTaskBook, currentConfig);
            model.getRedoStack().push(saveToBeAdded);
        }
        return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
}
