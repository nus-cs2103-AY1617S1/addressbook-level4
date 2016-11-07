package seedu.todo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seedu.todo.commons.exceptions.AmbiguousEventTypeException;
import seedu.todo.commons.exceptions.InvalidNaturalDateException;
import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Tokenizer;
import seedu.todo.controllers.concerns.CalendarItemFilter;
import seedu.todo.controllers.concerns.Disambiguator;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

// @@author A0139812A
/**
 * Controller to list CalendarItems.
 */
public class ListController extends Controller {
    
    private static final String NAME = "List";
    private static final String DESCRIPTION = "Lists all tasks and events.";
    private static final String COMMAND_SYNTAX = "list [task/event] [complete/incomplete] [on date] or [from date to date]";
    private static final String COMMAND_KEYWORD = "list";
    
    private static final String MESSAGE_LISTING_ALL = "Showing all tasks and events.\n\n"
                                                    + "You have a total of %d incomplete tasks, %d overdue tasks, "
                                                    + "and %d upcoming events.";
    private static final String MESSAGE_LISTING_FILTERED = "Showing %s %s and %s %s.\n\nYour query: %s";
    private static final String MESSAGE_UNKNOWN_TOKENS = "Could not parse your query as it contained unknown tokens: %s";
    private static final String MESSAGE_AMBIGUOUS_TYPE = "We could not tell if you wanted to clear events or tasks. \n"
            + "Note that only tasks can be \"complete\"/\"incomplete\", "
            + "while only events can be \"past\", \"over\" or \"future\".";
    private static final String MESSAGE_INVALID_DATE = "We could not parse the date in your query, please try again.";
    
    public static final String TEMPLATE_LIST = "list [from \"%s\"] [to \"%s\"] [tag \"%s\"]";
    public static final String TEMPLATE_LIST_TASKS = "list tasks [\"%s\"] [from \"%s\"] [to \"%s\"] [tag \"%s\"]";
    public static final String TEMPLATE_LIST_EVENTS = "list events [\"%s\"] [from \"%s\"] [to \"%s\"] [tag \"%s\"]";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX, COMMAND_KEYWORD); 

    @Override
    public CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }
    
    @Override
    public void process(String input) throws ParseException {
        
        TodoListDB db = TodoListDB.getInstance();        
        
        // First, we check if it's a basic command, then don't bother filtering.
        if (input.toLowerCase().trim().equals(COMMAND_KEYWORD)) {
            String consoleMessage = String.format(MESSAGE_LISTING_ALL, db.countIncompleteTasks(), 
                    db.countOverdueTasks(), db.countFutureEvents());
            Renderer.renderIndex(db, consoleMessage);
            return;
        }
        
        List<Task> filteredTasks = new ArrayList<>();
        List<Event> filteredEvents = new ArrayList<>();
        
        // Parse the input with Tokenizer.
        Map<String, String[]> parsedResult = Tokenizer.tokenize(CalendarItemFilter.getFilterTokenDefinitions(), input);
        
        // Check if there are any unknown tokens.
        if (Disambiguator.getUnknownTokenString(parsedResult) != null) {
            String errorMessage = String.format(MESSAGE_UNKNOWN_TOKENS, Disambiguator.getUnknownTokenString(parsedResult));
            renderDisambiguation(parsedResult, true, true, errorMessage);
            return;
        }

        // Determine if command should return tasks/events/both.
        boolean[] tasksOrEventsBools;
        try {
            tasksOrEventsBools = CalendarItemFilter.parseIsTaskEvent(parsedResult);
        } catch (AmbiguousEventTypeException e) {
            renderDisambiguation(parsedResult, true, true, MESSAGE_AMBIGUOUS_TYPE);
            return;
        }
        
        boolean isTask = tasksOrEventsBools[0];
        boolean isEvent = tasksOrEventsBools[1];

        // Filter tasks and events.
        try {
            if (isTask) {
                filteredTasks = CalendarItemFilter.filterTasks(parsedResult);
            }
            if (isEvent) {
                filteredEvents = CalendarItemFilter.filterEvents(parsedResult);
            }
        } catch (InvalidNaturalDateException e) {
            renderDisambiguation(parsedResult, isTask, isEvent, MESSAGE_INVALID_DATE);
            return;
        }
        
        // Render the new view with filtered tasks.
        String consoleMessage = String.format(MESSAGE_LISTING_FILTERED, 
                filteredTasks.size(), StringUtil.pluralizer(filteredTasks.size(), "task", "tasks"),
                filteredEvents.size(), StringUtil.pluralizer(filteredEvents.size(), "event", "events"), input);
        Renderer.renderSelected(TodoListDB.getInstance(), consoleMessage, filteredTasks, filteredEvents);
    }
    
    /**
     * Disambiguate an ambiguous input by auto-populating a templated command on
     * a best-effort basis.
     * 
     * @param parsedResult
     * @param isTask
     * @param isEvent
     * @param errorMessage
     */
    private void renderDisambiguation(Map<String, String[]> parsedResult, boolean isTask, boolean isEvent, String errorMessage) {
        Map<String, String> extractedTokens = Disambiguator.extractParsedTokens(parsedResult);
        String consoleCommand;
        
        if ((isTask && isEvent) || (!isTask && !isEvent)) {
            consoleCommand = String.format(TEMPLATE_LIST, extractedTokens.get("startTime"), 
                    extractedTokens.get("endTime"), extractedTokens.get("tag"));
        } else if (isTask) {
            consoleCommand = String.format(TEMPLATE_LIST_TASKS, extractedTokens.get("taskStatus"), 
                    extractedTokens.get("startTime"), extractedTokens.get("endTime"), extractedTokens.get("tag"));
        } else {
            consoleCommand = String.format(TEMPLATE_LIST_EVENTS, extractedTokens.get("eventStatus"), 
                    extractedTokens.get("startTime"), extractedTokens.get("endTime"), extractedTokens.get("tag"));
        }
        
        Renderer.renderDisambiguation(consoleCommand, errorMessage);
    }
}
