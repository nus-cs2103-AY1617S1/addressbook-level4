package harmony.mastermind.logic.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import harmony.mastermind.logic.parser.ParserSearch;
import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " dinner 2105";
    
    public static final String COMMAND_SUMMARY = "Searching for a task:"
            + "\n" + COMMAND_WORD + " KEYWORD" + "\n" +  "\n" + "Searching for a task based on date:" + 
            "\n" + COMMAND_WORD + " DATE in DDMMYY format";

    private final Set<String> keywords;
    public static ArrayList<GenericMemory> findResult;
    Memory memory;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
//        model.searchTask(keywords.toString());
        return new CommandResult(COMMAND_WORD, getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
    
    //@@author A0143378Y
    // Checks if memory item contains the keyword.
    // If contains, add to exactResult
    private static void containsKeyword(String keyword, Memory memory, ArrayList<GenericMemory> exactResult, int i) {
        if (memory.get(i).getName().equals(keyword)) { // found exact matching name item
            exactResult.add(memory.get(i));
        }
    }

    //@@author A0143378Y
    // Driver for recursive searching of array of keywords
    // Returns ArrayList of GenericEvents found containing keywords in their name or description
    public static ArrayList<GenericMemory> searchTerms(String[] keywords, Memory memory) {
        ArrayList<GenericMemory> result = new ArrayList<GenericMemory>(memory.getList());
        findResult = recursiveSearchTerms(keywords, 0, result); // calls recursive search to narrow down results
        if (findResult.size() == 0) { // no results
            System.out.println("No items found");
        } else if (findResult.size() == 1) { // one result, display item
            ListCommand.displayDetailed(findResult.get(0));
        } else { // multiple results, display as list
            assert findResult.size() > 1;
            String title = formatSearchTitle(keywords);
            ListCommand.displayList(findResult, title);
        }
        return findResult;
    }

    //@@author A0143378Y
    // Formats search title with keywords used to perform the search
    private static String formatSearchTitle(String[] keywords) {
        String title = "Search Keywords Result: " + keywords[0];
        for (int i=1; i<keywords.length; i++) {
            title += ", " + keywords[i];
        }
        return title;
    }

    //@@author A0143378Y
    public static ArrayList<GenericMemory> recursiveSearchTerms(String[] keywords, int index, ArrayList<GenericMemory> result) {
        ArrayList<GenericMemory> newResult = new ArrayList<GenericMemory>();
        if (index >= keywords.length) { // Base case: no more terms to search
            return result;
        } else { // Narrowing down results
            assert index < keywords.length;
            for (int i=0; i < result.size(); i++) {
                if (result.get(i).getName().contains(keywords[index])) { // Found term in name
                    newResult.add(result.get(i));
                } else if ((result.get(i).getDescription()!= null) && result.get(i).getDescription().contains(keywords[index])) { // has description and found term in description
                    newResult.add(result.get(i));
                }
            }
            return recursiveSearchTerms(keywords, index+1, newResult);
        }
    }

    //@@author A0143378Y
    public static ArrayList<GenericMemory> findDate(Calendar date, Memory memory) {
        assert date != null;
        findResult = new ArrayList<GenericMemory>();
        for (int i=0; i < memory.getSize(); i++) {
            Calendar start = memory.get(i).getStart();
            Calendar end = memory.get(i).getEnd();

            if (testTwoCalendar(date, start) || // Checks if given date is equals or between item's start and end date
                    testTwoCalendar(date, end) ||
                    (date.after(start) && date.before(end)) ) {
                findResult.add(memory.get(i));
            }
        }

        return findResult;
    }

    //@@author A0143378Y
    // Returns true if two dates are the same
    private static boolean testTwoCalendar(Calendar a, Calendar b) {
        if (b == null) {
            return false;
        } else {
            assert (a != null) && (b != null);
            return (a.get(Calendar.YEAR)== b.get(Calendar.YEAR))&&
                    (a.get(Calendar.MONTH) == b.get(Calendar.MONTH))&&
                    (a.get(Calendar.DATE)== b.get(Calendar.DATE));
        }
    }

}
