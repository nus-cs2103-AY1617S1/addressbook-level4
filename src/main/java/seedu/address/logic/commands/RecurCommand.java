package seedu.address.logic.commands;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Undo;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

public class RecurCommand extends Command {

    public static final String COMMAND_WORD = "recur";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recur a task in the scheduler. "
            + "Parameters: [INDEX] every DATE_TIME_INTERVAL until DATE_TIME\n"
            + "Example: " + COMMAND_WORD
            + " 1 every 7 days until next month\n"
            + "Example: " + COMMAND_WORD
            + " every hour until tonight\n";

    public static final String MESSAGE_SUCCESS = "Recur task added: %1$s";
    public static final String MESSAGE_MISSING_TASK = "Invalid index or no previous add command";
    public static final String MESSAGE_FAILURE = "Incorrect recurring specification.";
    
    private final int targetIndex;
    private final String args;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public RecurCommand(int targetIndex, String args) {
        this.targetIndex = targetIndex;
        this.args = args;
    }
    
    public RecurCommand(String args) {
        this(0,args);
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        ReadOnlyTask task;
        task = getTaskFromIndexOrLastModified();
        if (task == null) {
            return new CommandResult(MESSAGE_MISSING_TASK);
        }
        
        try {
            DateGroup dg = new PrettyTimeParser().parseSyntax(args).get(0);
            Undo undo = new Undo(COMMAND_WORD);
            Task toAdd;
            System.out.println(new PrettyTimeParser().parseSyntax(args).size());
            System.out.println(dg.getRecursUntil());    
            System.out.println(dg.getDates().size());
            do {
                toAdd = new Task(task);
                toAdd.addDuration(dg.getRecurInterval());
                undo.addTask(toAdd);
                task = toAdd;
            } while (toAdd.getStartDate().getDate().before(dg.getRecursUntil()));
            model.addTask(undo.getTaskArray());
            CommandHistory.addMutateCmd(undo);
            return new CommandResult(String.format(MESSAGE_SUCCESS, task));
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IndexOutOfBoundsException | NullPointerException ex) {
            return new CommandResult(MESSAGE_FAILURE
                    + "\n"
                    + MESSAGE_USAGE);
        }
    }

    private ReadOnlyTask getTaskFromIndexOrLastModified() {
        ReadOnlyTask task;
        if (targetIndex <= 0) {
            task = CommandHistory.getModTask();
        }
        else {
            try {
                UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
                task = lastShownList.get(targetIndex - 1);
            } catch (IndexOutOfBoundsException iobe) {
                return null;
            }
        }
        return task;
    }
}
