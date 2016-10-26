/**
 * 
 */
package seedu.jimi.logic;

import java.util.Stack;

import seedu.jimi.logic.commands.Command;
import seedu.jimi.logic.commands.CommandResult;
import seedu.jimi.logic.commands.TaskBookEditor;

/**
 * @@author A0148040R
 * 
 * History of the operations
 * This is a singleton class
 */
public final class History {
    
    private static History instance = null;
    private final Stack<Context> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    
    private static final String MESSAGE_REACHED_UNDO_LIMIT = "No more commands to undo!";
    private static final String MESSAGE_REACHED_REDO_LIMIT = "No more commands to redo!";
    
    private History() {}
    
    public CommandResult undo() {
        assert undoStack.peek().cmd instanceof TaskBookEditor;
        if (!undoStack.isEmpty()) {
            Context previous = undoStack.pop();
            previous.cmd.undo();
            redoStack.push(previous.cmd);   
            return previous.result;
        }
        return new CommandResult(MESSAGE_REACHED_UNDO_LIMIT);
    }
    
    public CommandResult redo() {
        assert redoStack.peek() instanceof TaskBookEditor;
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            CommandResult result = cmd.execute();
            undoStack.push(new Context(cmd, result));
            return result;
        }
        return new CommandResult(MESSAGE_REACHED_REDO_LIMIT);
    }
    
    public void execute(final Command cmd, final CommandResult result) {
        if (cmd instanceof TaskBookEditor) {
            undoStack.push(new Context(cmd, result));
            redoStack.clear();
        }
    }
    
    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }
    
    private class Context {
        private Command cmd;
        private CommandResult result;
        
        private Context(final Command cmd, final CommandResult result) {
            this.cmd = cmd;
            this.result = result;
        }
    }
    
}
