package seedu.todo.logic.parser;

import java.util.Map;
import java.util.Optional;

public interface ParseResult {

    String getComand();

    Optional<String> getPositionalArgument();

    Map<String, String> getNamedArguments();

}