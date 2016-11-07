# A0139922Yunused
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
	@Test
    public void ceilDate_sameDate_equals() {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atTime(10,0);
        LocalDateTime todayPlusAnHour = today.plusHours(1);
        assertEquals(DateUtil.ceilDate(today), DateUtil.ceilDate(todayPlusAnHour));
    }
	
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void ceilDate_sameDate_not_null() {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atTime(10,0);
        LocalDateTime todayPlusAnHour = today.plusHours(1);
        assertNotNull(DateUtil.ceilDate(today));
        assertNotNull(DateUtil.ceilDate(todayPlusAnHour));
        assertEquals(DateUtil.ceilDate(today), DateUtil.ceilDate(todayPlusAnHour));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void ceilDate_differentDate_not_equals() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tmr = LocalDateTime.now().plusDays(1);
        assertNotEquals(DateUtil.ceilDate(today), DateUtil.ceilDate(tmr));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void ceilDate_differentDate_not_null() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tmr = LocalDateTime.now().plusDays(1);
        assertNotNull(DateUtil.ceilDate(today));
        assertNotNull(DateUtil.ceilDate(tmr));
        assertNotEquals(DateUtil.ceilDate(today), DateUtil.ceilDate(tmr));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    public void ceilDate_nullDate_null() {
        LocalDateTime nullDate = null;
        assertEquals(null, DateUtil.ceilDate(nullDate));
    }
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void formatShortDateTests_null_test() {
        assertNull(DateUtil.formatShortDate(null));
        assertNotNull(DateUtil.formatShortDate(LocalDateTime.now()));
    }
	
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
	@Test
	public void formatTime_null_test() {
	    assertNull(DateUtil.formatTime(null));
	    assertNotNull(DateUtil.formatTime(LocalDateTime.now()));
	}
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testIfDateExist_false() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));
        assertFalse(DateUtil.checkIfDateExist(currentDate));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckIfDateExist_with_year_diff_true() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));
        // Only year is different
        assertTrue(DateUtil.checkIfDateExist(currentDate.plusYears(1)));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckIfDateExist_with_month_diff_true() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));
        // Only month is different
        assertTrue(DateUtil.checkIfDateExist(currentDate.plusMonths(1)));
        assertTrue(DateUtil.checkIfDateExist(currentDate.plusMonths(12)));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckifDateExist_with_day_diff_true() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));
        // Only day is different
        assertTrue(DateUtil.checkIfDateExist(currentDate.plusDays(1)));
        assertTrue(DateUtil.checkIfDateExist(currentDate.plusDays(365)));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckifDateExist_with_day_month_diff_true() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));  
        // Day and Month are different
        currentDate = currentDate.plusDays(1);
        currentDate = currentDate.plusMonths(1);
        assertTrue(DateUtil.checkIfDateExist(currentDate));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckifDateExist_with_day_year_diff_true() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));  
        // Day and Month are different
        currentDate = currentDate.plusDays(1);
        currentDate = currentDate.plusYears(1);
        assertTrue(DateUtil.checkIfDateExist(currentDate));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckifDateExist_with_month_year_diff_true() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));  
        // Day and Month are different
        currentDate = currentDate.plusMonths(1);
        currentDate = currentDate.plusYears(1);
        assertTrue(DateUtil.checkIfDateExist(currentDate));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckifDateExist_equals_false() {
        LocalDateTime currentDate = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfDateExist(currentDate));
        assertFalse(DateUtil.checkIfDateExist(currentDate));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testCheckIfTimeExist_false() {
        LocalDateTime currentTime = LocalDateTime.now();
        assertNotNull(DateUtil.checkIfTimeExist(currentTime));
        assertFalse(DateUtil.checkIfTimeExist(currentTime));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testIfTimeExist_true() {
        LocalDateTime currentTime = LocalDateTime.now().toLocalDate().atTime(10,0);
        assertNotNull(DateUtil.checkIfTimeExist(currentTime));
        assertTrue(DateUtil.checkIfTimeExist(currentTime.toLocalDate().atTime(currentTime.getHour() - currentTime.getHour(), 
                currentTime.getMinute())));
        assertTrue(DateUtil.checkIfTimeExist(currentTime.toLocalDate().atTime(currentTime.getHour(), 
                currentTime.getMinute() - currentTime.getMinute())));
        assertTrue(DateUtil.checkIfTimeExist(currentTime.toLocalDate().atTime(currentTime.getHour() - currentTime.getHour(), 
                currentTime.getMinute() - currentTime.getMinute())));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testParseTimeStamp_task_setTimeToMin() {
        //set the time to 00:00 for task
        assertNotEquals(DateUtil.parseTimeStamp(currentDay, null, true), currentDay);
        assertEquals(DateUtil.parseTimeStamp(currentDay, null, true), DateUtil.floorDate(currentDay));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testParseTimeStamp_event_setTimeToMin() {
        //will set the time to 00:00 for event
        assertNotEquals(DateUtil.parseTimeStamp(currentDay, nextDay, true), currentDay);
        assertEquals(DateUtil.parseTimeStamp(currentDay, nextDay, true), DateUtil.floorDate(currentDay));
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testParseTimeStamp_event_setTimeToMax() {
        // will set the time to 23:59
        assertNotEquals(DateUtil.parseTimeStamp(nextDay, currentDay, false), nextDay);
        assertEquals(DateUtil.parseTimeStamp(nextDay, currentDay, false), nextDayAt2359);
    }

```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testParseTimeStamp_event_followNextDayTime() {
        // will set the time to follow the nextDay
        assertNotEquals(DateUtil.parseTimeStamp(currentDay, nextDayAt2Pm, true), currentDay);
        assertEquals(DateUtil.parseTimeStamp(currentDay, nextDayAt2Pm, true), currentDayAt2Pm);
    }
    
```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testParseTimeStamp_event_followCurrentDayTime() {
        // will set the time to follow the currentDay
        assertEquals(DateUtil.parseTimeStamp(currentDayAt2Pm, nextDay, true), currentDayAt2Pm);
        assertNotEquals(DateUtil.parseTimeStamp(nextDay, currentDayAt2Pm, false), nextDay);
        assertEquals(DateUtil.parseTimeStamp(nextDay, currentDayAt2Pm, false), nextDayAt2Pm);
    }

```
###### /java/seedu/todo/commons/util/DateUtilTest.java
``` java
    @Test
    public void testParseTimeStamp_event_followGivenDateTime() {
        // if date and time exist, will not overwrite it
        assertEquals(DateUtil.parseTimeStamp(currentDayAt2Pm, nextDay, true), currentDayAt2Pm);
        assertEquals(DateUtil.parseTimeStamp(currentDayAt2Pm, nextDayAt2Pm, true), currentDayAt2Pm);
        assertEquals(DateUtil.parseTimeStamp(nextDayAt2Pm, currentDay, false), nextDayAt2Pm);
        assertEquals(DateUtil.parseTimeStamp(nextDayAt2Pm, currentDayAt2Pm, false), nextDayAt2Pm);
    }
}
```
###### /java/seedu/todo/commons/util/FilterUtilTest.java
``` java
public class FilterUtilTest {
    public static final LocalDateTime TODAY = LocalDateTime.now();
    public static final LocalDateTime TOMORROW = LocalDateTime.now().plusDays(1);
    public static final LocalDateTime YESTERDAY = LocalDateTime.now().minusDays(1);
    Task firstTestTask = getFirstTestTask();
    Task secondTestTask = getSecondTestTask();
    List<Event> overdueEvents = getOverdueEvents();
    List<Event> currentEvents = getCurrentEvents();
    
    /* ======================== Test cases for Filtering Task Methods ========================== */
    
    @Test 
    public void testFilterOutTask_equals() {
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        calendarItems.add(firstTestTask);
        assertEquals(calendarItems, FilterUtil.filterOutTask(calendarItems));
    }
    
    @Test
    public void testFilterOutTask_not_equals() {
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        calendarItems.add(firstTestTask);
        calendarItems.addAll(overdueEvents);
        assertNotEquals(calendarItems, FilterUtil.filterOutTask(calendarItems));
    }
    
    @Test
    public void testFilterTaskByNames_filter_with_empty_taskList_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        // Empty task list with empty name list
        assertEquals(tasks, FilterUtil.filterTaskByNames(tasks, nameList));
        // Empty task list with name list
        nameList.add("Nothing");
        assertEquals(tasks, FilterUtil.filterTaskByNames(tasks, nameList));
    }
    
    @Test
    public void testFilterTaskByNames_filter_by_fullname_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        
        // Filter out first test task
        nameList.add("Buy");
        tasks.add(firstTestTask);
        assertEquals(tasks, FilterUtil.filterTaskByNames(tasks, nameList));
        
        tasks.add(secondTestTask);
        List<Task> filteredResult = getEmptyTaskList();
        filteredResult.add(firstTestTask);
        assertEquals(filteredResult, (FilterUtil.filterTaskByNames(tasks, nameList)));
    }
    
    @Test
    public void testFilterTaskByNames_filter_by_fullname_not_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        List<Task> filteredResult = getEmptyTaskList();
        
        nameList.add("Buy");
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        filteredResult.add(firstTestTask);
        filteredResult.add(secondTestTask);
        assertNotEquals(filteredResult, (FilterUtil.filterTaskByNames(tasks, nameList)));
    }
    
    @Test
    public void testFilterTaskByNames_filter_by_subname_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        List<Task> filteredResult = getEmptyTaskList();
        
        // Filter out first test task
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        filteredResult.add(firstTestTask);
        nameList.add("Milk");
        assertEquals(filteredResult, FilterUtil.filterTaskByNames(tasks, nameList));
    }
    
    @Test
    public void testFilterTaskByNames_filter_by_subname_not_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        List<Task> filteredResult = getEmptyTaskList();
        
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        filteredResult.add(firstTestTask);
        filteredResult.add(secondTestTask);
        nameList.add("Milk");
        assertNotEquals(filteredResult, FilterUtil.filterTaskByNames(tasks, nameList));
    }
    
    @Test
    public void testFilterTaskByTags_with_empty_task_list_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        // Empty task list and name list
        assertEquals(tasks, FilterUtil.filterTaskByTags(tasks, nameList));
        // Empty task list
        nameList.add("Nothing");
        assertEquals(tasks, FilterUtil.filterTaskByTags(tasks, nameList));
    }
    
    @Test
    public void testFilterTaskByTags_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        List<Task> filteredResult = getEmptyTaskList();
        
        // Filter out first test task
        nameList.add("personal");
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        filteredResult.add(firstTestTask);
        assertEquals(filteredResult, FilterUtil.filterTaskByTags(tasks, nameList));
    }
    
    @Test
    public void testFilterTaskByTags_not_equals() {
        List<Task> tasks = getEmptyTaskList();
        HashSet<String> nameList = new HashSet<String>();
        List<Task> filteredResult = getEmptyTaskList();
        
        // Filter out first test task
        nameList.add("Buy");
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        filteredResult.add(firstTestTask);
        assertNotEquals(filteredResult, FilterUtil.filterTaskByTags(tasks, nameList));
    }

    @Test
    public void testFilterCompletedTaskList_equals() {
        List<Task> tasks = getEmptyTaskList();
        // Check with empty tasks
        assertEquals(tasks, FilterUtil.filterTasksByStatus(tasks, true));
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        
        // Filter out completed tasks
        List<Task> filteredTasks = getEmptyTaskList();
        filteredTasks.add(firstTestTask);
        assertEquals(filteredTasks, FilterUtil.filterTasksByStatus(tasks, true));
    }
    
    @Test
    public void testFilterCompletedTaskList_not_equals() {
        List<Task> tasks = getEmptyTaskList();
        List<Task> filteredResult = getEmptyTaskList();
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        filteredResult.add(secondTestTask);
        
        // Filter out completed tasks
        assertNotEquals(filteredResult, FilterUtil.filterTasksByStatus(tasks, true));
    }

    @Test
    public void testFilterIncompletedTaskList_equals() {
        List<Task> tasks = getEmptyTaskList();
        // Check with empty tasks
        assertEquals(tasks, FilterUtil.filterTasksByStatus(tasks, false));
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        
        // Filter out incomplete tasks
        List<Task> filteredTasks = getEmptyTaskList();
        filteredTasks.add(secondTestTask);
        assertEquals(filteredTasks, FilterUtil.filterTasksByStatus(tasks, false));
    }
    
    @Test
    public void testFilterIncompletedTaskList_not_equals() {
        List<Task> tasks = getEmptyTaskList();
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        
        // Filter out incomplete tasks
        List<Task> filteredTasks = getEmptyTaskList();
        filteredTasks.add(firstTestTask);
        filteredTasks.add(secondTestTask);
        assertNotEquals(filteredTasks, FilterUtil.filterTasksByStatus(tasks, false));
    }
    
    @Test 
    public void testFilterTaskBySingleDate_with_empty_tasks_equals() {
        List<Task> tasks = getEmptyTaskList();
        assertEquals(tasks, FilterUtil.filterTaskBySingleDate(tasks, DateUtil.floorDate(TODAY)));
        assertEquals(tasks, FilterUtil.filterTaskBySingleDate(tasks, null));
    }

    @Test
    public void testFilterTaskBySingleDate_equals() {
        List<Task> tasks = getEmptyTaskList();
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        
        // Filter out first task
        List<Task> filteredTasks = getEmptyTaskList();
        filteredTasks.add(firstTestTask);
        assertEquals(filteredTasks, FilterUtil.filterTaskBySingleDate(filteredTasks, DateUtil.floorDate(TODAY)));
    }
    
    @Test
    public void testFilterTaskBySingleDate_not_equals() {
        List<Task> tasks = getEmptyTaskList();
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        
        List<Task> filteredTasks = getEmptyTaskList();
        filteredTasks.add(firstTestTask);
        filteredTasks.add(firstTestTask);
        assertNotEquals(filteredTasks, FilterUtil.filterTaskBySingleDate(tasks, TODAY));
    }
    
    @Test 
    public void testFilterTaskWithDateRange_with_empty_tasks_equals() {
        List<Task> tasks = getEmptyTaskList();
        assertEquals(tasks, FilterUtil.filterTaskWithDateRange(tasks, 
                LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4)));
    }
    
    @Test
    public void testFilterTaskWithDateRange_out_of_range_equals() {
        List<Task> tasks = getEmptyTaskList();
        tasks.add(firstTestTask);
        // Filter out of range of test task
        assertEquals(getEmptyTaskList(), FilterUtil.filterTaskWithDateRange(tasks, 
                LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4)));
    }

    @Test
    public void testFilterTaskWithDateRange_equals() {
        List<Task> tasks = getEmptyTaskList();
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
       
        // Filter out first task
        List<Task> filteredTasks = getEmptyTaskList();
        filteredTasks.add(firstTestTask);
        assertEquals(filteredTasks, FilterUtil.filterTaskWithDateRange(tasks, TODAY, TODAY));
    }
    
    @Test
    public void testFilterTaskWithDateRange_with_null_dates_equals() {
        List<Task> tasks = getEmptyTaskList();
        tasks.add(firstTestTask);
        tasks.add(secondTestTask);
        
        List<Task> filteredTasks = getEmptyTaskList();
        filteredTasks.add(firstTestTask);
        filteredTasks.add(secondTestTask);
        // Filter out both task
        assertEquals(filteredTasks, FilterUtil.filterTaskWithDateRange(tasks, null, TOMORROW));
        assertEquals(filteredTasks, FilterUtil.filterTaskWithDateRange(tasks, YESTERDAY, null));
    }
    
    /* ============================= Test cases for Event Filtering Methods ==================== */
    
    @Test
    public void testFilterOutEvent_equals() {
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        calendarItems.addAll(overdueEvents);
        assertEquals(calendarItems, FilterUtil.filterOutEvent(calendarItems));
    }
    
    @Test
    public void testFilterOutEvent_not_equals() {
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        calendarItems.add(firstTestTask);
        calendarItems.addAll(overdueEvents);
        assertNotEquals(calendarItems, FilterUtil.filterOutEvent(calendarItems));
    }
    
    @Test
    public void testFilterEventByNames_filter_with_empty_eventList_equals() {
        List<Event> events = getEmptyEventList();
        HashSet<String> nameList = new HashSet<String>();
        // Empty event list and name list
        assertEquals(events, FilterUtil.filterEventByNames(events, nameList));
        // Empty event list
        nameList.add("Nothing");
        assertEquals(events, FilterUtil.filterEventByNames(events, nameList));
    }
    
    @Test
    public void testFilterEventByNames_filter_equals() {
        List<Event> events = getEmptyEventList();
        HashSet<String> nameList = new HashSet<String>();
        List<Event> filteredResult = getEmptyEventList();
        
        // Filter out overdue events
        nameList.add("CS2103");
        nameList.add("Roadshow");
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        assertEquals(filteredResult, FilterUtil.filterEventByNames(events, nameList));
    }
    
    @Test
    public void testFilterEventByNames_filter_not_equals() {
        List<Event> events = getEmptyEventList();
        HashSet<String> nameList = new HashSet<String>();
        events.addAll(overdueEvents);
        nameList.add("Nothing");
        assertNotEquals(events, FilterUtil.filterEventByNames(events, nameList));
    }
    
    @Test
    public void testFilterEventByTags_with_empty_event_list_equals() {
        List<Event> events = getEmptyEventList();
        HashSet<String> nameList = new HashSet<String>();
        // Empty event list and name list
        assertEquals(events, FilterUtil.filterEventByTags(events, nameList));
        // Empty event list
        nameList.add("Nothing");
        assertEquals(events, FilterUtil.filterEventByTags(events, nameList));
    }

    @Test
    public void testFilterEventByTags_equals() {
        List<Event> events = getEmptyEventList();
        HashSet<String> nameList = new HashSet<String>();
        List<Event> filteredResult = getEmptyEventList();
        
        // Filter out overdue events
        nameList.add("CS3216");
        nameList.add("CSIT");
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        assertEquals(filteredResult, FilterUtil.filterEventByTags(events, nameList));
    }
    
    @Test
    public void testFilterEventByTags_not_equals() {
        List<Event> events = getEmptyEventList();
        HashSet<String> nameList = new HashSet<String>();
        List<Event> filteredResult = getEmptyEventList();

        // Filter out overdue events
        nameList.add("test");
        events.addAll(overdueEvents);
        filteredResult.addAll(overdueEvents);
        assertNotEquals(filteredResult, FilterUtil.filterEventByTags(events, nameList));
    }
    
    @Test 
    public void testFilterIsOverEventList_with_empty_event_list_equals() {
        List<Event> events = new ArrayList<Event>();
        // Filter with empty events list
        assertEquals(events, FilterUtil.filterEventsByStatus(events, true));
    }

    @Test
    public void testFilterIsOverEventList_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = getEmptyEventList();
        
        // Filter out overdue events
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        assertEquals(filteredResult, FilterUtil.filterEventsByStatus(events, true));
    }
    
    @Test
    public void testFilterIsOverEventList_not_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = getEmptyEventList();
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        filteredResult.addAll(currentEvents);
        assertNotEquals(filteredResult, FilterUtil.filterEventsByStatus(events, false));
    }
    
    @Test 
    public void testFilterCurrentEventList_with_empty_event_list_equals() {
        List<Event> events = new ArrayList<Event>();
        // Filter with empty events list
        assertEquals(events, FilterUtil.filterEventsByStatus(events, false));
    }

    @Test
    public void testFilterCurrentEventList_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = getEmptyEventList();
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(currentEvents);
        assertEquals(filteredResult, FilterUtil.filterEventsByStatus(events, false));    
    }
    
    @Test
    public void testFilterCurrentEventList_not_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = getEmptyEventList();
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        assertNotEquals(filteredResult, FilterUtil.filterEventsByStatus(events, true));
    }

    @Test
    public void testFilterEventsBySingleDate_with_empty_event_list_equals() {
        List<Event> events = new ArrayList<Event>();
        // Events is empty
        assertEquals(events, FilterUtil.filterEventBySingleDate(events, TODAY));
    }
    
    @Test
    public void testFilterEventBySingleDate_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = getEmptyEventList();
        // Filter out overdue events 
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        assertEquals(filteredResult, FilterUtil.filterEventBySingleDate(events, DateUtil.floorDate(YESTERDAY)));
    }
    
    @Test
    public void testFilterEventBySingleDate_not_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = getEmptyEventList();
        
        // Filter out current events 
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(currentEvents);
        assertNotEquals(filteredResult, FilterUtil.filterEventBySingleDate(events, DateUtil.floorDate(YESTERDAY)));
    }

    @Test
    public void testFilterEventWithDateRange_empty_event_list_equals() {
        List<Event> events = new ArrayList<Event>();
        // Events is empty
        assertEquals(events, FilterUtil.filterEventWithDateRange(events, YESTERDAY, YESTERDAY));
    }
    
    @Test
    public void testFilterEventWithDateRange_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = new ArrayList<Event>();
        // Filter out overdue events 
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        assertEquals(filteredResult, 
                FilterUtil.filterEventWithDateRange(events, DateUtil.floorDate(YESTERDAY), DateUtil.ceilDate(YESTERDAY)));
    }
    
    @Test
    public void testFilterEventWithDateRange_null_dates_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = new ArrayList<Event>();
        // Filter out both overdue and current events
        events.addAll(overdueEvents);
        events.addAll(currentEvents);
        filteredResult.addAll(overdueEvents);
        filteredResult.addAll(currentEvents);
        assertEquals(events, FilterUtil.filterEventWithDateRange(events, null, DateUtil.ceilDate(TOMORROW)));
        assertEquals(events, FilterUtil.filterEventWithDateRange(events, DateUtil.floorDate(YESTERDAY), null));
        assertEquals(events, FilterUtil.filterEventWithDateRange(events, null, null));
    }
    
    @Test
    public void testFilterEventWithDateRange_not_equals() {
        List<Event> events = new ArrayList<Event>();
        List<Event> filteredResult = new ArrayList<Event>();
        // Filter out overdue events 
        events.addAll(currentEvents);
        events.addAll(overdueEvents);
        filteredResult.addAll(currentEvents);
        assertNotEquals(filteredResult, 
                FilterUtil.filterEventWithDateRange(events, DateUtil.floorDate(YESTERDAY), DateUtil.ceilDate(YESTERDAY)));
    }
    
    /* ===================  Helper methods to be use to generate Task and Event for testing =============== */
    
    private Task getFirstTestTask() {
        Task task = new Task();
        task.setName("Buy Milk");
        task.setCalendarDateTime(TODAY);
        task.addTag("personal");
        task.setCompleted();
        return task;
    }
    
    private Task getSecondTestTask() {
        Task task = new Task();
        task.setName("CS2103");
        task.setCalendarDateTime(TOMORROW);
        task.addTag("CS2103");
        return task;
    }
    
    private List<Task> getEmptyTaskList() {
        return new ArrayList<Task>();
    }
    
    private List<Event> getEmptyEventList() {
        return new ArrayList<Event>();
    }
    
    private List<Event> getOverdueEvents() {
        List<Event> events = new ArrayList<Event>();
        Event event = new Event();
        event.setStartDate(YESTERDAY);
        event.setEndDate(YESTERDAY);
        event.setName("CS2103 V0.5");
        event.addTag("CS2103");
        events.add(event);
        event.removeTag("CS2103");
        event.setName("CSIT roadshow");
        event.addTag("CSIT");
        events.add(event);
        return events;
    }
    
    private List<Event> getCurrentEvents() {
        List<Event> events = new ArrayList<Event>();
        Event event = new Event();
        event.setStartDate(TODAY);
        event.setEndDate(TOMORROW);
        event.setName("CS3216 9th Steps");
        event.addTag("CS3216");
        events.add(event);
        event.removeTag("CS3216");
        event.setName("CS3217 9th Steps");
        event.addTag("CS3217");
        events.add(event);
        return events;
    }
}
```
###### /java/seedu/todo/commons/util/ParseUtilTest.java
``` java
public class ParseUtilTest {
    
    Map<String, String[]> parsedResult = new HashMap<String, String[]>();
    public static final String EMPTY_TOKEN = "";
    public static final String [] NULL_TOKEN_RESULT = new String [] { null }; 
    public static final String [] TOKEN_RESULT = new String [] {"test", "test1, test2"};
    
    public static final String TEST_TOKEN = "test";
    public static final String TOKEN_KEYWORD_DOES_NOT_EXIST = "random";
    
    public static final int TOKEN_RESULT_INDEX_ONE = 0;
    public static final int TOKEN_RESULT_INDEX_TWO = 1;
    
    public static final String CORRECT_NATURAL_DATE = "today";
    public static final String INCORRECT_NATURAL_DATE = "todar";
    
    public static final String DATE_ON_FORMAT = Tokenizer.TIME_TOKEN;
    public static final String DATE_FROM_FORMAT = Tokenizer.TIME_FROM_TOKEN;
    public static final String DATE_TO_FORMAT = Tokenizer.TIME_TO_TOKEN;
    
    @Test
    public void testIsTokenNull_true() {
        parsedResult.put(TEST_TOKEN, NULL_TOKEN_RESULT);
        assertTrue(ParseUtil.isTokenNull(parsedResult, TEST_TOKEN));
    }
    
    @Test
    public void testIsTokenNull_false() {
        parsedResult.put(TEST_TOKEN, TOKEN_RESULT);
        assertFalse(ParseUtil.isTokenNull(parsedResult, TEST_TOKEN));
    }

    @Test
    public void testDoesTokenContainKeyword_true() {
        parsedResult.put(TEST_TOKEN, TOKEN_RESULT);
        assertTrue(ParseUtil.doesTokenContainKeyword(parsedResult, TEST_TOKEN, TOKEN_RESULT[TOKEN_RESULT_INDEX_ONE]));
    }
    
    @Test
    public void testDoesTokenContainKeyword_false() {
        parsedResult.put(TEST_TOKEN, TOKEN_RESULT);
        assertFalse(ParseUtil.doesTokenContainKeyword(parsedResult, TEST_TOKEN, TOKEN_KEYWORD_DOES_NOT_EXIST));
    }
    
    @Test
    public void testDoesTokenContainKeyword_with_empty_token_false() {
        parsedResult.put(TEST_TOKEN, TOKEN_RESULT);
        assertFalse(ParseUtil.doesTokenContainKeyword(parsedResult, EMPTY_TOKEN, TOKEN_RESULT[TOKEN_RESULT_INDEX_ONE]));
        assertFalse(ParseUtil.doesTokenContainKeyword(parsedResult, EMPTY_TOKEN, NULL_TOKEN_RESULT[TOKEN_RESULT_INDEX_ONE]));
    }

    @Test
    public void testGetTokenResult_not_null() {
        assertNotNull(ParseUtil.getTokenResult(parsedResult, TEST_TOKEN), TOKEN_RESULT[TOKEN_RESULT_INDEX_TWO]);
    }
    
    @Test
    public void testGetTokenResult_null() {
        assertNull(ParseUtil.getTokenResult(parsedResult, EMPTY_TOKEN));
    }
    
    @Test
    public void testGetTokenResult_equals() {
        parsedResult.put(TEST_TOKEN, TOKEN_RESULT);
        assertEquals(ParseUtil.getTokenResult(parsedResult, TEST_TOKEN), TOKEN_RESULT[TOKEN_RESULT_INDEX_TWO]);
    }
    
    @Test
    public void testGetTokenResult_not_equals() {
        parsedResult.put(TEST_TOKEN, TOKEN_RESULT);
        assertNotEquals(ParseUtil.getTokenResult(parsedResult, TEST_TOKEN), TOKEN_RESULT[TOKEN_RESULT_INDEX_ONE]);
    }
    
    @Test 
    public void testParseDates_with_valid_dates_not_null() {
        String [] date_on_result = { "time", "today" };
        String [] incorrect_date_result = { "" , null };
        
        parsedResult.put(DATE_ON_FORMAT, date_on_result);
        parsedResult.put(DATE_FROM_FORMAT, incorrect_date_result);
        parsedResult.put(DATE_TO_FORMAT, incorrect_date_result);
        assertNotNull(ParseUtil.parseDates(parsedResult));
    }
    
    @Test
    public void testParseDates_null() {
        String [] incorrect_date_result = { "" , null };
        parsedResult.put(DATE_ON_FORMAT, incorrect_date_result);
        parsedResult.put(DATE_FROM_FORMAT, incorrect_date_result);
        parsedResult.put(DATE_TO_FORMAT, incorrect_date_result);
        assertNull(ParseUtil.parseDates(parsedResult)); 
    }

    @Test
    public void testParseDates_equals() {
        String[] test_result = { "3", "today" , "tuesday" , "wednesday" };        
        String [] date_on_result = { "time", "today" };
        String [] date_from_result = { "timeFrom" , "tuesday" };
        String [] date_to_result = { "timeTo" , "wednesday" };
        
        parsedResult.put(DATE_ON_FORMAT, date_on_result);
        parsedResult.put(DATE_FROM_FORMAT, date_from_result);
        parsedResult.put(DATE_TO_FORMAT, date_to_result);
        assertArrayEquals(test_result, ParseUtil.parseDates(parsedResult));
    }
}
```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test
    public void testSplitStringBySpace_null() {
        assertNull(StringUtil.splitStringBySpace(null));
    }
    
```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test
    public void testSplitStringBySpace_not_null() {
        String testcase1 = "TEST TEST";
        assertNotNull(StringUtil.splitStringBySpace(testcase1));
    }

```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test 
    public void testSplitStringBySpace_equals() {
        String testcase1 = "TEST";
        String testcase2 = "TEST TEST";
        assertArrayEquals(testcase1.split(" "), StringUtil.splitStringBySpace(testcase1));
        assertArrayEquals(testcase2.split(" "),StringUtil.splitStringBySpace(testcase2));
    }
    
```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test
    public void testFormatNumberOfTaskWithPuralizer_equals() {
        int single = 1;
        assertEquals(String.format("%d task", single), StringUtil.formatNumberOfTaskWithPuralizer(single));
        int pural = 2;
        assertEquals(String.format("%d tasks", pural), StringUtil.formatNumberOfTaskWithPuralizer(pural));
    }
    
```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test
    public void testFormatNumberOfTaskWithPuralizer_not_equals() {
        int single = 1;
        assertNotEquals(String.format("%d tasks", single), StringUtil.formatNumberOfTaskWithPuralizer(single));
        int pural = 2;
        assertNotEquals(String.format("%d task", pural), StringUtil.formatNumberOfTaskWithPuralizer(pural));
    }

```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test
    public void testFormatNumberOfEventWithPuralizer_equals() {
        int single = 1;
        assertEquals(String.format("%d event", single), StringUtil.formatNumberOfEventWithPuralizer(single));
        int pural = 2;
        assertEquals(String.format("%d events", pural), StringUtil.formatNumberOfEventWithPuralizer(pural));
    }
    
```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test
    public void testFormatNumberOfEventWithPuralizer_not_equals() {
        int single = 1;
        assertNotEquals(String.format("%d events", single), StringUtil.formatNumberOfEventWithPuralizer(single));
        int pural = 2;
        assertNotEquals(String.format("%d event", pural), StringUtil.formatNumberOfEventWithPuralizer(pural));
    }
    
```
###### /java/seedu/todo/commons/util/StringUtilTest.java
``` java
    @Test
    public void testDisplayNumberOfTaskAndEventFoundWithPuralizer_equals() {
        int numTasks = 0;
        int numEvents = 0;
        assertEquals("No item found!", StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(numTasks, numEvents));
        numTasks = 1;
        assertEquals("1 task", StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(numTasks, numEvents));
        numEvents = 1;
        assertEquals("1 task and 1 event", StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(numTasks, numEvents));
        numTasks = 0;
        assertEquals("1 event", StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(numTasks, numEvents));
    }
}
```
###### /java/seedu/todo/guitests/ClearCommandTest.java
``` java
    @Test
    public void clear_tasks_by_single_date() {
        console.runCommand("clear tasks on tmr");
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    
        // Check if Tasks and Events still in the GUI
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_tasks_by_date_range() {
        console.runCommand("clear tasks from today to tmr");
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_tasks_by_date_range_with_single_date() {
        console.runCommand("clear tasks from today");
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskNotVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_events_by_single_date() {
        console.runCommand("clear events on tmr");
        // For console text area to check output message        
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_events_by_date_range() {
        console.runCommand("clear events from today to tmr");
        // For console text area to check output message        
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_events_by_date_range_with_single_date() {
        console.runCommand("clear events from today");
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 2;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventNotVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_by_single_date() {
        console.runCommand("clear on tmr");
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_by_date_range() {
        console.runCommand("clear from today to tmr");
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_by_date_range_with_single_date() {
        console.runCommand("clear from today");
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 2;
        String expectedOutputMessage = String.format(ClearController.MESSAGE_CLEAR_SUCCESS_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskNotVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventNotVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_with_invalid_date_syntax() {
        console.runCommand("clear from todar");
        // For console text area to check error message
        String expectedDisambiguation = ClearController.CLEAR_DATE_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        String expectedOutputMessage = formatConsoleOutputTextArea(ClearController.MESSAGE_NO_DATE_DETECTED);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_with_date_conflict() {
        console.runCommand("clear by today to tmr");
        // For console text area to check error message
        String expectedDisambiguation = ClearController.CLEAR_DATE_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        String expectedOutputMessage = formatConsoleOutputTextArea(ClearController.MESSAGE_DATE_CONFLICT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_with_item_type_conflict() {
        console.runCommand("clear tasks events");
        // For console text area to check error message
        String expectedDisambiguation = ClearController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        String expectedOutputMessage = formatConsoleOutputTextArea(ClearController.MESSAGE_ITEM_TYPE_CONFLICT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_with_invalid_command_syntax_by_event_status() {
        console.runCommand("clear current events");
        // For console text area to check error message
        String expectedDisambiguation = ClearController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        String expectedOutputMessage = formatConsoleOutputTextArea(ClearController.MESSAGE_CLEAR_UNABLE_TO_SUPPORT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
    
    @Test
    public void clear_with_invalid_command_syntax_by_task_status() {
        console.runCommand("clear completed tasks");
        // For console text area to check error message
        String expectedDisambiguation = ClearController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        String expectedOutputMessage = formatConsoleOutputTextArea(ClearController.MESSAGE_CLEAR_UNABLE_TO_SUPPORT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
        
        // Check if Tasks and Events still in the GUI
        assertTaskVisibleAfterCmd("list", task1);
        assertTaskVisibleAfterCmd("list", task2);
        assertEventVisibleAfterCmd("list", event3);
        assertEventVisibleAfterCmd("list", event4);
    }
}
```
###### /java/seedu/todo/guitests/FindCommandTest.java
``` java
 */
public class FindCommandTest extends GuiTest {
```
###### /java/seedu/todo/guitests/FindCommandTest.java
``` java
    @Test
    public void find_by_name() {
        String command = "find name buy";
        // To check if the tasks and events are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_tasks_by_name() {
        String command = "find name buy tasks";
        // To check if the tasks are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_events_by_name() {
        String command = "find name buy events";
        // To check if the events are been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_by_invalid_name() {
        String command = "find name tester";
        // To check if the tasks and events are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check error message
        assertEquals(console.getConsoleTextArea(), FindController.MESSAGE_NO_RESULT_FOUND);
    }
    
    @Test
    public void find_by_tag() {
        String command = "find tagName buy";
        // To check if the tasks and events are been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_tasks_by_tag() {
        String command = "find tagName personal tasks";
        // To check if the tasks are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_events_by_tag() {
        String command = "find tagName buy events";
        // To check if event 4 is been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_by_invalid_tag() {
        // To check if all tasks and events are still in the view
        String command = "find tagName tester";
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        assertEquals(console.getConsoleTextArea(), FindController.MESSAGE_NO_RESULT_FOUND);
    }
    
    @Test
    public void find_by_keyword() {
        String command = "find buy";
        // To check if tasks and events are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_tasks_by_keyword() {
        String command = "find buy tasks";
        // To check if tasks are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_events_by_keyword() {
        String command = "find buy events";
        // To check if event4 is been filtered
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_by_invalid_keyword() {
        String command = "find tester";
        // To check if all tasks and events are still in the view
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        assertEquals(console.getConsoleTextArea(), FindController.MESSAGE_NO_RESULT_FOUND);
    }
    
    @Test
    public void find_by_tasks_complete_status() {
        console.runCommand("complete 1");
        String command = "find buy complete";
        // To check if task1 is been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_by_tassk_incomplete_status() {
        String command = "find buy incomplete";
        // To check if tasks are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_by_events_current_status() {
        String command = "find buy current";
        // To check if event3 is been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_by_events_over_status() {
        String command = "find buy over";
        // To check if all tasks and events are still in the view
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        assertEquals(console.getConsoleTextArea(), FindController.MESSAGE_NO_RESULT_FOUND);
    }
    
    @Test
    public void find_by_single_date() {
        String command = "find buy on today";
        // To check if task1 is been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_by_date_range() {
        String command = "find buy from today";
        // To check if tasks and events are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);

        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_tasks_by_single_date() {
        String command = "find buy tasks on today";
        
        // To check if task1 is been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test 
    public void find_tasks_by_date_range() {
        String command = "find buy tasks from today to tmr";
        
        // To check if all tasks are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_tasks_by_date_range_with_single_date() {
        String command = "find buy tasks from today";
        // To check if all tasks are been filtered as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_events_by_single_date() {
        String command = "find CS2103 events on tomorrow";
        // To check if event3 is been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_events_by_date_range_with_single_date() {
        String command = "find buy events from today";
        // To check if event4 is been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_events_by_date_range() {
        String command = "find CS2103 events from today to tmr";
        // To check if event3 is been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(FindController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_missingKeywords_disambiguate() {
        String command = "find";
        console.runCommand(command);
        
        // To check if the consoleInputText matches the controller corrected syntax
        String expectedDisambiguation = FindController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(FindController.MESSAGE_NO_KEYWORD_FOUND);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_invalidTaskSyntax_disambiguate() {
        String command = "find buy task over";
        console.runCommand(command);
        
        // To check if the consoleInputText matches the controller corrected syntax
        String expectedDisambiguation = FindController.FIND_TASK_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(FindController.MESSAGE_INVALID_TASK_STATUS);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_invalidEventSyntax_disambiguate() {
        String command = "find buy event complete";
        console.runCommand(command);
        
        // To check if the consoleInputText matches the controller corrected syntax
        String expectedDisambiguation = FindController.FIND_EVENT_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);

        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(FindController.MESSAGE_INVALID_EVENT_STATUS);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_with_invalid_single_date() {
        String command = "find buy by todar";
        console.runCommand(command);
        
        // To check if the consoleInputText matches the controller corrected syntax
        String expectedDisambiguation = FindController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(FindController.MESSAGE_NO_DATE_DETECTED);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_with_date_conflict() {
        String command = "find buy by today from tmr";
        console.runCommand(command);
        
        // To check if the consoleInputText matches the controller corrected syntax
        String expectedDisambiguation = FindController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(FindController.MESSAGE_DATE_CONFLICT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void find_with_invalid_date_range() {
        String command = "find buy from today to tml";
        console.runCommand(command);
        
        // To check if the consoleInputText matches the controller corrected syntax
        String expectedDisambiguation = FindController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(FindController.MESSAGE_NO_DATE_DETECTED);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
}
```
###### /java/seedu/todo/guitests/guihandles/SideBarHandle.java
``` java
 */
public class SideBarHandle extends GuiHandle {

    private static final String TAGLIST = "#sidebarTagsPlaceholder";

    public SideBarHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }
    
    /** 
     * Returns a TaskListDateItemHandle that corresponds to the name specified.
     * If it doesn't exist, it returns null.
     */
    public TagListItemHandle getTagListItem(String tagName) {
        Optional<Node> tagItemNode = guiRobot.lookup(TAGLIST).queryAll().stream()
                .filter(node -> new TagListItemHandle(guiRobot, primaryStage, node).getName().equals(tagName))
                .findFirst();
        
        if (tagItemNode.isPresent()) {
            return new TagListItemHandle(guiRobot, primaryStage, tagItemNode.get());
        } else {
            return null;
        }
    }
}
```
###### /java/seedu/todo/guitests/guihandles/TagListItemHandle.java
``` java
 */
public class TagListItemHandle extends GuiHandle {

    private static final String TAGLISTITEM_LABEL = "#labelText";
    private Node node;

    public TagListItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    public String getName() {
        return getStringFromText(TAGLISTITEM_LABEL, node);
    }

}
```
###### /java/seedu/todo/guitests/GuiTest.java
``` java
     * Utility method for testing if tag has been successfully added to the GUI side panel.
     * This runs a command and checks if TagList contains tag that matches
     * the tag that was just added.
     * 
     * Assumption : No tags can have the same name
     * 
     */
    protected void assertTaskTagVisibleAfterCmd(String command, Task taskToAdd) {
        // Run the command in the console.
        console.runCommand(command);
        int tag_index = 0;
        
        // Get the Tag List
        ArrayList<String> taskTagList = taskToAdd.getTagList();
        
        // Assumption each task only got 1 tag
        String tagName = taskTagList.get(tag_index);
        
        // Check if tag exist in the side panel tag list
        TagListItemHandle tagItem = sidebar.getTagListItem(tagName);
        assertNotNull(tagItem);
        
        // Check if the tag found is equal to the tag name of the task
        String tagItemName = tagItem.getName();
        assertEquals(tagItemName, tagName);
    }
    
    /**
```
###### /java/seedu/todo/guitests/GuiTest.java
``` java
     * Utility method for testing if tag has been successfully removed from the GUI side panel.
     * This runs a command and checks if TagList contains tag that matches
     * the tag that was just removed.
     * 
     * Assumption : No tags can have the same name
     * 
     */
    protected void assertTaskTagNotVisibleAfterCmd(String command, Task taskToAdd) {
        // Get the Tag List
        ArrayList<String> taskTagList = taskToAdd.getTagList();
        
        // Get the tag name with assumption each task only got 1 tag
        String tagName = getTagNameFromCommand(command);
        
        // Run the command in the console.
        console.runCommand(command);
        
        // Check if tag exist in the side panel tag list
        TagListItemHandle tagItem = sidebar.getTagListItem(tagName);
        assertNull(tagItem);
        
        // Check if tag has been removed
        int expected_tag_list_size = 0;
        assertEquals(taskTagList.size(), expected_tag_list_size);
    }
    
    /**
```
###### /java/seedu/todo/guitests/GuiTest.java
``` java
     * Utility method for testing if tag is not successfully added into the GUI side panel.
     * This runs a command and checks if TagList contains tag that matches
     * the tag that is attempt to be added.
     * 
     * Assumption : No tags can have the same name
     * 
     */
    protected void assertTaskTagListFull(String command, Task taskToAdd) {
        // Get the Tag List
        ArrayList<String> taskTagList = taskToAdd.getTagList();
        
        // Get the next tag name from command
        String tagName = getTagNameFromCommand(command);
        
        // Run the command in the console.
        console.runCommand(command);
        int expected_tag_list_size = 20;
        
        // Check if tag exist in the side panel tag list
        TagListItemHandle tagItem = sidebar.getTagListItem(tagName);
        assertNull(tagItem);
        
        assertEquals(expected_tag_list_size, taskTagList.size());
    }
    
    /*
```
###### /java/seedu/todo/guitests/GuiTest.java
``` java
     * Extract out the tag name that is been parsed in the command
     *  
     * Assumption only 1 tag name is provided and tag name wil be provided
     * @return tagName
     */
    private String getTagNameFromCommand(String command) {
        int tagName_index = 2;
        String tagName = StringUtil.splitStringBySpace(command)[tagName_index];
        assert tagName != null;
        return tagName;
    }
    
    /*
```
###### /java/seedu/todo/guitests/GuiTest.java
``` java
     * To format disambiguate message at console output text area
     * 
     * @return formatted output
     */
    protected String formatConsoleOutputTextArea(String expectedDisambiguateMessage) {
        return String.format(Renderer.MESSAGE_DISAMBIGUATE + "\n\n%s", expectedDisambiguateMessage);
    }
}
```
###### /java/seedu/todo/guitests/ListCommandTest.java
``` java
 */
public class ListCommandTest extends GuiTest {
```
###### /java/seedu/todo/guitests/ListCommandTest.java
``` java
    @Test
    public void list_all() {
        String command = "list";
        // To check if all tasks and events are still in the view
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        String expectedOutputMessage = ListController.MESSAGE_LIST_SUCCESS;
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_tasks() {
        String command = "list tasks";
        // To check if all tasks are been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_completed_tasks() {
        console.runCommand("complete 1");
        String command = "list complete";
        // To check if task1 is been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test 
    public void list_incomplete_tasks() {
        String command = "list incomplete";
        // To check if all tasks are been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_events() {
        String command = "list events";
        // To check if all events are been filtered out as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 2;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    
    @Test
    public void list_over_events() {
        String command = "list over";
        // To check if all tasks events are still in the view
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        String expectedOutputMessage = ListController.MESSAGE_NO_RESULT_FOUND;
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_current_events() {
        String command = "list current";
        // To check if all events are been filtered out as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 2;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_by_single_date() {
        String command = "list today";
        // To check if task1 is been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_by_date_range() {
        String command = "list from today";
        // To check if all tasks and events are been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 2;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_tasks_by_single_date() {
        String command = "list tasks on today";
        // To check if task1 is been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 1;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test 
    public void list_tasks_by_date_range_with_single_date() {
        String command = "list tasks from today";
        // To check if all tasks are been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test 
    public void list_tasks_by_date_range() {
        String command = "list tasks from today to tmr";
        // To check if all tasks are been filtered out as expected
        assertTaskVisibleAfterCmd(command, task1);
        assertTaskVisibleAfterCmd(command, task2);
        assertEventNotVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 2;
        expectedNumOfEvents = 0;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_events_by_single_date() {
        String command = "list events on tomorrow";
        // To check if event3 is been filtered as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventNotVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 1;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_events_by_date_range_with_single_date() {
        String command = "list events from today";
        // To check if all events are been filtered out as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 2;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_events_by_date_range() {
        String command = "list events from today to " + THE_DAY_AFTER_TOMORROW_STRING;
        // To check if all events are been filtered out as expected
        assertTaskNotVisibleAfterCmd(command, task1);
        assertTaskNotVisibleAfterCmd(command, task2);
        assertEventVisibleAfterCmd(command, event3);
        assertEventVisibleAfterCmd(command, event4);
        
        // For console text area to check output message
        expectedNumOfTasks = 0;
        expectedNumOfEvents = 2;
        String expectedOutputMessage = String.format(ListController.MESSAGE_RESULT_FOUND_FORMAT,
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(expectedNumOfTasks, expectedNumOfEvents));
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_invalidTaskSyntax_disambiguate() {
        String command = "list task over";
        console.runCommand(command);
        // For console input text to check controller corrected syntax
        String expectedDisambiguation = ListController.LIST_TASK_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(ListController.MESSAGE_INVALID_TASK_STATUS);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_invalidEventSyntax_disambiguate() {
        String command = "list event complete";
        console.runCommand(command);
        // For console input text to check controller corrected syntax
        String expectedDisambiguation = ListController.LIST_EVENT_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(ListController.MESSAGE_INVALID_EVENT_STATUS);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_invalidDateSyntax_disambiguate_with_date_range() {
        String command = "list by today to tml";
        console.runCommand(command);
        // For console input text to check controller corrected syntax
        String expectedDisambiguation = ListController.LIST_DATE_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(ListController.MESSAGE_DATE_CONFLICT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_invalidDateSyntax_disambiguate_with_single_date_by_keyword() {
        String command = "list by todar";
        console.runCommand(command);
        // For console input text to check controller corrected syntax
        String expectedDisambiguation = ListController.LIST_DATE_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(ListController.MESSAGE_NO_DATE_DETECTED);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_invalidDateSyntax_disambiguate_with_single_date() {
        String command = "list todar";
        console.runCommand(command);
        // For console input text to check controller corrected syntax
        String expectedDisambiguation = ListController.LIST_DATE_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(ListController.MESSAGE_NO_DATE_DETECTED);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_invalidDateSyntax_disambiguate_with_date_conflict() {
        String command = "list today by tmr";
        console.runCommand(command);
        // For console input text to check controller corrected syntax
        String expectedDisambiguation = ListController.LIST_DATE_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(ListController.MESSAGE_DATE_CONFLICT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
    
    @Test
    public void list_invalidDateSyntax_disambiguate_with_date_conflict_by_status() {
        String command = "list today over";
        console.runCommand(command);
        // For console input text to check controller corrected syntax
        String expectedDisambiguation = ListController.LIST_DATE_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguation);
        
        // For console text area to check error message
        String expectedOutputMessage = formatConsoleOutputTextArea(ListController.MESSAGE_DATE_CONFLICT);
        assertEquals(console.getConsoleTextArea(), expectedOutputMessage);
    }
}
```
###### /java/seedu/todo/guitests/TagControllerTest.java
``` java
 */
public class TagControllerTest extends GuiTest {
```
###### /java/seedu/todo/guitests/TagControllerTest.java
``` java
    @Test
    public void tag_succcessfully() {
        String command = "tag 1 personal";
        assertTaskTagVisibleAfterCmd(command, task);
        assertEquals(console.getConsoleTextArea(), TagController.MESSAGE_TAG_SUCCESS);
    }
    
    @Test
    public void tag_with_duplicated_names() {
        // To run the command
        String command = "tag 1 personal";
        
        assertTaskTagVisibleAfterCmd(command, task);
        console.runCommand(command);
        
        // For console input
        int tag_index = 1;
        String expectedDisambiguationForConsoleInput = String.format(TagController.TAG_FORMAT, tag_index);
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(TagController.MESSAGE_TAG_NAME_EXIST);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void tag_when_tag_list_is_full() {
        taskWithoutTag = generateTags(taskWithoutTag);
        // To run Command
        String command = "tag 1 personal";
        
        assertTaskTagListFull(command, taskWithoutTag);
        
        // For console input 
        String expectedDisambiguationForConsoleInput = TagController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(TagController.MESSAGE_EXCEED_TAG_SIZE);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void tag_without_index() {
        // To run command
        String command = "tag";
        console.runCommand(command);
        
        // For console input 
        String expectedDisambiguationForConsoleInput = TagController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(TagController.MESSAGE_MISSING_INDEX_AND_TAG_NAME);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void tag_with_invalid_index_out_of_range() {
        // To run command
        String command = "tag 2";
        console.runCommand(command);
        
        // For console input 
        int tag_index_out_of_range = 2;
        String expectedDisambiguationForConsoleInput = String.format(TagController.TAG_FORMAT, tag_index_out_of_range);
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(TagController.MESSAGE_INDEX_OUT_OF_RANGE);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void tag_with_invalid_index_as_alphabets() {
        // To run command
        String command = "tag personal";
        console.runCommand(command);
        
        // For console input 
        String expectedDisambiguationForConsoleInput = TagController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(TagController.MESSAGE_INDEX_NOT_NUMBER);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void tag_without_name() {
        // To run command
        String command = "tag 1";
        console.runCommand(command);
        
        // For console input 
        String expectedDisambiguationForConsoleInput = TagController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(TagController.MESSAGE_TAG_NAME_NOT_FOUND);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    private Task generateTags(Task task) {
        for (int i = 0; i < task.getTagListLimit(); i ++) {
            console.runCommand("tag 1 " + Integer.toString(i));
            task.addTag(Integer.toString(i));
        }
        return task;
    }
}
```
###### /java/seedu/todo/guitests/UntagControllerTest.java
``` java
 */
public class UntagControllerTest extends GuiTest {
```
###### /java/seedu/todo/guitests/UntagControllerTest.java
``` java
    @Test
    public void untag_succesfully() {
        console.runCommand("tag 1 personal");
        String command = "untag 1 personal";
        assertTaskTagNotVisibleAfterCmd(command, taskWithoutTag);
    }
    
    @Test
    public void untag_task_with_invalid_name() {
        // To run command
        console.runCommand("tag 1 test");
        String command = "untag 1 personal";
        console.runCommand(command);
        
        // For console input 
        int tag_index = 1;
        String expectedDisambiguationForConsoleInput = String.format(UntagController.UNTAG_FORMAT, tag_index);
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(UntagController.MESSAGE_TAG_NAME_EXIST);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void untag_without_name() {
        // To run command
        String command = "untag 1";
        console.runCommand(command);
        
        // For console input 
        String expectedDisambiguationForConsoleInput = UntagController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(UntagController.MESSAGE_TAG_NAME_NOT_FOUND);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void untag_without_index() {
        // To run command
        String command = "untag";
        console.runCommand(command);
        
        // For console input 
        String expectedDisambiguationForConsoleInput = UntagController.COMMAND_SYNTAX;
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(UntagController.MESSAGE_MISSING_INDEX_AND_TAG_NAME);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
    
    @Test
    public void untag_with_invalid_index() {
        // To run command
        String command = "untag 2";
        console.runCommand(command);
        
        // For console input 
        int index_out_of_range = 2;
        String expectedDisambiguationForConsoleInput = String.format(UntagController.UNTAG_FORMAT, index_out_of_range);
        assertEquals(console.getConsoleInputText(), expectedDisambiguationForConsoleInput);
        
        // For console text area to check error message
        String expectedDisambiguationForConsoleTextArea = formatConsoleOutputTextArea(UntagController.MESSAGE_INDEX_OUT_OF_RANGE);
        assertEquals(console.getConsoleTextArea(), expectedDisambiguationForConsoleTextArea);
    }
}
```
