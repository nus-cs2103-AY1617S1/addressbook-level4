package seedu.testplanner.commons.util;

import static org.junit.Assert.*;
import seedu.dailyplanner.commons.util.DateUtil;

import org.junit.Test;

import seedu.dailyplanner.model.task.Time;
//@@author A0146749N
public class DateUtilTest {

	@Test
	public void convertHourTo24HrTest() {
		int result = DateUtil.convertTo24HrFormat(new Time("09.00AM"));
		assertEquals(9, result);
	}

}
