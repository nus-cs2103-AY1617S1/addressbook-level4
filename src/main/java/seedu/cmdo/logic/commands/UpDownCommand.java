package seedu.cmdo.logic.commands;

import seedu.cmdo.commons.core.EventsCenter;
import seedu.cmdo.commons.events.ui.JumpToListRequestEvent;
import seedu.cmdo.commons.events.ui.UpDownCommandEvent;

//@@author A0141006B
/**
 * Moves up and down the task list.
 */
public class UpDownCommand extends Command {

    public static final String COMMAND_WORD = "up/down";
    public static final String UP = "up";
    public static final String UP_ALT = "u";
    public static final String DOWN = "down";
    public static final String DOWN_ALT = "d";
    public static final String TOP = "top";
    public static final String TOP_ALT = "t";
    public static final String BOTTOM = "bottom";
    public static final String BOTTOM_ALT = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Moves up and down the list\n"
            + "Example: up";

    public static final String MESSAGE_UPDOWN_SUCCESS = "";

    public static String type;

    private static final EventsCenter ec = EventsCenter.getInstance();
    
    public UpDownCommand(String type) {
    	this.type = type;
    }

    @Override
    public CommandResult execute() {
        int indexToGoTo = 0;
    	int listSize = model.getFilteredTaskList().size();
    	String message = "Nothing happened.";
    	switch (type) {
    		case UP:
               	indexToGoTo = currentSelected-1;
               	message = "Up!";
                ec.post(new UpDownCommandEvent(type));
               	break;
    		case DOWN:
               	indexToGoTo = currentSelected+1;
               	message = "Down!";
                ec.post(new UpDownCommandEvent(type));
               	break;
    		case TOP:
    			indexToGoTo = 0;
    			message = "I can see my house from here.";
    			break;
    		case BOTTOM:
    			indexToGoTo = listSize-1;
    			message = "Right at the bottom.";
    			break;
        } 
        if (indexToGoTo < 0) {
    		return new CommandResult("You can't go up no more.");
    	} else if (indexToGoTo >= listSize) {
    		return new CommandResult("You can't go down no more.");
    	}
    	ec.post(new JumpToListRequestEvent(indexToGoTo));
    	return new CommandResult(message);
    }
}
