package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CommandUtil;
import seedu.address.model.TaskBook.TaskType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Datetime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.ui.PersonListPanel;


//@@author A0143884W
/**
 * Deletes a task identified using it's last displayed index from the task book.
 */
public class EditCommand extends Command implements Undoable{

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number given in the most recent listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " A1 do that instead date/13-10-16";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";

    private ReadOnlyTask toEdit;
    private Task toAdd;

    private String targetIndex;
    private Name name;
    private Description description;
    private Datetime datetime;
    private UniqueTagList tags;

    public EditCommand(String targetIndex, String name, String description, String datetime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }    

        populateNonNullFields(targetIndex, name, description, datetime, tagSet);
    }

    private void populateNonNullFields(String targetIndex, String name, String description, String datetime,
            final Set<Tag> tagSet) throws IllegalValueException {
        if (name != null){
            this.name = new Name(name);       
        }
        if (description != null){
            this.description = new Description(description);
        }

        if (datetime != null){
            this.datetime = new Datetime(datetime);
        }

        this.tags = new UniqueTagList(tagSet);
        this.targetIndex = targetIndex;
    }  

    @Override
    public CommandResult execute() {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyTask> lastDatedTaskList = model.getFilteredDatedTaskList();
        UnmodifiableObservableList<ReadOnlyTask> lastUndatedTaskList = model.getFilteredUndatedTaskList();

        if (!CommandUtil.isValidIndex(targetIndex, lastUndatedTaskList.size(), lastDatedTaskList.size())){
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        TaskType type = CommandUtil.getTaskType(targetIndex);
        int indexNum = CommandUtil.getIndex(targetIndex);
        
        if (type == TaskType.DATED) {
            toEdit = lastDatedTaskList.get(indexNum - 1);
        }
        else if (type == TaskType.UNDATED){
            toEdit = lastUndatedTaskList.get(indexNum - 1);
        }
        else {
            assert false : "Task type not found";
        }
        
        populateEditedTaskFields();

        try {
            model.addTask(toAdd);
            model.deleteTask(toEdit);           
            populateUndo();
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);     
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toAdd));
    }

    // use original task as base, insert fields that have been input in edit
    private void populateEditedTaskFields() {

        toAdd  = new Task (toEdit.getName(), toEdit.getDescription(), toEdit.getDatetime(), 
                toEdit.getStatus(), toEdit.getTags());

        if (name != null){
            toAdd.setName(name);     
        }
        if (description != null){
            toAdd.setDescription(description);
        }
        if (datetime != null){
            toAdd.setDatetime(datetime);
        }

        toAdd.setTags(tags);
    }

    @Override
    public void populateUndo(){
        assert COMMAND_WORD != null;
        assert toAdd != null;
        assert toEdit != null;
        model.addUndo(COMMAND_WORD, toAdd, toEdit);
    } 

}