package harmony.mastermind.logic.parser;

/*
 * Parser for search
 * Takes in command from GUI.Main, and call appropriate method in search
 * 
 */


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import harmony.mastermind.logic.commands.*;
import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;

public class ParserSearch extends ParserMemoryMain {
    
    private static final String SEARCHING_FOR_TERM = "Searching for term";

    private static final String SEARCH_DATE_RESULT = "Search Date Result";
    
    private static final String ERROR_ITEM_NOT_FOUND = "No items found!";
    private static final String ERROR_SEARCH_FOR_DATE = "Item with date found.";
    private static final String ERROR_INVALID_DATE = "Error with date set. Please try again";
    private static final String ERROR_INVALID_COMMAND = "You may only search for term(s) or items contain a date";
    
    private static final int SEARCH_TERM = 21;
    private static final int SEARCH_DATE = 22;

    private static Calendar dateTime;
    
    //@author A0143378Y
    public static void run(String command, Memory memory){
        initVar();
        setCommand(command);
        search(memory);
        
    }

    //@author A0143378Y
    /*
     * Initializing variables
     */
    public static void initVar(){
        dateTime =  new GregorianCalendar();
        setType(-1);
        setLength(-1);
        setProper(true);
    }
    
    //@author A0143378Y
    /*
     * Set command type
     */
    private static void setSearchType(String[] details) {
        
        if(command.contains("date")&&(details.length == 2)){
            setType(SEARCH_DATE);
        }else if(details.length >= 1 && command.trim().length() !=0){
            setType(SEARCH_TERM);
        }
    }
    
    //@author A0143378Y
    /*
     * Takes in terms from user, parse and send as String array to method search
     */
    private static void searchTerm(Memory memory) {
        String[] toSearch = command.split(", ");
        FindCommand.searchTerms(toSearch, memory);
    }

    //@author A0143378Y
    /*
     * Takes in date from user, check that date is set in variable dateTime
     * If date is not in proper form or invalid, return an error message to user
     * Else calls searchDate to search for item with the date
     */
    private static void searchDate(String[] details, Memory memory) {
        setProper(setDate(details[1], dateTime));
        if(setProper) { 
            assert setProper == true;
            searchDate(memory);
        }
    }

    //@author A0143378Y
    /*
     * Takes in command and memory
     * Set command type and call method corresponding to the command.
     * If command is invalid, generate error message.
     * 
     */
    private static void search(Memory memory){
        String[] details = command.split(" ");

        setSearchType(details);
        runCodeIfCommandIsValid(memory, details);

    }

    //@author A0143378Y
    /*
     * Check if command is valid
     * If command is invalid, generate error message.
     * If command is valid, run search
     */
    private static void runCodeIfCommandIsValid(Memory memory, String[] details) {
        switch (type) {
        case SEARCH_TERM:
            searchTerm(memory);
            break;

        case SEARCH_DATE:
            searchDate(details, memory);
            break;
            
        }
    }
    
    //@author A0143378Y
    /*
     * Takes in an arraylist, message to be printed if search is successful, and the type of result being displayed
     * If list is empty, error message is printed
     * If list has exactly one item, displayDetailed is called (display an item)
     * If list has more than one item, then displayList is called (display a list)
     */
    private static String displayResult(ArrayList<GenericMemory> result, String reportSuccess, String type){
        if (result.size() == 0) {
            return ERROR_ITEM_NOT_FOUND;
        } else if (result.size() == 1) {;
            return ListCommand.displayDetailed(result.get(0));
        } else {
            assert result.size() >1;
            return ListCommand.displayList(result, type);
        }
    }

    //@author A0143378Y
    /*
     * With the dateTime variable set in searchDate, method calls findDate to search for item with the
     * date. 
     * This means if it is an event, then the date will fall after the start date and before the end date
     * If it is a one day event or a deadline, then it will return such item if the date is exactly the
     * start date of the event or end date of the deadline.
     * Items are stored in an arraylist and displayed
     * 
     */
    private static void searchDate(Memory memory) {
        ArrayList<GenericMemory> findResult = FindCommand.findDate(dateTime, memory);
        displayResult(findResult, ERROR_SEARCH_FOR_DATE, SEARCH_DATE_RESULT);
        
    }
}
