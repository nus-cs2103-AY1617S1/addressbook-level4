package seedu.address.logic.commands;

//@@author A0139498J
/**
 * Format help instructions for a particular command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String ALL_COMMANDS = "all";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows usage instructions for a particular command.\n\t"
            + "Example: " + COMMAND_WORD + " add\n"
            + COMMAND_WORD + " all: Shows usage instructions for all commands.\n\t"
            + "Example: " + COMMAND_WORD + " " + ALL_COMMANDS;
    
    public static final String TOOL_TIP = "help";

    public static final String MESSAGE_ALL_COMMAND_WORDS = "Commands available: "
            + " " + AddCommand.COMMAND_WORD
            + " " + DeleteCommand.COMMAND_WORD
            + " " + ClearCommand.COMMAND_WORD
            + " " + DoneCommand.COMMAND_WORD
            + " " + EditCommand.COMMAND_WORD
            + " " + FindCommand.COMMAND_WORD
            + " " + ListCommand.COMMAND_WORD
            + " " + UndoCommand.COMMAND_WORD
            + " " + RedoCommand.COMMAND_WORD
            + " " + StoreCommand.COMMAND_WORD
            + " " + HelpCommand.COMMAND_WORD
            + " " + ExitCommand.COMMAND_WORD;
    
    public static final String MESSAGE_ALL_USAGES = AddCommand.MESSAGE_USAGE
            + "\n" + DeleteCommand.MESSAGE_USAGE
            + "\n" + ClearCommand.MESSAGE_USAGE
            + "\n" + DoneCommand.MESSAGE_USAGE
            + "\n" + EditCommand.MESSAGE_USAGE
            + "\n" + FindCommand.MESSAGE_USAGE
            + "\n" + ListCommand.MESSAGE_USAGE
            + "\n" + UndoCommand.MESSAGE_USAGE
            + "\n" + RedoCommand.MESSAGE_USAGE
            + "\n" + StoreCommand.MESSAGE_USAGE
            + "\n" + HelpCommand.MESSAGE_USAGE
            + "\n" + ExitCommand.MESSAGE_USAGE;

    private String commandType;
    
    public HelpCommand(String commandType) {
        this.commandType = commandType;
    }
    
    @Override
    public CommandResult execute() {
        switch (commandType) {
        case AddCommand.COMMAND_WORD:    return new CommandResult(AddCommand.MESSAGE_USAGE);
        case ClearCommand.COMMAND_WORD:  return new CommandResult(ClearCommand.MESSAGE_USAGE);       
        case DeleteCommand.COMMAND_WORD: return new CommandResult(DeleteCommand.MESSAGE_USAGE);
        case DoneCommand.COMMAND_WORD:   return new CommandResult(DoneCommand.MESSAGE_USAGE);
        case EditCommand.COMMAND_WORD:   return new CommandResult(EditCommand.MESSAGE_USAGE);
        case FindCommand.COMMAND_WORD:   return new CommandResult(FindCommand.MESSAGE_USAGE);
        case ListCommand.COMMAND_WORD:   return new CommandResult(ListCommand.MESSAGE_USAGE);
        case UndoCommand.COMMAND_WORD:   return new CommandResult(UndoCommand.MESSAGE_USAGE);
        case RedoCommand.COMMAND_WORD:   return new CommandResult(RedoCommand.MESSAGE_USAGE);
        case StoreCommand.COMMAND_WORD:  return new CommandResult(StoreCommand.MESSAGE_USAGE);
        case HelpCommand.COMMAND_WORD:   return new CommandResult(HelpCommand.MESSAGE_USAGE);
        case ExitCommand.COMMAND_WORD:   return new CommandResult(ExitCommand.MESSAGE_USAGE);
        case ALL_COMMANDS:               return new CommandResult(MESSAGE_ALL_USAGES);
        default: return new CommandResult(MESSAGE_ALL_COMMAND_WORDS + "\n" + HelpCommand.MESSAGE_USAGE);
        }
    }
}
