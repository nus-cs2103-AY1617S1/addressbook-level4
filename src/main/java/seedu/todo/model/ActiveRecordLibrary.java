package seedu.todo.model;

import java.util.*;
import java.util.function.*;
import seedu.todo.commons.exceptions.*;

public class ActiveRecordLibrary<E> {
    private Set<E> records = new HashSet<E>();
    
    public boolean add(E record) {
        return records.add(record);
    }
    
    public List<E> where(Function<E, Boolean> pred) {
        List<E> result = new ArrayList<E>();
        for (E record : records) {
            if (pred.apply(record))
                result.add(record);
        }
        return result;
    }
    
    public E find(Function<E, Boolean> pred) throws RecordNotFoundException {
        for (E record : records) {
            if (pred.apply(record))
                return record;
        }
        throw new RecordNotFoundException();
    }
    
    public boolean save(E record) {
        return true;
    }
    
    public boolean destroy(E record) {
        return records.remove(record);
    }
    
    public boolean save() {
        return true;
    }
    
    public boolean validate(E record) {
        return true;
    }

}
