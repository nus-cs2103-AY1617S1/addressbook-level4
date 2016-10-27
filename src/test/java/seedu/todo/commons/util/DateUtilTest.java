package seedu.todo.commons.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import seedu.todo.commons.util.DateUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DateUtilTest {
	
	@Test
	public void toDate_equalTimestamps() {
		long currTimeMs = java.lang.System.currentTimeMillis();
		Date testDate = new Date(currTimeMs);
		LocalDateTime testDateTime = fromEpoch(currTimeMs);
		Date actualDate = DateUtil.toDate(testDateTime);
		
		assertEquals(testDate.getTime(), actualDate.getTime());
	}
	
	@Test
	public void toDate_differentTimestamps() {
		long currTimeMs = java.lang.System.currentTimeMillis();
		Date testDate = new Date(currTimeMs);
		LocalDateTime testDateTime = fromEpoch(currTimeMs + 1);
		Date actualDate = DateUtil.toDate(testDateTime);
		
		assertNotEquals(testDate.getTime(), actualDate.getTime());
	}
	
	@Test
	public void floorDate_sameDate() {
		long testEpoch1 = 1476099300000l; // 10/10/2016 @ 11:35am (UTC)
		long testEpoch2 = 1476099360000l; // 10/10/2016 @ 11:36am (UTC)
		LocalDateTime testDateTime1 = fromEpoch(testEpoch1);
		LocalDateTime testDateTime2 = fromEpoch(testEpoch2);
		
		assertEquals(DateUtil.floorDate(testDateTime1), DateUtil.floorDate(testDateTime2));
	}
	
	@Test
	public void floorDate_differentDate() {
	    long testEpoch1 = 1476099300000l; // 10/10/2016 @ 11:35am (UTC)
	    long testEpoch2 = 1476185700000l; // 11/10/2016 @ 11:35am (UTC)
	    LocalDateTime testDateTime1 = fromEpoch(testEpoch1);
	    LocalDateTime testDateTime2 = fromEpoch(testEpoch2);
	    
	    assertNotEquals(DateUtil.floorDate(testDateTime1), DateUtil.floorDate(testDateTime2));
	}
	
	@Test
	public void floorDate_nullTest() {
		assertEquals(DateUtil.floorDate(null), null);
	}
	
	@Test
	public void formatDayTests() {
	    LocalDateTime now = LocalDateTime.now();
	    assertEquals(DateUtil.formatDay(now), "Today");
	    assertEquals(DateUtil.formatDay(now.plus(1, ChronoUnit.DAYS)), "Tomorrow");
	    
	    // Show day of week for d = {n+2,...,n+6} where n = today
	    for (int i = 2; i <= 6; i++) {
	        String expected = now.plus(i, ChronoUnit.DAYS).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
	        assertEquals(DateUtil.formatDay(now.plus(i, ChronoUnit.DAYS)), expected);
	    }
	    
	    assertEquals(DateUtil.formatDay(now.minus(1, ChronoUnit.DAYS)), "1 day ago");
	    assertEquals(DateUtil.formatDay(now.minus(6, ChronoUnit.DAYS)), "6 days ago");
	    assertEquals(DateUtil.formatDay(now.minus(14, ChronoUnit.DAYS)), "14 days ago");
	}
	
	@Test
	public void formatShortDateTests() {
	    LocalDateTime now = LocalDateTime.now();
	    
	    String expectedToday = now.format(DateTimeFormatter.ofPattern("E dd MMM"));
	    assertEquals(DateUtil.formatShortDate(now), expectedToday);
	    
	    String expectedTomorrow = now.plus(1,  ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("E dd MMM"));
	    assertEquals(DateUtil.formatShortDate(now.plus(1, ChronoUnit.DAYS)), expectedTomorrow);
	    
	    for (int i = 2; i <= 6; i++) {
	        String expected = now.plus(i,  ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("dd MMM"));
	        assertEquals(DateUtil.formatShortDate(now.plus(i, ChronoUnit.DAYS)), expected);
	    }
	    
	    // Test dates in the past
	    LocalDateTime pastDate = fromEpoch(946656000000l); // 1 Jan 2000 UTC+8
	    assertEquals(DateUtil.formatShortDate(pastDate), pastDate.format(DateTimeFormatter.ofPattern("E dd MMM")));
	}
	
	@Test
	public void parseShortDateTests() {
	    // TODO
	}
	
	@Test
	public void formatTimeTests() {
	    assertEquals(DateUtil.formatTime(LocalDateTime.of(2016, 1, 1, 0, 0)), "00:00");
	    assertEquals(DateUtil.formatTime(LocalDateTime.of(2016, 1, 1, 2, 0)), "02:00");
	    assertEquals(DateUtil.formatTime(LocalDateTime.of(2016, 1, 1, 2, 33)), "02:33");
	    assertEquals(DateUtil.formatTime(LocalDateTime.of(2016, 1, 1, 13, 0)), "13:00");
	    assertEquals(DateUtil.formatTime(LocalDateTime.of(2016, 1, 1, 23, 59)), "23:59");
	}
	
	private static LocalDateTime fromEpoch(long epoch) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
	}
	
}
