package seedu.todo.logic;

import javafx.collections.ObservableList;
import seedu.todo.logic.commands.CommandResult;
import seedu.todo.model.person.ReadOnlyPerson;
import seedu.todo.model.task.ImmutableTask;

/**
 * A logic stub meant to aid UI development, by returning a fixed list of tasks.
 */
public class DummyLogic implements Logic {

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
        // TODO returns a fixed set of tasks.
        return null;
    }

}
