package seedu.address.model.task.stub;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;

//@@author A0135782Y
public class NameStub extends Name {

    private static final String DUMMY_NAME = "dummy";

    public NameStub() throws IllegalValueException {
        this(DUMMY_NAME);
    }
    public NameStub(String name) throws IllegalValueException {
        super(name);
    }
    
    @Override
    public String toString() {
        return "";
    }
}