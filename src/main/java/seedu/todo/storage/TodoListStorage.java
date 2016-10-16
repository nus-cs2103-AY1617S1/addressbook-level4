package seedu.todo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.model.ImmutableTodoList;

/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class TodoListStorage implements MoveableStorage<ImmutableTodoList> {

    private static final Logger logger = LogsCenter.getLogger(TodoListStorage.class);

    private String filePath;

    public TodoListStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getLocation() {
        return filePath;
    }

    @Override
    public Optional<ImmutableTodoList> read() throws DataConversionException, IOException {
        return read(filePath);
    }

    @Override
    public Optional<ImmutableTodoList> read(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;
        File todoListFile = new File(filePath);

        if (!todoListFile.exists()) {
            logger.info("TodoList file " + todoListFile + " not found");
            return Optional.empty();
        }

        ImmutableTodoList TodoListOptional = XmlFileStorage.loadTodoListFromFile(new File(filePath));

        return Optional.of(TodoListOptional);
    }
    
    @Override
    public void save(ImmutableTodoList todoList, String newLocation) throws IOException {
        filePath = newLocation;
        save(todoList);
    }

    @Override
    public void save(ImmutableTodoList todoList) throws IOException {
        assert todoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveTodoListToFile(file, new XmlSerializableTodoList(todoList));
    }
}
