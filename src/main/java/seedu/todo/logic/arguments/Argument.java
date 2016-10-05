package seedu.todo.logic.arguments;

import seedu.todo.commons.exceptions.IllegalValueException;

abstract public class Argument<T> implements Parameter {
    private String name;
    private String description;
    private String flag;
    private boolean optional = true;
    private boolean set = false;
    
    protected T value;
    protected T defaultValue;
    
    public Argument(String name) {
        this.name = name;
    }
    
    public Argument(String name, T defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }
    
    @Override
    public void setValue(String input) throws IllegalValueException {
        set = true;
    }
    
    public T getValue() {
        return value;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public Argument<T> name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Argument<T> description(String description) {
        this.description = description;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public Argument<T> flag(String flag) {
        this.flag = flag;
        return this;
    }

    public boolean isOptional() {
        return optional;
    }
    
    public boolean isSet() {
        return set;
    }

    public Argument<T> required() {
        this.optional = false;
        return this;
    }
    
    @Override
    public boolean isPositional() {
        return flag == null;
    }
    
}
