package seedu.savvytasker.logic;

import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.savvytasker.MainApp;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.events.model.AliasSymbolChangedEvent;
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
import seedu.savvytasker.logic.parser.UnaliasCommandParser;
import seedu.savvytasker.logic.parser.UndoCommandParser;
import seedu.savvytasker.logic.parser.UnmarkCommandParser;
import seedu.savvytasker.model.Model;
import seedu.savvytasker.model.alias.AliasSymbol;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.storage.Storage;
import seedu.savvytasker.ui.Ui;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final MasterParser parser;
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new MasterParser();
        this.undoStack = new Stack<Command>();
        this.redoStack = new Stack<Command>();
        
        registerAllDefaultCommandParsers();
        loadAllAliasSymbols();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parse(commandText);
        command.setModel(model);
        command.setLogic(this);
        
        CommandResult result = command.execute();
        
        if (!(command instanceof ListCommand)) {
            // forcefully show the task list instead
            Ui uiManager = MainApp.getUiManager();
            if (uiManager != null) {
                uiManager.showTaskList(true);
            }
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
            undoStack.push(command);
            redoStack.clear();
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
    }
    
    private void loadAllAliasSymbols() {
        List<AliasSymbol> allSynbols = model.getSavvyTasker().getReadOnlyListOfAliasSymbols();
        for (AliasSymbol symbol : allSynbols) {
            parser.addAliasSymbol(symbol);
        }
    }
    
    private boolean undo() {
        boolean undone = false;
        
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            undone = true;
        }
        
        return undone;
    }

    private boolean redo() {
        boolean redone = false;
        
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.redo();
            undoStack.push(command);
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
