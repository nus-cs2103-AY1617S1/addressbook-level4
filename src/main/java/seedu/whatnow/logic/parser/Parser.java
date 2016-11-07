//@@author A0139772U
package seedu.whatnow.logic.parser;

import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatnow.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.commands.*;
import seedu.whatnow.model.task.Name;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.TaskDate;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /**
     * One or more keywords separated by whitespace
     */
    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");

    //@@author A0126240W
    /**
     * Regular Expressions
     */
    private static final Pattern UPDATE_FORMAT = Pattern
            .compile("^((todo|schedule)\\s(\\d+)\\s(description|date|time|tag)($|\\s))");

    private static final Pattern DATE = Pattern.compile("^(([3][0-1])|([1-2][0-9])|([0]??[1-9]))$");
    private static final Pattern DATE_WITH_SUFFIX = Pattern
            .compile("^((([3][0-1])|([1-2][0-9])|([0]??[1-9]))(st|nd|rd|th))$");
    private static final Pattern MONTH_IN_FULL = Pattern
            .compile("^(january|february|march|april|may|june|july|august|september|october|november|december)$");
    private static final Pattern MONTH_IN_SHORT = Pattern
            .compile("^(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)$");
    private static final Pattern YEAR = Pattern.compile("^([0-9]{4})$");

    private static final Pattern DATE_WITH_SLASH_FORMAT = Pattern
            .compile("^(([3][0-1])|([1-2][0-9])|([0]??[1-9]))[/](([1][0-2])|([0]??[1-9]))[/]([0-9]{4})$");
    private static final Pattern DATE_WITH_HYPHEN_FORMAT = Pattern
            .compile("^(([3][0-1])|([1-2][0-9])|([0]??[1-9]))[-](([1][0-2])|([0]??[1-9]))[-]([0-9]{4})$");
    private static final Pattern DATE_WITH_DOT_FORMAT = Pattern
            .compile("^(([3][0-1])|([1-2][0-9])|([0]??[1-9]))[.](([1][0-2])|([0]??[1-9]))[.]([0-9]{4})$");
    private static final Pattern TIME_FORMAT = Pattern
            .compile("^(([1][0-2])|([0-9])|([0][1-9]))((:|\\.)([0-5][0-9]))??((am)|(pm))$");
    private static final Pattern TAG_FORMAT = Pattern.compile("^(t/)");

    private static final Pattern TODAY_OR_TOMORROW = Pattern.compile("^(today|tomorrow|tdy|tmr)$");
    private static final Pattern DAYS_IN_FULL = Pattern
            .compile("^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$");
    private static final Pattern DAYS_IN_SHORT = Pattern.compile("^(mon|tue|tues|wed|thu|thur|fri|sat|sun)$");
    private static final Pattern RECURRING_PERIOD = Pattern.compile("^(day|week|month|year)$");

    private static final Pattern KEYWORD_FOR_DATE = Pattern.compile("^((on)|(by)|(from)|(to))$");
    private static final Pattern KEYWORD_FOR_TIME = Pattern.compile("^((at)|(by)|(from)|(to)|(till))$");
    private static final Pattern KEYWORD_FOR_RECURRING = Pattern.compile("^(every)$");
    private static final Pattern KEYWORD_FOR_END_OF_RECURRING = Pattern.compile("^(until|till)$");

    /**
     * Integer Constants
     */
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final int TASK_TYPE = 0;
    private static final int INDEX = 1;
    private static final int ARG_TYPE = 2;
    private static final int ARG = 3;

    private static final int TIME_WITHOUT_PERIOD = 0;
    private static final int TIME_HOUR = 0;
    private static final int TIME_MINUTES = 1;

    private static final int ADD_COMMAND_DESCRIPTION_INDEX = 1;
    private static final int ADD_COMMAND_TAG_INDEX = 1;
    private static final int ADD_COMMAND_MIN_ARGUMENTS = 2;
    private static final int NUM_OF_QUOTATION_MARKS = 2;

    private static final int UPDATE_COMMAND_MIN_ARGUMENTS = 3;

    private static final int LIST_ARG = 0;

    private static final int CHANGE_LOCATION = 0;
    private static final int CHANGE_LOCATION_TO = 1;
    private static final int CHANGE_LOCATION_TO_PATH = 2;

    /**
     * String Constants
     */
    private static final String NONE = "none";

    private static final String DELIMITER_BLANK_SPACE = " ";
    private static final String DELIMITER_DOUBLE_QUOTATION_MARK = "\"";
    private static final String DELIMITER_FORWARD_SLASH = "/";
    private static final String DELIMITER_HYPHEN = "-";
    private static final String DELIMITER_DOT = "\\.";

    private static final String BACK_SLASH = "\\";
    private static final String FORWARD_SLASH = "/";
    private static final String HYPHEN = "-";
    private static final String DOT = ".";
    private static final String EMPTY_STRING = "";

    private static final String DATE_SUFFIX_REGEX = "(st|nd|rd|th)$";
    private static final String SINGLE_DIGIT = ("^(\\d)$");

    private static final String TIME_COLON = ":";
    private static final String TIME_DOT = ".";
    private static final String TIME_AM = "am";
    private static final String TIME_PM = "pm";
    private static final String TIME_DEFAULT_MINUTES = "00";
    private static final String DEFAULT_START_TIME = "12:00am";
    private static final String DEFAULT_END_TIME = "11:59pm";

    private static final String TASK_TYPE_FLOATING = "todo";
    private static final String TASK_TYPE_NON_FLOATING = "schedule";

    private static final String LIST_COMMAND_ARG_COMPLETED = "done";
    private static final String LIST_COMMAND_ARG_NOT_SPECIFIED = "";
    private static final String LIST_COMMAND_ARG_ALL_TASKS = "all";

    private static final String TASK_ARG_DESCRIPTION = "description";
    private static final String TASK_ARG_TAG = "tag";
    private static final String TASK_ARG_DATE = "date";
    private static final String TASK_ARG_TIME = "time";

    private static HashMap<String, Integer> MONTHS_IN_FULL = new HashMap<String, Integer>();
    private static HashMap<String, Integer> MONTHS_IN_SHORT = new HashMap<String, Integer>();
    
    private Task taskToAdd = new Task();
    Set<String> tags = new HashSet<String>();
    private boolean validArgument = true;
    private boolean hasDate = false;
    private boolean hasSubDate = false;
    private boolean hasTime = false;
    private boolean hasRecurring = false;
    private boolean hasRecurringEndDate = false;
    
    private int numOfDate = 0;
    private int numOfTime = 0;

    public Parser() {
        mapFullMonthsToMonthsInNumFormat();
        mapShortMonthsToMonthsInNumFormat();
    }

    //@@author A0139772U
    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     * @throws ParseException
     * @throws IllegalValueException 
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UpdateCommand.COMMAND_WORD:
            return prepareUpdate(arguments);

        case ChangeCommand.COMMAND_WORD:
            return prepareChange(arguments);

        case MarkDoneCommand.COMMAND_WORD:
            return prepareMarkDone(arguments);

        case MarkUndoneCommand.COMMAND_WORD:
            return prepareMarkUndone(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case FreeTimeCommand.COMMAND_WORD:
            return prepareFreeTime(arguments);

        case PinCommand.COMMAND_WORD:
            return preparePin(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    //@@author A0126240W
    private static void mapFullMonthsToMonthsInNumFormat() {
        MONTHS_IN_FULL.put("january", 1);
        MONTHS_IN_FULL.put("february", 2);
        MONTHS_IN_FULL.put("march", 3);
        MONTHS_IN_FULL.put("april", 4);
        MONTHS_IN_FULL.put("may", 5);
        MONTHS_IN_FULL.put("june", 6);
        MONTHS_IN_FULL.put("july", 7);
        MONTHS_IN_FULL.put("august", 8);
        MONTHS_IN_FULL.put("september", 9);
        MONTHS_IN_FULL.put("october", 10);
        MONTHS_IN_FULL.put("november", 11);
        MONTHS_IN_FULL.put("december", 12);
    }

    //@@author A0126240W
    private static void mapShortMonthsToMonthsInNumFormat() {
        MONTHS_IN_SHORT.put("jan", 1);
        MONTHS_IN_SHORT.put("feb", 2);
        MONTHS_IN_SHORT.put("mar", 3);
        MONTHS_IN_SHORT.put("apr", 4);
        MONTHS_IN_SHORT.put("may", 5);
        MONTHS_IN_SHORT.put("jun", 6);
        MONTHS_IN_SHORT.put("jul", 7);
        MONTHS_IN_SHORT.put("aug", 8);
        MONTHS_IN_SHORT.put("sep", 9);
        MONTHS_IN_SHORT.put("oct", 10);
        MONTHS_IN_SHORT.put("nov", 11);
        MONTHS_IN_SHORT.put("dec", 12);
    }

    //@@author A0126240W
    /**
     * Counts the number of occurrence of a substring in a string
     * 
     * @param str
     *            The given string
     * @param findStr
     *            The substring to look for in a given string
     * @return the number of occurrence
     */
    private static int countOccurence(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {
            lastIndex = str.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += findStr.length();
            }
        }

        return count;
    }
    
    //@@author A0126240W
    private void setupAddVariables() {
        validArgument = true;
        hasDate = false;
        hasSubDate = false;
        hasTime = false;
        hasRecurring = false;
        hasRecurringEndDate = false;
        
        numOfDate = 0;
        numOfTime = 0;
        
        taskToAdd.setTaskDate(null);
        taskToAdd.setStartDate(null);
        taskToAdd.setEndDate(null);
        taskToAdd.setTaskTime(null);
        taskToAdd.setStartTime(null);
        taskToAdd.setEndTime(null);
        taskToAdd.setPeriod(null);
        taskToAdd.setEndPeriod(null);
        tags = new HashSet<String>();
    }
    
    //@@author A0126240W
    private boolean isInvalidAddFormat(String[] arguments) {
        return arguments.length < ADD_COMMAND_MIN_ARGUMENTS;
    }
    
    //@@author A0126240W
    private boolean isTodoTask(String[] arguments) {
        return arguments.length == ADD_COMMAND_MIN_ARGUMENTS;
    }
    
    //@@author A0126240W
    private boolean isScheduleTask(String[] arguments) {
        return arguments.length > ADD_COMMAND_MIN_ARGUMENTS;
    }
    
    //@@author A0126240W
    private boolean isValidAddFormat(String[] arguments) {
        if (isInvalidAddFormat(arguments)) {
            return false;
        } else if (isTodoTask(arguments)) {
            return true;
        } else if (isScheduleTask(arguments)) {
            return true;
        } else {
            return false;
        }
    }

    //@@author A0126240W
    /**
     * Formats the input date to the DD/MM/YYYY format
     * @param date The date to be formatted in DD/MM/YYYY format but DD and MM may be single digit
     * @return the formatted date with a zero padded in front of single digits in DD and MM
     */
    public static String formatDate(String date) {
        String[] splitDate = null;
        String formattedDate = EMPTY_STRING;
        if (date.contains(FORWARD_SLASH)) {
            splitDate = date.split(DELIMITER_FORWARD_SLASH);
        } else if (date.contains(HYPHEN)) {
            splitDate = date.split(DELIMITER_HYPHEN);
        } else if (date.contains(DOT)) {
            splitDate = date.split(DELIMITER_DOT);
        } else {
            return date;
        }

        for (int i = 0; i < splitDate.length; i++) {
            formattedDate += splitDate[i].replaceAll(SINGLE_DIGIT, ZERO + splitDate[i]);
            if (i < splitDate.length - ONE) {
                formattedDate += FORWARD_SLASH;
            }
        }

        return formattedDate;
    }

    //@@author A0126240W
    /**
     * Formats the input time to the colon format E.g. 12:30am, 4:20pm etc
     * 
     * @param time
     *            The time to be formatted
     * @param period
     *            The time period
     * @return the formatted time
     */
    public static String formatTime(String time, String period) {
        String[] splitTimePeriod = null;
        String[] splitTime = null;
        String formattedTime;

        splitTimePeriod = time.toLowerCase().split(period);
        if (splitTimePeriod[TIME_WITHOUT_PERIOD].contains(TIME_COLON)) {
            splitTime = splitTimePeriod[TIME_WITHOUT_PERIOD].split(TIME_COLON);
        }

        if (splitTimePeriod[TIME_WITHOUT_PERIOD].contains(TIME_DOT)) {
            splitTime = splitTimePeriod[TIME_WITHOUT_PERIOD].split(BACK_SLASH + TIME_DOT);
        }
        
        formattedTime = (splitTime != null) ? splitTime[TIME_HOUR].replaceAll(SINGLE_DIGIT, ZERO + splitTime[TIME_HOUR]) : splitTimePeriod[TIME_WITHOUT_PERIOD].replaceAll(SINGLE_DIGIT, ZERO + splitTimePeriod[TIME_WITHOUT_PERIOD]);
        formattedTime += TIME_COLON;
        formattedTime += (splitTime != null) ? splitTime[TIME_MINUTES] : TIME_DEFAULT_MINUTES;
        formattedTime += period;

        return formattedTime;
    }

    //@@author A0126240W
    /**
     * Calls the formatTime method to format the time
     * 
     * @param time
     *            The time to be formatted
     * @return the formatted time
     */
    public static String formatTime(String time) {
        String formattedTime;
        if (time.contains(TIME_AM)) {
            formattedTime = formatTime(time, TIME_AM);
        } else {
            formattedTime = formatTime(time, TIME_PM);
        }

        return formattedTime;
    }

    //@@author A0126240W
    /**
     * Formats all the date and time in taskToAdd to the following format:
     * Date: dd/MM/yyyy
     * Time: HH:MM (With am/pm without any space)
     */
    private void formatDateTimeInTask() {
        if (taskToAdd.getEndPeriod() != null) {
            taskToAdd.setEndPeriod(formatDate(taskToAdd.getEndPeriod()));
        }

        if (taskToAdd.getTaskDate() != null) {
            taskToAdd.setTaskDate(formatDate(taskToAdd.getTaskDate()));
        } else if (taskToAdd.getStartDate() != null) {
            taskToAdd.setStartDate(formatDate(taskToAdd.getStartDate()));
            taskToAdd.setEndDate(formatDate(taskToAdd.getEndDate()));
        }

        if (taskToAdd.getTaskTime() != null) {
            taskToAdd.setTaskTime(formatTime(taskToAdd.getTaskTime()));
        } else if (taskToAdd.getStartTime() != null) {
            taskToAdd.setStartTime(formatTime(taskToAdd.getStartTime()));
            taskToAdd.setEndTime(formatTime(taskToAdd.getEndTime()));
        }
    }
    
    //@@author A0126240W
    private boolean hasKeyword(String argument) {
        if (KEYWORD_FOR_DATE.matcher(argument).find()) {
            hasDate = true;
            if (KEYWORD_FOR_TIME.matcher(argument).find()) {
                hasTime = true;
            }
        } else if (KEYWORD_FOR_TIME.matcher(argument).find()) {
            hasTime = true;
            if (KEYWORD_FOR_END_OF_RECURRING.matcher(argument).find()) {
                hasDate = true;
                hasRecurringEndDate = true;
            } 
        } else if (KEYWORD_FOR_RECURRING.matcher(argument).find()) {
            hasRecurring = true;
        } else if (KEYWORD_FOR_END_OF_RECURRING.matcher(argument).find()) {
            hasDate = true;
            hasRecurringEndDate = true;
        } else {
            return false;
        }
        
        return true;
    }
    
    //@@author A0126240W
    private boolean hasTags(String argument) {
        if (TAG_FORMAT.matcher(argument).find()) {
            return true;
        } else {
            return false;
        }
    }
    
    //@@author A0126240W
    public boolean hasDate(String argument) {
        if (TODAY_OR_TOMORROW.matcher(argument).find()) {
            return true;
        } else if (DAYS_IN_FULL.matcher(argument).find()) {
            return true;
        } else if (DAYS_IN_SHORT.matcher(argument).find()) {
            return true;
        } else if (DATE_WITH_SLASH_FORMAT.matcher(argument).find()) {
            return true;
        } else if (DATE_WITH_HYPHEN_FORMAT.matcher(argument).find()) {
            return true;
        } else if (DATE_WITH_DOT_FORMAT.matcher(argument).find()) {
            return true;
        } else {
            return false;
        }
    }
    
    //@@author A0126240W
    public boolean hasSubDate(String argument) {
        if (DATE.matcher(argument).find()) {
            return true;
        } else if (DATE_WITH_SUFFIX.matcher(argument).find()) {
            return true;
        } else if (MONTH_IN_FULL.matcher(argument).find()) {     
            return true;
        } else if (MONTH_IN_SHORT.matcher(argument).find()) {
            return true;
        } else if (YEAR.matcher(argument).find()) {
            return true;
        } else {
            return false;
        }
    }
    
    //@@author A0126240W
    public boolean isYear(String argument) {
        if (YEAR.matcher(argument).find()) {
            return true;
        } else {
            return false;
        }
    }
    
    //@@author A0126240W
    public boolean hasTime(String argument) {
        if (TIME_FORMAT.matcher(argument).find()) {
            return true;
        } else {
            return false;
        }
    }
    
    //@@author A0126240W
    private boolean hasNoEndDateForRecurring() {
        return taskToAdd.getPeriod() != null && taskToAdd.getEndPeriod() == null;
    }
    
    //@@author A0126240W
    private boolean hasNoEndTime() {
        return taskToAdd.getStartDate() != null && taskToAdd.getTaskTime() != null;
    }
    
    //@@author A0126240W
    public String getSubDate(String argument) {
        if (DATE.matcher(argument).find()) {
            return argument + FORWARD_SLASH;
        } else if (DATE_WITH_SUFFIX.matcher(argument).find()) {
            return argument.replaceAll(DATE_SUFFIX_REGEX, EMPTY_STRING) + FORWARD_SLASH;
        } else if (MONTH_IN_FULL.matcher(argument).find()) {     
            return MONTHS_IN_FULL.get(argument) + FORWARD_SLASH;
        } else if (MONTH_IN_SHORT.matcher(argument).find()) {
            return MONTHS_IN_SHORT.get(argument) + FORWARD_SLASH;
        } else if (YEAR.matcher(argument).find()) {
            return argument;
        } else {
            return null;
        }
    }
    
    //@@author A0126240W
    public boolean setDate(int numOfDate, String date) {
        if (numOfDate == ONE) {
            taskToAdd.setTaskDate(date);
        } else if (numOfDate == TWO) {
            taskToAdd.setStartDate(taskToAdd.getTaskDate());
            taskToAdd.setTaskDate(null);;
            taskToAdd.setEndDate(date);
        } else {
            return false;
        }
        
        return true;
    }
    
    //@@author A0126240W
    public boolean setTime(int numOfTime, String time) {
        if (numOfTime == ONE) {
            taskToAdd.setTaskTime(time);
            if (taskToAdd.getStartDate() != null && taskToAdd.getEndDate() != null) {
                setDefaultStartTime();
            }
        } else if (numOfTime == TWO) {
            if (taskToAdd.getTaskTime() == null) {
                taskToAdd.setStartTime(taskToAdd.getEndTime());
                taskToAdd.setEndTime(time);
            } else {
                taskToAdd.setStartTime(taskToAdd.getTaskTime());             
                taskToAdd.setTaskTime(null);
                taskToAdd.setEndTime(time);
            }
        } else {
            return false;
        }
        
        return true;
    }
    
    //@@author A0126240W
    private void setDefaultStartTime() {
        taskToAdd.setEndTime(taskToAdd.getTaskTime());
        taskToAdd.setTaskTime(null);
        taskToAdd.setStartTime(DEFAULT_START_TIME);
    }
    
    //@@author A0126240W
    private void setDefaultEndTime() {
        taskToAdd.setStartTime(taskToAdd.getTaskTime());
        taskToAdd.setTaskTime(null);
        taskToAdd.setEndTime(DEFAULT_END_TIME);
    }
 
    //@@author A0126240W
    private boolean setRecurring(String period) {
        if (DAYS_IN_FULL.matcher(period).find()) {
            taskToAdd.setPeriod(period);
        } else if (DAYS_IN_FULL.matcher(period).find()) {
            taskToAdd.setPeriod(period);
        } else if (RECURRING_PERIOD.matcher(period).find()) {
            taskToAdd.setPeriod(period);
        } else {
            return false;
        }
        
        return true;
    }
    
    //@@author A0126240W
    private void setTags(String argument) {
        String[] splitTag = argument.trim().split(DELIMITER_FORWARD_SLASH);
        tags.add(splitTag[ADD_COMMAND_TAG_INDEX]);
    }
    
    //@@author A0126240W
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     * @throws IllegalValueException 
     */
    private Command prepareAdd(String args) {
        setupAddVariables();
        String[] arguments = null;
        String[] argComponents = null;
        args = args.trim();

        // Check whether there are two quotation marks ""
        if (countOccurence(args, DELIMITER_DOUBLE_QUOTATION_MARK) != NUM_OF_QUOTATION_MARKS) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        arguments = args.split(DELIMITER_DOUBLE_QUOTATION_MARK);
        
        if (!isValidAddFormat(arguments)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            taskToAdd.setName(new Name(arguments[ADD_COMMAND_DESCRIPTION_INDEX].trim()));
            
            if (isTodoTask(arguments)) {
                return new AddCommand(taskToAdd, Collections.emptySet());
            }
            
            argComponents = arguments[arguments.length - ONE].trim().split(DELIMITER_BLANK_SPACE);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        for (int i = 0; i < argComponents.length; i++) {
            if (!hasDate && !hasSubDate && !hasTime && !hasRecurring && !hasRecurringEndDate) {
                if (hasDate(argComponents[i].toLowerCase())) {
                    argComponents[i] = argComponents[i].toLowerCase();
                    numOfDate++;
                    if (!setDate(numOfDate, argComponents[i])) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                } else if (hasSubDate(argComponents[i].toLowerCase())) {
                    argComponents[i] = argComponents[i].toLowerCase();
                    hasSubDate = true;
                    numOfDate++;
                    if (!setDate(numOfDate, getSubDate(argComponents[i]))) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    } 
                } else if (hasTime(argComponents[i].toLowerCase())) {
                    argComponents[i] = argComponents[i].toLowerCase();
                    numOfTime++;
                    if (!setTime(numOfTime, argComponents[i])) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                } else if (hasTags(argComponents[i])) {
                    setTags(argComponents[i]);
                } else {
                    if (!hasKeyword(argComponents[i].toLowerCase())) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                }

                continue;
            }
            
            argComponents[i] = argComponents[i].toLowerCase();

            if (hasDate) {
                if (hasRecurringEndDate && taskToAdd.getPeriod() != null) {    
                    if (hasDate(argComponents[i])) {
                        taskToAdd.setEndPeriod(argComponents[i]);
                        hasRecurringEndDate = false;
                    } else if (hasSubDate(argComponents[i])) {                     
                        taskToAdd.setEndPeriod(getSubDate(argComponents[i]));
                        hasSubDate = true;
                    } else {
                        validArgument = false;
                        hasRecurringEndDate = false;
                    }

                    hasTime = false;
                } else {
                    if (hasDate(argComponents[i])) {
                        hasTime = false;
                        numOfDate++;
                        if (!setDate(numOfDate, argComponents[i])) {
                            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                        }
                    } else if (hasSubDate(argComponents[i])) {
                        hasSubDate = true;
                        hasTime = false;
                        numOfDate++;
                        if (!setDate(numOfDate, getSubDate(argComponents[i]))) {
                            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                        }
                    } else if (!hasTime) {
                        validArgument = false;
                    }

                    hasRecurringEndDate = false;
                }

                hasDate = false;
                if (hasSubDate) {
                    continue;
                }              
            }

            if (hasSubDate) {
                if (hasRecurringEndDate && taskToAdd.getPeriod() != null) {  
                    if (hasSubDate(argComponents[i])) {
                        hasSubDate = true;  
                        taskToAdd.setEndPeriod(taskToAdd.getEndPeriod() + getSubDate(argComponents[i]));            
                        if (isYear(getSubDate(argComponents[i]))) {
                            hasSubDate = false;
                            hasRecurringEndDate = false;
                        }
                    } else {
                        validArgument = false;
                        hasSubDate = false;
                        hasRecurringEndDate = false;
                    }     
                } else {                   
                    if (hasSubDate(argComponents[i])) {
                        if (numOfDate == ONE) {
                            taskToAdd.setTaskDate(taskToAdd.getTaskDate() + getSubDate(argComponents[i]));
                        } else if (numOfDate == TWO) {
                            taskToAdd.setEndDate(taskToAdd.getEndDate() + getSubDate(argComponents[i]));;
                        } else {
                            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                        }
                        
                        if (isYear(getSubDate(argComponents[i]))) {
                            hasSubDate = false;
                        }
                    } else {
                        validArgument = false;
                        hasSubDate = false;
                    }
                }
            }

            if (hasTime && !hasRecurringEndDate) {
                if (hasTime(argComponents[i])) {
                    numOfTime++;
                    if (!setTime(numOfTime, argComponents[i])) {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                    }
                } else {
                    validArgument = false;
                }

                hasTime = false;
            }

            if (hasRecurring) {
                hasRecurring = false;
                if (!setRecurring(argComponents[i])) {
                    validArgument = false;
                }
            }

            if (!validArgument) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        }

        formatDateTimeInTask();
        
        if (hasNoEndTime()) {
            setDefaultEndTime();
        }
        
        if (hasNoEndDateForRecurring()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_RECURRING_NO_END_DATE));
        }

        try {
            return new AddCommand(taskToAdd, tags);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    //@@author A0141021H
    /**
     * Parses arguments in the context of the change data file location command.
     * 
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareChange(String args) {
        String[] argComponents= args.trim().split(DELIMITER_BLANK_SPACE);
        if(argComponents[CHANGE_LOCATION].equals("location") && argComponents[CHANGE_LOCATION_TO].equals("to")){
            return new ChangeCommand(argComponents[CHANGE_LOCATION_TO_PATH]);
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        }
    }

    //@@author A0139772U
    private Command prepareList(String args) {
        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);
        String listArg = argComponents[LIST_ARG];
        if (!isListCommandValid(listArg)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(listArg);
    }

    private boolean isListCommandValid(String listArg) {
        return listArg.equals(LIST_COMMAND_ARG_COMPLETED) || listArg.equals(LIST_COMMAND_ARG_NOT_SPECIFIED)
                || listArg.equals(LIST_COMMAND_ARG_ALL_TASKS);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);
        if (argComponents.length < 2) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(argComponents[TASK_TYPE], index.get());
    }

    //@@author A0126240W
    /**
     * Parses arguments in the context of the update task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     * @throws ParseException
     */
    private Command prepareUpdate(String args) {
        if (args == null)
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        if (!UPDATE_FORMAT.matcher(args.trim().toLowerCase()).find()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);

        if (argComponents.length < UPDATE_COMMAND_MIN_ARGUMENTS)
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        String taskType = argComponents[TASK_TYPE];
        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        String argType = argComponents[ARG_TYPE];
        String arg = "";

        int numOfDate = 0;
        int numOfTime = 0;

        if (argComponents.length > UPDATE_COMMAND_MIN_ARGUMENTS) {
            for (int i = ARG; i < argComponents.length; i++) {
                if (argType.toUpperCase().compareToIgnoreCase(TASK_ARG_DESCRIPTION) == 0) {
                    if (argComponents[i].toUpperCase().compareToIgnoreCase(NONE) == 0)
                        return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT + UpdateCommand.MESSAGE_USAGE);

                    arg += argComponents[i] + DELIMITER_BLANK_SPACE;
                } else if (argType.toUpperCase().compareToIgnoreCase(TASK_ARG_DATE) == 0) {
                    if (DATE_WITH_SLASH_FORMAT.matcher(argComponents[i]).find()) {
                        numOfDate++;
                        if (numOfDate == ONE)
                            arg = argComponents[i];
                        else if (numOfDate == TWO)
                            arg += DELIMITER_BLANK_SPACE + argComponents[i];
                    } else if (TODAY_OR_TOMORROW.matcher(argComponents[i].toLowerCase()).find()) {
                        numOfDate++;
                        if (numOfDate == ONE)
                            arg = argComponents[i];
                        else if (numOfDate == TWO)
                            arg += DELIMITER_BLANK_SPACE + argComponents[i];
                    } else if (DATE.matcher(argComponents[i].toLowerCase()).find()) {
                        numOfDate++;
                        if (numOfDate == ONE) {
                            arg = argComponents[i].toLowerCase();
                            arg += FORWARD_SLASH;
                        } else if (numOfDate == TWO) {
                            arg += DELIMITER_BLANK_SPACE + argComponents[i].toLowerCase();
                            arg += FORWARD_SLASH;
                        }
                    } else if (DATE_WITH_SUFFIX.matcher(argComponents[i].toLowerCase()).find()) {
                        numOfDate++;
                        if (numOfDate == ONE) {
                            arg = argComponents[i].toLowerCase().replaceAll(DATE_SUFFIX_REGEX, EMPTY_STRING);
                            arg += FORWARD_SLASH;
                        } else if (numOfDate == TWO) {
                            arg = argComponents[i].toLowerCase();
                            arg += FORWARD_SLASH;
                        }
                    } else if (MONTH_IN_FULL.matcher(argComponents[i].toLowerCase()).find()) {
                        if (numOfDate == ONE) {
                            arg += MONTHS_IN_FULL.get(argComponents[i].toLowerCase());
                        } else if (numOfDate == TWO) {
                            arg += MONTHS_IN_FULL.get(argComponents[i].toLowerCase());
                        }
                    } else if (MONTH_IN_SHORT.matcher(argComponents[i].toLowerCase()).find()) {
                        if (numOfDate == ONE) {
                            arg += MONTHS_IN_SHORT.get(argComponents[i].toLowerCase());
                        } else if (numOfDate == TWO) {
                            arg += MONTHS_IN_FULL.get(argComponents[i].toLowerCase());
                        }
                    } else if (YEAR.matcher(argComponents[i].toLowerCase()).find()) {
                        if (numOfDate == ONE) {
                            arg += FORWARD_SLASH;
                            arg += argComponents[i].toLowerCase();
                        } else if (numOfDate == TWO) {
                            arg += FORWARD_SLASH;
                            arg += argComponents[i].toLowerCase();
                        }
                    } else if (argComponents[i].toUpperCase().compareToIgnoreCase(NONE) == 0) {
                        arg = null;
                    }
                } else if (argType.toUpperCase().compareToIgnoreCase(TASK_ARG_TIME) == 0) {
                    if (TIME_FORMAT.matcher(argComponents[i]).find()) {
                        numOfTime++;
                        if (numOfTime == ONE)
                            arg = argComponents[i];
                        else if (numOfTime == TWO)
                            arg += DELIMITER_BLANK_SPACE + argComponents[i];
                    } else if (argComponents[i].toUpperCase().compareToIgnoreCase(NONE) == 0)
                        arg = null;
                } else if (argType.toUpperCase().compareToIgnoreCase(TASK_ARG_TAG) == 0) {
                    if (argComponents[i].toUpperCase().compareToIgnoreCase(NONE) == 0)
                        arg = null;
                    else
                        arg += argComponents[i] + DELIMITER_BLANK_SPACE;
                }
            }

            try {
                return new UpdateCommand(taskType, index.get(), argType, arg);
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            } catch (ParseException pe) {
                return new IncorrectCommand(pe.getMessage());
            } catch (NoSuchElementException nsee) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
            }
        }

        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        if (!isValidUpdateCommandFormat(taskType, index.get(), argType)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        try {
            return new UpdateCommand(taskType, index.get(), argType, arg);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException pe) {
            return new IncorrectCommand(pe.getMessage());
        }
    }

    //@@author A0139772U
    /**
     * Parses arguments in the context of the markDone task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareMarkDone(String args) {
        String[] argComponents = args.trim().split(" ");
        if(argComponents.length > 1) {
            Optional<Integer> index = parseIndex(argComponents[INDEX]);
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
            }
            return new MarkDoneCommand(argComponents[TASK_TYPE], index.get());
        } else {
            if(argComponents.length == 1 && !argComponents[0].equals("")) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_MISSING_INDEX));
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_MISSING_TASKTYPE_AND_INDEX));
            }
        }
    }

    //@@author A0141021H
    /**
     * Parses arguments in the context of the markUndone task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMarkUndone(String args) {
        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);
        if(argComponents.length > 1) {
            Optional<Integer> index = parseIndex(argComponents[INDEX]);
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneCommand.MESSAGE_MISSING_INDEX));
            }
            return new MarkUndoneCommand(argComponents[TASK_TYPE], index.get());
        } else {
            if(argComponents.length == 1 && !argComponents[0].equals("")) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneCommand.MESSAGE_MISSING_INDEX));
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneCommand.MESSAGE_MISSING_TASKTYPE_AND_INDEX));
            }
        }
    }

    //@@author A0139772U
    /**
     * Checks that the command format is valid
     * 
     * @param type is todo/schedule, index is the index of item on the list,
     * argType is description/tag/date/time
     */
    private boolean isValidUpdateCommandFormat(String type, int index, String argType) {
        if (!(type.compareToIgnoreCase(TASK_TYPE_FLOATING) == 0
                || type.compareToIgnoreCase(TASK_TYPE_NON_FLOATING) == 0)) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        if (!(argType.compareToIgnoreCase(TASK_ARG_DESCRIPTION) == 0 || argType.compareToIgnoreCase(TASK_ARG_TAG) == 0
                || argType.compareToIgnoreCase(TASK_ARG_DATE) == 0
                || argType.compareToIgnoreCase(TASK_ARG_TIME) == 0)) {
            return false;
        }
        return true;
    }

    //@@author A0139772U
    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

    // @@author A0139772U
    /**
     * Parses arguments in the context of the free time command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFreeTime(String args) {
        if (args == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
        }

        String date = args.trim();
        try {
            if (TODAY_OR_TOMORROW.matcher(date).find() || DAYS_IN_FULL.matcher(date).find() || DAYS_IN_SHORT.matcher(date).find()) {
                date = TaskDate.formatDayToDate(date);
            } else if (!date.equals(EMPTY_STRING)){
                date = TaskDate.formatDateToStandardDate(date);
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
            }
            return new FreeTimeCommand(date);
        } catch (ParseException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ie) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
        }
    }

    private Command preparePin(String args) {
        if (args == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_MISSING_DATE));
        }

        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);
        if(argComponents.length == 1) {
            if(argComponents[ZERO].equals(TASK_ARG_DATE)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_MISSING_DATE));
            } else if(argComponents[ZERO].equals(TASK_ARG_TAG)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_MISSING_TAG));
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
            }
        } else if(argComponents.length <= 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
        } else {
            String type = argComponents[ZERO];
            String keyword = argComponents[ONE];
            return new PinCommand(type, keyword);
        }
    }
}