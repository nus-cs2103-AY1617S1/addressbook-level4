package seedu.taskscheduler.logic.commands;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.Undo;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList;

public class RecurCommand extends Command {

    public static final String COMMAND_WORD = "recur";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recur a task in the scheduler. "
            + "Parameters: [INDEX] every DATE_TIME_INTERVAL until DATE_TIME\n"
            + "Example: " + COMMAND_WORD
            + " every 1 week until 2 months later\n"
            + "Example: " + COMMAND_WORD
            + " 1 every 3 days until next month";

    public static final String MESSAGE_SUCCESS = "Recur task added: %1$s";
    public static final String MESSAGE_MISSING_TASK = "Invalid index or no previous add command";
    public static final String MESSAGE_FAILURE = "Incorrect recurring specification.\n" + MESSAGE_USAGE;
    public static final String MESSAGE_INVALID_TASK_FOR_RECUR = "Selected task is invalid for recursion," 
            + " please select task with dates";
    
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
//            System.out.println(dg.getRecursUntil());   
//            System.out.println(new Date(dg.getRecurInterval()));  

            Undo undo = new Undo(COMMAND_WORD);
            undo = addRecurTasks(task, dg, undo);
            model.addTask(undo.getTaskArray());
            
            CommandHistory.addMutateCmd(undo);
            return new CommandResult(String.format(MESSAGE_SUCCESS, undo.getArrayString()));
            
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IndexOutOfBoundsException ioobe) {
            return new CommandResult(MESSAGE_FAILURE);
        } catch (NullPointerException npe) {
            return new CommandResult(MESSAGE_INVALID_TASK_FOR_RECUR);
        }
    }

    private Undo addRecurTasks(ReadOnlyTask task, DateGroup dg, Undo undo) {
        Task toAdd;
        do {
            toAdd = new Task(task);
            toAdd.addDuration(dg.getRecurInterval());
            undo.addTask(toAdd);
            task = toAdd;
        } while ((toAdd.getEndDate().getDate().getTime() + dg.getRecurInterval()) 
                < dg.getRecursUntil().getTime());
        return undo;
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
