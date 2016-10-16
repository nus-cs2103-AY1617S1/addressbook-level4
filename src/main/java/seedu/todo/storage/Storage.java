package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage {

    UserPrefs readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(UserPrefs userPrefs) throws IOException;
}
