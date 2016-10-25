package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.parser.CommandParser;
import seedu.task.model.Model;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.storage.Storage;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandParser parser;
    private final HistoryManager historyManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new CommandParser();
        this.historyManager = new HistoryManager();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        
        command.setData(model);
        command.setHistory(historyManager);
        if (command != null && command instanceof IncorrectCommand) {
            return command.execute(false);
        } else if (command == null && command instanceof IncorrectCommand) {
            return command.execute(false);
        }
        logger.info("SUCCESS");
        if(!commandText.equals("undo")){
            historyManager.getPreviousCommandList().add(commandText);
            for(int i = 0; i < historyManager.getPreviousCommandList().size(); i++){
                logger.info("" + historyManager.getPreviousCommandList().get(i));
            }
            return command.execute(false);
        }
        else{
            for(int i = 0; i < historyManager.getPreviousCommandList().size(); i++){
                logger.info("" + historyManager.getPreviousCommandList().get(i));
            }
            return command.execute(true);
        }
        
        
    }
    
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ArrayList<RollBackCommand> getUndoList() {
        // TODO Auto-generated method stub
        return historyManager.getUndoList();
    }

    @Override
    public ArrayList<String> getPreviousCommandList() {
        // TODO Auto-generated method stub
        return historyManager.getPreviousCommandList();
    }
}