//@@author A0133367E
package seedu.agendum.logic.commands;

import seedu.agendum.model.Model;

/**
 * Create an alias for a reserved command keyword
 */
public class AliasCommand extends Command {

    // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "alias";
    public static final String COMMAND_FORMAT = "alias <original-command> <your-command>";
    public static final String COMMAND_DESCRIPTION = "create your shorthand command";
    public static final String MESSAGE_SUCCESS = "New alias <%1$s> created for <%2$s>";
    public static final String MESSAGE_FAILURE_ALIAS_IN_USE = "<%1$s> is already an alias for <%2$s>";
    public static final String MESSAGE_FAILURE_UNAVAILABLE_ALIAS = "<%1$s> is a reserved command word";
    public static final String MESSAGE_FAILURE_NON_ORIGINAL_COMMAND =
            "We don't recognise <%1$s> as an Agendum Command";
    public static final Object MESSAGE_USAGE = COMMAND_WORD + " - "
            + COMMAND_DESCRIPTION + "\n"
            + COMMAND_FORMAT + "\n"
            + "Example: " + COMMAND_WORD + " mark m";

    private String aliasValue;
    private String aliasKey;
    private CommandLibrary commandLibrary;
    
    public AliasCommand(String aliasKey, String aliasValue) {
        this.aliasKey = aliasKey;
        this.aliasValue = aliasValue;
    }

    public void setData(Model model, CommandLibrary commandLibrary) {
        this.model = model;
        this.commandLibrary = commandLibrary;
    }

    @Override
    public CommandResult execute() {
        if (!commandLibrary.isReservedCommandKeyword(aliasValue)) {
            return new CommandResult(String.format(
                    MESSAGE_FAILURE_NON_ORIGINAL_COMMAND, aliasValue));
        }

        if (commandLibrary.isExistingAliasKey(aliasKey)) {
            String associatedValue = commandLibrary.getAliasedValue(aliasKey);
            return new CommandResult(String.format(
                    MESSAGE_FAILURE_ALIAS_IN_USE, aliasKey, associatedValue));
        }

        if (commandLibrary.isReservedCommandKeyword(aliasKey)) {
            return new CommandResult(String.format(
                    MESSAGE_FAILURE_UNAVAILABLE_ALIAS, aliasKey));
        }
        
        commandLibrary.addNewAlias(aliasKey, aliasValue);
        return new CommandResult(String.format(MESSAGE_SUCCESS, aliasKey, aliasValue));
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
