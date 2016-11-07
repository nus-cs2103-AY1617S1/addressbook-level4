package jym.manager.logic.commands;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.model.tag.Tag;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.*;

/**
 * Adds a task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: DESCRIPTION by DEADLINE (dd MM yyyy HH:mm)  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " do laundry by 07-06-2017 12:30";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the address book";

    private final TaskManagerItem toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, String address, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Description(description),
                new Location(address),
                new UniqueTagList(tagSet)
        );
    }
//@@author A0153440R
    /**
     * Variable constructor with any number of inputs of dates, addresses, or priorities.
     * @param description Task description
     * @param objects List of objects to initialize task with
     * @throws IllegalValueException
     */
    public AddCommand(String description, Object ... objects) throws IllegalValueException{
    	if(objects.length > 3){//this should never happen b/c we control parser. max args is date(s), location, priority
    		throw new IllegalArgumentException();
    	}
    	for(Object o : objects){
    		if(o instanceof List){
    			List<LocalDateTime> d = (List<LocalDateTime>)o;
    			if(d.size() == 1)
    				o = d.get(0);
    		}
    	}

        this.toAdd = new Task(new Description(description), objects);

    }
    public AddCommand(String description) throws IllegalValueException {

        this.toAdd = new Task(
                new Description(description)
        );
    }

    public AddCommand(String description, LocalDateTime deadline) throws IllegalValueException {
     
        this.toAdd = new Task(
                new Description(description),
                deadline
        );
    }
 //@@author

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask((Task)toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
