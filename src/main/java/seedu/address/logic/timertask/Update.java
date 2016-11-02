package seedu.address.logic.timertask;

import java.text.ParseException;
import java.util.TimerTask;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.Model;

/**
 * Runs update command every day and when the user starts it up
 * @author Muhammad Arif Bin Syed Nasser
 *
 */
public class Update extends TimerTask {
	
	private Logic logic;
	
	public Update (Logic logic) {
		super();
		this.logic = logic;
	}
	
	@Override
	public void run() {
		try {
			logic.execute("update");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}
