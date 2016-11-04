package seedu.simply.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.simply.commons.core.Config;
import seedu.simply.commons.core.LogsCenter;
import seedu.simply.commons.util.ConfigUtil;
import seedu.simply.model.SaveState;
import seedu.simply.model.TaskBook;

//@@author A0147890U
/**
 * 
 * @author Ronald
 *Redo undone commands
 */
public class RedoCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(RedoCommand.class);
    public static final String COMMAND_WORD = "redo";
    
    public static final String MESSAGE_REDO_TASK_SUCCESS = "Redo successful.";
    public static final String MESSAGE_REDO_TASK_FAILURE = "Failed to redo task.";
    
    private int numTimes;
    
    public RedoCommand() {};
    
    public RedoCommand(int numTimes) {
        this.numTimes = numTimes;
        
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        
        if (numTimes > model.getRedoStack().size()) {
            Command command = new IncorrectCommand("There are not so many tasks available to be redone.");
            return command.execute();
        }
        
        for (int i = 0; i < numTimes; i++) {
            TaskBook currentTaskBook = new TaskBook(model.getAddressBook());
            
            
            SaveState saveToResetTo = model.getRedoStack().pop();
            TaskBook taskToResetTo = saveToResetTo.getSaveStateTaskBook();
            model.resetData(taskToResetTo);
            
            Config currentConfig = new Config(model.getConfig());
            Config config = saveToResetTo.getSaveStateConfig();
            model.setConfig(config);
            
            System.out.println(config.getTaskBookFilePath());
            try {
                ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            } catch (IOException e) {
                logger.warning("config file could not be saved to");
            }
           
            SaveState saveToBeAdded = new SaveState(currentTaskBook, currentConfig);
            model.getCommandHistory().add("redo");
            model.getUndoStack().push(saveToBeAdded);
        }
        return new CommandResult(MESSAGE_REDO_TASK_SUCCESS);
    }
    
}
