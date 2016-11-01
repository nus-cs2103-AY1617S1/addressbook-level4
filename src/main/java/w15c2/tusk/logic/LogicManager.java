package w15c2.tusk.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import w15c2.tusk.commons.core.ComponentManager;
import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.logic.autocomplete.AutocompleteEngine;
import w15c2.tusk.logic.autocomplete.AutocompleteResult;
import w15c2.tusk.logic.autocomplete.AutocompleteSource;
import w15c2.tusk.logic.commands.CommandHistory;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;
import w15c2.tusk.logic.parser.TaskCommandsParser;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.task.InMemoryTaskList;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.storage.task.TaskStorage;

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
    public ObservableList<String> getHelpList() {
        return model.getHelpList();
    }
    
  //@@author A0138978E
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
