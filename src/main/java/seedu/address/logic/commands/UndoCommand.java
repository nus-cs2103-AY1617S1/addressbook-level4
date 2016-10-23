package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.model.SaveState;
import seedu.address.model.TaskBook;

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
            
            SaveState saveToResetTo = undoStack.pop();
            TaskBook taskToResetTo = saveToResetTo.getSaveStateTaskBook();
            model.resetData(taskToResetTo);
            
            config = saveToResetTo.getSaveStateConfig();
            System.out.println(config.getAddressBookFilePath());
            try {
                ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
                System.out.println("This is supposed to print");
            } catch (IOException e) {
                System.out.println("oops i did it again");
            }
            
            Config currentConfig = new Config(config);
            SaveState saveToBeAdded = new SaveState(currentTaskBook, currentConfig);
            redoStack.push(saveToBeAdded);
        }
        return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
}
