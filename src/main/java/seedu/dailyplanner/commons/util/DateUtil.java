package seedu.dailyplanner.commons.util;

import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Time;

public class DateUtil {

	public static boolean hasStartandEndTime(ReadOnlyTask storedTask) {
		Time storedStartTime = storedTask.getStart().getTime();
		Time storedEndTime = storedTask.getEnd().getTime();

		if (!(storedStartTime.toString().equals("")) && !(storedEndTime.toString().equals("")))
			return true;

		return false;
	}

}
