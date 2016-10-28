# Tiong YaoCong A0139922Y
###### /java/seedu/todo/controllers/ClearController.java
``` java
 *
 */
public class ClearController implements Controller {
    
    private static final String NAME = "Clear";
    private static final String DESCRIPTION = "Clear all tasks/events or by specify date.";
    private static final String COMMAND_SYNTAX = "clear [task/event] [on date]";
    private static final String COMMAND_WORD = "clear";
    private static final String MESSAGE_CLEAR_NO_ITEM_FOUND = "No item found!";
    private static final String MESSAGE_CLEAR_SUCCESS = "A total of %s deleted!\n" + "To undo, type \"undo\".";

    //Use by array access
    private static final int KEYWORD = 0;
    private static final int RESULT = 1;
    private static final int MAXIMUM_SIZE = 2;
    //Use by accessing date value
    private static final int INDEX_DATE_ON = 0;
    private static final int INDEX_DATE_FROM = 1;
    private static final int INDEX_DATE_TO = 2;

    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith(COMMAND_WORD)) ? 1 : 0;
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
        tokenDefinitions.put("default", new String[] {"clear"});
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks" });
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "before", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "until" });
        return tokenDefinitions;
    }
    
    @Override
    public void process(String input) throws ParseException {
        TodoListDB db = TodoListDB.getInstance();

        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        String[] parsedDates = parseDates(parsedResult);
        
        // Task or event specified?
        boolean deleteAll = parseDeleteAllType(parsedResult);
        
        // Task or event?
        boolean isTask = true; //default
        
        // task or event keyword is been found
        if (!deleteAll) {
            isTask = parseIsTask(parsedResult);
        }
        
        //no dates provided and input is exactly the same as COMMAND_WORD
        if (parsedDates == null && parseExactClearCommand(parsedResult) && deleteAll) {
            destroyAll(db);
            return;
        } else {
            //invalid date provide by user with no date keywords parsed by natty
            if (deleteAll && !parseExactClearCommand(parsedResult) && parsedDates == null) { //no item type and date provided
                LocalDateTime date = parseDateWithNoKeyword(parsedResult);
                if (date == null) {
                    displayErrorMessage(input, parsedDates, deleteAll, isTask);
                    return;
                } 
            }
        }
        
        //parsing of dates with keywords with natty
        LocalDateTime dateOn = parseDateWithNoKeyword(parsedResult);
        LocalDateTime dateFrom = (LocalDateTime) null;
        LocalDateTime dateTo = (LocalDateTime) null;
        if (parsedDates != null) {
            String naturalOn = parsedDates[INDEX_DATE_ON];
            String naturalFrom = parsedDates[INDEX_DATE_FROM];
            String naturalTo = parsedDates[INDEX_DATE_TO];
            // if all are null = no date provided
            
            // Parse natural date using Natty.
            dateOn = naturalOn == null ? (LocalDateTime) null : parseNatural(naturalOn);
            dateFrom = naturalFrom == null ? (LocalDateTime) null : parseNatural(naturalFrom); 
            dateTo = naturalTo == null ? (LocalDateTime) null : parseNatural(naturalTo);
        }
        
        //invoke destroy command
        destroyByDate(db, parsedDates, dateOn, dateFrom, dateTo, deleteAll, isTask, input);
    }

    /** ================ DESTROY TASKS/EVENTS WITH FILTERED KEYWORDS ================== **/

    /**
     * Clear all tasks and events by a single or a range of date that exist in the database.
     * 
     * @param db
     *            TodoListDB object
     * @param parsedDate
     *            null if no date entered, or natural date that user has entered
     * @param dateOn, dateFrom
     *            null if parsing failed or Due date for Task or start date for Event
     * @param dateTo
     *            null if parsing failed or End date for Event
     * @param deleteAll
     *            true if no CalendarItem Type provided, false if "task" or "event" keyword found
     * @param isTask
     *            true if "task" keyword found, false if "event" keyword found
     * @param input
     *            the input the user have entered
     */
    private void destroyByDate(TodoListDB db, String[] parsedDate, LocalDateTime dateOn, 
            LocalDateTime dateFrom, LocalDateTime dateTo, boolean deleteAll,
            boolean isTask, String input) {
        if (dateOn == null && dateFrom == null && dateTo == null && deleteAll) {
            displayErrorMessage(input, parsedDate, deleteAll, isTask);
        }
        else if (dateOn != null) {
            destroyBySelectedDate(db, dateOn, deleteAll, isTask);
            return;
        } else {
            if (!deleteAll && parsedDate != null && dateFrom == null
                    && dateTo == null && dateOn == null) { //date provided is invalid
                displayErrorMessage(input, parsedDate, deleteAll, isTask);
                return;
            } else {
                if (parsedDate != null) {
                    if (parsedDate[INDEX_DATE_FROM] != null && parsedDate[INDEX_DATE_TO] != null 
                            && (dateFrom == null || dateTo == null)) {
                        displayErrorMessage(input, parsedDate, deleteAll, isTask);
                        return;
                    }
                }
                destroyByRange(db, dateFrom, dateTo, deleteAll, isTask);
                return;
            }
        } 
    }

    /**
     * clear all tasks and events of given date range that exist in the database.
     * 
     * @param TodoListDB
     * @param dateFrom
     *            null if parsing failed or Due date for Task or start date for Event
     * @param dateTo
     *            null if parsing failed or End date for Event
     * @param deleteAll
     *            true if no CalendarItem Type provided, false if "task" or "event" keyword found
     * @param isTask
     *            true if "task" keyword found, false if "event" keyword found
     */
    private void destroyByRange(TodoListDB db, LocalDateTime dateFrom, LocalDateTime dateTo, 
            boolean deleteAll, boolean isTask) {
        if (dateFrom == null) {
            dateFrom = LocalDateTime.MIN;
        } 
        
        if (dateTo == null) {
            dateTo = LocalDateTime.MAX;
        }
        
        int numTasks = db.getTaskByRange(dateFrom, dateTo).size();
        int numEvents = db.getEventByRange(dateFrom, dateTo).size();
        
        //if no tasks or events are been found
        if (numTasks == 0 && numEvents == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        //if CalendarItem type not specified
        if (deleteAll) {
            db.destroyAllEventByRange(dateFrom, dateTo);
            db.destroyAllTaskByRange(dateFrom, dateTo);
        } else if (isTask) {
            // no task is been found
            if (numTasks == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByRange(dateFrom, dateTo);
            numEvents = 0;
        } else {
            // no event is been found
            if (numEvents == 0) {
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByRange(dateFrom, dateTo);
            numTasks = 0;
        }
        
        //save and render
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, StringUtil.formatNumberOfTaskAndEventWithPuralizer(numTasks, numEvents)));
    }
    

    /**
     * clear all tasks and events of the date that exist in the database.
     * 
     * @param TodoListDB
     * @param givenDate
     *            null if parsing failed or Due date for Task or start date for Event
     * @param deleteAll
     *            true if no CalendarItem Type provided, false if "task" or "event" keyword found
     * @param isTask
     *            true if "task" keyword found, false if "event" keyword found
     */
    private void destroyBySelectedDate(TodoListDB db, LocalDateTime givenDate, boolean deleteAll, boolean isTask) {
        int numTasks = db.getTaskByDate(givenDate).size();
        int numEvents = db.getEventByDate(givenDate).size();
        
        // no tasks or events are been found
        if (numTasks == 0 && numEvents == 0) {
            Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
            return;
        }
        
        // task or event is not specified
        if (deleteAll) {
            db.destroyAllEventByDate(givenDate);
            db.destroyAllTaskByDate(givenDate);
        } else if (isTask) { //deleting task
            if (numTasks == 0) { //if no task is found
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllTaskByDate(givenDate);
            numEvents = 0;
        } else { //deleting events
            if (numEvents == 0) { //if no event is found
                Renderer.renderIndex(db, MESSAGE_CLEAR_NO_ITEM_FOUND);
                return;
            }
            db.destroyAllEventByDate(givenDate);
            numTasks = 0;
        }
        
        //save and render
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, StringUtil.formatNumberOfTaskAndEventWithPuralizer(numTasks, numEvents)));
    }

    /**
     * clear all tasks and events that exist in the database.
     * 
     * @param TodoListDB
     */
    private void destroyAll(TodoListDB db) {
        int totalCalendarItems = db.getAllEvents().size() + db.getAllTasks().size();
        db.destroyAllEvent();
        db.destroyAllTask();
        db.save();
        Renderer.renderIndex(db, String.format(MESSAGE_CLEAR_SUCCESS, totalCalendarItems));
    }
    
    /** ================ PARSING METHODS ================== **/

    /**
     * Extracts the intended COMMAND_WORD from parsedResult.
     * 
     * @param parsedResult
     * @return true if no String provided after command word, false if some String provided after command word 
     */ 
    private boolean parseExactClearCommand(Map<String, String[]> parsedResult) {
        return parsedResult.get("default")[RESULT] == null;
    }

     /**
     * Extracts the date without any keyword from parsedResult.
     * 
     * @param parsedResult
     * @return LocalDatetime date if found, or null if no date found
     */    
    private LocalDateTime parseDateWithNoKeyword(Map<String, String[]> parsedResult) {
        if (parsedResult.get("default").length == MAXIMUM_SIZE) { // user enter more than 1 date with no keyword
            if (parsedResult.get("default")[RESULT] != null) {
                return parseNatural(parsedResult.get("default")[RESULT]);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    /**
     * Parse a natural date into a LocalDateTime object.
     * 
     * @param natural
     * @return LocalDateTime object
     */
    private LocalDateTime parseNatural(String natural) {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(natural);
        Date date = null;
        try {
            date = groups.get(0).getDates().get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error!"); // TODO
            return null;
        }
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return DateUtil.floorDate(ldt);
    }
    
    /**
     * Extracts the natural dates from parsedResult.
     * 
     * @param parsedResult
     * @return { naturalOn, naturalFrom, naturalTo } or null if no date provided
     */
    private String[] parseDates(Map<String, String[]> parsedResult) {
        String naturalFrom = (String) null;
        String naturalTo = (String) null;
        String naturalOn = (String) null;
        
        if (parsedResult.get("time") == null) {
            if (parsedResult.get("timeFrom") != null) {
                naturalFrom = parsedResult.get("timeFrom")[RESULT];
            }
            if (parsedResult.get("timeTo") != null) {
                naturalTo = parsedResult.get("timeTo")[RESULT];
            }
        } else {
            naturalOn = parsedResult.get("time")[RESULT];
        }
        
        if (naturalFrom != null || naturalTo != null || naturalOn != null) {
            return new String[] { naturalOn, naturalFrom, naturalTo };
        } else {
            return null;
        }
    }
    
    /**
     * Extracts the intended CalendarItem type specify from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task or event is not specify, false if either Task or Event specify
     */
    private boolean parseDeleteAllType (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("eventType") != null);
    }
    
    /**
     * Extracts the intended CalendarItem type from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task, false if Event
     */
    private boolean parseIsTask (Map<String, String[]> parsedResult) {
        return parsedResult.get("eventType")[KEYWORD].contains("task");
    }

    /** ================ FORMATTING OF SUCCESS/ERROR MESSAGE ================== **/

    /**
     * display error message due to invalid clear command
     * 
     * @param input
     *            based on user input
     * @param parsedDate            
     *            the date entered by the user
     * @param deleteAll
     *            true if no CalendarItem type provided, isTask will be ignored
     * @param isTask
     *            true if task keyword, false if event keyword is provided         
     */
    private void displayErrorMessage(String input, String[] parsedDate, boolean deleteAll, boolean isTask) {
        String consoleDisplayMessage = String.format("You have entered : %s.",input);
        String commandLineMessage = COMMAND_WORD;
        if (!deleteAll) {
            if (isTask) {
                commandLineMessage = String.format("%s %s", commandLineMessage, "task");
            } else {
                commandLineMessage = String.format("%s %s", commandLineMessage, "event");
            }
        }
        if (parsedDate != null) {
            if (parsedDate[0] != null) {
                commandLineMessage = String.format("%s by <date>", commandLineMessage);
            } else if (parsedDate[1] != null && parsedDate[2] != null) {
                commandLineMessage = String.format("%s from <date> to <date>", commandLineMessage);
            } else if (parsedDate[1] != null) {
                commandLineMessage = String.format("%s from <date>", commandLineMessage);
            } else {
                commandLineMessage = String.format("%s to <date>", commandLineMessage);
            }
        }
        Renderer.renderDisambiguation(commandLineMessage, consoleDisplayMessage);
    }

}
```
###### /java/seedu/todo/controllers/FindController.java
``` java
 *
 */
public class FindController implements Controller {
    
    private static final String NAME = "Find";
    private static final String DESCRIPTION = "Find all tasks and events based on the provided keywords.\n" + 
    "This command will be search with non-case sensitive keywords.";
    private static final String COMMAND_SYNTAX = "find [name] or/and [on date]";
    private static final String COMMAND_WORD = "find";
    
    private static final String MESSAGE_LISTING_SUCCESS = "A total of %s found!";
    private static final String MESSAGE_LISTING_FAILURE = "No task or event found!";
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        return (input.toLowerCase().startsWith(COMMAND_WORD)) ? 1 : 0;
    }
    
    private static Map<String, String[]> getTokenDefinitions() {
        Map<String, String[]> tokenDefinitions = new HashMap<String, String[]>();
        tokenDefinitions.put("default", new String[] {"find"});
        tokenDefinitions.put("eventType", new String[] { "event", "events", "task", "tasks"});
        tokenDefinitions.put("status", new String[] { "complete" , "completed", "uncomplete", "uncompleted"});
        tokenDefinitions.put("time", new String[] { "at", "by", "on", "time" });
        tokenDefinitions.put("timeFrom", new String[] { "from" });
        tokenDefinitions.put("timeTo", new String[] { "to", "before" });
        tokenDefinitions.put("name", new String[] { "name" });
        tokenDefinitions.put("tag", new String [] { "tag" }); 
        return tokenDefinitions;
    }

    @Override
    public void process(String input) throws ParseException {
        
        Map<String, String[]> parsedResult;
        parsedResult = Tokenizer.tokenize(getTokenDefinitions(), input);
        
        HashSet<String> itemNameList = new HashSet<String>();
        HashSet<String> tagNameList = new HashSet<String>();
        
        parseExactFindCommand(parsedResult, itemNameList);
        
        parseName(parsedResult, itemNameList); //parse additional name enter by user
        parseTag(parsedResult, tagNameList);
        
        // Task or event?
        boolean listAll = parseListAllType(parsedResult);
        
        boolean isTask = true; //default
        //if listing all type , set isTask and isEvent true
        if (!listAll) {
            isTask = parseIsTask(parsedResult);
        }
        
        boolean listAllStatus = parseListAllStatus(parsedResult);
        boolean isCompleted = false; //default 
        //if listing all status, isCompleted will be ignored, listing both complete and uncomplete
        if (!listAllStatus) {
            isCompleted = !parseIsUncomplete(parsedResult);
        }
        
        String[] parsedDates = parseDates(parsedResult);
        if (parsedDates == null && listAllStatus == true && listAll == true 
                && itemNameList.size() == 0 && tagNameList.size() == 0) {
            //display error message, no keyword provided
            String disambiguationString = String.format("%s %s %s %s %s", COMMAND_WORD, "<name>" , 
                    "<complete/incomplete>", "<task/event>", "<tag tagName>");  
            Renderer.renderDisambiguation(disambiguationString, input);
            return ;
        }
        
        LocalDateTime dateOn = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        
        if (parsedDates != null) {
            String naturalOn = parsedDates[0];
            String naturalFrom = parsedDates[1];
            String naturalTo = parsedDates[2];
    
            // Parse natural date using Natty.
            dateOn = naturalOn == null ? null : parseNatural(naturalOn); 
            dateFrom = naturalFrom == null ? null : parseNatural(naturalFrom); 
            dateTo = naturalTo == null ? null : parseNatural(naturalTo);
        }
        //setting up view
        setupView(isTask, listAll, isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, tagNameList);
        
    }

    /**
     * Setting up the view 
     * 
     * @param isTask
     *            true if CalendarItem should be a Task, false if Event
     * @param isEvent
     *            true if CalendarItem should be a Event, false if Task  
     * @param listAll
     *            true if listing all type, isTask or isEvent are ignored          
     * @param isCompleted
     *            true if user request completed item
     * @param listAllStatus
     *            true if user did not request any status, isCompleted is ignored
     * @param dateOn
     *            Date if user specify for a certain date
     * @param dateFrom
     *            Due date for Task or start date for Event
     * @param dateTo
     *            End date for Event
     * @param itemNameList 
     *            String of Calendar Item name that user enter as keyword
     * @param tagNameList 
     *            String of Tag Name that user enter as keyword                      
     */
    private void setupView(boolean isTask, boolean listAll, boolean isCompleted,
            boolean listAllStatus, LocalDateTime dateOn, LocalDateTime dateFrom,
            LocalDateTime dateTo, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        TodoListDB db = TodoListDB.getInstance();
        List<Task> tasks = null;
        List<Event> events = null;
        // isTask and isEvent = true, list all type
        if (listAll) {
            //no event or task keyword found
            isTask = false;
            tasks = setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, tagNameList, db);
            events = setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, tagNameList, db);
        }
        
        if (isTask) {
            tasks = setupTaskView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, tagNameList, db);
        } else {
            events = setupEventView(isCompleted, listAllStatus, dateOn, dateFrom, dateTo, itemNameList, tagNameList, db);
        }
        
        // Update console message
        int numTasks = 0;
        int numEvents = 0;
        
        if (tasks != null) {
            numTasks = tasks.size();
        }
        
        if(events != null) {
            numEvents = events.size();
        }
        
        String consoleMessage = MESSAGE_LISTING_FAILURE;
        if (numTasks != 0 || numEvents != 0) {
            consoleMessage = String.format(MESSAGE_LISTING_SUCCESS, formatDisplayMessage(numTasks, numEvents));
        }
        
        Renderer.renderSelected(db, consoleMessage, tasks, events);
       
    }
    
    private String formatDisplayMessage (int numTasks, int numEvents) {
        if (numTasks != 0 && numEvents != 0) {
            return String.format("%s and %s", formatTaskMessage(numTasks), formatEventMessage(numEvents));
        } else if (numTasks != 0) {
            return formatTaskMessage(numTasks);
        } else {
            return formatEventMessage(numEvents);
        }
    }
    
    private String formatEventMessage (int numEvents) {
        return String.format("%d %s", numEvents, StringUtil.pluralizer(numEvents, "event", "events"));
    }
    
    private String formatTaskMessage (int numTasks) {
        return String.format("%d %s", numTasks, StringUtil.pluralizer(numTasks, "task", "tasks"));
    }
    
    private List<Event> setupEventView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn, 
            LocalDateTime dateFrom, LocalDateTime dateTo, HashSet<String> itemNameList, HashSet<String> tagNameList, TodoListDB db) {
        final LocalDateTime NO_DATE = null;
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus && itemNameList.size() == 0 && tagNameList.size() == 0) {
                System.out.println("error"); //TODO : Nothing found
                return null;
            } else if (listAllStatus && (itemNameList.size() != 0 || tagNameList.size() != 0)) {
                return db.getEventByName(db.getAllEvents(), itemNameList, tagNameList);
            }
            else if (isCompleted) {
                return db.getEventByRangeWithName(NO_DATE, LocalDateTime.now(), itemNameList, tagNameList);
            } else {
                return db.getEventByRangeWithName(LocalDateTime.now(), NO_DATE, itemNameList, tagNameList);
            } 
        } else if (dateOn != null) { //by keyword found
            return db.getEventbyDateWithName(dateOn, itemNameList, tagNameList);
        } else {
            return db.getEventByRangeWithName(dateFrom, dateTo, itemNameList, tagNameList);
        }
    }

    private List<Task> setupTaskView(boolean isCompleted, boolean listAllStatus, LocalDateTime dateOn, 
            LocalDateTime dateFrom, LocalDateTime dateTo, HashSet<String> itemNameList, HashSet<String> tagNameList, TodoListDB db) {
        if (dateFrom == null && dateTo == null && dateOn == null) {
            if (listAllStatus && itemNameList.size() == 0 && tagNameList.size() == 0) {
                System.out.println("error"); //TODO : Nothing found
                return null;
            } else {
                //getting all task by the name, dateFrom and dateTo will be null
                return db.getTaskByRangeWithName(dateFrom, dateTo, isCompleted, listAllStatus, itemNameList, tagNameList);
            }
        } else if (dateOn != null) { //by keyword found
            return db.getTaskByDateWithStatusAndName(dateOn, isCompleted, listAllStatus, itemNameList, tagNameList);
        } else {
            return db.getTaskByRangeWithName(dateFrom, dateTo, isCompleted, listAllStatus, itemNameList, tagNameList);
        }
    }
    
    /**
     * Extract the name keyword enter by the user and put in the hashset of name keywords
     * @param parsedResult
     */
    private void parseExactFindCommand(Map<String, String[]> parsedResult, HashSet<String> itemNameList) {
        if (parsedResult.get("default")[1] != null) {
            String[] result = parsedResult.get("default")[1].trim().split(" ");
            for (int i = 0; i < result.length; i ++) {
                itemNameList.add(result[i]);
            }
        } 
    }
    
    /**
     * Extract the name keyword enter by the user and put in the hashset of name keywords
     * @param parsedResult, tagNameList to store all the keywords
     */
    
    private void parseName(Map<String, String[]> parsedResult, HashSet<String> itemNameList) {
        if (parsedResult.get("name") != null && parsedResult.get("name")[1] != null) {
            String[] result = parsedResult.get("name")[1].trim().split(" ");
            for (int i = 0; i < result.length; i ++) {
                itemNameList.add(result[i].trim());
            }
        } 
    }
    
    /**
     * Extract the tag name keyword enter by the user and put in the hashset of tag keywords
     * @param parsedResult, tagNameList to store all the keywords
     */
    
    private void parseTag(Map<String, String[]> parsedResult, HashSet<String> tagNameList) {
        if (parsedResult.get("tag") != null && parsedResult.get("tag")[1] != null) {
            String[] result = parsedResult.get("tag")[1].trim().split(",");
            for (int i = 0; i < result.length; i ++) {
                tagNameList.add(result[i].trim());
            }
        } 
    }
    
    /**
     * Parse a natural date into a LocalDateTime object.
     * 
     * @param natural
     * @return LocalDateTime object
     */
    private LocalDateTime parseNatural(String natural) {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(natural);
        Date date = null;
        try {
            date = groups.get(0).getDates().get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error!"); // TODO
            return null;
        }
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return DateUtil.floorDate(ldt);
    }
    
    /**
     * Extracts the intended CalendarItem type specify from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task or event is not specify, false if either Task or Event specify
     */
    private boolean parseListAllType (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("eventType") != null);
    }
    
    /**
     * Extracts the intended status type specify from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task or event is not specify, false if either Task or Event specify
     */
    private boolean parseListAllStatus (Map<String, String[]> parsedResult) {
        return !(parsedResult.get("status") != null);
    }
    
    /**
     * Extracts the intended CalendarItem status from parsedResult.
     * 
     * @param parsedResult
     * @return true if uncomplete, false if complete
     */
    private boolean parseIsUncomplete (Map<String, String[]> parsedResult) {
        return parsedResult.get("status")[0].contains("uncomplete");
    }
    
    /**
     * Extracts the intended CalendarItem type from parsedResult.
     * 
     * @param parsedResult
     * @return true if Task, false if Event
     */
    private boolean parseIsTask (Map<String, String[]> parsedResult) {
        return parsedResult.get("eventType")[0].contains("task");
    }
    
    /**
     * Extracts the natural dates from parsedResult.
     * 
     * @param parsedResult
     * @return { naturalOn, naturalFrom, naturalTo }
     */
    private String[] parseDates(Map<String, String[]> parsedResult) {
        String naturalFrom = null;
        String naturalTo = null;
        String naturalOn = null;
        
        if (parsedResult.get("time") == null) {
            if (parsedResult.get("timeFrom") != null) {
                naturalFrom = parsedResult.get("timeFrom")[1];
            }
            if (parsedResult.get("timeTo") != null) {
                naturalTo = parsedResult.get("timeTo")[1];
            }
        } else {
            naturalOn = parsedResult.get("time")[1];
        }
        
        if (naturalFrom == null && naturalTo == null && naturalOn == null) {
            // no date found
            return null;
        }
        return new String[] { naturalOn, naturalFrom, naturalTo };
    }
    

}
```
###### /java/seedu/todo/controllers/TagController.java
``` java
 *
 */
public class TagController implements Controller {
    
    private static final String NAME = "Tag";
    private static final String DESCRIPTION = "Tag a task/event by listed index";
    private static final String COMMAND_SYNTAX = "tag <index> <tag name>";
    
    private static final String MESSAGE_TAG_SUCCESS = "Item has been tagged successfully.";
    private static final String MESSAGE_INDEX_OUT_OF_RANGE = "Could not tag task/event: Invalid index provided!";
    private static final String MESSAGE_MISSING_INDEX_AND_TAG_NAME = "Please specify the index of the item and the tag name to tag.";
    private static final String MESSAGE_INDEX_NOT_NUMBER = "Index has to be a number!";
    private static final String MESSAGE_TAG_NAME_NOT_FOUND = "Could not tag task/event: Tag name not provided!";
    private static final String MESSAGE_EXCEED_TAG_SIZE = "Could not tag task/event : Tag size exceed";
    private static final String MESSAGE_TAG_NAME_EXIST = "Could not tag task/event: Tag name already exist!";
    
    private static final int ITEM_INDEX = 0;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.toLowerCase().startsWith("tag") || input.startsWith("tags")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        // Extract param
        String param = args.replaceFirst("(tag|tags)", "").trim();
        
        if (param.length() <= 0) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_MISSING_INDEX_AND_TAG_NAME);
            return;
        }
        
        assert param.length() > 0;
        
        String[] parsedResult = parseParam(param);
        // Get index.
        int index = 0;
        String tagName = null;
        try {
            index = Integer.decode(parsedResult[ITEM_INDEX]);
            tagName = param.replaceFirst(parsedResult[ITEM_INDEX], "").trim();
        } catch (NumberFormatException e) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_INDEX_NOT_NUMBER);
            return;
        }
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        if (calendarItem == null) {
            Renderer.renderDisambiguation(String.format("tag %d", index), MESSAGE_INDEX_OUT_OF_RANGE);
            return;
        }
        
        // Check if tag name is provided
        if (parsedResult.length <= 1) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_TAG_NAME_NOT_FOUND);
            return;
        }
        
        assert calendarItem != null;
        
        boolean isTagNameDuplicate = calendarItem.getTagList().contains(tagName);
        
        if (isTagNameDuplicate) {
            Renderer.renderDisambiguation(String.format("tag %d", index), MESSAGE_TAG_NAME_EXIST);
            return;
        }
        
        boolean resultOfTagging = calendarItem.addTag(tagName);
        
        // Re-render
        if (resultOfTagging) {
            db.updateTagList(tagName);
            db.save();
            Renderer.renderIndex(db, MESSAGE_TAG_SUCCESS);
        } else {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_EXCEED_TAG_SIZE);
        }
    }

    private String[] parseParam(String param) {
        return param.split(" ");
    }

}
```
###### /java/seedu/todo/controllers/UntagController.java
``` java
 *
 */
public class UntagController implements Controller {
    
    private static final String NAME = "Untag";
    private static final String DESCRIPTION = "Untag a task/event by listed index";
    private static final String COMMAND_SYNTAX = "untag <index> <tag name>";
    
    private static final String MESSAGE_UNTAG_SUCCESS = "Item has been untagged successfully.";
    private static final String MESSAGE_INDEX_OUT_OF_RANGE = "Could not untag task/event: Invalid index provided!";
    private static final String MESSAGE_MISSING_INDEX_AND_TAG_NAME = "Please specify the index of the item and the tag name to untag.";
    private static final String MESSAGE_INDEX_NOT_NUMBER = "Index has to be a number!";
    private static final String MESSAGE_TAG_NAME_NOT_FOUND = "Could not untag task/event: Tag name not found!";
    private static final String MESSAGE_TAG_NAME_DOES_NOT_EXIST = "Could not untag task/event: Tag name does not exist!";
    
    private static final int ITEM_INDEX = 0;
    
    private static CommandDefinition commandDefinition =
            new CommandDefinition(NAME, DESCRIPTION, COMMAND_SYNTAX); 

    public static CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public float inputConfidence(String input) {
        // TODO
        return (input.toLowerCase().startsWith("untag")) ? 1 : 0;
    }

    @Override
    public void process(String args) {
        // TODO: Example of last minute work
        
        // Extract param
        String param = args.replaceFirst("untag", "").trim();
        
        if (param.length() <= 0) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_MISSING_INDEX_AND_TAG_NAME);
            return;
        }
        
        assert param.length() > 0;
        
        String[] parsedResult = parseParam(param);
        // Get index.
        int index = 0;
        String tagName = null;
        try {
            index = Integer.decode(parsedResult[ITEM_INDEX]);
            tagName = param.replaceFirst(parsedResult[ITEM_INDEX], "").trim();
        } catch (NumberFormatException e) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_INDEX_NOT_NUMBER);
            return;
        }
        
        // Get record
        EphemeralDB edb = EphemeralDB.getInstance();
        CalendarItem calendarItem = edb.getCalendarItemsByDisplayedId(index);
        TodoListDB db = TodoListDB.getInstance();
        
        if (calendarItem == null) {
            Renderer.renderDisambiguation(String.format("untag %d", index), MESSAGE_INDEX_OUT_OF_RANGE);
            return;
        }
        
        // Check if tag name is provided
        if (parsedResult.length <= 1) {
            Renderer.renderDisambiguation(COMMAND_SYNTAX, MESSAGE_TAG_NAME_NOT_FOUND);
            return;
        }
        
        assert calendarItem != null;
               
        boolean resultOfTagging = calendarItem.removeTag(tagName);
        
        // Re-render
        if (resultOfTagging) {
            //db.updateTagList(tagName);
            db.save();
            Renderer.renderIndex(db, MESSAGE_UNTAG_SUCCESS);
        } else {
            Renderer.renderDisambiguation(String.format("untag %d", index), MESSAGE_TAG_NAME_DOES_NOT_EXIST);
        }
    }

    private String[] parseParam(String param) {
        return param.split(" ");
    }

}
```
###### /java/seedu/todo/models/CalendarItem.java
``` java
     */
    public ArrayList<String> getTagList();
   
    /**
     * Add a new tag in the list of tag of the calendar item. 
     * 
     * @param tagName <String>
     * @return true if it has not reached the max tag list size, false if tag list already reach the max size
```
###### /java/seedu/todo/models/CalendarItem.java
``` java
     */
    public boolean addTag(String tagName);
    
    /**
     * Remove a existing tag in the tag list of tag of the calendar item. 
     * 
     * @param tagName <String>
     * @return true if tagName is removed successfully, false if failed to remove tagName due to unable to find
```
###### /java/seedu/todo/models/CalendarItem.java
``` java
     */
    public boolean removeTag(String tagName);

}
```
###### /java/seedu/todo/models/Event.java
``` java
    public ArrayList<String> getTagList() {
        return tagList;
    }

    @Override
```
###### /java/seedu/todo/models/Event.java
``` java
    public boolean addTag(String tagName) {
        if(tagList.size() < MAX_TAG_LIST_SIZE) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

    @Override
```
###### /java/seedu/todo/models/Event.java
``` java
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }

}
```
###### /java/seedu/todo/models/Task.java
``` java
    public ArrayList<String> getTagList() {
        return tagList;
    }

    @Override
```
###### /java/seedu/todo/models/Task.java
``` java
    public boolean addTag(String tagName) {
        if(tagList.size() < MAX_TAG_LIST_SIZE) {
            tagList.add(tagName);
            return true;
        } else {
            return false;
        }
    }

    @Override
```
###### /java/seedu/todo/models/Task.java
``` java
    public boolean removeTag(String tagName) {
        return tagList.remove(tagName);
    }

}
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public void destroyAllTask() {
        tasks = new LinkedHashSet<Task>();
    }
    
    /**
     * Destroys all Task in the DB by date
     * 
     * @return true if the save was successful, false otherwise
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public void destroyAllTaskByDate(LocalDateTime givenDate) {
        List<Task> selectedTasks = getTaskByDate(givenDate);
        tasks.removeAll(selectedTasks);
    }
    
    /**
     * Destroys all Task in the DB by a range of date
     * 
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public void destroyAllTaskByRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Task> selectedTasks = getTaskByRange(dateFrom, dateTo);
        tasks.removeAll(selectedTasks);
    }
    
    /**
     * Create a new Event in the DB and return it.<br>
     * <i>The new record is not persisted until <code>save</code> is explicitly
     * called.</i>
     * 
     * @return event
     * 
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public void destroyAllEvent() {
        events = new LinkedHashSet<Event>();
    }
    
    /**
     * Destroys all Event in the DB by date
     * 
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public void destroyAllEventByDate(LocalDateTime givenDate) {
        List<Event> selectedEvents = getEventByDate(givenDate);
        events.removeAll(selectedEvents);
    }
    
    /**
     * Destroys all Event in the DB by a range of date
     * 
     * 
     * @return true if the save was successful, false otherwise
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public void destroyAllEventByRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Event> selectedEvents = getEventByRange(dateFrom, dateTo);
        events.removeAll(selectedEvents);
    }
    
    
    
    /**
     * Gets the singleton instance of the TodoListDB.
     * 
     * @return TodoListDB
     * 
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */   
    public List<Event> getAllCurrentEvents() {
        ArrayList<Event> currentEvents = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (!currEvent.isOver()) {
                currentEvents.add(currEvent);
            }
        }
        return currentEvents;
    }
    
    /**
     * Get a list of Incomplete Tasks in the DB.
     * 
     * @return tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getIncompleteTasksAndTaskFromTodayDate() {
        ArrayList<Task> incompleteTasks = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        LocalDateTime todayDate = DateUtil.floorDate(LocalDateTime.now());
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            if (!currTask.isCompleted()) { //if incompleted
                incompleteTasks.add(currTask);
            } else {
                if (currTask.getDueDate() != null && DateUtil.floorDate(currTask.getDueDate()).compareTo(todayDate) >= 0) {
                    incompleteTasks.add(currTask);
                }
            }
        }
        return incompleteTasks;
    }

    /**
     * Filter a list of tasks with a provided item name list
     * 
     * @param tasks 
     *            a list of tasks after searching condition
     * @param itemNameList
     *            a list of keyword to search based on task name
     * @return tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getTaskByName(List<Task> tasks, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Task> taskByName = new ArrayList<Task>();
        Iterator<Task> tagIterator = tasks.iterator();
        Iterator<String> taskNameIterator = itemNameList.iterator();
        Iterator<String> tagNameIterator = tagNameList.iterator();
        boolean isFound = false;
        while (tagIterator.hasNext()) {
            Task currTask = tagIterator.next();
            String currTaskName = currTask.getName().toLowerCase();
            ArrayList<String> currTaskTagList = currTask.getTagList();
            String[] currTaskStartingNameBetweenSpace = currTaskName.split(" ");
            while(taskNameIterator.hasNext() || tagNameIterator.hasNext()) {
                String currentMatchingNameString = "";
                String currentMatchingTagNameString = "";
                
                try {
                    currentMatchingNameString = taskNameIterator.next().toLowerCase();
                } catch (NoSuchElementException e) {
                    currentMatchingNameString = null;
                }
                
                try {
                    currentMatchingTagNameString = tagNameIterator.next().toLowerCase();
                } catch  (NoSuchElementException e) {
                    currentMatchingTagNameString = null;
                }
                
                if (currentMatchingNameString != null && currentMatchingTagNameString != null) {
                    if (currTaskName.startsWith(currentMatchingNameString) || currTaskTagList.contains(currentMatchingTagNameString)){
                        taskByName.add(currTask);
                        isFound = true;
                    } else {
                        for (int i = 0; i < currTaskStartingNameBetweenSpace.length; i ++) {
                            if(currTaskStartingNameBetweenSpace[i].startsWith(currentMatchingNameString)) {
                                taskByName.add(currTask);
                                isFound = true;
                                break;
                            }
                        }
                    }
                } else if (currentMatchingNameString != null) {
                    if (currTaskName.startsWith(currentMatchingNameString)) {
                        taskByName.add(currTask);
                    } else {
                        for (int i = 0; i < currTaskStartingNameBetweenSpace.length; i ++) {
                            if(currTaskStartingNameBetweenSpace[i].startsWith(currentMatchingNameString)) {
                                taskByName.add(currTask);
                                isFound = true;
                                break;
                            }
                        }
                    }
                } else {
                    if (currTaskTagList.contains(currentMatchingTagNameString)) {
                        taskByName.add(currTask);
                    }
                }
                if (isFound) {
                    isFound = false;
                    break;
                }
            }
            tagNameIterator = tagNameList.iterator();
            taskNameIterator = itemNameList.iterator();
        }
        return taskByName;
    }

   /**
     * Get a list of Task in the DB filtered by status , name and one date.
     * 
     * @param givenDate
     *               LocalDateTime parsed by Natty
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if searching for both completed and incomplete tasks, false if either one is been specify
     * @param itemNameList
     *               list of String to be used to search against task name                                        
     * @return list of tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getTaskByDateWithStatusAndName(LocalDateTime givenDate, boolean isCompleted, 
            boolean listAllStatus, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Task> taskByDate = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (listAllStatus) {
                if (currTaskDueDate.equals(givenDate)) {
                    taskByDate.add(currTask);
                }
            } else {
                if (currTaskDueDate.equals(givenDate) && currTask.isCompleted() == isCompleted) {
                    taskByDate.add(currTask);
                }
            }
        }
        
        if (itemNameList.size() == 0) {
            return taskByDate;
        } else {
            return getTaskByName(taskByDate, itemNameList, tagNameList);
        }
    }

    /**
     * Get a list of Task in the DB filtered by a given date.
     * 
     * @param givenDate 
     *                LocalDateTime format parsed by Natty
     *                
     * @return list of tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getTaskByDate(LocalDateTime givenDate) {
        ArrayList<Task> taskByDate = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (currTaskDueDate.equals(givenDate)) {
                taskByDate.add(currTask);
            }
        }
        return taskByDate;
    }
    
    /**
     * Get a list of Task in the DB filtered by status and one date.
     * 
     * @param givenDate 
     *                  LocalDateTime parsed by Natty
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if searching for both completed and incomplete tasks, false if either one is been specify
     * @return list of tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getTaskByDateWithStatus(LocalDateTime givenDate, boolean isCompleted, boolean listAllStatus) {
        ArrayList<Task> taskByDate = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (listAllStatus) {
                if (currTaskDueDate.equals(givenDate)) {
                    taskByDate.add(currTask);
                }
            } else {
                if (currTaskDueDate.equals(givenDate) && currTask.isCompleted() == isCompleted) {
                    taskByDate.add(currTask);
                }
            }
        }
        return taskByDate;
    }

    /**
     * Get a list of Task in the DB filtered by status, name and range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if searching for both completed and incomplete tasks, false if either one is been specify
     *               
     * @return list of tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getTaskByRangeWithName (LocalDateTime fromDate , LocalDateTime toDate, boolean isCompleted, 
            boolean listAllStatus, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Task> taskByRange = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (listAllStatus) {
                if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0) {
                    taskByRange.add(currTask);
                }
            } else {
                if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0 && 
                        currTask.isCompleted() == isCompleted) {
                    taskByRange.add(currTask);
                }
            }
        }
        
        if (itemNameList.size() == 0 && tagNameList.size() == 0) {
            return taskByRange;
        } else {
            return getTaskByName(taskByRange, itemNameList, tagNameList);
        }
    }
    
    /**
     * Get a list of Task in the DB filtered by range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date   
     * @return list of tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getTaskByRange (LocalDateTime fromDate , LocalDateTime toDate) {
        ArrayList<Task> taskByRange = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0) {
                taskByRange.add(currTask);
            }
            
        }
        return taskByRange;
    }
    
    /**
     * Get a list of Task in the DB filtered by status and range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     * @param isCompleted
     *               true if searching for completed task, else false for incomplete tasks  
     * @param listAllStatus
     *               true if status of task is not been specify else false if complete or incomplete is been specify
     *                             
     * @return list of tasks
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Task> getTaskByRangeWithStatus (LocalDateTime fromDate , LocalDateTime toDate, 
            boolean isCompleted, boolean listAllStatus) {
        ArrayList<Task> taskByRange = new ArrayList<Task>();
        Iterator<Task> iterator = tasks.iterator();
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Task currTask = iterator.next();
            LocalDateTime currTaskDueDate = DateUtil.floorDate(currTask.getDueDate());
            if (currTaskDueDate == null) {
                currTaskDueDate = LocalDateTime.MIN;
            }
            
            if (listAllStatus) {
                if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0) {
                    taskByRange.add(currTask);
                }
            } else {
                if (currTaskDueDate.compareTo(fromDate) >= 0 && currTaskDueDate.compareTo(toDate) <= 0 && 
                        currTask.isCompleted() == isCompleted) {
                    taskByRange.add(currTask);
                }
            }
        }
        
        return taskByRange;
    }

    /**
     * Get a list of Event in the DB filtered by status, name and one date.
     * 
     * @param givenDate
     *                LocalDateTime parsed by Natty to be used to search event that start from this date
     * @return list of events
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Event> getEventbyDateWithName(LocalDateTime givenDate, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Event> eventByDate = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (DateUtil.floorDate(currEvent.getCalendarDT()).equals(givenDate)) {
                eventByDate.add(currEvent);
            }
        }

        if (itemNameList.size() == 0) {
            return eventByDate;
        } else {
            return getEventByName(eventByDate, itemNameList, tagNameList);
        }
    }
    
    /**
     * Get a list of Event in the DB filtered by status and one date.
     * 
     * @param givenDate
     *                LocalDateTime parsed by Natty to be used to search event that start from this date
     * @param itemNameList
     *                list of name keyword entered by user , to be used for match event name
     * @return list of events
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Event> getEventByDate(LocalDateTime givenDate) {
        ArrayList<Event> eventByDate = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (DateUtil.floorDate(currEvent.getCalendarDT()).equals(givenDate)) {
                eventByDate.add(currEvent);
            }
        }
        return eventByDate;
    }

   /**
     * Get a list of Event in the DB filtered by status, name and range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     * @param itemNameList
     *                list of name keyword entered by user , to be used for match event name
     * @return list of events
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Event> getEventByRangeWithName (LocalDateTime fromDate , LocalDateTime toDate, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Event> eventByRange = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        
        //if either date are null, set it to min or max
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (DateUtil.floorDate(currEvent.getStartDate()).compareTo(fromDate) >= 0 && 
                    DateUtil.floorDate(currEvent.getStartDate()).compareTo(toDate) <= 0) {
                eventByRange.add(currEvent);
            }
        }
        
        if (itemNameList.size() == 0) {
            return eventByRange;
        } else {
            return getEventByName(eventByRange, itemNameList, tagNameList);
        }
    }
    
    /**
     * Get a list of Event in the DB filtered by status and range of date.
     * 
     * @param fromDate
     *                LocalDateTime parsed by Natty to be used to search as Start Date
     * @param toDate
     *                LocalDateTime parsed by Natty to be used to search as END Date               
     *                           
     * @return list of events
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */
    public List<Event> getEventByRange (LocalDateTime fromDate , LocalDateTime toDate) {
        ArrayList<Event> eventByRange = new ArrayList<Event>();
        Iterator<Event> iterator = events.iterator();
        
        //if either date are null, set it to min or max
        if (fromDate == null) {
            fromDate = LocalDateTime.MIN;
        }
        
        if (toDate == null) {
            toDate = LocalDateTime.MAX;
        }
        while (iterator.hasNext()) {
            Event currEvent = iterator.next();
            if (DateUtil.floorDate(currEvent.getStartDate()).compareTo(fromDate) >= 0 && 
                    DateUtil.floorDate(currEvent.getStartDate()).compareTo(toDate) <= 0) {
                eventByRange.add(currEvent);
            }
        }
        return eventByRange;
    }

    /**
     * Filter a list of event with a given name list
     * 
     * @param events
     *                list of events to be used for filtering            
     * @param itemNameList
     *                list of name keyword entered by user , to be used for match event name
     * @return list of events
```
###### /java/seedu/todo/models/TodoListDB.java
``` java
     */    
    public List<Event> getEventByName(List<Event> events, HashSet<String> itemNameList, HashSet<String> tagNameList) {
        ArrayList<Event> eventByName = new ArrayList<Event>();
        Iterator<Event> eventIterator = events.iterator();
        Iterator<String> eventNameIterator = itemNameList.iterator();
        Iterator<String> tagNameIterator = tagNameList.iterator();
        while (eventIterator.hasNext()) {
            Event currEvent = eventIterator.next();
            String currEventName = currEvent.getName().toLowerCase();
            ArrayList<String> currEventTagList = currEvent.getTagList();
            while(eventNameIterator.hasNext() || tagNameIterator.hasNext()) {
                String currentMatchingNameString = "";
                String currentMatchingTagNameString = "";
                
                try {
                    currentMatchingNameString = eventNameIterator.next().toLowerCase();
                } catch (NoSuchElementException e) {
                    currentMatchingNameString = null;
                }
                
                try {
                    currentMatchingTagNameString = tagNameIterator.next().toLowerCase();
                } catch  (NoSuchElementException e) {
                    currentMatchingTagNameString = null;
                }
                
                if (currentMatchingNameString != null && currentMatchingTagNameString != null) {
                    if (currEventName.contains(currentMatchingNameString) || currEventTagList.contains(currentMatchingTagNameString)){
                        eventByName.add(currEvent);
                    }
                } else if (currentMatchingNameString != null) {
                    if (currEventName.contains(currentMatchingNameString)) {
                        eventByName.add(currEvent);
                    }
                } else {
                    if (currEventTagList.contains(currentMatchingTagNameString)) {
                        eventByName.add(currEvent);
                    }
                }
            }
            tagNameIterator = tagNameList.iterator();
            eventNameIterator = itemNameList.iterator();
        }
        return eventByName;
    }
}
```
