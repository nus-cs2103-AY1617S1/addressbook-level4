//@@author A0144939R
package seedu.task.logic.commands;

/**
 * Alias command
 */
public class AliasCommand extends Command {
    
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
      if(isValidAliasCommandPair(alias, command)) {
          model.setMapping(command, alias);
          return new CommandResult(true, MESSAGE_SUCCESS);
      } else {
          return new CommandResult(false, MESSAGE_FAILURE);
      }
    }
    
    public boolean isValidAliasCommandPair(String alias, String command) {
        //check that alias is not null and that alias is not a command
        //checks that command is valid command
        return true;
    }

}

