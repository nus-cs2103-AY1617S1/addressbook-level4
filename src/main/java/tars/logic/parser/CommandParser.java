package tars.logic.parser;

import tars.logic.commands.Command;

/**
 * Represents a parser command with hidden internal logic and the ability to be executed.
 * 
 * @@author A0139924W
 */
public abstract class CommandParser {
    protected static final Prefix namePrefix = new Prefix("/n");
    protected static final Prefix tagPrefix = new Prefix("/t");
    protected static final Prefix priorityPrefix = new Prefix("/p");
    protected static final Prefix dateTimePrefix = new Prefix("/dt");
    protected static final Prefix recurringPrefix = new Prefix("/r");
    protected static final Prefix deletePrefix = new Prefix("/del");
    protected static final Prefix addTagPrefix = new Prefix("/ta");
    protected static final Prefix removeTagPrefix = new Prefix("/tr");
    protected static final Prefix donePrefix = new Prefix("/do");
    protected static final Prefix undonePrefix = new Prefix("/ud");
    protected static final Prefix listPrefix = new Prefix("/ls");
    protected static final Prefix editPrefix = new Prefix("/e");

    public abstract Command prepareCommand(String args);
}
