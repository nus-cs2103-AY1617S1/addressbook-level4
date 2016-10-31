package seedu.address.model.task.stub;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;

class NameStub extends Name {

    public NameStub(String name) throws IllegalValueException {
        super(name);
    }
    
    @Override
    public String toString() {
        return "";
    }
}