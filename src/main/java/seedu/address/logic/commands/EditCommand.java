package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CommandUtil;
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
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class EditCommand extends Command implements Undoable{

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number given in the most recent listing.\n"
            + "Parameters: INDEX (must be a positive integer) FIELD_TO_EDIT(include delimiter d/, date/, t/ etc)\n"
            + "Example: " + COMMAND_WORD + " 1 do that instead date/13-10-16";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";

    private ReadOnlyTask toEdit;
    private Task toAdd;

    private int targetIndex;
    private Name name;
    private Description description;
    private Datetime datetime;
    private UniqueTagList tags;

    public EditCommand(int targetIndex, String name, String description, String datetime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }    

        populateNonNullFields(targetIndex, name, description, datetime, tagSet);
    }

    private void populateNonNullFields(int targetIndex, String name, String description, String datetime,
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

        if (!CommandUtil.isValidIndex(targetIndex, lastUndatedTaskList.size(), 
                lastDatedTaskList.size(), PersonListPanel.DATED_DISPLAY_INDEX_OFFSET)){
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        
        if (targetIndex > PersonListPanel.DATED_DISPLAY_INDEX_OFFSET) {
            toEdit = lastDatedTaskList.get(targetIndex - 1 - PersonListPanel.DATED_DISPLAY_INDEX_OFFSET);
        }
        else {
            toEdit = lastUndatedTaskList.get(targetIndex - 1);
        }

        populateEditedTaskFields();

        try {
        	model.addTask(toAdd);
            model.deleteTask(toEdit);           
            populateUndo();
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_PERSON);     
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