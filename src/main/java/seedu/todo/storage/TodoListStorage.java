package seedu.todo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.commons.util.XmlUtil;
import seedu.todo.model.ImmutableTodoList;

import javax.xml.bind.JAXBException;

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
        File file = new File(filePath);

        if (!file.exists()) {
            logger.info("TodoList file " + file + " not found");
            return Optional.empty();
        }

        try {
            return Optional.of(XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class));
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
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

        try {
            XmlUtil.saveDataToFile(file, new XmlSerializableTodoList(todoList));
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }
}
