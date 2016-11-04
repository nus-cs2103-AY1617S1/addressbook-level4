package seedu.stask.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.stask.commons.exceptions.IllegalValueException;
import seedu.stask.commons.util.CommandUtil;
import seedu.stask.model.tag.Tag;
import seedu.stask.model.tag.UniqueTagList;
import seedu.stask.model.task.Datetime;
import seedu.stask.model.task.Description;
import seedu.stask.model.task.Name;
import seedu.stask.model.task.Status;
import seedu.stask.model.task.Task;
import seedu.stask.model.task.Status.State;

//@@author A0143884W
/**
 * Adds a task to the task book.
 */
public class AddCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book.\n"
            + "Parameters: TASKNAME d/TASK_DESCRIPTION date/DD-MM-YYYY [24HR] [to 24HR] [t/TAG]...\n" + "Example: "
            + COMMAND_WORD + " Wash Clothes d/Wash with detergent date/27-9-2016 2359 t/!!!";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "THERE IS A DUPLICATE TASK IN THE TASK BOOK!";
    public static final String MESSAGE_CLASHING_EVENTS = "THIS EVENT CLASHES WITH OTHER EVENT(S) IN THE TASK BOOK!";
    public static final int NO_DUPLICATE_OR_CLASH = 0;
    public static final int DUPLICATE = 1;
    public static final int CLASH = 2;

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String description, String datetime, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        if (tags != null){
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }
        }

        this.toAdd = new Task(new Name(name), 
                new Description(description), 
                new Datetime(datetime), 
                new Status(State.NONE),
                new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        int checkForDuplicateOrClash = model.addTask(toAdd);
        populateUndo();

        return CommandUtil.generateCommandResult(new CommandResult(String.format(MESSAGE_SUCCESS, toAdd)), checkForDuplicateOrClash);


    }
    
    @Override
    public void populateUndo(){
        assert COMMAND_WORD != null;
        assert toAdd != null;
        model.addUndo(COMMAND_WORD, toAdd);
        model.clearRedo();
    }
}
