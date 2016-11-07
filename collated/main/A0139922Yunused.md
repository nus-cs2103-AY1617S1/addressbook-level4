# A0139922Yunused
###### /java/seedu/todo/commons/util/DateUtil.java
``` java
    /**
     * Performs a "ceiling" operation on a LocalDateTime, and returns a new LocalDateTime
     * with time set to 23:59.
     * 
     * @param dateTime   LocalDateTime for operation to be performed on.
     * @return           "Ceiled" LocalDateTime.
     */
    public static LocalDateTime ceilDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        
        return dateTime.toLocalDate().atTime(23, 59);
    }
```
###### /java/seedu/todo/commons/util/DateUtil.java
``` java
    /*
     * Check a LocalDateTime if the time is the same as the current time
     * 
     * @param date
     * @return true if it is not the same as current time, false if it is the same as current time 
     */
    public static boolean checkIfTimeExist(LocalDateTime date) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.getHour() != date.getHour() || currentTime.getMinute() != date.getMinute();
    }
    
```
###### /java/seedu/todo/commons/util/DateUtil.java
``` java
    /*
     * Check a LocalDateTime if the date is the same as the current date
     * 
     * @param date
     * @return true if it is not the same as current date, false if it is the same as current date 
     */
    public static boolean checkIfDateExist(LocalDateTime date) {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.getDayOfYear() != date.getDayOfYear() || currentDate.getMonth() != date.getMonth() || 
                currentDate.getYear() != date.getYear();
    }
    
```
###### /java/seedu/todo/commons/util/DateUtil.java
``` java
    /* 
     * To convert LocalDateTime to 00:00 or 23:59 if not specified
     * @param actualDate 
     *                  is the date that that is require for checking
     * @param checkedDate
     *                  is the date to be used for checking
     * @isDateFrom
     *                  if true, actualDate is dateFrom, false if actualDate is dateTo                 
     * 
     * @return the correct date format
     */
    public static LocalDateTime parseTimeStamp(LocalDateTime actualDate, LocalDateTime checkedDate, boolean isDateFrom) {
        // Check for date
        if (checkedDate != null && actualDate != null && checkIfDateExist(checkedDate) && !checkIfDateExist(actualDate)) {
            if (!isDateFrom) {
                actualDate = checkedDate.toLocalDate().atTime(actualDate.getHour(), actualDate.getMinute());
            }
        }
        // Check for time
        if (checkedDate != null && actualDate != null && checkIfTimeExist(checkedDate) && !checkIfTimeExist(actualDate)) {
            actualDate = actualDate.toLocalDate().atTime(checkedDate.getHour(), checkedDate.getMinute());            
        }
        if (actualDate != null && !checkIfTimeExist(actualDate)) {
            if (isDateFrom) {
                actualDate = floorDate(actualDate);
            } else {
                actualDate = ceilDate(actualDate);
            }
        }
        return actualDate;
    }
}
```
###### /java/seedu/todo/commons/util/FilterUtil.java
``` java
/**
 * Helper function to help in filtering results
 */

public class FilterUtil {
    
    /*==================== Filtering Methods for Tasks ======================*/
    
    /*
     * Use to filter out Task items from calendarItems
     * 
     * @param calendarItems
     *              List of mixture of Task and Event
     * @return filteredTasks
     *              List containing only Task             
     */
    public static List<Task> filterOutTask(List<CalendarItem> calendarItems) {
        List<Task> filteredTasks = new ArrayList<Task>();
        for (int i = 0; i < calendarItems.size(); i ++) {
            if (calendarItems.get(i) instanceof Task) {
                filteredTasks.add((Task) calendarItems.get(i));
            }
        }
        return filteredTasks;
    }
    
    /**
     * Filter the task list based on matching task name list
     * @param tasks 
     *             Provided list for filtering
     * @param namelist
     *             Search and filter based on the name list
     * @return filteredTasks
     *              List containing only Task that filtered by the item name
     */
    public static List<Task> filterTaskByNames(List<Task> tasks, HashSet<String> nameList) {
        // If search list size is 0 , user not searching based on name
        List<Task> filteredTasks = new ArrayList<Task>();
        if (nameList.size() == 0) {
            return filteredTasks;
        }
        
        // Loop through all the tasks
        for (int i = 0; i < tasks.size(); i ++) {
            Task task = tasks.get(i);
            
            // For every task, loop through all the name list
            Iterator<String> nameListIterator = nameList.iterator();
            while (nameListIterator.hasNext()) {
                String matchingName = nameListIterator.next();
                if (matchWithFullName(task, matchingName) || matchWithSubName(task, matchingName)) {
                    filteredTasks.add(task); // Once found, add and break 
                    break;
                }
            }
            // Reset the name list for other task
            nameListIterator = nameList.iterator();
        }
        return filteredTasks;
    }
    
    /**
     * Filter the task list based on matching tag name list
     * @param tasks 
     *             Provided list for filtering
     * @param namelist
     *             Search and filter based on the name list
     * @return filteredTasks
     *              List containing only Task that filtered by the tag names 
     */
    public static List<Task> filterTaskByTags(List<Task> tasks, HashSet<String> nameList) {
        // If no search list is provided, user not searching based on tags
        List<Task> filteredTasks = new ArrayList<Task>();
        if (nameList.size() == 0) {
            return filteredTasks;
        }
        
        // Lopp through all the tasks
        for (int i = 0; i < tasks.size(); i ++) {
            // Get the task tag list
            Task task = tasks.get(i);
            ArrayList<String> taskTagList = task.getTagList();
            
            // Loop through the tag names list
            Iterator<String> nameListIterator = nameList.iterator();
            while (nameListIterator.hasNext()) {
                String currentMatchingName = nameListIterator.next();
                if (taskTagList.contains(currentMatchingName)) {
                    filteredTasks.add(task); // Once found a matching tag, add and break;
                    break;
                }
            }
            nameListIterator = nameList.iterator();
        }
        return filteredTasks;
    }
    
    /**
     * Filter the task list based on incomplete status
     * @param tasks 
     *             Provided list for filtering
     * @param taskStatus
     *             True if searching for is completed, false if search for incomplete.
     * @return filteredTasks
     *             List containing only Task that filtered by status (e.g. complete or incomplete)
     */
    public static List<Task> filterTasksByStatus(List<Task> tasks, boolean taskStatus) {
        // If tasks is empty, return immediately
        if (tasks.size() == 0) {
            return tasks;
        }
        
        List<Task> filteredTasks = new ArrayList<Task>();
        // Loop through all the tasks
        for (int i = 0; i < tasks.size(); i ++) {
            Task task = tasks.get(i);
            // Check if it is the same as the required task status
            if (task.isCompleted() == taskStatus) {
                filteredTasks.add(task); //Add any task has the same task status
            }
        }
        return filteredTasks;
    }
    
    /**
     * Filter the task list based on single date
     * @param tasks 
     *             Provided list for filtering
     * @param date            
     *             Search based on this date
     * @return filteredTasks
     *              List containing only Task that filtered by a single date
     */
    public static List<Task> filterTaskBySingleDate(List<Task> tasks, LocalDateTime date) {
        // If tasks is empty, return immediately
        if (tasks.size() == 0) {
            return tasks;
        }
        
        ArrayList<Task> filteredTasks = new ArrayList<Task>();
        // Loop through all the tasks
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            // Searched date should not be null, break out if it is.
            assert date != null; 
            // Check if task start date is the same as the searched date
            date = DateUtil.floorDate(date);
            LocalDateTime taskDate = DateUtil.floorDate(task.getCalendarDateTime());
            
            // May have floating tasks, skip floating tasks 
            if (taskDate != null && taskDate.equals(date)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }
    
    /**
     * Filter the task list with a date range
     * @param tasks 
     *             Provided list for filtering
     * @param startDate            
     *             Search based on this as starting date
     * @param endDate            
     *             Search based on this as ending date
     * @return filteredTasks
     *              List containing only Task that filtered by a range of two dates
     */
    public static List<Task> filterTaskWithDateRange(List<Task> tasks, LocalDateTime startDate, LocalDateTime endDate) {
        // If tasks is empty, return immediately
        if (tasks.size() == 0) {
            return tasks;
        }
    
        // If start date is null, set it to MIN dateTime, user could searched by "from today"
        if (startDate == null) {
            startDate = LocalDateTime.MIN;
        }
        
        // If end date is null, set it to MAX dateTime, user could searched by "to today"
        if (endDate == null) {
            endDate = LocalDateTime.MAX;
        }
        
        ArrayList<Task> filteredTasks = new ArrayList<Task>();
        // Loop through all the tasks
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            LocalDateTime taskDate = DateUtil.floorDate(task.getDueDate());
            // May have floating task, set the date to MIN dateTime, to avoid been filtered 
            if (taskDate == null) {
                taskDate = LocalDateTime.MIN;
            }
            
            // Set the searched date to its min and max value
            startDate = DateUtil.floorDate(startDate);
            endDate = DateUtil.ceilDate(endDate);
            // Compare if the task date is within the range of start and end date
            if (taskDate.compareTo(startDate) >= 0 && taskDate.compareTo(endDate) <= 0) {
                filteredTasks.add(task); // Add if it is within the range
            }
        }
        return filteredTasks;
    }
    
    /*==================== Filtering Methods for Events ======================*/
    
    /*
     * Use to filter out Event items from calendarItems
     * 
     * @param calendarItems
     *              List of mixture of Task and Event
     * @return filteredTasks
     *              List containing only Event             
     */
    public static List<Event> filterOutEvent(List<CalendarItem> calendarItems) {
        List<Event> filteredEvents = new ArrayList<Event>();
        for (int i = 0; i < calendarItems.size(); i ++) {
            if (calendarItems.get(i) instanceof Event) {
                filteredEvents.add((Event) calendarItems.get(i));
            }
        }
        return filteredEvents;
    }
    
    /**
     * Filter the event list based on event name list
     * @param events 
     *             Provided list for filtering
     * @param namelist
     *             Search and filter based on the name list
     * @return filteredEvents
     *              List containing only Event that event name matches with the item name
     */
    public static List<Event> filterEventByNames(List<Event> events, HashSet<String> nameList) {
        List<Event> filteredEvents = new ArrayList<Event>();
        // If name list size is 0, means not searching by name list 
        if (nameList.size() == 0) {
            return filteredEvents;
        }
        
        // Lopp through all the events
        for (int i = 0; i < events.size(); i ++) {
            Event event = events.get(i);
            // Loop through all the name list
            Iterator<String> nameListIterator = nameList.iterator();
            while (nameListIterator.hasNext()) {
                String matchingName = nameListIterator.next().toLowerCase();
                // If found a match with its full name or sub names, break 
                if (matchWithFullName(event, matchingName) || matchWithSubName(event, matchingName)) {
                    filteredEvents.add(event);
                    break;
                }
            }
            // Reset the nameList for the next event
            nameListIterator = nameList.iterator();
        }
        return filteredEvents;
    }
    
    /**
     * Filter the event list based on tag name list
     * @param events 
     *             Provided list for filtering
     * @param namelist
     *             Search and filter based on the name list
     * @return filteredEvents
     *              List containing only Event that tag names matches with the tag name
     */
    public static List<Event> filterEventByTags(List<Event> events, HashSet<String> nameList) {
        List<Event> filteredEvents = new ArrayList<Event>();
        // If name list size is 0, means not searching by tags 
        if (nameList.size() == 0) {
            return filteredEvents;
        }
        
        // Loop through all the events
        for (int i = 0; i < events.size(); i ++) {
            Event event = events.get(i);
            // Get the tag list of the event
            ArrayList<String> taskTagList = event.getTagList();
            Iterator<String> nameListIterator = nameList.iterator();
            // Loop through the tag names list
            while (nameListIterator.hasNext()) {
                String currentMatchingName = nameListIterator.next();
                // If found a match, add and break
                if (taskTagList.contains(currentMatchingName)) {
                    filteredEvents.add(event);
                    break;
                }
            }
            // Reset the tag names list for the next event
            nameListIterator = nameList.iterator();
        }
        return filteredEvents;
    }
    
    /**
     * Filter the event list if the event date is over
     * @param events 
     *             Provided list for filtering
     * @param isEventOver
     *             True if searching for event that are over, false if searching for current event  
     * @return filteredEvents
     *              List containing only Event that status matches with the status, For e.g. over or current
     */            
    public static List<Event> filterEventsByStatus(List<Event> events, boolean eventStatus) {
        // If events is empty, return immediately
        if (events.size() == 0) {
            return events;
        }
        
        List<Event> filteredEvents = new ArrayList<Event>();
        // Loop through the events
        for (int i = 0; i < events.size(); i ++) {
            Event event = events.get(i);
            // Add into filteredEvents, once the status of the event matches
            if (event.isOver() == eventStatus) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
    
    /**
     * Filter the event list based on single date
     * @param events 
     *             Provided list for filtering
     * @param date            
     *             Search based on this date
     * @return filteredEvents
     *              List containing only Event date that matches with searched date
     */
    public static List<Event> filterEventBySingleDate(List<Event> events, LocalDateTime date) {
        // If events is empty, return immediately
        if (events.size() == 0) {
            return events;
        }
        
        ArrayList<Event> filteredEvents = new ArrayList<Event>();
        // Loop through all the events
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            // Search dates cannot be null
            assert date != null;
            date = DateUtil.floorDate(date);
            
            // Event start date should not be null
            assert event.getStartDate() != null; 
            LocalDateTime eventDate = DateUtil.floorDate(event.getStartDate());
            
            // Check against start date, if equals add it in
            if (eventDate.equals(date)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
    
    /**
     * Filter the event list with range of dates
     * @param events 
     *             Provided list for filtering
     * @param startDate            
     *             Search based on this as starting date
     * @param endDate            
     *             Search based on this as ending date
     * @return filteredEvents
     *              List containing only Event that date fall between startDate and endDate
     */
    public static List<Event> filterEventWithDateRange(List<Event> events, LocalDateTime startDate, LocalDateTime endDate) {
        // If events is empty, return immediately
        if (events.size() == 0) {
            return events;
        }
    
        // Set startDate to MIN, user could search "from Today"
        if (startDate == null) {
            startDate = LocalDateTime.MIN;
        }
        
        // Set endDate to MaX, user could search "to Today"
        if (endDate == null) {
            endDate = LocalDateTime.MAX;
        }
        
        ArrayList<Event> filteredEvents = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        // Loop through all the events
        while (iterator.hasNext()) {
            Event event = iterator.next();
            // Event dates should not be null
            assert event.getStartDate() != null;
            assert event.getEndDate() != null;
            LocalDateTime eventStartDate = DateUtil.floorDate(event.getStartDate());
            LocalDateTime eventEndDate = DateUtil.floorDate(event.getEndDate());
            
            // Check if the event start dates and end dates is within the search dates
            startDate = DateUtil.floorDate(startDate);
            endDate = DateUtil.ceilDate(endDate);
            if (eventStartDate.compareTo(startDate) >= 0 && eventEndDate.compareTo(endDate) <= 0) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
    
    /*==================== Helper Methods to Check Command Conflict ===================================*/
    
    /*
     * To be use to check if there are more than 1 event type entered by user 
     * 
     * @return true if more than 1 event type found, false, if only 1 or 0 event type found
     */
    public static boolean isItemTypeConflict(String input) {
        return input.contains("task") && input.contains("event");
    }
    
    /*==================== Helper Methods for filtering CalendarItem name ======================*/
    
    /*
     * Use to check if calendarItem name starts with the matching name 
     * 
     * @return true if it calendarItem's name starts with the matching name, 
     * false if calendarItem's name does not starts with matching name
     */
    private static boolean matchWithFullName(CalendarItem calendarItem, String matchingName) {
        String taskName = calendarItem.getName().toLowerCase();
        return taskName.startsWith(matchingName.toLowerCase());
    }
    
    /*
     * Use to check if calendarItem name split by space starts with the matching name 
     * 
     * @return true if any of the calendarItem's name that is split by space that starts with the matching name, 
     * false if calendarItem's name that is split by space does not starts with matching name
     */
    private static boolean matchWithSubName(CalendarItem calendarItem, String matchingName) {
        String[] nameBySpace = StringUtil.splitStringBySpace(calendarItem.getName());
        for (int i = 0; i < nameBySpace.length; i ++) {
            if (nameBySpace[i].toLowerCase().startsWith(matchingName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
}
```
###### /java/seedu/todo/commons/util/ParseUtil.java
``` java
/**
 * Helper functions for parsing.
 */
public class ParseUtil {
    private static final int TOKEN_INDEX = 0;
    private static final int TOKEN_RESULT_INDEX = 1;
    
    /*
     * To be used to check if there exist an item tagged with the token
     */
    public static boolean isTokenNull(Map<String, String[]> parsedResult, String token) {
        return parsedResult.get(token) == null || parsedResult.get(token)[TOKEN_INDEX] == null;
    }
    /*
     * To check if parsedResult with the token containing the keyword provided 
     * 
     * @return true if keyword is found, false if it is not found
     */
    public static boolean doesTokenContainKeyword(Map<String, String[]> parsedResult, String token, String keyword) {
        if (!isTokenNull(parsedResult, token)) {
            return parsedResult.get(token)[TOKEN_INDEX].contains(keyword);
        }
        return false;
    }
    
    /*
     * To be used to get input from token
     * 
     * @return the parsed result from tokenizer
     */
    public static String getTokenResult(Map<String, String[]> parsedResult, String token) {
        if(!isTokenNull(parsedResult, token)) {
            return parsedResult.get(token)[TOKEN_RESULT_INDEX];
        }
        return null;
    }
    
    /**
     * Extracts the natural dates from parsedResult.
     * 
     * @param parsedResult
     * @return { numOfdateFound, naturalOn, naturalFrom, naturalTo } 
     */
    public static String[] parseDates(Map<String, String[]> parsedResult) {
        String naturalFrom = getTokenResult(parsedResult, "timeFrom");
        String naturalTo = getTokenResult(parsedResult, "timeTo");
        String naturalOn = getTokenResult(parsedResult, "time");
        int numOfDateFound = 0;
        
        String [] dateResult = { null, naturalOn, naturalFrom, naturalTo };
        for (int i = 0; i < dateResult.length; i ++) {
            if (dateResult[i] != null) {
                numOfDateFound ++;
            }
        }
        
        if (numOfDateFound == 0) {
            return null;
        } else {
            dateResult[0] = Integer.toString(numOfDateFound);
        }

        return dateResult;
    }
}
```
###### /java/seedu/todo/commons/util/StringUtil.java
``` java
    /*
     * Format the display message depending on the number of tasks and events 
     * 
     * @param numTasks
     *          the number of tasks found 
     * @param numEvents
     *          the number of events found    
     *        
     * @return the display message for console message output           
     */
    public static String displayNumberOfTaskAndEventFoundWithPuralizer (int numTasks, int numEvents) {
        if (numTasks != 0 && numEvents != 0) {
            return String.format("%s and %s", formatNumberOfTaskWithPuralizer(numTasks), formatNumberOfEventWithPuralizer(numEvents));
        } else if (numTasks != 0) {
            return formatNumberOfTaskWithPuralizer(numTasks);
        } else if (numEvents != 0){
            return formatNumberOfEventWithPuralizer(numEvents);
        } else {
            return "No item found!";
        }
    }
    
    /*
     * Format the number of events found based on the events found
     * 
     *  @param numEvents 
     *          the number of events found
     */
    public static String formatNumberOfEventWithPuralizer (int numEvents) {
        return String.format("%d %s", numEvents, pluralizer(numEvents, "event", "events"));
    }
    
    /*
     * Format the number of tasks found based on the tasks found
     * 
     *  @param numTasks 
     *          the number of tasks found
     */
    public static String formatNumberOfTaskWithPuralizer (int numTasks) {
        return String.format("%d %s", numTasks, pluralizer(numTasks, "task", "tasks"));
    }
```
###### /java/seedu/todo/commons/util/StringUtil.java
``` java
    /*
     * Convert input into individual input by splitting with space  
     */
    public static String[] splitStringBySpace(String input) {
        return (input == null) ? null : input.trim().split(" ");
    }
}
```
###### /java/seedu/todo/controllers/ClearController.java
``` java
/**
 * Controller to clear task/event by Type
 */
public class ClearController implements Controller {
    
    private static final String NAME = "Clear";
    private static final String DESCRIPTION = "Clear all tasks/events or by specify date.";
    private static final String COMMAND_WORD = "clear";
    
    // Syntax correction to console input
    public static final String COMMAND_SYNTAX = "clear \"task/event\" on \"date\"";
    public static final String CLEAR_DATE_SYNTAX = "clear \"date\" [or from \"date\" to \"date\"]";
    
    // Message output to console text area
    public static final String MESSAGE_CLEAR_SUCCESS_FORMAT = "A total of %s deleted!";
    public static final String MESSAGE_CLEAR_NO_ITEM_FOUND = "No item found!";
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "All tasks and events have been deleted!\n" + "To undo, type \"undo\".";
    public static final String MESSAGE_CLEAR_UNABLE_TO_SUPPORT = "Unable to clear!\nCannot clear by status!";
    public static final String MESSAGE_DATE_CONFLICT = "Unable to clear!\nMore than 1 date criteria is provided!";
    public static final String MESSAGE_NO_DATE_DETECTED = "Unable to clear!\nThe natural date entered is not supported.";
    public static final String MESSAGE_ITEM_TYPE_CONFLICT = "Unable to clear!\nMore than 1 item type is provided!";
    
    // Use to access parsing of dates
    private static final int NUM_OF_DATES_FOUND_INDEX = 0;
    private static final int COMMAND_INPUT_INDEX = 0;
    private static final int DATE_CRITERIA_INDEX = 0;
    private static final int DATE_ON_INDEX = 1;
    private static final int DATE_FROM_INDEX = 2;
    private static final int DATE_TO_INDEX = 3;

    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (StringUtil.splitStringBySpace(input.toLowerCase())[COMMAND_INPUT_INDEX]).equals(COMMAND_WORD) ? 1 : 0;
    }
    
    /**
     * Get the token definitions for use with <code>tokenizer</code>.<br>
     * This method exists primarily because Java does not support HashMap
     * literals...
     * 
     * @return tokenDefinitions
     */
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put(Tokenizer.DEFAULT_TOKEN, new String[] { COMMAND_WORD });
        tokenDefinitions.put(Tokenizer.EVENT_TYPE_TOKEN, Tokenizer.EVENT_TYPE_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_TOKEN, Tokenizer.TIME_DEFINITION);
        tokenDefinitions.put(Tokenizer.TASK_STATUS_TOKEN, Tokenizer.TASK_STATUS_DEFINITION);
        tokenDefinitions.put(Tokenizer.EVENT_STATUS_TOKEN, Tokenizer.EVENT_STATUS_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_FROM_TOKEN, Tokenizer.TIME_FROM_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_TO_TOKEN, Tokenizer.TIME_TO_DEFINITION);
        return tokenDefinitions;
    }
    
    @Override
    public void process(String input) throws ParseException {
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        TodoListDB db = TodoListDB.getInstance();
        
        if (input.trim().equals(COMMAND_WORD)) {
            db.destroyAllTaskAndEvents();
            Renderer.renderIndex(db, MESSAGE_CLEAR_ALL_SUCCESS);
            return; // Clear all
        }
        
        boolean isItemTypeProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.EVENT_TYPE_TOKEN);
        boolean isTaskStatusProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.TASK_STATUS_TOKEN);
        boolean isEventStatusProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.EVENT_STATUS_TOKEN);
        
        if (isErrorCommand(isTaskStatusProvided, isEventStatusProvided, input)) {
            return; // Break out if found error
        }
        
        boolean isTask = true; //default
        if (isItemTypeProvided) {
            isTask = ParseUtil.doesTokenContainKeyword(parsedResult, Tokenizer.EVENT_TYPE_TOKEN, "task");
        }
        
        LocalDateTime [] validDates = parsingDates(parsedResult);
        if (validDates == null) {
            return; // Break out when date conflict found
        }
        
        // Setting up view
        List<Task> tasks; //default
        List<Event> events; //default
        List<CalendarItem> calendarItems;
        // Filter Task and Event by Type
        if (!isItemTypeProvided) {
            tasks = db.getAllTasks();
            events = db.getAllEvents();
        } else {
            if (isTask) {
                events = new ArrayList<Event>();
                tasks = db.getAllTasks();
            } else {
                tasks = new ArrayList<Task>();
                events = db.getAllEvents();
            }
        }
        
        // Filter Task and Event by date
        calendarItems = filterTasksAndEventsByDate(tasks, events, parsedResult);
        if (calendarItems == null) {
            return; // Date conflict detected
        }
        tasks = FilterUtil.filterOutTask(calendarItems);
        events = FilterUtil.filterOutEvent(calendarItems);
        
        // Show message if no items had been found
        if (tasks.size() == 0 && events.size() == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        deleteSelectedTasksAndEvents(tasks, events, db);
    }
    
    /*====================== Helper Methods to check for Error/Syntax Command ===================*/
    
    /*
     * To be used to parsed dates and check for any dates conflict
     * 
     * @return null if dates conflict detected, else return { dateCriteria, dateOn, dateFrom, dateTo }
     */
    private LocalDateTime[] parsingDates(Map<String, String[]> parsedResult) {
        String[] parsedDates = ParseUtil.parseDates(parsedResult);
        //date enter with COMMAND_WORD e.g list today
        String date = ParseUtil.getTokenResult(parsedResult, Tokenizer.DEFAULT_TOKEN);
        
        if (date != null && parsedDates != null) {
            Renderer.renderDisambiguation(CLEAR_DATE_SYNTAX, MESSAGE_DATE_CONFLICT);
            return null;
        }
        
        LocalDateTime dateCriteria = null;
        LocalDateTime dateOn = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        
        if (date != null) {
            try {
                dateCriteria = DateParser.parseNatural(date);
            } catch (InvalidNaturalDateException e) {
                Renderer.renderDisambiguation(CLEAR_DATE_SYNTAX, MESSAGE_NO_DATE_DETECTED);
                return null;
            }
        }
        
        if (parsedDates != null) {
            String naturalOn = parsedDates[DATE_ON_INDEX];
            String naturalFrom = parsedDates[DATE_FROM_INDEX];
            String naturalTo = parsedDates[DATE_TO_INDEX];
            if (naturalOn != null && Integer.parseInt(parsedDates[NUM_OF_DATES_FOUND_INDEX]) > 1) {
                //date conflict detected
                Renderer.renderDisambiguation(CLEAR_DATE_SYNTAX, MESSAGE_DATE_CONFLICT);
                return null;
            }
            // Parse natural date using Natty.
            try {
                dateOn = naturalOn == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalOn)); 
                dateFrom = naturalFrom == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalFrom)); 
                dateTo = naturalTo == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalTo));
            } catch (InvalidNaturalDateException e) {
                    Renderer.renderDisambiguation(CLEAR_DATE_SYNTAX, MESSAGE_NO_DATE_DETECTED);
                    return null;
            }
        }
        return new LocalDateTime[] { dateCriteria, dateOn, dateFrom, dateTo };
    }
    
    /*
     * To be use to check if there are any command syntax error
     * 
     * @return true, if there is error in command syntax, false if syntax is allowed
     */
    private boolean isErrorCommand(boolean isTaskStatusProvided, boolean isEventStatusProvided, String input) {
        // Check if any status is provided
        if (isTaskStatusProvided || isEventStatusProvided) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_CLEAR_UNABLE_TO_SUPPORT);
            return true;
        }
        // Check if more than 1 item type is provided
        if (FilterUtil.isItemTypeConflict(input)) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_ITEM_TYPE_CONFLICT);
            return true;
        }
        return false;
    }
    
    /* =================== Helper methods to filter out Task and Events ==================*/
    
    /*
     * Filter out the selected tasks and events based on the dates
     * and update tasks and events accordingly
     * 
     * @param tasks
     *            List of Task items
     * @param events           
     *            List of Event items
     * @param parsedResult
     *            parsedResult by Tokenizer
     * @return        
     *            tasks and events in a list form by date or null when date conflict found
     */
    private List<CalendarItem> filterTasksAndEventsByDate(List<Task> tasks, List<Event> events, Map<String, 
            String[]> parsedResult) {
        // Get dates from input
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        LocalDateTime [] validDates = parsingDates(parsedResult);
        List<Task> filteredTasks = tasks;
        List<Event> filteredEvents = events;
        if (validDates == null) {
            return null; // Break out when date conflict found
        }
        
        // Set dates that are found, if not found value will be null
        LocalDateTime dateCriteria = validDates[DATE_CRITERIA_INDEX];
        LocalDateTime dateOn = validDates[DATE_ON_INDEX];
        LocalDateTime dateFrom = validDates[DATE_FROM_INDEX];
        LocalDateTime dateTo = validDates[DATE_TO_INDEX];
        
        if (dateCriteria != null) {
            // Filter by single date
            assert dateOn == null;
            assert dateFrom == null;
            assert dateTo == null;
            filteredTasks = FilterUtil.filterTaskBySingleDate(tasks, dateCriteria);
            filteredEvents = FilterUtil.filterEventBySingleDate(events, dateCriteria);
        }
        
        if (dateOn != null) {
            // Filter by single date
            filteredTasks = FilterUtil.filterTaskBySingleDate(tasks, dateOn);
            filteredEvents = FilterUtil.filterEventBySingleDate(events, dateOn);
        } else if (dateFrom != null || dateTo != null) {
            // Filter by range
            filteredTasks = FilterUtil.filterTaskWithDateRange(tasks, dateFrom, dateTo);
            filteredEvents =FilterUtil.filterEventWithDateRange(events, dateFrom, dateTo);
        }
        
        calendarItems.addAll(filteredTasks);
        calendarItems.addAll(filteredEvents);
        return calendarItems;
    }    
    
    /* =============== Helper Methods to delete selected Tasks and Events ============*/
    
    /*
     * Delete the selected Tasks and Events , filtered out by helper methods
     * 
     * @param tasks
     *             A list of Task that already been filtered for deletion
     * @param events
     *             A list of Event that already been filtered for deletion
     * @param db
     *             The same instance of db used to filtered out both tasks and events                        
     */
    private void deleteSelectedTasksAndEvents(List<Task> tasks, List<Event> events, TodoListDB db) {
        db.destroyAllTaskAndEventsByList(tasks, events);
        String consoleMessage = String.format(MESSAGE_CLEAR_SUCCESS_FORMAT, 
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(tasks.size(), events.size()));
        Renderer.renderIndex(db, consoleMessage);
    }
}
```
###### /java/seedu/todo/controllers/FindController.java
``` java
/**
 * Controller to find task/event by keyword
 */
public class FindController implements Controller {
    
    private static final String NAME = "Find";
    private static final String DESCRIPTION = "Find all tasks and events based on the provided keywords.\n" + 
        "This command will be searching with non-case sensitive keywords.";
    private static final String COMMAND_WORD = "find";
    
    // Syntax correction to console input
    public static final String COMMAND_SYNTAX = "find \"name\" tagName \"tag\" on \"date\" \"task/event\"";
    public static final String FIND_TASK_SYNTAX = "find \"name\" task \"complete/incomplete\"";
    public static final String FIND_EVENT_SYNTAX = "find \"name\" event \"over/ongoing\"";
    
    // Message output to console text area
    public static final String MESSAGE_RESULT_FOUND_FORMAT = "A total of %s found!";
    public static final String MESSAGE_NO_RESULT_FOUND = "No task or event found!";
    public static final String MESSAGE_NO_KEYWORD_FOUND = "No keyword found!";
    public static final String MESSAGE_DATE_CONFLICT = "Unable to find!\nMore than 1 date criteria is provided!";
    public static final String MESSAGE_NO_DATE_DETECTED = "Unable to find!\nThe natural date entered is not supported.";
    public static final String MESSAGE_INVALID_TASK_STATUS = "Unable to find!\nTry searching with complete or incomplete";
    public static final String MESSAGE_INVALID_EVENT_STATUS = "Unable to find!\nTry searching with over or current";
    public static final String MESSAGE_ITEM_TYPE_CONFLICT = "Unable to list!\nMore than 1 item type is provided!";
    
    private static final int COMMAND_INPUT_INDEX = 0;
    //use to access parsing of dates
    private static final int NUM_OF_DATES_FOUND_INDEX = 0;
    private static final int DATE_ON_INDEX = 1;
    private static final int DATE_FROM_INDEX = 2;
    private static final int DATE_TO_INDEX = 3;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }
    
    @Override
    public float inputConfidence(String input) {
        String command = StringUtil.splitStringBySpace(input.toLowerCase())[COMMAND_INPUT_INDEX];
        return (command).equals(COMMAND_WORD) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put(Tokenizer.DEFAULT_TOKEN, new String[] { COMMAND_WORD });
        tokenDefinitions.put(Tokenizer.EVENT_TYPE_TOKEN, Tokenizer.EVENT_TYPE_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_TOKEN, Tokenizer.TIME_DEFINITION);
        tokenDefinitions.put(Tokenizer.TASK_STATUS_TOKEN, Tokenizer.TASK_STATUS_DEFINITION);
        tokenDefinitions.put(Tokenizer.EVENT_STATUS_TOKEN, Tokenizer.EVENT_STATUS_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_FROM_TOKEN, Tokenizer.TIME_FROM_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_TO_TOKEN, Tokenizer.TIME_TO_DEFINITION);
        tokenDefinitions.put(Tokenizer.ITEM_NAME_TOKEN, Tokenizer.ITEM_NAME_DEFINITION);
        tokenDefinitions.put(Tokenizer.TAG_NAME_TOKEN, Tokenizer.TAG_NAME_DEFINITION);
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        HashSet<String> itemNameList = new HashSet<String>();
        HashSet<String> tagNameList = new HashSet<String>();
        HashSet<String> keywordList = new HashSet<String>();
        
        // To be use to be filter out name and tag names
        updateHashList(parsedResult, keywordList, Tokenizer.DEFAULT_TOKEN);
        updateHashList(parsedResult, itemNameList, Tokenizer.ITEM_NAME_TOKEN);
        updateHashList(parsedResult, tagNameList, Tokenizer.TAG_NAME_TOKEN);
        itemNameList.addAll(keywordList);
        tagNameList.addAll(keywordList);
        
        // Show console output message, since no keyword found
        if (keywordList.size() == 0 && itemNameList.size() == 0 && tagNameList.size() == 0) {
            //No keyword provided, display error
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_NO_KEYWORD_FOUND);
            return;
        }
        
        // Check if input includes itemType and itemStatus
        boolean isItemTypeProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.EVENT_TYPE_TOKEN);
        boolean isTaskStatusProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.TASK_STATUS_TOKEN);
        boolean isEventStatusProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.EVENT_STATUS_TOKEN);
        
        // Set item type
        boolean isTask = true; //default
        if (isItemTypeProvided) {
            isTask = ParseUtil.doesTokenContainKeyword(parsedResult, Tokenizer.EVENT_TYPE_TOKEN, "task");
        }
        
        if (isErrorCommand(isTaskStatusProvided, isEventStatusProvided, isTask, isItemTypeProvided, input)) {
            return; // Break out if found error
        }
       
        // Setting up view
        TodoListDB db = TodoListDB.getInstance();
        List<Task> tasks; //default
        List<Event> events; //default
        List<CalendarItem> calendarItems;
        
        // Filter out the tasks and events based on type and names
        if (!isItemTypeProvided) {
            tasks = filterByTaskNameAndTagName(itemNameList, tagNameList, db.getAllTasks());
            events = filterByEventNameAndTagName(itemNameList, tagNameList, db.getAllEvents());
        } else {
            if (isTask) {
                events = new ArrayList<Event>();
                tasks = filterByTaskNameAndTagName(itemNameList, tagNameList, db.getAllTasks());
            } else {
                tasks = new ArrayList<Task>();
                events = filterByEventNameAndTagName(itemNameList, tagNameList, db.getAllEvents());
            }
        }
        
        // Filter Task and Event by Status
        calendarItems = filterTasksAndEventsByStatus(parsedResult, isTaskStatusProvided, isEventStatusProvided, tasks, events);
        tasks = FilterUtil.filterOutTask(calendarItems);
        events = FilterUtil.filterOutEvent(calendarItems);
        
        // Filter Task and Event by date
        calendarItems = filterTasksAndEventsByDate(tasks, events, parsedResult);
        if (calendarItems == null) {
            return; // Date conflict detected
        }
        tasks = FilterUtil.filterOutTask(calendarItems);
        events = FilterUtil.filterOutEvent(calendarItems);
        
        // Show message if no items had been found
        if (tasks.size() == 0 && events.size() == 0) {
            Renderer.renderIndex(db, MESSAGE_NO_RESULT_FOUND);
            return;
        }
        
        String consoleMessage = String.format(MESSAGE_RESULT_FOUND_FORMAT, 
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(tasks.size(), events.size()));
        Renderer.renderSelectedIndex(db, consoleMessage, tasks, events);
    }

    /*======================== Helper Methods to filter tasks and events ========================================*/
    
    /*
     * Filter out the selected tasks and events based on the status and update tasks and events accordingly
     * 
     * @param parsedResult
     *            parsedResult by Tokenizer
     * @param isTaskStatusProvided
     *            true if complete or incomplete is found, else false
     * @param isEventStatusProvided
     *            true if over or current is found, else false                   
     * @param tasks
     *            List of Task items
     * @param events           
     *            List of Event items
     * @return        
     *            tasks and events in a list form by status
     */
    private List<CalendarItem> filterTasksAndEventsByStatus(Map<String, String[]> parsedResult, boolean isTaskStatusProvided,
            boolean isEventStatusProvided, List<Task> tasks, List<Event> events) {
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        // Set item status
        boolean isCompleted = false; //default 
        boolean isOver = false; //default
        List<Task> filteredTasks = tasks;
        List<Event> filteredEvents = events;
        
        // Filter out by Task Status if provided
        if (isTaskStatusProvided) {
            isCompleted = !ParseUtil.doesTokenContainKeyword(parsedResult, Tokenizer.TASK_STATUS_TOKEN, "incomplete");
            filteredTasks = FilterUtil.filterTasksByStatus(tasks, isCompleted);
            filteredEvents = new ArrayList<Event>();
        }
        
        // Filter out by Event Status if provided
        if (isEventStatusProvided) {
            isOver = ParseUtil.doesTokenContainKeyword(parsedResult, Tokenizer.EVENT_STATUS_TOKEN, "over");
            filteredEvents = FilterUtil.filterEventsByStatus(events, isOver);
            filteredTasks = new ArrayList<Task>();
        }
        calendarItems.addAll(filteredTasks);
        calendarItems.addAll(filteredEvents);
        return calendarItems;
    }

    /*
     * Filter out the selected tasks and events based on the dates
     * and update tasks and events accordingly
     * 
     * @param tasks
     *            List of Task items
     * @param events           
     *            List of Event items
     * @param parsedResult
     *            parsedResult by Tokenizer
     * @return        
     *            tasks and events in a list form by date or null when date conflict found
     */
    private List<CalendarItem> filterTasksAndEventsByDate(List<Task> tasks, List<Event> events, 
            Map<String, String[]> parsedResult) {
        // Get dates from input
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        LocalDateTime [] validDates = parsingDates(parsedResult);
        List<Task> filteredTasks;
        List<Event> filteredEvents;
        if (validDates == null) {
            return null; // Break out when date conflict found
        }
        
        // Set dates that are found, if not found value will be null
        LocalDateTime dateOn = validDates[DATE_ON_INDEX];
        LocalDateTime dateFrom = validDates[DATE_FROM_INDEX];
        LocalDateTime dateTo = validDates[DATE_TO_INDEX];
        
        if (dateOn != null) {
            // Filter by single date
            filteredTasks = FilterUtil.filterTaskBySingleDate(tasks, dateOn);
            filteredEvents = FilterUtil.filterEventBySingleDate(events, dateOn);
        } else {
            // Filter by range
            filteredTasks = FilterUtil.filterTaskWithDateRange(tasks, dateFrom, dateTo);
            filteredEvents = FilterUtil.filterEventWithDateRange(events, dateFrom, dateTo);
        }
        calendarItems.addAll(filteredTasks);
        calendarItems.addAll(filteredEvents);
        return calendarItems;
    }  
    
    /*
     * Filter out all the events based on the name list that has been parsed.
     * This method also ensure that no duplicate event will be return. 
     * 
     * @param itemNameList
     *            a list of item name that has been parsed from input
     * @param tagNameList           
     *            a List of tag name that has been parsed from input
     * @param events
     *            all the events in the DB           
     * @return a list of Event which names or tag names is filtered with the list
     */
    private List<Event> filterByEventNameAndTagName(HashSet<String> itemNameList, HashSet<String> tagNameList,
            List<Event> events) {
        HashSet<Event> mergedEvents = new HashSet<Event>();
        List<Event> eventsByNames = FilterUtil.filterEventByNames(events, itemNameList);
        List<Event> eventsByTags = FilterUtil.filterEventByTags(events, tagNameList);
        mergedEvents.addAll(eventsByNames);
        mergedEvents.addAll(eventsByTags);
        events = new ArrayList<Event>(mergedEvents);
        return events;
    }

    /*
     * Filter out all the tasks based on the name list that has been parsed.
     * This method also ensure that no duplicate task will be return. 
     * 
     * @param itemNameList
     *            a list of item name that has been parsed from input
     * @param tagNameList           
     *            a List of tag name that has been parsed from input
     * @param tasks
     *            all the tasks in the DB           
     * @return a list of Task which names or tag names is filtered with the list
     */
    private List<Task> filterByTaskNameAndTagName(HashSet<String> itemNameList, HashSet<String> tagNameList, 
            List<Task> tasks) {
        HashSet<Task> mergedTasks = new HashSet<Task>();
        List<Task> tasksByNames = FilterUtil.filterTaskByNames(tasks, itemNameList);
        List<Task> tasksByTags = FilterUtil.filterTaskByTags(tasks, tagNameList);
        mergedTasks.addAll(tasksByNames);
        mergedTasks.addAll(tasksByTags);
        tasks = new ArrayList<Task>(mergedTasks);
        return tasks;
    }
    
    /**
     * Extract the parsed result and update into the hashlist
     * @param parsedResult
     */
    private void updateHashList(Map<String, String[]> parsedResult, HashSet<String> hashList, 
            String token) {
        String result = ParseUtil.getTokenResult(parsedResult, token);
        // If found any matching , update list
        if (result != null) {
            hashList.add(result);
            String[] resultArray = StringUtil.splitStringBySpace(result);
            for (int i = 0; i < resultArray.length; i ++) {
                hashList.add(resultArray[i]);
            }
        }
    }
    
    /*============================ Helper Methods to check for Error/Syntax Command ===================*/
    
    /*
     * To be use to check if there are any command syntax error
     * 
     * @return true, if there is an error in the command syntax, false if syntax is allowed
     */
    private boolean isErrorCommand(boolean isTaskStatusProvided, boolean isEventStatusProvided, 
            boolean isTask, boolean isItemTypeProvided, String input) {
        // Check if more than 1 item type is provided
        if (FilterUtil.isItemTypeConflict(input)) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_ITEM_TYPE_CONFLICT);
            return true;
        }
        if (isItemTypeProvided) {
            // Task and Event Command Syntax detected
            if (isTask && isEventStatusProvided) {
                Renderer.renderDisambiguation(FIND_TASK_SYNTAX, MESSAGE_INVALID_TASK_STATUS);
                return true;
            }
            if (!isTask && isTaskStatusProvided) {
                Renderer.renderDisambiguation(FIND_EVENT_SYNTAX, MESSAGE_INVALID_EVENT_STATUS);
                return true;
            }
        }
        return false;
    }
    
    /*
     * To be used to parsed dates and check for any dates conflict
     * 
     * @return null if dates conflict detected, else return { null, dateOn, dateFrom, dateTo }
     */
    private LocalDateTime[] parsingDates(Map<String, String[]> parsedResult) {
        String[] parsedDates = ParseUtil.parseDates(parsedResult);
        LocalDateTime dateOn = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        
        if (parsedDates != null) {
            String naturalOn = parsedDates[DATE_ON_INDEX];
            String naturalFrom = parsedDates[DATE_FROM_INDEX];
            String naturalTo = parsedDates[DATE_TO_INDEX];
            
            if (naturalOn != null && Integer.parseInt(parsedDates[NUM_OF_DATES_FOUND_INDEX]) > 1) {
                // Date conflict detected
                Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_DATE_CONFLICT);
                return null;
            }
            // Parse natural date using Natty.
            try {
                dateOn = naturalOn == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalOn)); 
                dateFrom = naturalFrom == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalFrom)); 
                dateTo = naturalTo == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalTo));
            } catch (InvalidNaturalDateException e) {
                    Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_NO_DATE_DETECTED);
                    return null;
            }           
        }
        return new LocalDateTime[] { null, dateOn, dateFrom, dateTo };
    }
}
```
###### /java/seedu/todo/controllers/ListController.java
``` java
/**
 * Controller to list CalendarItems
 */
public class ListController implements Controller {
    
    private static final String NAME = "List";
    private static final String DESCRIPTION = "List all tasks and events by type or status.";
    private static final String COMMAND_SYNTAX = "list \"task complete/incomplete\" or \"event over/ongoing\"" + 
        "[on date] or [from date to date]";
    private static final String COMMAND_WORD = "list";
    
    // Syntax correction to console input
    public static final String LIST_TASK_SYNTAX = "list task \"complete/incomplete\"";
    public static final String LIST_EVENT_SYNTAX = "list event \"over/current\"";
    public static final String LIST_DATE_SYNTAX = "list \"date\" [or from \"date\" to \"date\"]";
    
    // Message output to console text area
    public static final String MESSAGE_RESULT_FOUND_FORMAT = "A total of %s found!";
    public static final String MESSAGE_NO_RESULT_FOUND = "No task or event found!";
    public static final String MESSAGE_LIST_SUCCESS = "Listing Today's, incompleted tasks and ongoing events";
    public static final String MESSAGE_INVALID_TASK_STATUS = "Unable to list!\nTry listing with complete or incomplete";
    public static final String MESSAGE_INVALID_EVENT_STATUS = "Unable to list!\nTry listing with over or current";
    public static final String MESSAGE_DATE_CONFLICT = "Unable to list!\nMore than 1 date criteria is provided!";
    public static final String MESSAGE_NO_DATE_DETECTED = "Unable to list!\nThe natural date entered is not supported.";
    public static final String MESSAGE_ITEM_TYPE_CONFLICT = "Unable to list!\nMore than 1 item type is provided!";
    
    // Use to access parsing of dates
    private static final int NUM_OF_DATES_FOUND_INDEX = 0;
    private static final int COMMAND_INPUT_INDEX = 0;
    private static final int DATE_CRITERIA_INDEX = 0;
    private static final int DATE_ON_INDEX = 1;
    private static final int DATE_FROM_INDEX = 2;
    private static final int DATE_TO_INDEX = 3;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (StringUtil.splitStringBySpace(input.toLowerCase())[COMMAND_INPUT_INDEX]).equals(COMMAND_WORD) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put(Tokenizer.DEFAULT_TOKEN, new String[] { COMMAND_WORD });
        tokenDefinitions.put(Tokenizer.EVENT_TYPE_TOKEN, Tokenizer.EVENT_TYPE_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_TOKEN, Tokenizer.TIME_DEFINITION);
        tokenDefinitions.put(Tokenizer.TASK_STATUS_TOKEN, Tokenizer.TASK_STATUS_DEFINITION);
        tokenDefinitions.put(Tokenizer.EVENT_STATUS_TOKEN, Tokenizer.EVENT_STATUS_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_FROM_TOKEN, Tokenizer.TIME_FROM_DEFINITION);
        tokenDefinitions.put(Tokenizer.TIME_TO_TOKEN, Tokenizer.TIME_TO_DEFINITION);
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        TodoListDB db = TodoListDB.getInstance();
        
        // If input is just "list", invoke Renderer
        if (input.trim().equals(COMMAND_WORD)) {
            Renderer.renderIndex(db, MESSAGE_LIST_SUCCESS);
            return;
        }
        
        // Check if input includes itemType and itemStatus
        boolean isItemTypeProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.EVENT_TYPE_TOKEN);
        boolean isTaskStatusProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.TASK_STATUS_TOKEN);
        boolean isEventStatusProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.EVENT_STATUS_TOKEN);
        
        // Set item type
        boolean isTask = true; //default
        if (isItemTypeProvided) {
            isTask = ParseUtil.doesTokenContainKeyword(parsedResult, Tokenizer.EVENT_TYPE_TOKEN, "task");
        }
        
        if (isErrorCommand(isTaskStatusProvided, isEventStatusProvided, isTask, isItemTypeProvided, input)) {
            return; // Break out if found error
        }
        
        // Setting up view
        List<Task> tasks; //default
        List<Event> events; //default
        List<CalendarItem> calendarItems;
        // Filter Task and Event by Type
        if (!isItemTypeProvided) {
            tasks = db.getAllTasks();
            events = db.getAllEvents();
        } else {
            if (isTask) {
                events = new ArrayList<Event>();
                tasks = db.getAllTasks();
            } else {
                tasks = new ArrayList<Task>();
                events = db.getAllEvents();
            }
        }

        // Filter Task and Event by Status
        calendarItems = filterTasksAndEventsByStatus(parsedResult, isTaskStatusProvided, isEventStatusProvided, 
                tasks, events);
        tasks = FilterUtil.filterOutTask(calendarItems);
        events = FilterUtil.filterOutEvent(calendarItems);
        
        // Filter Task and Event by date
        calendarItems = filterTasksAndEventsByDate(tasks, events, parsedResult);
        if (calendarItems == null) {
            return; // Date conflict detected
        }
        tasks = FilterUtil.filterOutTask(calendarItems);
        events = FilterUtil.filterOutEvent(calendarItems);
        
        // Show message if no items had been found
        if (tasks.size() == 0 && events.size() == 0) {
            Renderer.renderIndex(db, MESSAGE_NO_RESULT_FOUND);
            return;
        }
        
        String consoleMessage = String.format(MESSAGE_RESULT_FOUND_FORMAT, 
                StringUtil.displayNumberOfTaskAndEventFoundWithPuralizer(tasks.size(), events.size()));
        Renderer.renderSelectedIndex(db, consoleMessage, tasks, events);
    }
    
    /* ============================== Helper methods to filter out by criterias ================================*/
    
    /*
     * Filter out the selected tasks and events based on the dates
     * and update tasks and events accordingly
     * 
     * @param tasks
     *            List of Task items
     * @param events           
     *            List of Event items
     * @param parsedResult
     *            Parsed result by Tokenizer, in order to filter out the dates   
     * @return a List of CalendarItem that is been filtered by date(s)                   
     */
    private List<CalendarItem> filterTasksAndEventsByDate(List<Task> tasks, List<Event> events, Map<String, String[]> parsedResult) {
        // Get dates from input
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        LocalDateTime [] validDates = parsingDates(parsedResult);
        List<Task> filteredTasks = tasks;
        List<Event> filteredEvents = events;
        if (validDates == null) {
            return null; // Break out when date conflict found
        }
        
        // Set dates that are found, if not found value will be null
        LocalDateTime dateCriteria = validDates[DATE_CRITERIA_INDEX];
        LocalDateTime dateOn = validDates[DATE_ON_INDEX];
        LocalDateTime dateFrom = validDates[DATE_FROM_INDEX];
        LocalDateTime dateTo = validDates[DATE_TO_INDEX];
        
        if (dateCriteria != null) {
            // Filter by single date
            assert dateOn == null;
            assert dateFrom == null;
            assert dateTo == null;
            filteredTasks = FilterUtil.filterTaskBySingleDate(tasks, dateCriteria);
            filteredEvents = FilterUtil.filterEventBySingleDate(events, dateCriteria);
        }
        
        if (dateOn != null) {
            // Filter by single date
            filteredTasks = FilterUtil.filterTaskBySingleDate(tasks, dateOn);
            filteredEvents = FilterUtil.filterEventBySingleDate(events, dateOn);
        } else if (dateFrom != null || dateTo != null) {
            // Filter by range
            filteredTasks = FilterUtil.filterTaskWithDateRange(tasks, dateFrom, dateTo);
            filteredEvents =FilterUtil.filterEventWithDateRange(events, dateFrom, dateTo);
        }
        
        calendarItems.addAll(filteredTasks);
        calendarItems.addAll(filteredEvents);
        return calendarItems;
    }  
    
    /*
     * Filter out the selected tasks and events based on the status and update tasks and events accordingly
     * 
     * @param parsedResult
     *            parsedResult by Tokenizer
     * @param isTaskStatusProvided
     *            true if complete or incomplete is found, else false
     * @param isEventStatusProvided
     *            true if over or current is found, else false                   
     * @param tasks
     *            List of Task items
     * @param events           
     *            List of Event items
     * @return        
     *            tasks and events in a list form by status
     */
    private List<CalendarItem> filterTasksAndEventsByStatus(Map<String, String[]> parsedResult, boolean isTaskStatusProvided,
            boolean isEventStatusProvided, List<Task> tasks, List<Event> events) {
        List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
        List<Task> filteredTasks = tasks;
        List<Event> filteredEvents = events;
        
        // Set item status
        boolean isCompleted = false; //default 
        boolean isOver = false; //default
        
        // Filter out by Task Status if provided
        if (isTaskStatusProvided) {
            isCompleted = !ParseUtil.doesTokenContainKeyword(parsedResult, Tokenizer.TASK_STATUS_TOKEN, "incomplete");
            filteredTasks = FilterUtil.filterTasksByStatus(tasks, isCompleted);
            filteredEvents = new ArrayList<Event>();
        }
        
        // Filter out by Event Status if provided
        if (isEventStatusProvided) {
            isOver = ParseUtil.doesTokenContainKeyword(parsedResult, Tokenizer.EVENT_STATUS_TOKEN, "over");
            filteredEvents = FilterUtil.filterEventsByStatus(events, isOver);
            filteredTasks = new ArrayList<Task>();
        }
        calendarItems.addAll(filteredTasks);
        calendarItems.addAll(filteredEvents);
        return calendarItems;
    }
    
    /*====================== Helper Methods to check for Error/Syntax Command ===================*/
    
    /*
     * To be use to check if there are any command syntax error
     * 
     * @return true, if there is an error in the command syntax, false if syntax is allowed
     */
    private boolean isErrorCommand(boolean isTaskStatusProvided, boolean isEventStatusProvided, 
            boolean isTask, boolean isItemTypeProvided, String input) {
        // Check if more than 1 item type is provided
        if (FilterUtil.isItemTypeConflict(input)) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_ITEM_TYPE_CONFLICT);
            return true;
        }
        if (isItemTypeProvided) {
            // Task and Event Command Syntax detected
            if (isTask && isEventStatusProvided) {
                Renderer.renderDisambiguation(LIST_TASK_SYNTAX, MESSAGE_INVALID_TASK_STATUS);
                return true;
            }
            
            if (!isTask && isTaskStatusProvided) {
                Renderer.renderDisambiguation(LIST_EVENT_SYNTAX, MESSAGE_INVALID_EVENT_STATUS);
                return true;
            }
        }
        return false;
    }
    
    /*
     * To be used to parsed dates and check for any dates conflict
     * 
     * @return null if dates conflict detected, else return { dateCriteria, dateOn, dateFrom, dateTo }
     */
    private LocalDateTime[] parsingDates(Map<String, String[]> parsedResult) {
        String[] parsedDates = ParseUtil.parseDates(parsedResult);
        
        // Date enter with COMMAND_WORD e.g list today
        String date = ParseUtil.getTokenResult(parsedResult, Tokenizer.DEFAULT_TOKEN);
        
        // Check for more than 1 date
        if (date != null && parsedDates != null) {
            Renderer.renderDisambiguation(LIST_DATE_SYNTAX, MESSAGE_DATE_CONFLICT);
            return null;
        }
        
        LocalDateTime dateCriteria = null;
        LocalDateTime dateOn = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        
        // Setting of dates value
        if (date != null) {
            try {
                dateCriteria = DateParser.parseNatural(date);
            } catch (InvalidNaturalDateException e) {
                Renderer.renderDisambiguation(LIST_DATE_SYNTAX, MESSAGE_NO_DATE_DETECTED);
                return null;
            }
        }
        
        if (parsedDates != null) {
            String naturalOn = parsedDates[DATE_ON_INDEX];
            String naturalFrom = parsedDates[DATE_FROM_INDEX];
            String naturalTo = parsedDates[DATE_TO_INDEX];
            
            if (naturalOn != null && Integer.parseInt(parsedDates[NUM_OF_DATES_FOUND_INDEX]) > 1) {
                //date conflict detected
                Renderer.renderDisambiguation(LIST_DATE_SYNTAX, MESSAGE_DATE_CONFLICT);
                return null;
            }
            // Parse natural date using Natty.
            try {
                dateOn = naturalOn == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalOn)); 
                dateFrom = naturalFrom == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalFrom)); 
                dateTo = naturalTo == null ? null : DateUtil.floorDate(DateParser.parseNatural(naturalTo));
            } catch (InvalidNaturalDateException e) {
                    Renderer.renderDisambiguation(LIST_DATE_SYNTAX, MESSAGE_NO_DATE_DETECTED);
                    return null;
            }           
        }
        
        boolean isEventStatusProvided = !ParseUtil.isTokenNull(parsedResult, Tokenizer.EVENT_STATUS_TOKEN);
        // Checking of date conflict with status, for e.g. list by today over
        if ((parsedDates != null || dateCriteria != null) && isEventStatusProvided ) {
            //detect date conflict
            Renderer.renderDisambiguation(LIST_DATE_SYNTAX, MESSAGE_DATE_CONFLICT);
            return null;
        }
        
        return new LocalDateTime[] { dateCriteria, dateOn, dateFrom, dateTo };
    }
}
```
###### /java/seedu/todo/controllers/TagController.java
``` java
/**
 * Controller to Tag a CalendarItem.
 */
public class TagController implements Controller {
    
    private static final String NAME = "Tag";
    private static final String DESCRIPTION = "Tag a task/event by listed index";
    public static final String COMMAND_SYNTAX = "tag <index> <tag name>";
    private static final String COMMAND_WORD = "tag";
    
    public static final String TAG_FORMAT = "tag %d";
    public static final String MESSAGE_TAG_SUCCESS = "Item has been tagged successfully.";
    public static final String MESSAGE_INDEX_OUT_OF_RANGE = "Could not tag task/event: Invalid index provided!";
    public static final String MESSAGE_MISSING_INDEX_AND_TAG_NAME = "Please specify the index of the item and the tag name to tag.";
    public static final String MESSAGE_INDEX_NOT_NUMBER = "Index has to be a number!";
    public static final String MESSAGE_TAG_NAME_NOT_FOUND = "Could not tag task/event: Tag name not provided!";
    public static final String MESSAGE_EXCEED_TAG_SIZE = "Could not tag task/event : Tag size exceed";
    public static final String MESSAGE_TAG_NAME_EXIST = "Could not tag task/event: Tag name already exist or Duplicate Tag Names!";
    
    private static final int COMMAND_INPUT_INDEX = 0;
    private static final int ITEM_INDEX = 0;
    private static final int TOKENIZER_DEFAULT_INDEX = 1;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (StringUtil.splitStringBySpace(input.toLowerCase())[COMMAND_INPUT_INDEX]).equals(COMMAND_WORD) ? 1 : 0;
    }
    
    /**
     * Get the token definitions for use with <code>tokenizer</code>.<br>
     * This method exists primarily because Java does not support HashMap
     * literals...
     * 
     * @return tokenDefinitions
     */
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put(Tokenizer.DEFAULT_TOKEN, new String[] { COMMAND_WORD });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        String param = parsedResult.get(Tokenizer.DEFAULT_TOKEN)[TOKENIZER_DEFAULT_INDEX];
        
        if (param == null) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_MISSING_INDEX_AND_TAG_NAME);
            return;
        }
        
        String[] parameters = StringUtil.splitStringBySpace(param);
        
        // Get index and tag names.
        int index = 0;
        String tagNames = null;
        
        try {
            index = Integer.decode(parameters[ITEM_INDEX]);
            tagNames = param.replaceFirst(parameters[ITEM_INDEX], "").trim();
        } catch (NumberFormatException e) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_INDEX_NOT_NUMBER);
            return;
        }
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        String[] parsedTagNames = parseTags(tagNames);
        
        if (isErrorCommand(parameters, index, calendarItem, parsedTagNames)) {
            return; // Break out once error
        }
        
        boolean resultOfTagging = addingTagNames(parsedTagNames, calendarItem);
        
        // Re-render
        if (resultOfTagging) {
            db.addIntoTagList(parsedTagNames);
            db.save();
            Renderer.renderSelectedIndex(db, MESSAGE_TAG_SUCCESS, edb.getAllDisplayedTasks(), edb.getAllDisplayedEvents());
        } else {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_EXCEED_TAG_SIZE);
        }
    }

    /*
     * To be used to check if there are any command syntax errors
     * 
     * @return true if error detected, else false.
     */
    private boolean isErrorCommand(String[] parameters, int index, CalendarItem calendarItem, String[] parsedTagNames) {
        // Check if index is out of range
        if (calendarItem == null) {
            Renderer.renderDisambiguation(String.format(TAG_FORMAT, index), MESSAGE_INDEX_OUT_OF_RANGE);
            return true;
        }
        
        // Check if tag name is provided
        if (parameters.length <= 1) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_TAG_NAME_NOT_FOUND);
            return true;
        }
        
        // Check if tag name already exist
        boolean isTagNameDuplicate = checkDuplicateTagName(parsedTagNames, calendarItem);
        
        if (isTagNameDuplicate) {
            Renderer.renderDisambiguation(String.format(TAG_FORMAT, index), MESSAGE_TAG_NAME_EXIST);
            return true;
        }
        
        return false;
    }

    /*
     * To be used to add tag into the tag list that belong to the CalendarItem
     * @param parsedTagNames
     *                     tag names that are entered by user and not duplicate and do not belong to the calendarItem
     * @param calendarItem                    
     *                     can be task or event
     *                     
     * @return true if all tags have been added successfully, false if one of the tags is not added successfully                     
     */
    private boolean addingTagNames(String[] parsedTagNames, CalendarItem calendarItem) {
        
        // If tag names parsed exceed the maximum tag list limit
        if (calendarItem.getTagList().size() + parsedTagNames.length > calendarItem.getTagListLimit()) {
            return false;
        }
        
        boolean result = true;
        for (int i = 0; i < parsedTagNames.length; i ++) {
            result = calendarItem.addTag(parsedTagNames[i].trim()) & result;
        }
        return result;
    }
    
    /*
     * To be used to check if user enter any duplicate tag name and if calendarItem already has that tag name
     * @param parsedTagNames
     *                   tag names that has been split into an array
     * @param calendarItem
     *                   calendarItem that can be either a task or event
     * 
     * @return true if tag name already exist or is entered more than once, false if it does not exist
     */
    private boolean checkDuplicateTagName(String[] parsedTagNames, CalendarItem calendarItem) {
        HashSet<String> parsedTagNamesList = new HashSet<String>();
        for (int i = 0; i < parsedTagNames.length; i ++) {
            // Checking with overall tag list in db
            if (calendarItem.getTagList().contains(parsedTagNames[i].trim())) {
                return true;
            }
            // Checking with the current array, if there are duplicate tags
            parsedTagNamesList.add(parsedTagNames[i]);
        }
        
        if (parsedTagNamesList.size() != parsedTagNames.length) {
            return true;
        }
        return false;
    }
    
    /*
     * To be used to split tag names by comma if more than one is entered
     * @param tags
     *           tag names that is entered
     *           
     * @return an array of tag name that is split by comma
     */
    private String [] parseTags(String tags) {
        return tags.split(",");
    }

}
```
###### /java/seedu/todo/controllers/UntagController.java
``` java
/**
 * Controller to untag a CalendarItem.
 */
public class UntagController implements Controller {
    
    private static final String NAME = "Untag";
    private static final String DESCRIPTION = "Untag a task/event by listed index";
    public static final String COMMAND_SYNTAX = "untag <index> <tag name>";
    private static final String COMMAND_WORD = "untag";
    
    public static final String UNTAG_FORMAT = "untag %d";
    public static final String MESSAGE_UNTAG_SUCCESS = "Item has been untagged successfully.";
    public static final String MESSAGE_INDEX_OUT_OF_RANGE = "Could not untag task/event: Invalid index provided!";
    public static final String MESSAGE_MISSING_INDEX_AND_TAG_NAME = "Please specify the index of the item and the tag name to untag.";
    public static final String MESSAGE_INDEX_NOT_NUMBER = "Index has to be a number!";
    public static final String MESSAGE_TAG_NAME_NOT_FOUND = "Could not untag task/event: Tag name not provided!";
    public static final String MESSAGE_TAG_NAME_DOES_NOT_EXIST = "Could not untag task/event: Tag name does not exist!";
    public static final String MESSAGE_TAG_NAME_EXIST = "Could not untag task/event : Tag name does not exist or Duplicate Tag name detected!";
    
    private static final int COMMAND_INPUT_INDEX = 0;
    private static final int ITEM_INDEX = 0;
    private static final int TOKENIZER_DEFAULT_INDEX = 1;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (StringUtil.splitStringBySpace(input.toLowerCase())[COMMAND_INPUT_INDEX]).equals(COMMAND_WORD) ? 1 : 0;
    }
    
    /**
     * Get the token definitions for use with <code>tokenizer</code>.<br>
     * This method exists primarily because Java does not support HashMap
     * literals...
     * 
     * @return tokenDefinitions
     */
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put(Tokenizer.DEFAULT_TOKEN, new String[] { COMMAND_WORD });
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        String param = parsedResult.get(Tokenizer.DEFAULT_TOKEN)[TOKENIZER_DEFAULT_INDEX];
        
        if (param == null) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_MISSING_INDEX_AND_TAG_NAME);
            return;
        }
        
        String[] parameters = StringUtil.splitStringBySpace(param);
        
        // Get index.
        int index = 0;
        String tagNames = null;
        try {
            index = Integer.decode(parameters[ITEM_INDEX]);
            tagNames = param.replaceFirst(parameters[ITEM_INDEX], "").trim();
        } catch (NumberFormatException e) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_INDEX_NOT_NUMBER);
            return;
        }
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        String[] parsedTagNames = parseTags(tagNames);
        
        if (isErrorCommand(parameters, index, calendarItem, parsedTagNames)) {
            return; // Break out once error
        }
               
        boolean resultOfUntagging = removingTagNames(parsedTagNames, calendarItem);
        
        // Re-render
        if (resultOfUntagging) {
            db.updateTagList(parsedTagNames);
            db.save();
            Renderer.renderSelectedIndex(db, MESSAGE_UNTAG_SUCCESS, edb.getAllDisplayedTasks(), edb.getAllDisplayedEvents());
        } else {
            Renderer.renderDisambiguation(String.format(UNTAG_FORMAT, index), MESSAGE_TAG_NAME_DOES_NOT_EXIST);
        }
    }
    
    /*
     * To be used to check if there are any command syntax errors
     * 
     * @return true if error detected, else false.
     */
    private boolean isErrorCommand(String[] parameters, int index, CalendarItem calendarItem, String[] parsedTagNames) {
        // Check if index is out of range
        if (calendarItem == null) {
            Renderer.renderDisambiguation(String.format(UNTAG_FORMAT, index), MESSAGE_INDEX_OUT_OF_RANGE);
            return true;
        }
        
        // Check if tag name is provided
        if (parameters.length <= 1) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_TAG_NAME_NOT_FOUND);
            return true;
        }
        
        // Check if tag name exist
        if (tagNameDoesNotExist(parsedTagNames, calendarItem)) {
            Renderer.renderDisambiguation(String.format(UNTAG_FORMAT, index), MESSAGE_TAG_NAME_EXIST);
            return true;
        }
        return false;
    }
    
    /*
     * To be used to remove tag from the tag list that belong to the CalendarItem
     * @param parsedTagNames
     *                     tag names that are entered by user and not duplicate and belong to the calendarItem
     * @param calendarItem                    
     *                     can be task or event
     *                     
     * @return true if all tags have been removed successfully, false if one of the tags its not removed successfully                     
     * */
    private boolean removingTagNames(String[] parsedTagNames, CalendarItem calendarItem) {
        assert parsedTagNames != null;
        assert calendarItem != null;
        
        boolean result = true;
        for (int i = 0; i < parsedTagNames.length; i ++) {
            result = calendarItem.removeTag(parsedTagNames[i].trim()) & result;
        }
        return result;
    }
    
    /*
     * To be used to check if user enter any duplicate tag name and if calendarItem has the exact tag name
     * @param parsedTagNames
     *                   tag names that has been split into an array
     * @param calendarItem
     *                   calendarItem that can be either a task or event
     * 
     * @return true if tag name does not exist or is entered more than once, false if it exist in the tag list
     */
    private boolean tagNameDoesNotExist(String[] parsedTagNames, CalendarItem calendarItem) {
        HashSet<String> parsedTagNamesList = new HashSet<String>();
        for (int i = 0; i < parsedTagNames.length; i ++) {
            // Checking with overall tag list in db
            if (!calendarItem.getTagList().contains(parsedTagNames[i].trim())) {
                return true;
            }
            
            // Checking with the current array, if there are any duplicate tag names
            parsedTagNamesList.add(parsedTagNames[i]);
        }
        return parsedTagNamesList.size() != parsedTagNames.length;
    }

    /*
     * To be used to split tag names by comma if more than one is entered
     * @param tags
     *           tag names that is entered
     *           
     * @return an array of tag name that is split by comma
     */
    private String [] parseTags(String tags) {
        return tags.split(",");
    }

}
```
###### /java/seedu/todo/models/CalendarItem.java
``` java
    /**
     * Returns the current tag list that belong to the CalendarItem, mainly for displaying purpose
     * 
     * @return ArrayList<String> tags
     */
    public ArrayList<String> getTagList();
   
```
###### /java/seedu/todo/models/CalendarItem.java
``` java
    /**
     * Add a new tag in the list of tag of the calendar item. 
     * 
     * @param tagName <String>
     * @return true if it has not reached the max tag list size, false if tag list already reach the max size
     */
    public boolean addTag(String tagName);
    
```
###### /java/seedu/todo/models/CalendarItem.java
``` java
    /**
     * Remove a existing tag in the tag list of tag of the calendar item. 
     * 
     * @param tagName <String>
     * @return true if tagName is removed successfully, false if failed to remove tagName due to unable to find
     */
    public boolean removeTag(String tagName);

```
###### /java/seedu/todo/models/CalendarItem.java
``` java
    /**
     * Get the limit of the tag that is allowed for calendar item
     * 
     * @return the limit of tag list
     */    
    public int getTagListLimit();
}
```
###### /java/seedu/todo/models/Event.java
``` java
    /**
     * Return the tag list that belong to the calendar item
     */
    @Override
    public ArrayList<String> getTagList() {
        return tagList;
    }

```
###### /java/seedu/todo/models/Event.java
``` java
    /**
     * Adding the tag into the tag list that belong to the calendar item
     * @param tagName
     *               name of the tag
     *               
     * @return true tag name is successfully added, false if tag list if full         
     */
    @Override
    public boolean addTag(String tagName) {
        if(tagList.size() < MAX_TAG_LIST_SIZE) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

```
###### /java/seedu/todo/models/Event.java
``` java
    /**
     * Removing the tag from the tag list that belong to the calendar item
     * @param tagName
     *               name of the tag
     *               
     * @return true tag name is successfully removed, false if tag name does not exist    
     */
    @Override
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }
    
    /**
```
###### /java/seedu/todo/models/Event.java
``` java
    /**
     * Returning the maximum tag list size that is allowed     
     */
    @Override
    public int getTagListLimit() {
        return MAX_TAG_LIST_SIZE;
    }

}
```
###### /java/seedu/todo/models/Task.java
``` java
    /**
     * Return the tag list that belong to the calendar item
     */
    @Override
    public ArrayList<String> getTagList() {
        return tagList;
    }

```
###### /java/seedu/todo/models/Task.java
``` java
    /**
     * Adding the tag into the tag list that belong to the calendar item
     * @param tagName
     *               name of the tag
     *               
     * @return true tag name is successfully added, false if tag list if full         
     */
    @Override
    public boolean addTag(String tagName) {
        if(tagList.size() < MAX_TAG_LIST_SIZE) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

```
###### /java/seedu/todo/models/Task.java
``` java
    /**
     * Removing the tag from the tag list that belong to the calendar item
     * @param tagName
     *               name of the tag
     *               
     * @return true tag name is successfully removed, false if tag name does not exist    
     */
    @Override
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }
    
    
    /**
```
###### /java/seedu/todo/models/Task.java
``` java
    /**
     * Returning the maximum tag list size that is allowed           
     */
    @Override
    public int getTagListLimit() {
        return MAX_TAG_LIST_SIZE;
    }

}
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Add into the overall Tags in the DB.
     */
    public void addIntoTagList(String[] parsedTagNames) {
        assert parsedTagNames != null;
        for (int i = 0; i < parsedTagNames.length; i ++) {
            String tagName = parsedTagNames[i].trim();
            if (tagList.get(tagName) != null) {
                int currentTagCount = tagList.get(tagName);
                tagList.put(tagName, currentTagCount + 1);
            } else {
                tagList.put(tagName, 1);
            }
        }
    }
    
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Remove from the overall Tags with a single tagName that exist in the DB.
     */
    public void updateTagList(String[] parsedTagNames) {
        assert parsedTagNames != null;
        for (int i = 0; i < parsedTagNames.length; i ++) {
            String tagName = parsedTagNames[i].trim();
            int currentTagCount = tagList.get(tagName);
            
            int newTagCount = currentTagCount - 1;
            if (newTagCount == 0) {
                tagList.remove(tagName);
            } else {
                tagList.put(tagName, newTagCount);
            }
        }
    }
    
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Remove from the overall Tags with a given List of CalendarItem that exist in the DB.
     * @param <E>listOfItem of type CalendarItem
     */
    public <E> void removeFromTagList(List<E> listOfCalendarItem) {
        assert listOfCalendarItem != null;
        
        ArrayList<String> selectedTagList = new ArrayList<String>();
        for (int i = 0; i < listOfCalendarItem.size(); i ++) {
            selectedTagList.addAll(((CalendarItem) listOfCalendarItem.get(i)).getTagList());
        }
        
        updateTagList(selectedTagList.toArray(new String[0]));
    }
    
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Get a list of Tags in the DB.
     * 
     * @return tagList
     */
    public HashMap<String, Integer> getTagList() {
        return tagList;
    }
    
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Count tags which are already inserted into the db
     * 
     * @return Number of tags
     */
    public int countTagList() {
        return tagList.size();
    }
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Destroys all Task and Events in the DB and persists the commit.
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllTaskAndEvents() {
        removeFromTagList(new ArrayList<Task>(tasks));
        removeFromTagList(new ArrayList<Event>(events));
        tasks = new LinkedHashSet<Task>();
        events = new LinkedHashSet<Event>();
        return save();
    }
    
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Destroys Task and Events based on list in the DB and persists the commit.
     * @taskLists 
     *            List of tasks to be destroyed
     * @eventLists
     *            List of events to be destroyed           
     * 
     * @return true if the save was successful, false otherwise
     */
    public boolean destroyAllTaskAndEventsByList(List<Task> tasksList , List<Event> eventsList) {
        removeFromTagList(new ArrayList<Task>(tasksList));
        removeFromTagList(new ArrayList<Event>(eventsList));
        
        //removing tasks and events
        tasks.removeAll(tasksList);
        events.removeAll(eventsList);
        return save();
    }
    
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Destroys all Task in the DB and persists the commit.
     * 
     * @return true if the save was successful, false otherwise
     */
    public void destroyAllTask() {
        removeFromTagList(new ArrayList<Task>(tasks));
        tasks = new LinkedHashSet<Task>();
    }
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
    /**
     * Destroys all Event in the DB and persists the commit.
     */
    public void destroyAllEvent() {
        removeFromTagList(new ArrayList<Event>(events));
        events = new LinkedHashSet<Event>();
    }
```
