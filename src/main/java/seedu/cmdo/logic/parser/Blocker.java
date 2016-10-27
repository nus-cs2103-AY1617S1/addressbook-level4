package seedu.cmdo.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.cmdo.MainApp;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.commons.exceptions.TaskBlockedException;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;

public class Blocker {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
	
	LocalDate checkingDate;
	LocalTime checkingTime;
	LocalDateTime checking;
	LocalDateTime againstStart;
	LocalDateTime againstEnd;

	public Blocker() {}

	//@@author A0139661Y
	public ArrayList<LocalDateTime> checkBlocked(Task toCheck, UnmodifiableObservableList<ReadOnlyTask> blockedList)
										throws TaskBlockedException {
		ArrayList<LocalDateTime> dateTimeList = new ArrayList<LocalDateTime>();
		if (toCheck.isRange()) {
			LocalDateTime startDt = LocalDateTime.of(toCheck.getDueByDate().start, toCheck.getDueByTime().start);
			LocalDateTime endDt = LocalDateTime.of(toCheck.getDueByDate().end, toCheck.getDueByTime().end);
			dateTimeList.add(startDt);
			dateTimeList.add(endDt);
		} else {
    		LocalDateTime dt = LocalDateTime.of(toCheck.getDueByDate().start, toCheck.getDueByTime().start);
    		dateTimeList.add(dt);
		}
        if (isBlocked(dateTimeList, blockedList)) {
        	throw new TaskBlockedException(Messages.MESSAGE_TIMESLOT_BLOCKED);
        }
        return dateTimeList;
	}
	
	/**
	 * Checks to see if a local date time array list of ranges falls within a blocked slot.
	 * 
	 * @param datesAndTimes
	 * @return boolean based on outcome
	 * 
	 * @@author A0139661Y
	 */
	public boolean isBlocked(List<LocalDateTime> datesAndTimes, UnmodifiableObservableList<ReadOnlyTask> blockedList) {
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
				if ((checking.isAfter(againstStart) && checking.isBefore(againstEnd))
						|| checking.isEqual(againstStart) 
						|| checking.isEqual(againstEnd)) {
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