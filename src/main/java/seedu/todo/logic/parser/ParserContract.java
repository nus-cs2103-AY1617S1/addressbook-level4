package seedu.todo.logic.parser;

import seedu.todo.commons.exceptions.IllegalValueException;

public interface ParserContract {
    public ParseResult parse(String input) throws IllegalValueException;
}
