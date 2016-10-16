package seedu.todo.storage;

import seedu.todo.commons.exceptions.DataConversionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * Represents an storage mechanism that allows an object to be saved to a 
 * specific location
 */
public interface MoveableStorage<T> extends FixedStorage<T> {
    
    public String getLocation(); 
    
    public Optional<T> read(String location) throws DataConversionException, FileNotFoundException;
    
    public void save(T object, String newLocation) throws IOException;
}
