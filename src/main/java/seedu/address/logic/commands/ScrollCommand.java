package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;

//@@author A0139097U
public class ScrollCommand extends Command {
	
	public static final String COMMAND_WORD = "scroll";
	public static final String MESSAGE_USAGE = "Scrolls to selected position "
			+ "Keywords: up, down or index";
	public static final String MESSAGE_SUCCESS = "Scrolled to ";
	
	private final String direction;
	
	public ScrollCommand(String direction) {
		this.direction = direction.trim();
	}
	@Override
	public CommandResult execute() {
		if(direction.equalsIgnoreCase("bottom")){
			EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredPersonList().size() - 1));
		} else if(direction.equalsIgnoreCase("top")) {
			EventsCenter.getInstance().post(new JumpToListRequestEvent(0));
		} else {
			try {
				int index = Integer.parseInt(direction);
				EventsCenter.getInstance().post(new JumpToListRequestEvent(index - 1));
			} catch (NumberFormatException e) {
				return new CommandResult(MESSAGE_USAGE);
			}
		}
		return new CommandResult(MESSAGE_SUCCESS + direction);
	}
	
}
