package seedu.todo.logic.parser;

import java.util.Map;
import java.util.Optional;

//@@author A0135817B
public interface ParseResult {

    String getCommand();

    Optional<String> getPositionalArgument();

    Map<String, String> getNamedArguments();

}
