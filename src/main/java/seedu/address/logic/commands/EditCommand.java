package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.todo.Title;

import java.util.Optional;
import java.util.Set;

/**
 * Edits a to-do in the current to-do list
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    private final int toDoIndex;
    private Optional<Title> title = Optional.empty();

    public EditCommand(int toDoIndex) {
        System.out.println("New edit command with index: " + toDoIndex);
       this.toDoIndex = toDoIndex;
    }

    public void setTitle(String title) throws IllegalValueException {
        System.out.println("Setting title: " + title);
        this.title = Optional.of(new Title(title));
    }

    public void setDueDate(String dueDate) throws IllegalValueException {
        System.out.println("Setting due date: " + dueDate);
    }

    public void setDateRange(String startDate, String endDate) throws IllegalValueException {
        System.out.println("Setting date range: " +  startDate + " - " + endDate);
    }

    public void setTags(Set<String> tags) throws IllegalValueException {
        System.out.println("Setting tags: " +  tags.toString());
    }

    @Override
    public CommandResult execute(Model model, EventsCenter eventsCenter) {
        assert model != null;

        // TODO: Edit Model
        return new CommandResult("Not implemented");
    }
}
