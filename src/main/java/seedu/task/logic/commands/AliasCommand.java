//@@author A0144939R
package seedu.task.logic.commands;

import seedu.task.commons.logic.CommandKeys;
import seedu.task.commons.logic.CommandKeys.Commands;

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
    
    private String commandString;
    private String alias;
    
    
    public AliasCommand(String command, String alias) {
        this.commandString = command.trim();
        this.alias = alias.trim();
    }
    

    @Override
    public CommandResult execute() {
      if(isValidAliasCommandPair(alias, commandString)) {
          Commands command = CommandKeys.commandKeyMap.get(commandString);
          model.setMapping(command, alias);
          return new CommandResult(true, MESSAGE_SUCCESS);
      } else {
          return new CommandResult(false, MESSAGE_FAILURE);
      }
    }
    /**
     * Checks if a given alias can be mapped to given command
     * Note: one command can have multiple aliases
     * @param alias Alias specified by user
     * @param command Command the alias aliases to
     * @return
     */
    public boolean isValidAliasCommandPair(String alias, String command) {
        //check that alias is not null and that alias is not a command
        //checks that command is valid command
        if(alias != null && command != null) {
            if(CommandKeys.commandKeyMap.containsKey(commandString) && CommandKeys.commandKeyMap.get(commandString) != null) {
                return true;
            } else {
                return false;
            }
        }
        return false;
   }

}

