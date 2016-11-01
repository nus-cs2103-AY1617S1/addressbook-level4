//@@author A0144939R
package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.storage.FilePathChangedEvent;

/**
 * Alias command
 */
public class AliasCommand extends UndoableCommand {
    
    public static final String COMMAND_WORD = "alias";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Aliases a command to a given string "
            + "Parameters: ALIASED VALUE"
            + "Example: " + COMMAND_WORD
            + "add a";
    
    public static final String MESSAGE_SUCCESS = "Command successfully aliased";
    public static final String MESSAGE_FAILURE = "Error, cannot alias command";
    
    private String command;
    private String alias;
    
    
    public AliasCommand(String command, String alias) {
        this.command = command.trim();
        this.alias = alias.trim();
    }
    
    

    @Override
    public CommandResult execute() {
      try {  
          model.setMapping(command, alias);
          return new CommandResult(true, MESSAGE_SUCCESS);
      } catch(Exception e) {
          return new CommandResult(false, MESSAGE_FAILURE);
      }
    }

    @Override
    public CommandResult rollback() {
        
        
    }
}

