package seedu.savvytasker.logic;

import javafx.collections.ObservableList;
import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.logic.commands.Command;
import seedu.savvytasker.logic.commands.CommandResult;
import seedu.savvytasker.logic.parser.*;
import seedu.savvytasker.model.Model;
import seedu.savvytasker.model.person.ReadOnlyTask;
import seedu.savvytasker.storage.Storage;

import java.util.Stack;
import java.util.logging.Logger;

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
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parse(commandText);
        command.setData(model);
        
        CommandResult result = command.execute();
        if (command.canUndo()) {
            undoStack.push(command);
            redoStack.clear();
        }
        
        return result;
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
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
    }

    @Override
    public boolean undo() {
        boolean undone = false;
        
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            undone = true;
        }
        
        return undone;
    }

    @Override
    public boolean redo() {
        boolean redone = false;
        
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.redo();
            undoStack.push(command);
            redone = true;
        }
        
        return redone;
    }
    
    public void addPreprocessSymbol(String symbol, String representation) {
        assert symbol != null;
        assert representation != null;
        
        
    }
    
    public void removePreprocessingSymbol(String symbol) {
        assert symbol != null;
    }
}
