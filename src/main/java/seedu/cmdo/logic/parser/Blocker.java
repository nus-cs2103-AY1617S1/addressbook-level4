package seedu.cmdo.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.cmdo.MainApp;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.logic.Logic;
import seedu.cmdo.logic.LogicManager;
import seedu.cmdo.model.Model;
import seedu.cmdo.model.task.ReadOnlyTask;

public class Blocker {

	private static Blocker blocker;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
	
	LocalDate checkingDate;
	LocalTime checkingTime;
	LocalDateTime checking;
	LocalDateTime againstStart;
	LocalDateTime againstEnd;
		
	private Model model; 
	private static UnmodifiableObservableList<ReadOnlyTask> blockedList; 
	   
	private Blocker(Model model) { 
		init(model); 
	} 
	   
	private void init(Model model) { 
		blockedList = model.getBlockedList();  
	} 
	   
    public static Blocker getInstance(Model model) { 
      if (blocker == null) { 
    	  blocker = new Blocker(model); 
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
	public boolean isBlocked(ArrayList<LocalDateTime> datesAndTimes) {
		reset();
		for (LocalDateTime dt : datesAndTimes) {
			logger.info(String.format("Checking before is %s", checking.toString()));
			checkingDate = dt.toLocalDate();
			checkingTime = dt.toLocalTime();
			checking = LocalDateTime.of(checkingDate, checkingTime);
			logger.info(String.format("Checking after is %s", checking.toString()));
			for (ReadOnlyTask rot : blockedList) {
				againstStart = LocalDateTime.of(rot.getDueByDate().start, rot.getDueByTime().start);
				againstEnd = LocalDateTime.of(rot.getDueByDate().end, rot.getDueByTime().end);
				logger.info(String.format("AgainstStart is %s", againstStart.toString()));
				logger.info(String.format("AgainstEnd is %s", againstEnd.toString()));
				if (checking.isAfter(againstStart) && checking.isBefore(againstEnd)) {
					logger.info("Date is between blocked range.");
					return true; 
				}
			}
		}
		return false;
	}
	
	/**
	 * Resets values such that each time the Blocker is called we start fresh
	 * 
	 * @@author A0139661Y
	 */
	private void reset() {
		checkingDate = LocalDate.MAX;
		checkingTime = LocalTime.MAX;
		checking = LocalDateTime.MAX;
		againstStart = LocalDateTime.MIN;
		againstEnd = LocalDateTime.MIN;
	}
}