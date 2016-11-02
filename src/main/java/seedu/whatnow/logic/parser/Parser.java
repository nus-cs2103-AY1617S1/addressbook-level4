//@@author A0139772U
package seedu.whatnow.logic.parser;

import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatnow.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.commands.*;
import seedu.whatnow.model.tag.Tag;

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

    //@@author A0139128A-unused
    /**
     * Forward slashes are reserved for delimiter prefixes variable number of
     * tags
     */
    private static final Pattern TASK_DATA_ARGS_FORMAT = Pattern
            .compile("(?<name>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");

    /**
     * This arguments is for e.g. add task on today, add task on 18/10/2016
     */
    private static final Pattern TASK_MODIFIED_WITH_DATE_ARGS_FORMAT = Pattern.compile("(?<name>[^/]+)\\s"
            + "(.*?\\bon|by\\b.*?\\s)??" + "(?<dateArguments>([0-3]??[0-9][//][0-1]??[0-9][//][0-9]{4})??)"
            + "(?<tagArguments>(?: t/[^/]+)*)");

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
    private static final Pattern TIME_FORMAT = Pattern
            .compile("^(([1][0-2])|([0-9]))((:|\\.)([0-5][0-9]))??((am)|(pm))$");
    private static final Pattern TAG_FORMAT = Pattern.compile("^(t/)");

    private static final Pattern TODAY_OR_TOMORROW = Pattern.compile("^(today|tomorrow)$");
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

    private static final int INCREASE_DATE_BY_ONE_DAY = 1;
    private static final int INCREASE_DATE_BY_SEVEN_DAYS = 7;

    /**
     * String Constants
     */
    private static final String NONE = "none";

    private static final String DELIMITER_BLANK_SPACE = " ";
    private static final String DELIMITER_DOUBLE_QUOTATION_MARK = "\"";
    private static final String DELIMITER_FORWARD_SLASH = "/";

    private static final String BACK_SLASH = "\\";
    private static final String FORWARD_SLASH = "/";
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

    HashMap<String, Integer> MONTHS_IN_FULL = new HashMap<String, Integer>();
    HashMap<String, Integer> MONTHS_IN_SHORT = new HashMap<String, Integer>();

    //@@author A0139772U
    public Parser() {
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     * @throws ParseException
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

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

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
            return prepareFreeTimeCommand(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
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

    /**
     * Formats the input date to the DD/MM/YYYY format
     * @param date The date to be formatted in DD/MM/YYYY format but DD and MM may be single digit
     * @return the formatted date with a zero padded in front of single digits in DD and MM
     */
    private static String formatDate(String date) {
        String[] splitDate = date.split(FORWARD_SLASH);
        date = EMPTY_STRING;

        for (int i = 0; i < splitDate.length; i++) {
            date += splitDate[i].replaceAll(SINGLE_DIGIT, ZERO + splitDate[i]);
            if (i < splitDate.length - ONE) {
                date += FORWARD_SLASH;
            }
        }

        return date;
    }

    /**
     * Formats the input time to the colon format E.g. 12:30am, 4:20pm etc
     * 
     * @param time
     *            The time to be formatted
     * @param period
     *            The time period
     * @return the formatted time
     */
    private static String formatTime(String time, String period) {
        String[] splitTimePeriod = null;
        String[] splitTime = null;

        splitTimePeriod = time.toLowerCase().split(period);
        if (splitTimePeriod[TIME_WITHOUT_PERIOD].contains(TIME_COLON)) {
            splitTime = splitTimePeriod[TIME_WITHOUT_PERIOD].split(TIME_COLON);
        }

        if (splitTimePeriod[TIME_WITHOUT_PERIOD].contains(TIME_DOT)) {
            splitTime = splitTimePeriod[TIME_WITHOUT_PERIOD].split(BACK_SLASH + TIME_DOT);
        }

        time = (splitTime != null) ? splitTime[TIME_HOUR] : splitTimePeriod[TIME_WITHOUT_PERIOD];
        time += TIME_COLON;
        time += (splitTime != null) ? splitTime[TIME_MINUTES] : TIME_DEFAULT_MINUTES;
        time += period;

        return time;
    }

    /**
     * Calls the formatTime method to format the time
     * 
     * @param time
     *            The time to be formatted
     * @return the formatted time
     */
    private static String formatTime(String time) {
        if (time.contains(TIME_AM)) {
            time = formatTime(time, TIME_AM);
        } else {
            time = formatTime(time, TIME_PM);
        }

        return time;
    }

    private static HashMap<String, Integer> storeFullMonths(HashMap<String, Integer> months) {
        months.put("january", 1);
        months.put("february", 2);
        months.put("march", 3);
        months.put("april", 4);
        months.put("may", 5);
        months.put("june", 6);
        months.put("july", 7);
        months.put("august", 8);
        months.put("september", 9);
        months.put("october", 10);
        months.put("november", 11);
        months.put("december", 12);
        return months;
    }

    private static HashMap<String, Integer> storeShortMonths(HashMap<String, Integer> months) {
        months.put("jan", 1);
        months.put("feb", 2);
        months.put("mar", 3);
        months.put("apr", 4);
        months.put("may", 5);
        months.put("jun", 6);
        months.put("jul", 7);
        months.put("aug", 8);
        months.put("sep", 9);
        months.put("oct", 10);
        months.put("nov", 11);
        months.put("dec", 12);
        return months;
    }

    public String getDate(String argument) {
        if (TODAY_OR_TOMORROW.matcher(argument).find()) {
            return argument;
        } else if (DATE_WITH_SLASH_FORMAT.matcher(argument).find()) {
            return argument;
        } else {
            return null;
        }
    }

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

    public String getTime(String argument) {
        if (TIME_FORMAT.matcher(argument).find()) {
            return argument;
        } else {
            return null;
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        boolean validArgument = true;
        boolean hasDate = false;
        boolean hasSubDate = false;
        boolean hasTime = false;
        boolean hasRecurring = false;
        boolean hasRecurringEndDate = false;
        int numOfDate = 0;
        int numOfTime = 0;
        String name = null;
        String date = null;
        String startDate = null;
        String endDate = null;
        String time = null;
        String startTime = null;
        String endTime = null;
        String period = null;
        String endPeriod = null;
        Set<String> tags = new HashSet<String>();
        String[] argComponents = null;

        //Temporary variables
        String tempDate;

        MONTHS_IN_FULL = storeFullMonths(MONTHS_IN_FULL);
        MONTHS_IN_SHORT = storeShortMonths(MONTHS_IN_SHORT);    

        args = args.trim();

        // Check whether there are two quotation marks ""
        if (countOccurence(args, DELIMITER_DOUBLE_QUOTATION_MARK) != NUM_OF_QUOTATION_MARKS) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String[] arguments = args.split(DELIMITER_DOUBLE_QUOTATION_MARK);

        if (arguments.length < ADD_COMMAND_MIN_ARGUMENTS) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // E.g. add "Without date and time"
        if (arguments.length == ADD_COMMAND_MIN_ARGUMENTS) {
            name = arguments[ADD_COMMAND_DESCRIPTION_INDEX].trim();

            try {
                return new AddCommand(name, date, startDate, endDate, time, startTime, endTime, period, endPeriod, Collections.emptySet());
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            } catch (ParseException e) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        }

        name = arguments[ADD_COMMAND_DESCRIPTION_INDEX].trim();

        if (arguments.length > ADD_COMMAND_MIN_ARGUMENTS) {
            argComponents = arguments[arguments.length - ONE].trim().toLowerCase().split(DELIMITER_BLANK_SPACE);
        }

        for (int i = 0; i < argComponents.length; i++) {
            if (!hasDate && !hasSubDate && !hasTime && !hasRecurring && !hasRecurringEndDate) {
                if (KEYWORD_FOR_DATE.matcher(argComponents[i]).find()) {
                    hasDate = true;
                    if (KEYWORD_FOR_TIME.matcher(argComponents[i]).find()) {
                        hasTime = true;
                    }
                } else if (KEYWORD_FOR_TIME.matcher(argComponents[i]).find()) {
                    hasTime = true;
                    if (KEYWORD_FOR_END_OF_RECURRING.matcher(argComponents[i]).find()) {
                        hasDate = true;
                        hasRecurringEndDate = true;
                    } 
                } else if (KEYWORD_FOR_RECURRING.matcher(argComponents[i]).find()) {
                    hasRecurring = true;
                } else if (KEYWORD_FOR_END_OF_RECURRING.matcher(argComponents[i]).find()) {
                    hasDate = true;
                    hasRecurringEndDate = true;
                } else if (TAG_FORMAT.matcher(argComponents[i]).find()) {
                    String[] splitTag = argComponents[i].trim().split(DELIMITER_FORWARD_SLASH);
                    tags.add(splitTag[ADD_COMMAND_TAG_INDEX]);
                } else if (getDate(argComponents[i]) != null) {
                    tempDate = getDate(argComponents[i]);
                    numOfDate++;
                    if (numOfDate == ONE) {
                        date = tempDate;
                    } else if (numOfDate == TWO) {
                        startDate = date;
                        date = null;
                        endDate = tempDate;
                    }
                } else if (getSubDate(argComponents[i]) != null) {
                    tempDate = getSubDate(argComponents[i]);
                    hasSubDate = true;
                    numOfDate++;
                    if (numOfDate == ONE) {
                        date = tempDate;
                    } else if (numOfDate == TWO) {
                        startDate = date;
                        date = null;
                        endDate = tempDate;
                    }                
                } else if (getTime(argComponents[i]) != null) {
                    numOfTime++;
                    if (numOfTime == ONE) {
                        time = argComponents[i];
                        if (startDate != null & endDate != null) {
                            endTime = time;
                            time = null;
                            startTime = DEFAULT_START_TIME;
                        }
                    } else if (numOfTime == TWO) {
                        startTime = time;             
                        time = null;
                        endTime = argComponents[i];
                    }
                } else {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }

                continue;
            }

            if (hasDate) {
                if (hasRecurringEndDate && period != null) {    
                    if (getDate(argComponents[i]) != null) {
                        endPeriod = getDate(argComponents[i]);
                        hasRecurringEndDate = false;
                    } else if (getSubDate(argComponents[i]) != null) {                     
                        endPeriod = getSubDate(argComponents[i]);
                        hasSubDate = true;
                    } else {
                        validArgument = false;
                        hasRecurringEndDate = false;
                    }

                    hasTime = false;
                } else {
                    if (getDate(argComponents[i]) != null) {
                        tempDate = getDate(argComponents[i]);
                        numOfDate++;
                        if (numOfDate == ONE) {
                            date = tempDate;
                        } else if (numOfDate == TWO) {
                            startDate = date;
                            date = null;
                            endDate = tempDate;
                        }

                        hasTime = false;
                    } else if (getSubDate(argComponents[i]) != null) {
                        tempDate = getSubDate(argComponents[i]);
                        hasSubDate = true;
                        numOfDate++;
                        if (numOfDate == ONE) {
                            date = tempDate;
                        } else if (numOfDate == TWO) {
                            startDate = date;
                            date = null;
                            endDate = tempDate;
                        }

                        hasTime = false;
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
                if (hasRecurringEndDate && period != null) {  
                    tempDate = getSubDate(argComponents[i]);
                    if (tempDate != null) {
                        endPeriod += tempDate;
                        hasSubDate = true;               
                        if (YEAR.matcher(endPeriod).find()) {
                            hasSubDate = false;
                            hasRecurringEndDate = false;
                        }
                    } else {
                        validArgument = false;
                        hasSubDate = false;
                        hasRecurringEndDate = false;
                    }     
                } else {
                    tempDate = getSubDate(argComponents[i]);
                    if (tempDate != null) {
                        if (numOfDate == ONE) {
                            date += tempDate;
                        } else if (numOfDate == TWO) {
                            endDate += tempDate;
                        }

                        if (YEAR.matcher(tempDate).find()) {
                            hasSubDate = false;
                        }
                    } else {
                        validArgument = false;
                        hasSubDate = false;
                    }
                }
            }

            if (hasTime && !hasRecurringEndDate) {
                if (getTime(argComponents[i]) != null) {
                    numOfTime++;
                    if (numOfTime == ONE) {
                        time = argComponents[i];
                        if (startDate != null & endDate != null) {
                            endTime = time;
                            time = null;
                            startTime = DEFAULT_START_TIME;
                        }
                    } else if (numOfTime == TWO) {
                        startTime = time;
                        time = null;
                        endTime = argComponents[i];
                    } 
                } else {
                    validArgument = false;
                }

                hasTime = false;
            }

            if (hasRecurring) {
                if (DAYS_IN_FULL.matcher(argComponents[i]).find()) {
                    period = argComponents[i];
                } else if (DAYS_IN_FULL.matcher(argComponents[i]).find()) {
                    period = argComponents[i];
                } else if (RECURRING_PERIOD.matcher(argComponents[i]).find()) {
                    period = argComponents[i];
                } else {
                    validArgument = false;
                }

                hasRecurring = false;
            }

            if (!validArgument) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        }

        if (endPeriod != null) {
            endPeriod = formatDate(endPeriod);
        }

        if (date != null) {
            date = formatDate(date);
        } else if (startDate != null) {
            startDate = formatDate(startDate);
            endDate = formatDate(endDate);
        }

        if (time != null) {
            time = formatTime(time);
        } else if (startTime != null) {
            startTime = formatTime(startTime);
            endTime = formatTime(endTime);
        }

        if (startDate != null) {
            if (time != null) {
                startTime = time;
                time = null;
                endTime = DEFAULT_END_TIME;
            }
        }

        try {
            return new AddCommand(name, date, startDate, endDate, time, startTime, endTime, period, endPeriod, tags);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    //@@author A0139772U-reused
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
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
        if (args.equals(null))
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

        HashMap<String, Integer> fullMonths = new HashMap<String, Integer>();
        HashMap<String, Integer> shortMonths = new HashMap<String, Integer>();

        fullMonths = storeFullMonths(fullMonths);
        shortMonths = storeShortMonths(shortMonths);

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
                            arg += fullMonths.get(argComponents[i].toLowerCase());
                        } else if (numOfDate == TWO) {
                            arg += fullMonths.get(argComponents[i].toLowerCase());
                        }
                    } else if (MONTH_IN_SHORT.matcher(argComponents[i].toLowerCase()).find()) {
                        if (numOfDate == ONE) {
                            arg += shortMonths.get(argComponents[i].toLowerCase());
                        } else if (numOfDate == TWO) {
                            arg += shortMonths.get(argComponents[i].toLowerCase());
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
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareMarkUndone(String args) {
        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);
        if(argComponents.length > 1) {
            Optional<Integer> index = parseIndex(argComponents[INDEX]);
            System.out.println(argComponents[INDEX]);
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
     * @param type
     *            is todo/schedule, index is the index of item on the list,
     *            argType is description/tag/date/time
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

    //@@author A0139772U-reused
    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
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
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFreeTimeCommand(String args) {
        String date = args.trim();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        if (!(DATE_WITH_SLASH_FORMAT.matcher(date).find() || TODAY_OR_TOMORROW.matcher(date).find()
                || DAYS_IN_FULL.matcher(date).find() || DAYS_IN_SHORT.matcher(date).find())) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
        } else if (TODAY_OR_TOMORROW.matcher(date).find()) {
            if (date.equalsIgnoreCase("today")) {
                date = df.format(cal.getTime());
            } else {
                cal.add(Calendar.DATE, INCREASE_DATE_BY_ONE_DAY);
                date = df.format(cal.getTime());
            }
        } else if (DAYS_IN_FULL.matcher(date).find() || DAYS_IN_SHORT.matcher(date).find()) {
            switch (date) {
            case "mon":
                // fallthrough
            case "monday":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case "tue":
                // fallthrough
            case "tuesday":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case "wed":
                // fallthrough
            case "wednesday":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case "thur":
                // fallthrough
            case "thursday":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case "fri":
                // fallthrough
            case "friday":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case "sat":
                // fallthrough
            case "saturday":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            case "sun":
                // fallthrough
            case "sunday":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
            }
            if (cal.getTime().before(today.getTime())) {
                cal.add(Calendar.DATE, INCREASE_DATE_BY_SEVEN_DAYS);
            }
            date = df.format(cal.getTime());
        }
        return new FreeTimeCommand(date);
    }

}