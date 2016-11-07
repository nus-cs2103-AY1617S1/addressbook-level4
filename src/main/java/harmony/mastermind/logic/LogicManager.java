package harmony.mastermind.logic;

import java.util.logging.Logger;

import harmony.mastermind.commons.core.ComponentManager;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.logic.commands.Command;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.parser.Parser;
import harmony.mastermind.model.Model;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.storage.Storage;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.parser = new Parser();
    }

    @Override
    //@@author A0124797R
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, storage);
        CommandResult result = command.execute();
        return result;
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredFloatingTaskList() {
        return model.getFilteredFloatingTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredEventList() {
        return model.getFilteredEventList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return model.getFilteredDeadlineList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredArchiveList() {
        return model.getFilteredArchiveList();
    }

    //@@author A0124797R-unused
    // disable the use of importing txt file
    /**
     * parse the result of commands and handle ImportCommand separately
     */
    /*
    private CommandResult parseResult(Command cmd) {
        CommandResult result = cmd.execute();
        if (result.feedbackToUser.equals(ImportCommand.MESSAGE_READ_SUCCESS)) {
            result = handleImport((ImportCommand) cmd);
        }

        return result;
    }
    
    /**
     * handle the inputs from the reading of file from ImportCommand
     */
    /*
    public CommandResult handleImport(ImportCommand command) {
        ArrayList<String> lstOfCmd = command.getTaskToAdd();
        String errLines = "";
        int errCount = 0;
        int lineCounter = 0;
        
        for (String cmd: lstOfCmd) {
            lineCounter += 1;
            Command cmdResult = parser.parseCommand(cmd);
            cmdResult.setData(model, storage);
            CommandResult addResult = cmdResult.execute();
            boolean isFailure = isAddFailure(addResult);
            
            if (isFailure) {
                errCount += 1;
                errLines += Integer.toString(lineCounter) + ",";
            }
        }
        
        int addCount = lineCounter - errCount;
        if (errLines.isEmpty()) {
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(ImportCommand.MESSAGE_IMPORT_TXT_SUCCESS, addCount));
        } else {
            errLines = errLines.substring(0,errLines.length()-1);
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(ImportCommand.MESSAGE_IMPORT_TXT_FAILURE, addCount, errLines));
        }
        
    }
    */
    
    
    /**
     * Checks if adding of tasks from ImportCommand is valid
     */
    /*
    private boolean isAddFailure(CommandResult cmdResult) {
        if (cmdResult.toString().substring(MESSAGE_START_INDEX, MESSAGE_END_INDEX).equals(AddCommand.MESSAGE_SUCCESS.substring(MESSAGE_START_INDEX, MESSAGE_END_INDEX))) {
            return false;
        }
        
        return true;
    }
    */
}
