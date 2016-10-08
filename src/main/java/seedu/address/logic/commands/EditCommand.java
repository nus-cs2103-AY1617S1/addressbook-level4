package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.DateRange;
import seedu.address.model.todo.DueDate;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.Title;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Edits a to-do in the current to-do list
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    private final int toDoIndex;
    private Optional<Title> title = Optional.empty();
    private Optional<DateRange> dateRange = Optional.empty();
    private Optional<DueDate> dueDate = Optional.empty();
    private Set<Tag> tags = new HashSet<>();

    public EditCommand(int toDoIndex) {
        this.toDoIndex = toDoIndex;
    }

    public void setTitle(String title) throws IllegalValueException {
        assert title != null;

        this.title = Optional.of(new Title(title));
    }

    public void setDueDate(String dueDate) throws IllegalValueException {
        assert dueDate != null;

       try {
           this.dueDate = Optional.of(new DueDate(new SimpleDateFormat().parse(dueDate)));
       } catch (ParseException exception) {
           throw new IllegalValueException(Messages.MESSAGE_TODO_DUEDATE_INVALID_FORMAT);
       }
    }

    public void setDateRange(String startDate, String endDate) throws IllegalValueException {
        assert startDate != null;
        assert endDate != null;

        Date formattedStartDate;
        Date formattedEndDate;
        try {
            formattedStartDate = new SimpleDateFormat().parse(startDate);
        } catch (ParseException exception) {
            throw new IllegalValueException(Messages.MESSAGE_TODO_DATERANGE_START_INVALID_FORMAT);
        }

        try {
            formattedEndDate = new SimpleDateFormat().parse(endDate);
        } catch (ParseException exception) {
            throw new IllegalValueException(Messages.MESSAGE_TODO_DATERANGE_END_INVALID_FORMAT);
        }

        this.dateRange = Optional.of(new DateRange(
            formattedStartDate, formattedEndDate
        ));
    }

    /**
     * Sets tags for edit command
     * Tags will be checked for validity
     */
    public void setTags(Set<String> tags) throws IllegalValueException {
        assert tags != null;

        this.tags.clear();

        for (String tag : tags) {
            this.tags.add(new Tag(tag));
        }
    }

    @Override
    public CommandResult execute(Model model, EventsCenter eventsCenter) {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyToDo> lastShownList = model.getFilteredToDoList();

        if (lastShownList.size() < toDoIndex) {
            return new CommandResult(String.format(Messages.MESSAGE_TODO_ITEM_INDEX_INVALID, toDoIndex));
        }

        ReadOnlyToDo toDoToEdit = lastShownList.get(toDoIndex - 1);

        try {
            if (title.isPresent()) {
                model.editTodoTitle(toDoToEdit, title.get());
            }

            if (dueDate.isPresent()) {
                model.editTodoDueDate(toDoToEdit, dueDate.get());
            }

            if (dateRange.isPresent()) {
                model.editTodoDateRange(toDoToEdit, dateRange.get());
            }

            if (!tags.isEmpty()) {
                model.editTodoTags(toDoToEdit, tags);
            }
        } catch (IllegalValueException exception) {
            return new CommandResult(exception.toString());
        }

        return new CommandResult(String.format(Messages.MESSAGE_TODO_EDITED, toDoToEdit.getTitle().toString()));
    }
}
