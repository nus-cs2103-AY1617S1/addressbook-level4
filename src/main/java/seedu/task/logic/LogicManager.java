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

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandParser parser;
    private HistoryList historyList;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new CommandParser();
        historyList = new HistoryList();
        historyList.setPreviousCommandCounter(1);
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        
        command.setData(model);
        if (command instanceof IncorrectCommand) {
            return command.execute(false);
        }
        logger.info("SUCCESS");
        if(!commandText.equals("undo")){
            HistoryList.getPreviousCommand().add(commandText);
            for(int i = 0; i < historyList.getPreviousCommand().size(); i++){
                logger.info("" + historyList.getPreviousCommand().get(i));
            }
            return command.execute(false);
        }
        else{
            for(int i = 0; i < historyList.getPreviousCommand().size(); i++){
                logger.info("" + historyList.getPreviousCommand().get(i));
            }
            return command.execute(true);
        }
        
        
    }
    
    public HistoryList getHistoryList(){
        return historyList;
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
