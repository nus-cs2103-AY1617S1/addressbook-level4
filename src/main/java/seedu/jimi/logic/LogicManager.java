package seedu.jimi.logic;

import javafx.collections.ObservableList;
import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.logic.commands.Command;
import seedu.jimi.logic.commands.CommandResult;
import seedu.jimi.logic.parser.JimiParser;
import seedu.jimi.model.Model;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.storage.Storage;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final JimiParser jimiParser;
    private final History history;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.jimiParser = new JimiParser();
        this.history = History.getInstance();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = jimiParser.parseCommand(commandText);
        history.execute(command);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredFloatingTaskList() {
        return model.getFilteredFloatingTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredCompletedTaskList() {
        return model.getFilteredCompletedTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return model.getFilteredIncompleteTaskList();
    }
    
    @Override
    public ArrayList<ObservableList<ReadOnlyTask>> getFilteredDaysTaskList() {
        
        ArrayList<ObservableList<ReadOnlyTask>> daysTaskList = new ArrayList<>();
        
        daysTaskList.add(model.getFilteredDay1TaskList());
        daysTaskList.add(model.getFilteredDay2TaskList());
        daysTaskList.add(model.getFilteredDay3TaskList());
        daysTaskList.add(model.getFilteredDay4TaskList());
        daysTaskList.add(model.getFilteredDay5TaskList());
        daysTaskList.add(model.getFilteredDay6TaskList());
        daysTaskList.add(model.getFilteredDay7TaskList());
        
        return daysTaskList;
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDay1TaskList() {
        return model.getFilteredDay1TaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDay2TaskList() {
        return model.getFilteredDay2TaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDay3TaskList() {
        return model.getFilteredDay3TaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDay4TaskList() {
        return model.getFilteredDay4TaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDay5TaskList() {
        return model.getFilteredDay5TaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDay6TaskList() {
        return model.getFilteredDay6TaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDay7TaskList() {
        return model.getFilteredDay7TaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredAgendaTaskList() {
        return model.getFilteredAgendaTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredAgendaEventList() {
        return model.getFilteredAgendaEventList();
    }
}
