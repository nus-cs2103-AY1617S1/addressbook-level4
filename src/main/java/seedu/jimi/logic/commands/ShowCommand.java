package seedu.jimi.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.jimi.model.ModelManager;

/**
 * Shows certain sections of the task panel to the user.
 * @author zexuan
 *
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    
    public static final List<String> VALID_KEYWORDS = Arrays.asList(
            "floating", 
            "incomplete", 
            "complete",
            "today", 
            "tomorrow", 
            "monday", 
            "tuesday", 
            "wednesday", 
            "thursday", 
            "friday", 
            "saturday", 
            "sunday" 
    );
    
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Shows certain sections of the task panel in the agenda panel. \n"
            + "Parameters: NAME_OF_SECTION_TO_DISPLAY\n" 
            + "Example: " + COMMAND_WORD + " floating tasks\n"
            + "> Valid Keywords: Floating Tasks, Completed Tasks, Incomplete Tasks, Today, Tomorrow, {day of week displayed}";

    public static final String MESSAGE_SUCCESS = "Displayed tasks and events.";
    
    private final String sectionToShow; //section name from user input
    
    public ShowCommand() {
        this.sectionToShow = "";
    }
    
    public ShowCommand(String args) {
        this.sectionToShow = args;
    }
    
    /**
     * Updates the agenda lists with new relevant predicates to update lists show to user.
     */
    @Override
    public CommandResult execute() {
        ((ModelManager) model).showTaskPanelSection(sectionToShow);
        
        model.updateFilteredAgendaTaskList(new HashSet<>(Arrays.asList(sectionToShow)));
        model.updateFilteredAgendaEventList(new HashSet<>(Arrays.asList(sectionToShow)));
        
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }

}
