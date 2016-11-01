package seedu.agendum.ui;

import java.util.LinkedList;
import java.util.ListIterator;

//@@author A0148031R
/**
 * Stores previous valid and invalid commands in a linked list with a max size
 * New commands are added to the head of the linked list
 */
public class CommandBoxHistory {
    
    private static final int MAX_PREVIOUS_LINES = 15;
    private static final String PREVIOUS_QUERY = "previous";
    private static final String NEXT_QUERY = "next";
    private static final String EMPTY_QUERY = "";
    private static final String EMPTY_COMMAND = "";
    private LinkedList<String> pastCommands;
    private ListIterator<String> iterator;
    private String lastCommand = "";
    private String lastQuery = EMPTY_QUERY;
    
    private static CommandBoxHistory instance = null;
    
    protected CommandBoxHistory() {
        pastCommands = new LinkedList<>();
        iterator = pastCommands.listIterator();   
    }
    
    public static CommandBoxHistory getInstance() {
       if(instance == null) {
          instance = new CommandBoxHistory();
       }
       return instance;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    /**
     * Retrieves the previous valid/invalid command.
     * If there is no previous command, returns an empty string to clear the command box
     */
    public String getPreviousCommand() {
        if(!iterator.hasNext()) {
            lastQuery = EMPTY_QUERY;
            return EMPTY_COMMAND;
        } else if(lastQuery.equals(NEXT_QUERY)) {
            iterator.next();
        } 
        lastQuery = PREVIOUS_QUERY;
        return iterator.next();
    }

    /**
     * Retrieves the next valid/invalid command.
     * If there is no next command, return an empty string to clear the command box
     */
    public String getNextCommand() {
        if (!iterator.hasPrevious()) {
            lastQuery = EMPTY_QUERY;
            return EMPTY_COMMAND;
        } else if(lastQuery.equals(PREVIOUS_QUERY)) {
            iterator.previous();
        }  
        lastQuery = NEXT_QUERY;
        return iterator.previous();

    }

    /**
     * Takes in the latest command string entered and add it to command box history.
     * Updates the iterator to point to the latest element 
     */
    public void saveNewCommand(String newCommand) {
       lastCommand = newCommand;
       pastCommands.addFirst(lastCommand);

       if (pastCommands.size() > MAX_PREVIOUS_LINES) {
           pastCommands.removeLast();
       }

       iterator = pastCommands.listIterator();
    }

}
