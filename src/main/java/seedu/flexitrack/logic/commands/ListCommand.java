//@@author A0127686R
package seedu.flexitrack.logic.commands;

/**
 * Lists all task in the FlexiTrack to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_SHORTCUT = "l";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]" + 
            ": List all the to do lists in FlexiTrack.\n" 
            + COMMAND_WORD + " future : List all the to do lists that is due or start in the future.\n"
            + COMMAND_WORD + " past : List all the to do lists in the past.\n"
            + COMMAND_WORD + " mark: List all the to do lists that has been marked.\n"
            + COMMAND_WORD + " unmark: List all the to do lists in that has not been marked.\n"
            + "Example: " + COMMAND_WORD + " past \n";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String LIST_FUTURE_COMMAND = "future";
    public static final String LIST_PAST_COMMAND = "past";
    public static final String LIST_MARK_COMMAND = "mark";
    public static final String LIST_UNMARK_COMMAND = "unmark";
    public static final String LIST_BLOCK_COMMAND = "block";
    public static final String LIST_LAST_COMMAND = "last";
    public static final String LIST_NEXT_COMMAND = "next";
    public static final String LIST_LAST_WEEK_COMMAND = "last week";
    public static final String LIST_LAST_MONTH_COMMAND = "last month";
    public static final String LIST_NEXT_WEEK_COMMAND = "next week";
    public static final String LIST_NEXT_MONTH_COMMAND = "next month";
    public static final String LIST_UNSPECIFIED_COMMAND = "";
            
    public final String arguments;
    
    public ListCommand(String args) {
        this.arguments = args; 
    }

    @Override
    public CommandResult execute() {
        if (arguments.equals(LIST_UNSPECIFIED_COMMAND)){
            model.updateFilteredListToShowAll();
        }
        else {
            model.updateFilteredListToFitUserInput( arguments );
        }      
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
