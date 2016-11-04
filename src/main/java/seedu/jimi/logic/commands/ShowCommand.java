package seedu.jimi.logic.commands;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.FilteredListManager.ListId;
import seedu.jimi.model.ModelManager;
import seedu.jimi.model.datetime.DateTime;

// @@author A0138915X
/**
 * Shows certain sections of the task panel or all tasks and events to the user.
 *
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    
    private static final List<String> VALID_KEYWORDS = Arrays.asList(
            "overdue",
            "floating", 
            "incomplete", 
            "completed",
            "today", 
            "tomorrow", 
            "monday", 
            "tuesday", 
            "wednesday", 
            "thursday", 
            "friday", 
            "saturday", 
            "sunday",
            "all"
    );
    
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Shows certain sections of the task panel or all tasks and events in the agenda panel. \n"
            + "Parameters: SECTION_TO_SHOW\n" 
            + "Example: " + COMMAND_WORD + " floating\n"
            + "Valid case-insensitive sections to show: \n"
            + "> all, overdue, floating, complete, incomplete, today, tomorrow, {day of week displayed}";
    public static final String MESSAGE_INVALID_SECTION =
            "Invalid section to show!\n" 
            + "Valid case-insensitive sections to show: \n"
            + "> all, overdue, floating, complete, incomplete, today, tomorrow, {day of week displayed}";
    
    private final String userSelection; //section name from user input
    
    public ShowCommand() {
        this.userSelection = null;
    }
    
    public ShowCommand(String args) throws IllegalValueException {
        if (!isValidSectionToShow(args.trim())) {
            throw new IllegalValueException(MESSAGE_INVALID_SECTION);
        }
        this.userSelection = args.toLowerCase().trim();
    }
    
    /**
     * Updates the agenda lists with new relevant predicates to update lists show to user.
     */
    @Override
    public CommandResult execute() {
        ((ModelManager) model).showTaskPanelSection(userSelection);
        
        if (userSelection.equals("all")) {
            model.updateAllFilteredListsToNormalListing();
            return new CommandResult(getMessageForTaskListShownSummary(model.getShownSize()));
        }
        
        ListId sectionToShow = determineSectionToShow(userSelection);
        
        model.updateFilteredAgendaTaskList(sectionToShow);
        model.updateFilteredAgendaEventList(sectionToShow);
        
        return new CommandResult(getMessageForTaskListShownSummary(model.getShownSize()));
    }
    
    // @@author A0140133B
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.toLowerCase().equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getMessageUsage() {
        return MESSAGE_USAGE;
    }
    
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
    
    /*
     * =========================================================
     *                      Helper Methods
     * =========================================================
     */
    
    /** Checks if {@code test} is a valid keyword as shown in {@code VALID_KEYWORDS}. */
    private boolean isValidSectionToShow(String test) {
        return ShowCommand.VALID_KEYWORDS.stream()
                .filter(w -> w.toLowerCase().equals(test.toLowerCase()))
                .findAny()
                .isPresent();
    }
    // @@author
    // @@author A0138915X
    private ListId determineSectionToShow(String sectionToShow) {
        switch (userSelection) {
        case "overdue" :
            return ListId.OVERDUE;
        case "floating" :
            return ListId.FLOATING_TASKS;
        case "incomplete" :
            return ListId.INCOMPLETE;
        case "completed" :
            return ListId.COMPLETED;
        case "today" :
            return ListId.DAY_AHEAD_0;
        case "tomorrow" :
            return ListId.DAY_AHEAD_1;
        default :
            return determineDayOfWeekToShow(userSelection);
        }
    }

    private ListId determineDayOfWeekToShow(String userSelection) {
        ListId sectionToShow = null;
        
        LocalDateTime dateTime = new DateTime().getLocalDateTime();
        
        for (int i = 0; i < 7; i++) {
            String dayOfWeek = dateTime.getDayOfWeek().plus(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            if (dayOfWeek.toLowerCase().equals(userSelection)) {
                sectionToShow = ListId.values()[i];
                break;
            }
        }
        
        return sectionToShow;
    }
    
}
