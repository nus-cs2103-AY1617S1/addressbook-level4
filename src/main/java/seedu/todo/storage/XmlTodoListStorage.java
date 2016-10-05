package seedu.todo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.model.ReadOnlyTodoList;
import seedu.todo.model.ReadOnlyTodoList;

/**
 * A class to access TodoList data stored as an xml file on the hard disk.
 */
public class XmlTodoListStorage implements TodoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private String filePath;

    public XmlTodoListStorage(String filePath){
        this.filePath = filePath;
    }

	@Override
	public String getTodoListFilePath() {
		return filePath;
	}

	public Optional<ReadOnlyTodoList> readTodoList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File TodoListFile = new File(filePath);

        if (!TodoListFile.exists()) {
            logger.info("TodoList file "  + TodoListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTodoList TodoListOptional = XmlFileStorage.loadTodoListFromFile(new File(filePath));

        return Optional.of(TodoListOptional);
    }

	@Override
    public void saveTodoList(ReadOnlyTodoList TodoList, String filePath) throws IOException {
        assert TodoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveTodoListToFile(file, new XmlSerializableTodoList(TodoList));
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList TodoList) throws IOException {
        saveTodoList(TodoList, filePath);
    }
}
