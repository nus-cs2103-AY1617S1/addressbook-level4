package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.UserPrefs;

public class MockStorage implements Storage {
    private boolean todoListWasSaved = false;
    private ImmutableTodoList list;
    
    public MockStorage() {
    }
    
    public MockStorage(ImmutableTodoList list) {
        this.list = list;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        // Does nothing 
    }

    @Override
    public String getTodoListFilePath() {
        return null;
    }
    
    @Override
    public Optional<ImmutableTodoList> readTodoList() {
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<ImmutableTodoList> readTodoList(String filePath) {
        return Optional.ofNullable(list);
    }

    @Override
    public void saveTodoList(ImmutableTodoList todoList) {
        todoListWasSaved = true;
    }

    @Override
    public void saveTodoList(ImmutableTodoList todoList, String newLocation) {
        todoListWasSaved = true;
    }

    public void assertTodoListWasSaved() {
        assert todoListWasSaved;
        todoListWasSaved = false;
    }

}
