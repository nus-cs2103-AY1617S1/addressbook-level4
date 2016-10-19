package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.Time;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.ui.PersonListPanel;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number given in the most recent listing.\n"
            + "Parameters: INDEX (must be a positive integer) FIELD_TO_EDIT(include delimiter d/, date/, time/ etc)\n"
            + "Example: " + COMMAND_WORD + " 1 do that instead date/13.10.16";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Task: %1$s";

    private ReadOnlyTask toEdit;
    private Task toAdd;
    
    private int targetIndex;
    private Name name;
    private Description description;
    private Date date;
    private Time time;
    private UniqueTagList tags;

    public EditCommand(int targetIndex, String name, String description, List<java.util.Date> dateList, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }    
        
        if (name != null){
            this.name = new Name(name);       
        }
        if (description != null){
            this.description = new Description(description);
        }
        
        if (dateList != null){
        	// user inputs "date/" preceding empty <?date> group: converts Dated Task -> Undated Task
        	if (dateList.isEmpty()){
        		this.date = new Date((String)null);
        		this.time = new Time((String)null);
        	}
        	// user inputs edited Date which replaces Task Date
        	else {
        		this.date = new Date(dateList);
        		this.time = new Time(dateList);
        	}
        }
        
        this.tags = new UniqueTagList(tagSet);
        this.targetIndex = targetIndex;
    }  

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();
        UnmodifiableObservableList<ReadOnlyTask> lastUndatedTaskList = model.getFilteredUndatedTaskList();

        if (targetIndex <= PersonListPanel.DATED_DISPLAY_INDEX_OFFSET 
                && lastUndatedTaskList.size() >= targetIndex){
            toEdit = lastUndatedTaskList.get(targetIndex - 1);
        }
        else if (targetIndex > PersonListPanel.DATED_DISPLAY_INDEX_OFFSET 
                   && lastShownList.size() >= targetIndex - PersonListPanel.DATED_DISPLAY_INDEX_OFFSET){
            toEdit = lastShownList.get(targetIndex - 1 - PersonListPanel.DATED_DISPLAY_INDEX_OFFSET);
        }
        else {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        
        populateTaskFields();
                
        assert model != null;
        try {
            model.deletePerson(toEdit);
            model.addPerson(toAdd);           
        } catch (UniquePersonList.DuplicatePersonException e) {
                return new CommandResult(AddCommand.MESSAGE_DUPLICATE_PERSON);     
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, toAdd));
    }

    // use original task as base, insert fields that have been input in edit
    private void populateTaskFields() {

        toAdd  = new Task (toEdit.getName(), toEdit.getDescription(), toEdit.getDate(),
                toEdit.getTime(), toEdit.getTags());
        
        if (name != null){
            toAdd.setName(name);     
        }
        if (description != null){
            toAdd.setDescription(description);
        }
        if (date != null){
            toAdd.setDate(date);
        }
        if (time != null){
            toAdd.setTime(time);
        }
        
        toAdd.setTags(tags);
    }    

}