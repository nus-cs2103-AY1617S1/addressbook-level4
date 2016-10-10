package harmony.mastermind.logic.commands;

import java.util.HashSet;
import java.util.Set;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.Date;
import harmony.mastermind.model.task.Name;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.Time;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task in task manager
 *
 */
public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";
    public static final String NEXT_COMMAND_WORD = "actualEdit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX NAME at/TIME on/DATE [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 task at/1000 on/0110";

    
    public static final String MESSAGE_EDIT_TASK_PROMPT = "Edit the following task: %1$s";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task successfully edited: %1$s";
    public static final String COMMAND_SUMMARY = "Editting a task:"
            + "\n" + COMMAND_WORD + " INDEX NAME at/TIME on/DATE [t/TAG]...";
    
//    private MainWindow window;
    private final int targetIndex;
    private ReadOnlyTask taskToEdit;
    private Task toEdit;
    
    public EditCommand(int targetIndex,String name, String time, 
            String date, Set<String> tags) throws IllegalValueException{
        this.targetIndex = targetIndex;
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toEdit = new Task(
                new Name(name),
                new Time(time),
                new Date(date),
                new UniqueTagList(tagSet)
        );
    }
    
  
    
    @Override
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        taskToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            model.deleteTask(taskToEdit);
            model.addTask(toEdit);
//            
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_PROMPT, taskToEdit));

        } catch (TaskNotFoundException | DuplicateTaskException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
    }
 
}
