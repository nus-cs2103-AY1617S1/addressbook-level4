package seedu.todo.logic;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.model.person.ReadOnlyPerson;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.DummyTask;
import seedu.todo.model.task.ImmutableTask;

/**
 * A logic stub meant to aid UI development, by returning a fixed list of tasks.
 */
public class DummyLogic implements Logic {
    
    private List<ImmutableTask> tasks;
    
    public DummyLogic() throws IllegalValueException {
        DummyTask task1 = new DummyTask();
        task1.setTitle("Complete CS3230 Assignment 3");
        task1.setDescription("Submit the work through coursemology, and ask around for some hints.");
        task1.setCompleted(false);
        task1.setPinned(true);
        task1.setEndTime(LocalDateTime.parse("2016-10-25T23:59:00"));
        Set<Tag> tags1 = new HashSet<>();
        tags1.add(new Tag("CS3230"));
        tags1.add(new Tag("Pokemon Go"));
        tags1.add(new Tag("Amazing Module"));
        tags1.add(new Tag("Algorithms"));
        tags1.add(new Tag("Data Structures"));
        task1.setTags(tags1);
        tasks.add(task1);
    }
    
    @Override
    public CommandResult execute(String commandText) {
        //Does nothing
        return null;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        //Does nothing
        return null;
    }

    @Override
    public ObservableList<ImmutableTask> getObservableTaskList() {
        return FXCollections.observableArrayList(tasks);
    }

}
