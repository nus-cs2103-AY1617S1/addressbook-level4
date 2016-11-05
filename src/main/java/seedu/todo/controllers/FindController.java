package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.exceptions.ParseException;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.controllers.concerns.Tokenizer;
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
public class FindController implements Controller {
    
    private static final String NAME = "Find";
    private static final String DESCRIPTION = "Find tasks and events based on the provided keyword.\n";
    private static final String COMMAND_SYNTAX = "find <name>";
    private static final String COMMAND_WORD = "find";
    
    private static final String MESSAGE_LISTING_SUCCESS = "A total of %s found!";
    private static final String MESSAGE_LISTING_FAILURE = "No tasks or events found!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith(COMMAND_WORD)) ? 1 : 0;
    }

    @Override
    public void process(String input) throws ParseException {
        input = input.replaceFirst(COMMAND_WORD, "").trim();
        
        List<Predicate<Task>> taskPredicates = new ArrayList<Predicate<Task>>();
        taskPredicates.add(Task.predByName(input));
        List<Task> tasks = Task.where(taskPredicates);
        
        List<Predicate<Event>> eventPredicates = new ArrayList<Predicate<Event>>();
        eventPredicates.add(Event.predByName(input));
        List<Event> events = Event.where(eventPredicates);
        
        Renderer.renderSelected(TodoListDB.getInstance(), "Done!", tasks, events);
    }
}
