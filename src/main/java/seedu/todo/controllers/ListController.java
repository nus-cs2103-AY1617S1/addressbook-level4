package seedu.todo.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import seedu.todo.commons.exceptions.AmbiguousEventTypeException;
import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.controllers.concerns.CalendarItemFilter;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

// @@author A0139812A
/**
 * Controller to list CalendarItems.
 */
public class ListController implements Controller {
    
    private static final String NAME = "List";
    private static final String DESCRIPTION = "Lists all tasks and events.";
    private static final String COMMAND_SYNTAX = "list [task/event] [complete/incomplete] [on date] or [from date to date]";
    private static final String COMMAND_WORD = "list";
    
    private static final String MESSAGE_LISTING_ALL = "Showing all tasks and events.\n\n"
                                                    + "You have a total of %d incomplete tasks, %d overdue tasks, "
                                                    + "and %d upcoming events.";
    private static final String MESSAGE_LISTING_FILTERED = "Showing %s.\n\nYour query: %s";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith("list")) ? 1 : 0;
    }
    
    @Override
    public void process(String input) throws ParseException {
        
        TodoListDB db = TodoListDB.getInstance();        
        
        // First, we check if it's a basic command, then don't bother filtering.
        if (input.toLowerCase().trim().equals(COMMAND_WORD)) {
            String consoleMessage = String.format(MESSAGE_LISTING_ALL, db.countIncompleteTasks(), 
                    db.countOverdueTasks(), db.countFutureEvents());
            Renderer.renderIndex(db, consoleMessage);
            return;
        }
        
        List<Task> filteredTasks = new ArrayList<>();
        List<Event> filteredEvents = new ArrayList<>();
        
        // Parse the input with Tokenizer.
        Map<String, String[]> parsedResult = Tokenizer.tokenize(CalendarItemFilter.getFilterTokenDefinitions(), input);
        
        // Check if we managed to parse any tokens; if not, we ask for disambiguation.
        if (!containsTokens(parsedResult.values())) {
            System.out.println("Disambiguate: Invalid arguments");
            return;
        }

        // Determine if command should return tasks/events/both.
        boolean[] tasksOrEventsBools;
        try {
            tasksOrEventsBools = CalendarItemFilter.parseIsTaskEvent(parsedResult);
        } catch (AmbiguousEventTypeException e) {
            System.out.println("Disambiguate: Invalid combination of arguments");
            return;
        }
        
        boolean isTask = tasksOrEventsBools[0];
        boolean isEvent = tasksOrEventsBools[1];

        try {
            if (isTask) {
                filteredTasks = CalendarItemFilter.filterTasks(parsedResult);
            }
            if (isEvent) {
                filteredEvents = CalendarItemFilter.filterEvents(parsedResult);
            }
        } catch (InvalidNaturalDateException e) {
            System.out.println("Disambiguate: Invalid date format");
            return;
        }
        
        // Render the new view with filtered tasks.
        String consoleMessage = String.format(MESSAGE_LISTING_FILTERED, 
                StringUtil.formatNumberOfTaskAndEventWithPuralizer(filteredTasks.size(), filteredEvents.size()), input);
        Renderer.renderSelected(TodoListDB.getInstance(), consoleMessage, filteredTasks, filteredEvents);
    }
    
    private boolean containsTokens(Collection<String[]> tokensCollection) {
        for (String[] tokens : tokensCollection) {
            if (tokens.length > 0) {
                return true;
            }
        }
        
        return false;
    }
}
