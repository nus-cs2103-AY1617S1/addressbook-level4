package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.autocomplete.AutocompleteEngine;
import seedu.address.logic.autocomplete.AutocompleteResult;
import seedu.address.logic.autocomplete.AutocompleteSource;
import seedu.address.logic.commands.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.taskcommands.TaskCommand;
import seedu.address.logic.parser.TaskCommandsParser;
import seedu.address.model.Alias;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.Task;
import seedu.address.storage.task.TaskStorage;

/**
 * The main LogicManager_Task of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final InMemoryTaskList model;
    private final TaskCommandsParser parser;
    private final CommandHistory commandHistory;
    private final AutocompleteEngine autocompleteEngine;
    private AutocompleteResult currentAutocompleteResult;

    public LogicManager(InMemoryTaskList model, TaskStorage storage) {
        this.model = model;
        this.parser = new TaskCommandsParser();
        this.commandHistory = new CommandHistory();
        this.autocompleteEngine = new AutocompleteEngine(AutocompleteSource.getCommands());
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        commandHistory.addCommandTextToHistory(commandText);
        ReplaceAlias r = new ReplaceAlias(model);
        commandText = r.getAliasCommandText(commandText);
        TaskCommand command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
    	return model.getCurrentFilteredTasks();
    }

    @Override
    public ObservableList<Alias> getAlias() {
        return model.getAlias();
    }
    
    @Override
    public String getPreviousCommand() {
    	return commandHistory.getPreviousCommand();
    }
    
    @Override
    public String getNextCommand() {
    	return commandHistory.getNextCommand();
    }

	@Override
	public void setTextToAutocomplete(String text) {
		currentAutocompleteResult = autocompleteEngine.getQueryResult(text);		
	}

	@Override
	public String getNextAutocompleteSuggestion() {
		assert currentAutocompleteResult != null;
		
		return currentAutocompleteResult.getNextMatch();
		
	}


}
