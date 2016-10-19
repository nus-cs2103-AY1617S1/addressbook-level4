package seedu.cmdo.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.model.Model;
import seedu.cmdo.model.task.ReadOnlyTask;

public class Blocker {

	private static Blocker blocker;
	private Model model;
	private static UnmodifiableObservableList<ReadOnlyTask> blockedList;
	
	private Blocker() {
		init();
	}
	
	private void init() {
		blockedList = model.getFilteredTaskList(); 
	}
	
    public static Blocker getInstance() {
    	if (blocker == null) {
    		blocker = new Blocker();
    	} return blocker;
    }
	
	/**
	 * Checks to see if a local date time array list of ranges falls within a blocked slot.
	 * 
	 * @param datesAndTimes
	 * @return boolean based on outcome
	 * 
	 * @@author A0139661Y
	 */
	public static boolean isBlocked(ArrayList<LocalDateTime> datesAndTimes) {
		for (LocalDateTime dt : datesAndTimes) {
			LocalDate checkingDate = dt.toLocalDate();
			LocalTime checkingTime = dt.toLocalTime();
			LocalDateTime checking = LocalDateTime.of(checkingDate, checkingTime);
			
			for (ReadOnlyTask rot : blockedList) {
				LocalDateTime againstStart = LocalDateTime.of(rot.getDueByDate().start, rot.getDueByTime().start);
				LocalDateTime againstEnd = LocalDateTime.of(rot.getDueByDate().end, rot.getDueByTime().end);
				
				if (checking.isAfter(againstStart) && checking.isBefore(againstEnd)) {
					return true;
				} else if (checking.isEqual(againstStart) || checking.isEqual(againstEnd)) {
					return true;
				}
			}
		}
		return false;
	}
}