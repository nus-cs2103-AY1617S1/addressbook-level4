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

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.jimiParser = new JimiParser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = jimiParser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
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
        
        daysTaskList.add(model.getFilteredMondayTaskList());
        daysTaskList.add(model.getFilteredTuesdayTaskList());
        daysTaskList.add(model.getFilteredWednesdayTaskList());
        daysTaskList.add(model.getFilteredThursdayTaskList());
        daysTaskList.add(model.getFilteredFridayTaskList());
        daysTaskList.add(model.getFilteredSaturdayTaskList());
        daysTaskList.add(model.getFilteredSundayTaskList());
        
        return daysTaskList;
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredMondayTaskList() {
        return model.getFilteredMondayTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTuesdayTaskList() {
        return model.getFilteredTuesdayTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredWednesdayTaskList() {
        return model.getFilteredWednesdayTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredThursdayTaskList() {
        return model.getFilteredThursdayTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredFridayTaskList() {
        return model.getFilteredFridayTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredSaturdayTaskList() {
        return model.getFilteredSaturdayTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredSundayTaskList() {
        return model.getFilteredSundayTaskList();
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
