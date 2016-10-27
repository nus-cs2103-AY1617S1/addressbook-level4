package seedu.todo.controllers;

import seedu.todo.commons.EphemeralDB;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.CalendarItem;
import seedu.todo.models.TodoListDB;

/**
 * Controller to destroy a CalendarItem.
 * 
 * @author Tiong YaoCong
 *
 */
public class UntagController implements Controller {
    
    private static final String NAME = "Untag";
    private static final String DESCRIPTION = "Untag a task/event by listed index";
    private static final String COMMAND_SYNTAX = "untag <index> <tag name>";
    
    private static final String MESSAGE_UNTAG_SUCCESS = "Item has been untagged successfully.";
    private static final String MESSAGE_INDEX_OUT_OF_RANGE = "Could not untag task/event: Invalid index provided!";
    private static final String MESSAGE_MISSING_INDEX_AND_TAG_NAME = "Please specify the index of the item and the tag name to untag.";
    private static final String MESSAGE_INDEX_NOT_NUMBER = "Index has to be a number!";
    private static final String MESSAGE_TAG_NAME_NOT_FOUND = "Could not untag task/event: Tag name not found!";
    private static final String MESSAGE_TAG_NAME_DOES_NOT_EXIST = "Could not untag task/event: Tag name does not exist!";
    
    private static final int ITEM_INDEX = 0;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.toLowerCase().startsWith("untag")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        // Extract param
        String param = args.replaceFirst("untag", "").trim();
        
        if (param.length() <= 0) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_MISSING_INDEX_AND_TAG_NAME);
            return;
        }
        
        assert param.length() > 0;
        
        String[] parsedResult = parseParam(param);
        // Get index.
        int index = 0;
        String tagName = null;
        try {
            index = Integer.decode(parsedResult[ITEM_INDEX]);
            tagName = param.replaceFirst(parsedResult[ITEM_INDEX], "").trim();
        } catch (NumberFormatException e) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_INDEX_NOT_NUMBER);
            return;
        }
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        if (calendarItem == null) {
            Renderer.renderDisambiguation(String.format("untag %d", index), MESSAGE_INDEX_OUT_OF_RANGE);
            return;
        }
        
        // Check if tag name is provided
        if (parsedResult.length <= 1) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_TAG_NAME_NOT_FOUND);
            return;
        }
        
        assert calendarItem != null;
               
        boolean resultOfTagging = calendarItem.removeTag(tagName);
        
        // Re-render
        if (resultOfTagging) {
            //db.updateTagList(tagName);
            db.save();
            Renderer.renderIndex(db, MESSAGE_UNTAG_SUCCESS);
        } else {
            Renderer.renderDisambiguation(String.format("untag %d", index), MESSAGE_TAG_NAME_DOES_NOT_EXIST);
        }
    }

    private String[] parseParam(String param) {
        return param.split(" ");
    }

}
