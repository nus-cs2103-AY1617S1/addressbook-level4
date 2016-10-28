package seedu.todo.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.TodoListDB;

/**
 * Controller to update a CalendarItem.
 * 
 * @@author A0093907W
 */
public class UpdateController implements Controller {
    
    private static final String NAME = "Update";
    private static final String DESCRIPTION = "Updates a task by listed index.";
    private static final String COMMAND_SYNTAX = "update <index> <task> by <deadline>";
    
    private static final String MESSAGE_UPDATE_SUCCESS = "Item successfully updated!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.toLowerCase().startsWith("update")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        args = args.replaceFirst("update", "").trim();
        
        // Get index.
        System.out.println(args);
        
        Matcher matcher = Pattern.compile("^\\d+").matcher(args);
        matcher.find();
        String indexStr = matcher.group();
        int index = Integer.decode(indexStr.trim());
        
        // Parse name and date.
        args = args.replaceFirst(indexStr, "").trim();
        String[] splitted = args.split("( at | by )", 2);
        String name = splitted[0].trim();
        String naturalDate = splitted[1].trim();
        
        // Parse natural date using Natty.
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(naturalDate);
        Date date = groups.get(0).getDates().get(0);
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        if (calendarItem != null) {
            calendarItem.setName(name);
            calendarItem.setCalendarDT(ldt);
            db.save();
        }
        
        // Re-render
        Renderer.renderIndex(db, MESSAGE_UPDATE_SUCCESS);
    }
}
