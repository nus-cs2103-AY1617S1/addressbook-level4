package seedu.savvytasker.logic;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.events.model.AliasSymbolChangedEvent;
import seedu.savvytasker.commons.events.ui.ChangeListRequestEvent;
import seedu.savvytasker.commons.events.ui.ChangeListRequestEvent.DisplayedList;
import seedu.savvytasker.logic.commands.Command;
import seedu.savvytasker.logic.commands.CommandResult;
import seedu.savvytasker.logic.commands.ListCommand;
import seedu.savvytasker.logic.parser.AddCommandParser;
import seedu.savvytasker.logic.parser.AliasCommandParser;
import seedu.savvytasker.logic.parser.ClearCommandParser;
import seedu.savvytasker.logic.parser.DeleteCommandParser;
import seedu.savvytasker.logic.parser.ExitCommandParser;
import seedu.savvytasker.logic.parser.FindCommandParser;
import seedu.savvytasker.logic.parser.HelpCommandParser;
import seedu.savvytasker.logic.parser.ListCommandParser;
import seedu.savvytasker.logic.parser.MarkCommandParser;
import seedu.savvytasker.logic.parser.MasterParser;
import seedu.savvytasker.logic.parser.ModifyCommandParser;
import seedu.savvytasker.logic.parser.RedoCommandParser;
import seedu.savvytasker.logic.parser.StorageCommandParser;
import seedu.savvytasker.logic.parser.UnaliasCommandParser;
import seedu.savvytasker.logic.parser.UndoCommandParser;
import seedu.savvytasker.logic.parser.UnmarkCommandParser;
import seedu.savvytasker.model.Model;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final int MAX_UNDO_REDO_QUEUE_SIZE = 50;
    
    private final Model model;
    private final Storage storage;
    private final MasterParser parser;
    private final Deque<Command> undoDeque;
    private final Deque<Command> redoDeque;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.parser = new MasterParser();
        this.undoDeque = new LinkedList<Command>();
        this.redoDeque = new LinkedList<Command>();
        
        registerAllDefaultCommandParsers();
        loadAllAliasSymbols();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parse(commandText);
        command.setModel(model);
        command.setLogic(this);
        command.setStorage(storage);
        
        CommandResult result = command.execute();
        
        if (!(command instanceof ListCommand)) {
            // forcefully show the task list instead
            EventsCenter.getInstance().post(new ChangeListRequestEvent(DisplayedList.Task));
        }
        
        //@@author A0097627N
        if (command.isUndo()){
            if (!undo()) {
                result = new CommandResult("Cannot Undo");
            }
        }   
        else if (command.isRedo()){
            if (!redo()) {
                result = new CommandResult("Cannot Redo");
            }
        }
        else if (command.canUndo()){
            //@@author A0139916U
            undoDeque.addLast(command);
            if (undoDeque.size() > MAX_UNDO_REDO_QUEUE_SIZE) {
                undoDeque.removeFirst();
            }
            redoDeque.clear();
        }
        //@@author
        
        return result;
    }
    
    //@@author A0139915W
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    @Override
    public ObservableList<AliasSymbol> getAliasSymbolList() {
        return parser.getAliasSymbolList();
    }    
    //@@author

    //@@author A0138431L

    @Override
    public ObservableList<ReadOnlyTask> getFilteredFloatingTasks() {
        return model.getFilteredFloatingTasks();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredDailyTasks(int i, Date date) {
        return model.getFilteredDailyTasks(i, date);
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredUpcomingTasks(Date date) {
        return model.getFilteredUpcomingTasks(date);
    }
    //@@author
    
    //@@author A0139916U
    private void registerAllDefaultCommandParsers() {
        parser.registerCommandParser(new AddCommandParser());
        parser.registerCommandParser(new DeleteCommandParser());
        parser.registerCommandParser(new FindCommandParser());
        parser.registerCommandParser(new ListCommandParser());
        parser.registerCommandParser(new ModifyCommandParser());
        parser.registerCommandParser(new HelpCommandParser());
        parser.registerCommandParser(new ClearCommandParser());
        parser.registerCommandParser(new ExitCommandParser());
        parser.registerCommandParser(new MarkCommandParser());
        parser.registerCommandParser(new UnmarkCommandParser());
        parser.registerCommandParser(new UndoCommandParser());
        parser.registerCommandParser(new RedoCommandParser());
        parser.registerCommandParser(new AliasCommandParser());
        parser.registerCommandParser(new UnaliasCommandParser());
        parser.registerCommandParser(new StorageCommandParser());
    }
    
    private void loadAllAliasSymbols() {
        List<AliasSymbol> allSynbols = model.getSavvyTasker().getReadOnlyListOfAliasSymbols();
        for (AliasSymbol symbol : allSynbols) {
            parser.addAliasSymbol(symbol);
        }
    }
    
    /**
     * Undo last command and add it to the redo deque.
     * @return true if undone successfully, false otherwise
     */
    private boolean undo() {
        boolean undone = false;
        
        if (!undoDeque.isEmpty()) {
            Command command = undoDeque.removeLast();
            command.undo();
            redoDeque.addLast(command);
            undone = true;
        }
        
        return undone;
    }

    /**
     * Redo last command and add it to undone deque.
     * @return true if redone successfully, false otherwise
     */
    private boolean redo() {
        boolean redone = false;
        
        if (!redoDeque.isEmpty()) {
            Command command = redoDeque.removeLast();
            command.redo();
            undoDeque.addLast(command);
            redone = true;
        }
        
        return redone;
    }
    
    /**
     * Log the result of adding/removing a symbol in the parser.
     * 
     * @param success if the operation succeeded
     * @param changedSymbol the symbol that was involved in the operation
     * @param successMsgFormat the message to print if the operation succeeded. It should contain a single
     * %s string format specifier, which will be replaced by the symbol's string representation.
     * @param failureMsgFormat the message to print if the operation failed. It should contain a single
     * %s string format specifier, which will be replaced by the symbol's string representation.
     */
    private void logParserSymbolChange(boolean success, AliasSymbol changedSymbol,
            String successMsgFormat, String failureMsgFormat) {
        if (success) {
            logger.info(String.format(successMsgFormat, changedSymbol));
        } else {
            logger.warning(String.format(failureMsgFormat, changedSymbol));
        }
    }
    
    @Subscribe
    public void handleAliasSymbolChangedEvent(AliasSymbolChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(
                event, "Alias symbol " + event.getAction().toString().toLowerCase()));
        
        if (event.getAction().equals(AliasSymbolChangedEvent.Action.Added)) {
            logParserSymbolChange(
                    parser.addAliasSymbol(event.getSymbol()),
                    event.getSymbol(),
                    "Added alias symbol '%s' to parser",
                    "Failed to add alias symbol '%s' to parser");
        } else if (event.getAction().equals(AliasSymbolChangedEvent.Action.Removed)) {
            logParserSymbolChange(
                    parser.removeAliasSymbol(event.getSymbol().getKeyword()),
                    event.getSymbol(),
                    "Removed alias symbol '%s' from parser",
                    "Failed to remove alias symbol '%s' from parser");
        }
    }
    
    @Override
    public boolean canParseHeader(String header) {
        return parser.isCommandParserRegistered(header);
    }
}
