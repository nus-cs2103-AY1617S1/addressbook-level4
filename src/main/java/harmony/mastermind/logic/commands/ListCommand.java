package harmony.mastermind.logic.commands;

import java.util.ArrayList;
import harmony.mastermind.memory.GenericMemory;
import java.util.Optional;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String LISTING_ARCHIVES = "archive";
    public static final String LISTING_TASKS = "task";
    public static final String MESSAGE_USAGE = "list archive";
    public static final String MESSAGE_SUCCESS_ARCHIVED = "Listed all archived tasks";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String COMMAND_SUMMARY = "Listing all tasks:"
            + "\n" + COMMAND_WORD;   
    private static final String BRACKET_CLOSE = "] ";
    private static final String BRACKET_OPEN = ".   [";
    private static final String BLANK = " ";
    private static final String ALERT = "!";
    private static final String MARK = "x";
    private static final String ITEM_VIEW = "Item View";
    private static final String DISPLAY_ITEM_TYPE = "Display item type: ";
    private static final String LINE2 = "_________________________________________________________________";
    private static final String LINE = "_________________________________________________________________\n";
    public static GenericMemory detailedView = null;
    public static ArrayList<GenericMemory> listView = null;
    public static String listName = null;
    
    private boolean archives;
    
    public ListCommand() {
        archives = false;
    }
    
    public ListCommand(Optional<String> args) {
        archives = true;
    }

    @Override
    public CommandResult execute() {
        if (!archives) {
            model.updateFilteredListToShowAll();
            
            return new CommandResult(MESSAGE_SUCCESS);
        
        }else{
            return new CommandResult(MESSAGE_SUCCESS_ARCHIVED);
        }
    }

    //@author A0143378Y
    // Clears all main window text
    public static void displayClear() {
        detailedView = null;
    }

    //@author A0143378Y
    // Displays an individual GenericMemory item with all non-null details
    public static void displayDetailed(GenericMemory item) {
        displayClear();
        detailedView = item;

        assert detailedView != null;

        formatItem(item);
    }

    //@author A0143378Y
    // Formats the item display
    private static void formatItem(GenericMemory item) {
        System.out.println(ITEM_VIEW);
        System.out.println(LINE);
        System.out.println(item);
        System.out.println(LINE2);
    }

    //@author A0143378Y
    // Takes an ArrayList of GenericMemoryitems, and display them in a list form, with name as the heading
    public static void displayList(ArrayList<GenericMemory> list, String name) {
        displayClear();
        detailedView = null;
        listName = name;

        assert listName != null;

        formatList(list);
    }

    //@author A0143378Y
    // Formats the list display
    private static void formatList(ArrayList<GenericMemory> list) {
        System.out.println(listName);
        System.out.println(LINE);
        for (int i=0; i < list.size(); i++) {
            String line = i + 1 + BRACKET_OPEN;
            line = markSymbol(list, i, line);
            line += BRACKET_CLOSE + list.get(i).getName();

            System.out.println(line);
        }
        System.out.println(LINE2);
    }

    //@author A0143378Y
    // Selects and prints the appropriate marking symbol
    private static String markSymbol(ArrayList<GenericMemory> list, int i,
            String line) {
        if (list.get(i).getState() == 1) { // show marking for overdue/over items
            line += MARK;
        } else if (list.get(i).getState() == 2) { // show alert marking for overdue/ongoing items
            line += ALERT;
        } else { // show blank space for upcoming/incomplete items
            line+= BLANK;
        }
        return line;
    }

    //@author A0143378Y
    // Displays list of item with the corresponding type
    public static void displayType(ArrayList<GenericMemory> list, String type) {
        assert type.length() != 0;

        if (list.size() > 1) { // if list contains multiple items, show as list
            displayList(list, DISPLAY_ITEM_TYPE + type);
        } else if (list.size() == 1){ // if only one item, show item
            displayDetailed(list.get(0));
        }
    }

    //@author A0143378Y
    // Redisplays item view or list view to reflect changes such as updates, marks, or deletes
    public static void refreshDisplay() {
        if(detailedView != null) { 
            displayDetailed(detailedView);
        }
    }

}
