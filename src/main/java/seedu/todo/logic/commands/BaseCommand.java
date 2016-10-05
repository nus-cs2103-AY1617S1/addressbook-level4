package seedu.todo.logic.commands;

import seedu.todo.logic.parser.Parameter;

public abstract class BaseCommand {
    abstract public Parameter[] getArguments();
    
    abstract void execute();
}
