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

    /**
     * Forward slashes are reserved for delimiter prefixes
     * variable number of tags
     */
    private static final Pattern TASK_DATA_ARGS_FORMAT = Pattern.compile("(?<name>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)"); 

    /**
     * This arguments is for e.g. add task on today, add task on 18/10/2016
     */
    private static final Pattern TASK_MODIFIED_WITH_DATE_ARGS_FORMAT = Pattern.compile("(?<name>[^/]+)\\s" + "(.*?\\bon|by\\b.*?\\s)??" +
            "(?<dateArguments>([0-3]??[0-9][//][0-1]??[0-9][//][0-9]{4})??)" + "(?<tagArguments>(?: t/[^/]+)*)");	

    /**
     * Regular Expressions
     */
    private static final Pattern DATE_SUFFIX = Pattern.compile("(st|nd|rd|th)$");
    private static final Pattern DATE = Pattern.compile("^(([3][0-1])|([1-2][0-9])|([0]??[1-9]))$");
    private static final Pattern DATE_WITH_SUFFIX = Pattern.compile("^((([3][0-1])|([1-2][0-9])|([0]??[1-9]))(st|nd|rd|th))$");
    private static final Pattern MONTH_IN_FULL = Pattern.compile("^(january|february|march|april|may|june|july|august|september|october|november|december)$");
    private static final Pattern MONTH_IN_SHORT = Pattern.compile("^(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)$");
    private static final Pattern YEAR = Pattern.compile("^([0-9]{4})$");
    
    private static final Pattern DATE_WITH_SLASH_FORMAT = Pattern.compile("^(([3][0-1])|([1-2][0-9])|([0]??[1-9]))[/](([1][0-2])|([0]??[1-9]))[/]([0-9]{4})$");
    private static final Pattern TIME_FORMAT = Pattern.compile("^(([1][0-2])|([0-9]))((:|\\.)([0-5][0-9]))??((am)|(pm))$");
    private static final Pattern TAG_FORMAT = Pattern.compile("^(t/)");

    private static final Pattern TODAY_OR_TOMORROW = Pattern.compile("^(today|tomorrow)$");
    private static final Pattern DAYS_IN_FULL = Pattern.compile("^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$");
    private static final Pattern DAYS_IN_SHORT = Pattern.compile("^(mon|tue|tues|wed|thu|thur|fri|sat|sun)$");

    private static final Pattern KEYWORD_FOR_DATE = Pattern.compile("^((on)|(by)|(from)|(to))$");
    private static final Pattern KEYWORD_FOR_TIME = Pattern.compile("^((at)|(by)|(from)|(to)|(till))$");

    /**
     * Integer Constants
     */
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final int TASK_TYPE = 0;
    private static final int INDEX = 1;
    private static final int ARG_TYPE = 2;
    private static final int ARG = 3;

    private static final int DESCRIPTION = 1;
    private static final int TAG = 1;
    private static final int MIN_NUM_OF_VALID_PARTS_IN_ADD_ARGUMENTS = 2;
    private static final int NUM_OF_QUOTATION_MARKS = 2;

    private static final int TIME_WITHOUT_PERIOD = 0;
    private static final int TIME_HOUR = 0;
    private static final int TIME_MINUTES = 1;

    private static final int LIST_ARG = 0;

    private static final int CHANGE_LOCATION = 0;
    private static final int CHANGE_LOCATION_TO = 1;
    private static final int CHANGE_LOCATION_TO_PATH = 2;

    /**
     * String Constants
     */
    private static final String DELIMITER_BLANK_SPACE = " ";
    private static final String DELIMITER_DOUBLE_QUOTATION_MARK = "\"";
    private static final String DELIMITER_FORWARD_SLASH = "/";

    private static final String BACK_SLASH = "\\";
    private static final String FORWARD_SLASH = "/";
    private static final String EMPTY_STRING = "";
    
    private static final String DATE_SUFFIX_STRING = "(st|nd|rd|th)$";

    private static final String TIME_COLON = ":";
    private static final String TIME_DOT = ".";
    private static final String TIME_AM = "am";
    private static final String TIME_PM = "pm";
    private static final String TIME_DEFAULT_MINUTES = "00";

    private static final String TASK_TYPE_FLOATING = "todo";
    private static final String TASK_TYPE_NON_FLOATING = "schedule";

    private static final String LIST_COMMAND_ARG_COMPLETED = "done";
    private static final String LIST_COMMAND_ARG_NOT_SPECIFIED = "";
    private static final String LIST_COMMAND_ARG_ALL_TASKS = "all";

    private static final String TASK_ARG_DESCRIPTION = "description";
    private static final String TASK_ARG_TAG = "tag";
    private static final String TASK_ARG_DATE = "date";
    private static final String TASK_ARG_TIME = "time";

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
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

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Counts the number of occurrence of a substring in a string
     * @param str The given string
     * @param findStr The substring to look for in a given string
     * @return the number of occurrence
     */
    public static int countOccurence(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1) {
            lastIndex = str.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count++;
                lastIndex += findStr.length();
            }
        }

        return count;
    }

    /**
     * Formats the time to the colon format E.g. 12:30am, 4:20pm etc
     * @param time The time to be formatted
     * @param period The time period
     * @return the formatted time
     */
    public static String formatTime(String time, String period) {
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
     * @param time The time to be formatted
     * @return the formatted time
     */
    public static String formatTime(String time) {
        if (time.contains(TIME_AM)) {
            time = formatTime(time, TIME_AM);
        } else {
            time = formatTime(time, TIME_PM);
        }

        return time;
    }
    
    public static HashMap<String, Integer> storeFullMonths(HashMap<String, Integer> months) {
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
    
    public static HashMap<String, Integer> storeShortMonths(HashMap<String, Integer> months) {
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

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        boolean validArgument = true;
        boolean hasDate = false;
        boolean hasTime = false;
        int numOfDate = 0;
        int numOfTime = 0;
        String name = null;
        String date = null;
        String startDate = null;
        String endDate = null;
        String time = null;
        String startTime = null;
        String endTime = null;
        Set<String> tags = new HashSet<String>();
        String[] additionalArgs = null;
        HashMap<String, Integer> fullMonths = new HashMap<String, Integer>();
        HashMap<String, Integer> shortMonths = new HashMap<String, Integer>();
        
        fullMonths = storeFullMonths(fullMonths);
        shortMonths = storeShortMonths(shortMonths);    

        args = args.trim();

        //final Matcher matcher = TASK_MODIFIED_WITH_DATE_ARGS_FORMAT.matcher(args.trim());
        // Validate the format of the arguments
        /*if (!TASK_DATA_ARGS_FORMAT.matcher(args).find() && !TASK_MODIFIED_WITH_DATE_ARGS_FORMAT.matcher(args).find()){
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}*/

        // Check whether there are two quotation marks ""
        if (countOccurence(args, DELIMITER_DOUBLE_QUOTATION_MARK) != NUM_OF_QUOTATION_MARKS) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String[] arguments = args.split(DELIMITER_DOUBLE_QUOTATION_MARK);

        if (arguments.length < MIN_NUM_OF_VALID_PARTS_IN_ADD_ARGUMENTS) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // E.g. add "Without date and time"
        if (arguments.length == MIN_NUM_OF_VALID_PARTS_IN_ADD_ARGUMENTS) {
            name = arguments[DESCRIPTION].trim();

            try {
                return new AddCommand(name, date, startDate, endDate, time, startTime, endTime, Collections.emptySet());
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            } catch (ParseException e) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        }

        name = arguments[DESCRIPTION].trim();

        if (arguments.length > MIN_NUM_OF_VALID_PARTS_IN_ADD_ARGUMENTS) {
            additionalArgs = arguments[arguments.length - 1].trim().split(DELIMITER_BLANK_SPACE);
        }

        for (int i = 0; i < additionalArgs.length; i++) {
            if (KEYWORD_FOR_DATE.matcher(additionalArgs[i].toLowerCase()).find()) {
                hasDate = true;
                continue;
            }	        
            else if (KEYWORD_FOR_TIME.matcher(additionalArgs[i].toLowerCase()).find()) {
                hasTime = true;
                continue;
            }
            else if (TAG_FORMAT.matcher(additionalArgs[i]).find()) {
                String[] splitTag = additionalArgs[i].trim().split(DELIMITER_FORWARD_SLASH);
                tags.add(splitTag[TAG]);
                continue;
            } else if (!hasDate && TODAY_OR_TOMORROW.matcher(additionalArgs[i].toLowerCase()).find()) {
                numOfDate++;
                if (numOfDate == ONE) {
                    date = additionalArgs[i].toLowerCase();
                } else if (numOfDate == TWO) {
                    startDate = date;
                    date = null;
                    endDate = additionalArgs[i].toLowerCase();
                }
                continue;
            } else if (!hasTime && TIME_FORMAT.matcher(additionalArgs[i].toLowerCase()).find()) {
                numOfTime++;
                if (numOfTime == ONE) {
                    time = additionalArgs[i].toLowerCase();
                    if (startDate != null & endDate != null) {
                        endTime = time;
                        time = null;
                        startTime = "12:00am";
                    }
                } else if (numOfTime == TWO) {       
                    startTime = time;      
                    time = null;
                    endTime = additionalArgs[i].toLowerCase();
                }
                continue;
            } else if (!hasDate && !hasTime) {
                validArgument = false;
            }

            if (hasDate) {
                if (DATE_WITH_SLASH_FORMAT.matcher(additionalArgs[i]).find()) {
                    numOfDate++;
                    if (numOfDate == ONE) {
                        date = additionalArgs[i];
                    } else if (numOfDate == TWO) {
                        startDate = date;
                        date = null;
                        endDate = additionalArgs[i];
                    }
                    hasDate = false;
                } else if (TODAY_OR_TOMORROW.matcher(additionalArgs[i].toLowerCase()).find()) {
                    numOfDate++;
                    if (numOfDate == ONE) {
                        date = additionalArgs[i].toLowerCase();
                    } else if (numOfDate == TWO) {
                        startDate = date;
                        date = null;
                        endDate = additionalArgs[i].toLowerCase();
                    }
                    hasDate = false;
                } else if (TIME_FORMAT.matcher(additionalArgs[i].toLowerCase()).find()) {
                    numOfTime++;
                    if (numOfTime == ONE) {
                        time = additionalArgs[i].toLowerCase();
                        if (startDate != null & endDate != null) {
                            endTime = time;
                            time = null;
                            startTime = "12:00am";
                        }
                    } else if (numOfTime == TWO) {
                        startTime = time;                    
                        time = null;
                        endTime = additionalArgs[i].toLowerCase();       
                    }
                    hasDate = false;
                } else if (DATE.matcher(additionalArgs[i].toLowerCase()).find()) {
                    numOfDate++;
                    if (numOfDate == ONE) {
                        date = additionalArgs[i].toLowerCase();
                        date += FORWARD_SLASH;
                    } else if (numOfDate == TWO) {
                        startDate = date;
                        date = null;
                        endDate = additionalArgs[i].toLowerCase();
                        endDate += FORWARD_SLASH;
                    } 
                } else if (DATE_WITH_SUFFIX.matcher(additionalArgs[i].toLowerCase()).find()) {
                    numOfDate++;
                    if (numOfDate == ONE) {
                        date = additionalArgs[i].toLowerCase().replaceAll(DATE_SUFFIX_STRING, EMPTY_STRING);
                        date += FORWARD_SLASH;
                    } else if (numOfDate == TWO) {
                        startDate = date;
                        date = null;
                        endDate = additionalArgs[i].toLowerCase();
                        endDate += FORWARD_SLASH;
                    } 
                } else if (MONTH_IN_FULL.matcher(additionalArgs[i].toLowerCase()).find()) {     
                    if (numOfDate == ONE) {
                        date += fullMonths.get(additionalArgs[i].toLowerCase());
                    } else if (numOfDate == TWO) {
                        endDate += fullMonths.get(additionalArgs[i].toLowerCase());
                    }
                } else if (MONTH_IN_SHORT.matcher(additionalArgs[i].toLowerCase()).find()) {
                    if (numOfDate == ONE) {
                        date += shortMonths.get(additionalArgs[i].toLowerCase());               
                    } else if (numOfDate == TWO) {
                        endDate += shortMonths.get(additionalArgs[i].toLowerCase());     
                    } 
                } else if (YEAR.matcher(additionalArgs[i].toLowerCase()).find()) {
                    if (numOfDate == ONE) {
                        date += FORWARD_SLASH;
                        date += additionalArgs[i].toLowerCase();
                    } else if (numOfDate == TWO) {
                        endDate += FORWARD_SLASH;
                        endDate += additionalArgs[i].toLowerCase();
                    } 
                    hasDate = false;
                } else {
                    hasDate = false;
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            }

            if (hasTime) {
                if (TIME_FORMAT.matcher(additionalArgs[i].toLowerCase()).find()) {
                    numOfTime++;
                    if (numOfTime == ONE) {
                        time = additionalArgs[i].toLowerCase();
                        if (startDate != null & endDate != null) {
                            endTime = time;
                            time = null;
                            startTime = "12:00am";
                        }
                    } else if (numOfTime == TWO) {
                        startTime = time;             
                        time = null;
                        endTime = additionalArgs[i].toLowerCase();
                    }
                } else {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }

                hasTime = false;
            }

            if (!validArgument) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
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
                endTime = "11:59pm";
            }
        }

        try {
            return new AddCommand(name, date, startDate, endDate, time, startTime, endTime, tags);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

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

    /**
     * Parses arguments in the context of the change data file location command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareChange(String args) {
        String[] argComponents= args.trim().split(" ");
        if(argComponents[CHANGE_LOCATION].equals("location") && argComponents[CHANGE_LOCATION_TO].equals("to")){
            return new ChangeCommand(argComponents[CHANGE_LOCATION_TO_PATH]);
        }
        else{
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        }
    }

    private Command prepareList(String args) {
        String[] argComponents= args.trim().split(DELIMITER_BLANK_SPACE);
        String listArg = argComponents[LIST_ARG];
        if (!isListCommandValid(listArg)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
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
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);
        if (argComponents.length < 2) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(argComponents[TASK_TYPE], index.get());
    }

    /**
     * Parses arguments in the context of the update task command.
     *
     * @param args full command args string
     * @return the prepared command
     * @throws ParseException 
     */
    private Command prepareUpdate(String args) {
        if (args.equals(null))
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        String[] argComponents= args.trim().split(" ");

        if (argComponents.length < 3)
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        String type = argComponents[TASK_TYPE];
        String argType = argComponents[ARG_TYPE];
        String arg = "";
        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        for (int i = ARG; i < argComponents.length; i++) {
            if (argComponents[i].toUpperCase().compareToIgnoreCase("none") == 0 && argType.toUpperCase().compareToIgnoreCase("description") != 0) {
                arg = argComponents[i];
                try {
                    return new UpdateCommand(type, index.get(), argType, arg);
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                } catch (ParseException pe) {
                    return new IncorrectCommand(pe.getMessage());
                }
            }
            arg += argComponents[i] + " ";
        }
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        if (!isValidUpdateCommandFormat(type, index.get(), argType)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        try {
            return new UpdateCommand(type, index.get(), argType, arg);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException pe) {
            return new IncorrectCommand(pe.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the markDone task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMarkDone(String args) {
        String[] argComponents = args.trim().split(" ");
        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
        }
        return new MarkDoneCommand(argComponents[TASK_TYPE], index.get());
    }

    /**
     * Parses arguments in the context of the markUndone task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMarkUndone(String args) {
        String[] argComponents = args.trim().split(DELIMITER_BLANK_SPACE);
        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneCommand.MESSAGE_USAGE));
        }
        return new MarkUndoneCommand(argComponents[TASK_TYPE], index.get());
    }

    /**
     * Checks that the command format is valid
     * @param type is todo/schedule, index is the index of item on the list, argType is description/tag/date/time
     */
    private boolean isValidUpdateCommandFormat(String type, int index, String argType) {
        if (!(type.compareToIgnoreCase(TASK_TYPE_FLOATING) == 0 || type.compareToIgnoreCase(TASK_TYPE_NON_FLOATING) == 0)) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        if (!(argType.compareToIgnoreCase(TASK_ARG_DESCRIPTION) == 0 || argType.compareToIgnoreCase(TASK_ARG_TAG) == 0 
                || argType.compareToIgnoreCase(TASK_ARG_DATE) == 0 || argType.compareToIgnoreCase(TASK_ARG_TIME) == 0)) {
            return false;
        }
        return true;
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }
}