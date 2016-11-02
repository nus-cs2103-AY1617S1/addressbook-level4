package seedu.address.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.model.SaveState;
import seedu.address.model.TaskBook;


//@@author A0147890U
public class UndoCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(UndoCommand.class);

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo successful.";
    public static final String MESSAGE_UNDO_TASK_FAILURE = "Failed to undo task.";

    private int numTimes;

    public UndoCommand() {
    }

    public UndoCommand(int numTimes) {
        this.numTimes = numTimes;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        if (numTimes > model.getUndoStack().size()) {
            Command command = new IncorrectCommand("There are not so many tasks available to be undone.");
            return command.execute();
        }
        for (int i = 0; i < numTimes; i++) {
            TaskBook currentTaskBook = new TaskBook(model.getAddressBook());
            
            SaveState saveToResetTo = model.getUndoStack().pop();
            TaskBook taskToResetTo = saveToResetTo.getSaveStateTaskBook();
            model.resetData(taskToResetTo);
            
            Config currentConfig = new Config(model.getConfig());
            Config config = saveToResetTo.getSaveStateConfig();
            model.setConfig(config);
            
            try {
                ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            } catch (IOException e) {
                logger.warning("config file could not be saved to");
            }
            SaveState saveToBeAdded = new SaveState(currentTaskBook, currentConfig);
            model.getCommandHistory().add("undo");
            model.getRedoStack().push(saveToBeAdded);
        }
        return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
}
