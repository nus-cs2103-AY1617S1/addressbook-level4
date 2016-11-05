package seedu.todo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;
import seedu.todo.models.TodoListDB;

/**
 * Controller to find task/event by keyword
 * 
 * @@author A0093907W
 *
 */
public class FindController extends Controller {
    
    private static final String NAME = "Find";
    private static final String DESCRIPTION = "Find all tasks and events based on the provided keywords.\n" + 
    "This command will be search with non-case sensitive keywords.";
    private static final String COMMAND_SYNTAX = "find [name] or/and [on date]";
    private static final String COMMAND_KEYWORD = "find";
    
    private static final String MESSAGE_LISTING_SUCCESS = "A total of %s %s and %s %s found!";
    private static final String MESSAGE_LISTING_FAILURE = "No tasks or events found!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX, COMMAND_KEYWORD); 

    @Override
    public CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public void process(String input) throws ParseException {
        input = input.replaceFirst(COMMAND_KEYWORD, "").trim();
        
        List<Predicate<Task>> taskPredicates = new ArrayList<Predicate<Task>>();
        taskPredicates.add(Task.predByName(input));
        List<Task> tasks = Task.where(taskPredicates);
        
        List<Predicate<Event>> eventPredicates = new ArrayList<Predicate<Event>>();
        eventPredicates.add(Event.predByName(input));
        List<Event> events = Event.where(eventPredicates);
        
        if (tasks.size() == 0 && events.size() == 0) {
            Renderer.renderIndex(TodoListDB.getInstance(), MESSAGE_LISTING_FAILURE);
        } else {
            String consoleMessage = String.format(MESSAGE_LISTING_SUCCESS,
                    tasks.size(), StringUtil.pluralizer(tasks.size(), "task", "tasks"),
                    events.size(), StringUtil.pluralizer(events.size(), "event", "events"));
            Renderer.renderSelected(TodoListDB.getInstance(), consoleMessage, tasks, events);
        }
    }
}
