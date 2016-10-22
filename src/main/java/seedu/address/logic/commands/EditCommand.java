package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Edit a event/task in the task manager.\n\t"
            + "Parameters: EVENT_NAME s/START_DATE e/END_DATE [t/TAG]... [p/PRIORITY_LEVEL] \n\t"
            + "Example: " + COMMAND_WORD
            + " CS3230 Lecture s/14.10.2016-10 e/14.10.2016-12 t/school t/lecture p/2";

    public static final String MESSAGE_SUCCESS = "This event/task has been edited: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This event/task already exists in the task manager";
    public static final String MESSAGE_TASK_NOT_IN_LIST = "This event/task is not found in the task manager";
    private final Task toEdit;

    public EditCommand(String name,
            String deadline,Set<String> tags, int priority) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
		  tagSet.add(new Tag(tagName));
		}
		this.toEdit= new Task(
                new Name(name),
                new Deadline(deadline),
                new UniqueTagList(tagSet)
		);
	}

	@Override
	public CommandResult execute() {
		try{
			model.addTask(toEdit);
			ReadOnlyTask toRemove = null;
			for (ReadOnlyTask task: model.getFilteredTaskList()) {
				if(task.getName().equals(toEdit.getName()))
					toRemove = task;
			}
			model.deleteTask(toRemove);
			String message = String.format(MESSAGE_SUCCESS, toEdit);
			model.saveState(message);
			return new CommandResult(message);
		} catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }  catch (TaskNotFoundException e) {
			// TODO Auto-generated catch block
        	return new CommandResult(MESSAGE_TASK_NOT_IN_LIST);
		}
//		try {
//			addressBook.addPerson(toEdit);
//			ReadOnlyPerson toRemove = null;
//			for (ReadOnlyPerson person : addressBook.getAllPersons()) {
//				if(person.getName().equals(toEdit.getName()))
//					toRemove = person;
//			}
//            addressBook.removePerson(toRemove);
//            return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
//        } catch (UniquePersonList.DuplicatePersonException dpe) {
//            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
//        } catch (PersonNotFoundException pnfe) {
//        	return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
//		}

	}

}
