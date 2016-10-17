package seedu.whatnow.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Lists all tasks in WhatNow to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": the task specified\n"
            + "Parameters: done\n"
            + "Examples: " + COMMAND_WORD + " done or " + COMMAND_WORD + " all or " + COMMAND_WORD;
    
    public String type;

    public ListCommand(String type) {
        this.type = type;
    }
    
    private void mapInputToCorrectArgumentForExecution() {
        if (type.equals("done")) {
            type = "completed";
        } else if (type.equals("all")) {
            type = "all";
        } else {
            type = "incomplete";
        }
    }
    
    @Override
    public CommandResult execute() {
        mapInputToCorrectArgumentForExecution();
        if (type.equals("all")) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        
        String[] status = {type};
        Set<String> keyword = new HashSet<>(Arrays.asList(status));
        model.updateFilteredListToShowAllByStatus(keyword);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
