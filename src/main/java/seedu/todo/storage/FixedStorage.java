package seedu.todo.storage;

import seedu.todo.commons.exceptions.DataConversionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage mechanism to save an object to a fixed location
 * @param <T>
 */
public interface FixedStorage<T> {
    /**
     * Reads the object from storage 
     * @return the object read from storage 
     * @throws DataConversionException if the data read was not of the expected type 
     * @throws FileNotFoundException if there was no file  
     */
    public Optional<T> read() throws DataConversionException, FileNotFoundException;

    /**
     * Persists an object 
     * @param object the object that needs to be persisted 
     * @throws IOException if there was any problem saving the object to storage
     */
    public void save(T object) throws IOException;
}
