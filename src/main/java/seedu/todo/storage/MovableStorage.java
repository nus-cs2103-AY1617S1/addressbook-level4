package seedu.todo.storage;

import seedu.todo.commons.exceptions.DataConversionException;

import java.io.FileNotFoundException;
import java.io.IOException;

//@@author A0135817B
/**
 * Represents an storage mechanism that allows an object to be saved to a 
 * specific location
 */
public interface MovableStorage<T> extends FixedStorage<T> {
    
    String getLocation(); 
    
    T read(String location) throws DataConversionException, FileNotFoundException;
    
    void save(T object, String newLocation) throws IOException;
}
