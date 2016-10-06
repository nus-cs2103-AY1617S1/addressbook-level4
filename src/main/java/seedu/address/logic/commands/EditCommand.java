package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";
    public static final String NEXT_COMMAND_WORD = "actualEdit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 John Doe p/98765432 e/johnd@gmail.com "
            + "a/311, Clementi Ave 2, #02-25 t/friends" ;

    
    public static final String MESSAGE_EDIT_PERSON_PROMPT = "Edit the following person: %1$s";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Person successfully edited: %1$s";
    
//    private MainWindow window;
    private final int targetIndex;
    private ReadOnlyPerson personToEdit;
    private Person toEdit;
    
    public EditCommand(int targetIndex,String name, String phone, String email,
            String address, Set<String> tags) throws IllegalValueException{
        this.targetIndex = targetIndex;
        
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toEdit = new Person(
                new Name(name),
                new Phone(phone),
                new Email(email),
                new Address(address),
                new UniqueTagList(tagSet)
        );
    }
    
  
    
    @Override
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToEdit = lastShownList.get(targetIndex - 1);
        
        try {
            model.deletePerson(personToEdit);
            model.addPerson(toEdit);
//            
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_PROMPT, personToEdit));

        } catch (PersonNotFoundException | DuplicatePersonException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        
    }
 
}
