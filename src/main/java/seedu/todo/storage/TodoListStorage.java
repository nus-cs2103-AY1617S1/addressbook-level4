package seedu.todo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.commons.util.XmlUtil;
import seedu.todo.model.ImmutableTodoList;

import javax.xml.bind.JAXBException;

/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class TodoListStorage implements MovableStorage<ImmutableTodoList> {

    private String filePath;

    public TodoListStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getLocation() {
        return filePath;
    }

    @Override
    public ImmutableTodoList read() throws DataConversionException, FileNotFoundException {
        return read(filePath);
    }

    @Override
    public ImmutableTodoList read(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;
        File file = new File(filePath);

        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
    
    @Override
    public void save(ImmutableTodoList todoList, String newLocation) throws IOException {
        String oldLocation = filePath;
        filePath = newLocation;
        
        try {
            save(todoList);
        } catch (IOException e) {
            filePath = oldLocation;
            throw e;
        }
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
