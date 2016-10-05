package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.events.model.AddressBookChangedEvent;
import seedu.todo.commons.events.storage.DataSavingExceptionEvent;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ReadOnlyAddressBook;
import seedu.todo.model.ReadOnlyTodoList;
import seedu.todo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, TodoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    @Override
    String getTodoListFilePath();

    @Override
    Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyTodoList> readTodoList(String filePath) throws DataConversionException, IOException;

    @Override
    void saveTodoList(ReadOnlyTodoList todoList) throws IOException;

    @Override
    void saveTodoList(ReadOnlyTodoList todoList, String filePath) throws IOException;

    /**
     * Saves the current version of the ReadOnlyTodoList to the hard disk.
     * Creates the data file if it is missing. Raises
     * {@link DataSavingExceptionEvent} if there was an error during saving.
     * 
     * @throws IOException
     */
    void updateTodoListStorage(ReadOnlyTodoList todoList) throws IOException;

}
