package seedu.address.logic.parser;


import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.TimePeriod;


//@@author A0139655U
/**
 * Parses user input for add and edit commands
 */
public class CommandParserHelper {
    
    private final Logger logger = LogsCenter.getLogger(CommandParserHelper.class);
    
    private static final String MESSAGE_REPEATED_START_TIME = "Repeated start times are not allowed.";
    private static final String MESSAGE_REPEATED_END_TIME = "Repeated end times are not allowed.";
    private static final String MESSAGE_INVALID_MATCHER = "Matcher is unable to find a match.";
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    
    private static final String TASK_NAME = "taskName";
    private static final String START_DATE_FORMAT_ONE = "startDateFormatOne";
    private static final String START_DATE_FORMAT_TWO = "startDateFormatTwo";
    private static final String START_DATE_FORMAT_THREE = "startDateFormatThree";
    private static final String END_DATE_FORMAT_ONE = "endDateFormatOne";
    private static final String END_DATE_FORMAT_TWO = "endDateFormatTwo";
    private static final String END_DATE_FORMAT_THREE = "endDateFormatThree";
    private static final String END_DATE_FORMAT_FOUR = "endDateFormatFour";
    private static final String END_DATE_FORMAT_FIVE = "endDateFormatFive";
    private static final String END_DATE_FORMAT_SIX = "endDateFormatSix";
    private static final String RATE = "rate";
    private static final String TIME_PERIOD = "timePeriod";
    private static final String PRIORITY = "priority";

    private static final String REGEX_OPEN_BRACE = "(";
    private static final String REGEX_CASE_IGNORE = "?i:";
    private static final String REGEX_CLOSE_BRACE = ")";
    private static final String REGEX_GREEDY_SELECT = ".*?";
    private static final String REGEX_ESCAPE_CHARACTER = "\"";
    
    /** greedily captures the taskName until it reaches the following keyword */
    private static final String REGEX_NAME = "?<taskName>.*?";
    
    /** used for concatenating keyword to REGEX_NAME */
    private static final String REGEX_ADDITIONAL_KEYWORD = "(?:" + "(?: from )" + "|(?: at )" + "|(?: start )"
            + "|(?: by )" + "|(?: to )" + "|(?: end )" + ")";
    
    /** 
     * greedily captures everything after the keyword (from, at, start, by, to, end), 
     * until it reaches the next regex expression or end of input
     */
    private static final String REGEX_FIRST_DATE = "(?:" + "(?: from (?<" + START_DATE_FORMAT_ONE + ">.*?))"
            + "|(?: at (?<" + START_DATE_FORMAT_TWO + ">.*?))" + "|(?: start (?<" + START_DATE_FORMAT_THREE + ">.*?))"
            + "|(?: by (?<" + END_DATE_FORMAT_ONE + ">.*?))" + "|(?: to (?<" + END_DATE_FORMAT_TWO + ">.*?))"
            + "|(?: end (?<" + END_DATE_FORMAT_THREE + ">.*?))" + ")";
    
    /** 
     * greedily captures everything after the keyword (from, at, start, by, to, end), 
     * until it reaches the next regex expression or end of input
     */
    private static final String REGEX_SECOND_DATE = "(?:" + "(?: from (?:.*?))"
            + "|(?: at (?:.*?))" + "|(?: start (?:.*?))"
            + "|(?: by (?<" + END_DATE_FORMAT_FOUR + ">.*?))" + "|(?: to (?<" + END_DATE_FORMAT_FIVE + ">.*?))"
            + "|(?: end (?<" + END_DATE_FORMAT_SIX + ">.*?))" + ")";
    
    /** 
     * greedily captures everything after the keyword (repeat every, -), 
     * until it reaches the next regex expression or end of input
     */
    private static final String REGEX_RECURRENCE_AND_PRIORITY = "(?: repeat every (?<rate>\\d+)?(?<timePeriod>.*?))?"
            + "(?: -(?<priority>.*?))?";

    /** beginning of regex in the event that input is escaped */
    private static final String REGEX_OPEN_BRACE_CASE_IGNORE_NAME_ESCAPE = REGEX_OPEN_BRACE + REGEX_CASE_IGNORE
            + REGEX_ESCAPE_CHARACTER + REGEX_OPEN_BRACE + REGEX_NAME + REGEX_CLOSE_BRACE + REGEX_ESCAPE_CHARACTER;
    
    /** beginning of regex in the event that input is not escaped */
    private static final String REGEX_OPEN_BRACE_CASE_IGNORE_NAME = REGEX_OPEN_BRACE + REGEX_CASE_IGNORE
            + REGEX_OPEN_BRACE + REGEX_NAME;
    
    /** used for concatenating keyword to REGEX_NAME */
    private static final String REGEX_KEYWORD_GREEDY_SELECT = REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT;
    
    /** end of regex; only concatenated at the end after other required regex expressions have been concatenated. */
    private static final String REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE = REGEX_RECURRENCE_AND_PRIORITY
            + REGEX_CLOSE_BRACE;
    
    private Pattern pattern;
    private Matcher matcher;

    /**
     * Returns a HashMap containing Optional<String> values of
     * taskName, startDate, endDate, rate, timePeriod and priority.
     *
     * @param args  User input of task to add.
     * @return      Values of taskName, startDate, endDate, rate, timePeriod and priority.
     * @throws IllegalValueException    If args does not match the matcher.
     */
    public HashMap<String, Optional<String>> prepareAdd(String args) throws IllegalValueException {
        assert args != null;
        OptionalStringTask task = new OptionalStringTask();
        
        if (args.contains(REGEX_ESCAPE_CHARACTER)) {
            logger.log(Level.FINEST, "In prepareAdd, args contains escape character");
            generateMatcherForEscapeInput(args, task);
        } else {
            logger.log(Level.FINEST, "In prepareAdd, args does not contain escape character");
            generateMatcherForNonEscapeInput(args, task);
        }
        
        assignTaskParameters(task);
        return mapContainingVariables(task);
    }
    
    //@@author 
    public HashMap<String, Optional<String>> prepareEdit(String args) throws IllegalValueException {
        assert args != null;
        OptionalStringTask task = new OptionalStringTask();
        
        if (args.contains(REGEX_ESCAPE_CHARACTER)) {
            generateMatcherForEscapeInput(args, task);
        } else {
            generateMatcherForNonEscapeInput(args, task);
        }
        
        assignTaskParametersEdit(task);
        return mapContainingVariables(task);
    }
    
    //@@author A0139655U
    /**
     * Generates the matcher for the escaped input args.
     *
     * @param args  User input of task to add.
     * @param task  Object to store values for startDate and endDate.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForEscapeInput(String args, OptionalStringTask task) throws IllegalValueException {
        assert args != null && task != null;
        
        String argsMinusTaskName = generateArgsMinusTaskName(args);
        int numberOfKeywords = generateNumberOfKeywords(argsMinusTaskName);
        logger.log(Level.FINEST, "Number of keywords in \"" + args + "\" = " + numberOfKeywords);
        String regex = REGEX_OPEN_BRACE_CASE_IGNORE_NAME_ESCAPE;
        
        if (numberOfKeywords == ZERO) {
            generateMatcherForNoKeywordEscape(args, regex);
        } else if (numberOfKeywords == ONE) {
            generateMatcherForOneKeywordEscape(args, regex);
            matcherSetStartOrEndDate(task);
        } else if (numberOfKeywords >= TWO) {
            generateMatcherForTwoKeywordsEscape(args, regex);
            matcherSetStartOrEndDate(task);
            matcherSetEndDate(task);
        }
    }
    
    /**
     * Generates the matcher for the input args.
     *
     * @param args  User input of task to add.
     * @param task  Object to store values for startDate and endDate.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForNonEscapeInput(String args, OptionalStringTask task) throws IllegalValueException {
        assert args != null && task != null;
        
        int numberOfKeywords = generateNumberOfKeywords(args);
        logger.log(Level.FINEST, "Number of keywords in \"" + args + "\" = " + numberOfKeywords);
        String regex = generateStartOfRegex(numberOfKeywords);

        if (numberOfKeywords == ZERO) {
            generateMatcherForNoKeyword(args, regex);
        } else if (numberOfKeywords == ONE) {
            generateMatcherForOneKeyword(args, regex);
            matcherSetStartOrEndDate(task);
            if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
                tryGenerateMatcherForNoKeyword(args, task, regex);
            }
        } else if (numberOfKeywords >= TWO) {
            generateMatcherForTwoKeywords(args, regex);
            matcherSetStartOrEndDate(task);
            if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
                tryGenerateMatcherForOneOrNoKeyword(args, task, regex);
            } else { 
                matcherSetEndDate(task);
            }
        }
    }

    /**
     * Checks whether matcher for one keyword or zero keyword matches the input args.
     *
     * @param args  User input of task to add.
     * @param task  Object to store values for startDate and endDate.
     * @param regex Used to generate matcher
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void tryGenerateMatcherForOneOrNoKeyword(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        tryMatcherForOneKeyword(args, task, regex);
        matcherSetStartOrEndDate(task);
        if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
            regex += REGEX_KEYWORD_GREEDY_SELECT;
            tryGenerateMatcherForNoKeyword(args, task, regex);
        }
    }

    /**
     * Checks whether matcher for one keyword matches the input args.
     *
     * @param args  User input of task to add.
     * @param task  Object to store values for startDate and endDate.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void tryMatcherForOneKeyword(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        reinitialiseStartAndEndDatesToEmpty(task);
        regex += REGEX_KEYWORD_GREEDY_SELECT;
        generateMatcherForOneKeyword(args, regex);
    }

    /**
     * Checks whether matcher for zero keyword matches the input args.
     *
     * @param args  User input of task to add.
     * @param task  Object to store values for startDate and endDate.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void tryGenerateMatcherForNoKeyword(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        reinitialiseStartAndEndDatesToEmpty(task);
        regex += REGEX_KEYWORD_GREEDY_SELECT;
        generateMatcherForNoKeyword(args, regex);
    }

    /**
     * Returns a string containing args minus the escaped string.
     *
     * @param args  User input of task to add.
     * @return      Args minus escaped string.
     */
    private String generateArgsMinusTaskName(String args) {
        assert args != null;
        
        int indexOfEndOfTaskName = args.lastIndexOf(REGEX_ESCAPE_CHARACTER) + ONE;
        return args.substring(indexOfEndOfTaskName);
    }
    
    /**
     * Returns the number of occurrences of "from", "at", "start", "by", "to", "end" in args.
     * The value returned will be >= 0.
     *
     * @param args  User input of task to add.
     * @return      Number of occurrences of "from", "at", "start", "by", "to", "end".
     */
    private int generateNumberOfKeywords(String args) {    
        assert args != null;
        
        int numberOfKeywords = ZERO;
        pattern = Pattern.compile(REGEX_ADDITIONAL_KEYWORD);
        matcher = pattern.matcher(args);
        while (matcher.find()) {
            numberOfKeywords++;
        }
        
        assert numberOfKeywords >= 0;
        return numberOfKeywords;
    }
    
    /**
     * Generates the start of the regex that captures the taskName.
     *
     * @param numberOfKeywords  Number of occurrences of "from", "at", "start", "by", "to", "end".
     * @return                  The start of the regex.
     */
    private String generateStartOfRegex(int numberOfKeywords) {
        assert numberOfKeywords >= ZERO;
        
        String regex = REGEX_OPEN_BRACE_CASE_IGNORE_NAME;

        if (numberOfKeywords > TWO) {
            int numberOfAdditionalKeywords = numberOfKeywords - TWO;
            for (int i = 0; i < numberOfAdditionalKeywords; i++) {
                regex += REGEX_KEYWORD_GREEDY_SELECT;
            }
        }
        return regex;
    }

    /**
     * Validates the matcher for the given args, where args has no keywords.
     *
     * @param args  User input of task to add.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForNoKeyword(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        regex += REGEX_CLOSE_BRACE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
        generateAndValidateMatcher(args, regex);
    }
    
    /**
     * Generates and validates a matcher from the given args and regex.
     *
     * @param args  User input of task to add.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateAndValidateMatcher(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(args);
        if (!matcher.matches()) {
            logger.log(Level.FINE, "IllegalValueException thrown in CommandParserHelper, generateAndValidateMatcher, "
                    + "matcher does not match");
            throw new IllegalValueException(MESSAGE_INVALID_MATCHER);
        }
    }
    
    /**
     * Generates the matcher for the given escaped args, where args has no keyword.
     *
     * @param args  User input of task to add.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForNoKeywordEscape(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        regex += REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
        generateAndValidateMatcher(args, regex);
    }
    
    /**
     * Generates the matcher for the given escaped args, where args has one keyword.
     *
     * @param args  User input of task to add.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForOneKeywordEscape(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        String regexCopy = generateRegexForOneKeywordEscape(regex);
        generateAndValidateMatcher(args, regexCopy);
    }

    /**
     * Generates the matcher for the given args, where args has one keyword.
     *
     * @param args  User input of task to add.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForOneKeyword(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        String regexCopy = generateRegexForOneKeyword(regex);
        generateAndValidateMatcher(args, regexCopy);
    }
    
    /**
     * Generates the regex to match an escaped string with one keyword 
     * by concatenating the given regex generated by generateStartOfRegex() 
     * with the regex for matching one keyword.
     *
     * @param regex     Used to generate matcher.
     * @return regex    That matches an escaped string with one keyword.
     */
    private String generateRegexForOneKeywordEscape(String regex) {
        assert regex != null;
        return regex + REGEX_FIRST_DATE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }

    /**
     * Generates the regex to match an string with one keyword 
     * by concatenating the given regex generated by generateStartOfRegex() 
     * with the regex for matching one keyword.
     *
     * @param regex     Used to generate matcher.
     * @return regex    That matches a string with one keyword.
     */
    private String generateRegexForOneKeyword(String regex) {
        assert regex != null;
        return regex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }
    
    /**
     * Sets start date or end date depending on which matcher group was matched
     * @param regex  Used to generate matcher.
     */
    private void matcherSetStartOrEndDate(OptionalStringTask task) {
        assert task != null && matcher != null;
        task.startDate = matchesStartDateFormatsOneToThree();
        task.endDate = matchesEndDateFormatsOneToThree();
        assert task.startDate.isPresent() ^ task.endDate.isPresent();
    }
    
    /**
     * Reinitialises both start date and end date to be empty.
     *
     * @param task  Object to store values for startDate and endDate.
     */
    private void reinitialiseStartAndEndDatesToEmpty(OptionalStringTask task) {
        assert task != null;
        task.startDate = Optional.empty();
        task.endDate = Optional.empty();
    }
    
    /**
     * Verifies if either startDate or endDate is present, and if either dates are present,
     * then verifies whether the date is valid.
     *
     * @param startDate User input of start date. May not exist if user did not input start date.
     * @param endDate   User input of end date. May not exist if user did not input end date.
     * @return          True if the date that is present is valid.
     */
    private boolean startOrEndDateIsInvalid(Optional<String> startDate, Optional<String> endDate) {
        return startDate.isPresent() && !DateTime.isValidDate(startDate.get())
                || endDate.isPresent() && !DateTime.isValidDate(endDate.get());
    }
    
    /**
     * Checks whether any of the matcher groups for start date are present. 
     * Either none of the matcher groups are present, or only one matcher group is present. 
     * Return the value stored in the matcher group if it is present, else return Optional.empty().
     *
     * @return  The value of the matcher group if it is present, else return Optional.empty().
     */
    private Optional<String> matchesStartDateFormatsOneToThree() {
        assert matcher != null;

        Optional<String> startDate = Optional.empty();

        if (matcher.group(START_DATE_FORMAT_ONE) != null) {
            startDate = Optional.of(matcher.group(START_DATE_FORMAT_ONE).trim());
        } else if (matcher.group(START_DATE_FORMAT_TWO) != null) {
            startDate = Optional.of(matcher.group(START_DATE_FORMAT_TWO).trim());
        } else if (matcher.group(START_DATE_FORMAT_THREE) != null) {
            startDate = Optional.of(matcher.group(START_DATE_FORMAT_THREE).trim());
        }

        return startDate;
    }
    
    /**
     * Checks whether any of the matcher groups for end date are present. 
     * Either none of the matcher groups are present, or only one matcher group is present. 
     * Return the value of the matcher group if it is present, else return Optional.empty().
     *
     * @return  The value of the matcher group if it is present, else return Optional.empty().
     */
    private Optional<String> matchesEndDateFormatsOneToThree() {
        assert matcher != null;

        Optional<String> endDate = Optional.empty();

        if (matcher.group(END_DATE_FORMAT_ONE) != null) {
            endDate = Optional.of(matcher.group(END_DATE_FORMAT_ONE).trim());
        } else if (matcher.group(END_DATE_FORMAT_TWO) != null) {
            endDate = Optional.of(matcher.group(END_DATE_FORMAT_TWO).trim());
        } else if (matcher.group(END_DATE_FORMAT_THREE) != null) {
            endDate = Optional.of(matcher.group(END_DATE_FORMAT_THREE).trim());
        }

        return endDate;
    }

    /**
     * Generates the matcher for the given escaped args, where args has two keywords.
     *
     * @param args  User input of task to add.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForTwoKeywordsEscape(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        String regexCopy = generateRegexForTwoKeywordsEscape(regex);
        generateAndValidateMatcher(args, regexCopy);
    }

    /**
     * Generates the matcher for the given args, where args has two keywords.
     *
     * @param args  User input of task to add.
     * @param regex Used to generate matcher.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateMatcherForTwoKeywords(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        String regexCopy = generateRegexForTwoKeywords(regex);
        generateAndValidateMatcher(args, regexCopy);
    }
    
    /**
     * Generates the regex to match an escaped string with two keywords 
     * by concatenating the given regex generated by generateStartOfRegex() 
     * with the regex for matching two keywords.
     *
     * @param regex     Used to generate matcher.
     * @return regex    That matches an escaped string with two keywords.
     */
    private String generateRegexForTwoKeywordsEscape(String regex) {
        assert regex != null;
        return regex + REGEX_FIRST_DATE + REGEX_SECOND_DATE
                + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }

    /**
     * Generates the regex to match a string with two keywords 
     * by concatenating the given regex generated by generateStartOfRegex() 
     * with the regex for matching two keywords.
     *
     * @param regex     Used to generate matcher.
     * @return regex    That matches a string with two keywords.
     */
    private String generateRegexForTwoKeywords(String regex) {
        assert regex != null;
        return regex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_SECOND_DATE
                + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }

    /**
     * Sets the Task's end date if user input is valid.
     *
     * @param task  Object to store values for startDate and endDate.
     * @throws IllegalValueException    If user input contains repeated end date or repeated start date.
     */
    private void matcherSetEndDate(OptionalStringTask task) throws IllegalValueException {
        assert task != null;
        if (isRepeatedEndDate(task)) { 
            logger.log(Level.FINE, "IllegalValueException thrown in CommandParserHelper, matcherSetEndDate, repeated end time");
            throw new IllegalValueException(MESSAGE_REPEATED_END_TIME);
        } else {
            task.endDate = matchesEndDateFormatsFourToSix();
            if (isEndDateUnmatched(task)) {
                logger.log(Level.FINE, "IllegalValueException thrown in CommandParserHelper, matcherSetEndDate, repeated start time");
                throw new IllegalValueException(MESSAGE_REPEATED_START_TIME);
            }
        }
    }

    /**
     * Checks if end date is repeated in user input.
     * For e.g, "by 1030pm by 1050pm" is not allowed.
     * Since setStartOrEndDate(OptionalStringTask) would have set a start or end date,
     * thus if end date is present (i.e was set previously), it implies that end date is repeated.
     *
     * @param task  Object to store values for startDate and endDate.
     * @return      True if endDate is present, else returns false.
     */
    private boolean isRepeatedEndDate(OptionalStringTask task) {
        assert task != null;
        return task.endDate.isPresent();
    }

    /**
     * Checks if end date is still unmatched.
     * This may happen in cases like "from 1030pm from 1050pm", which is not allowed.
     * Thus if end date is still absent, it implies that start date is repeated.
     *
     * @param task  Object to store values for startDate and endDate.
     * @return      True if endDate is present, else returns false.
     */
    private boolean isEndDateUnmatched(OptionalStringTask task) {
        assert task != null;
        return !task.endDate.isPresent();
    }

    /**
     * Put all the values of parameters in task into a HashMap, and returns the HashMap.
     * 
     * @param task  OptionalStringTask object that contains String values to be converted to 
     *              an actual Task object.
     * @return      Map containing the values of parameters in task.
     */
    private HashMap<String, Optional<String>> mapContainingVariables(OptionalStringTask task) {
        assert task != null;
        HashMap<String, Optional<String>> map = new HashMap<String, Optional<String>>();
        
        map.put(Name.getMapNameKey(), task.taskName);
        map.put(DateTime.getMapStartDateKey(), task.startDate);
        map.put(DateTime.getMapEndDateKey(), task.endDate);
        map.put(RecurrenceRate.getMapRateKey(), task.rate);
        map.put(TimePeriod.getTimePeriodKey(), task.timePeriod);
        map.put(Priority.getMapPriorityKey(), task.priority);
        
        return map;
    }
    
    /**
     * Returns a HashMap containing user's input of rate and timePeriod.
     * 
     * @return  HashMap containing user's input of rate and timePeriod.
     *          If user did not input any fields, return Optional.empty() for the field in HashMap.
     */
    private HashMap<String, Optional<String>> matchesRateAndTimePeriod() throws IllegalValueException {
        HashMap<String, Optional<String>> map = new HashMap<String, Optional<String>>();

        Optional<String> rate = Optional.empty();
        Optional<String> timePeriod = Optional.empty();

        if (matcher.group(RATE) != null) {
            rate = Optional.of(matcher.group(RATE).trim());
        }

        if (matcher.group(TIME_PERIOD) != null) {
            timePeriod = Optional.of(matcher.group(TIME_PERIOD).trim());
        }
            
        map.put(RATE, rate);
        map.put(TIME_PERIOD, timePeriod);

        return map;
    }

    /**
     * Checks whether any of the matcher groups for end date are present. 
     * Either none of the matcher groups are present, or only one matcher group is present. 
     * Return the value of the matcher group if it is present, else return Optional.empty().
     *
     * @return  The value of the matcher group if it is present, else return Optional.empty().
     */
    private Optional<String> matchesEndDateFormatsFourToSix() {
        assert matcher != null;

        Optional<String> endDate = Optional.empty();

        if (matcher.group(END_DATE_FORMAT_FOUR) != null) {
            endDate = Optional.of(matcher.group(END_DATE_FORMAT_FOUR).trim());
        } else if (matcher.group(END_DATE_FORMAT_FIVE) != null) {
            endDate = Optional.of(matcher.group(END_DATE_FORMAT_FIVE).trim());
        } else if (matcher.group(END_DATE_FORMAT_SIX) != null) {
            endDate = Optional.of(matcher.group(END_DATE_FORMAT_SIX).trim());
        }

        return endDate;
    }

    /**
     * Returns user's trimmed input of priority.
     * 
     * @return  User's trimmed input of priority. If user did not specify a priority,
     *          by default, return "medium".
     */
    private String matchesPriority() {
        String priority;
        if (matcher.group(PRIORITY) != null) {
            priority = matcher.group(PRIORITY).trim();
        } else {
            priority = "medium";
        }
        return priority;
    }

    //@@author
    private String generatePriorityEdit(Matcher matcher) {
        String priority;
        if (matcher.group(PRIORITY) != null) {
            priority = matcher.group(PRIORITY).trim();
        } else {
            priority = "null";
        }
        return priority;
    }
    
    /**
     * Assigns values into Task's parameters.
     * 
     * @param task  OptionalStringTask object that contains String values to be converted to 
     *              an actual Task object.
     */
    private void assignTaskParameters(OptionalStringTask task) throws IllegalValueException {
        assert matcher.group(TASK_NAME) != null && task != null;
        task.taskName = Optional.of(matcher.group(TASK_NAME).trim());
        HashMap<String, Optional<String>> recurrenceRateMap = matchesRateAndTimePeriod();
        task.rate = recurrenceRateMap.get(RATE);
        task.timePeriod = recurrenceRateMap.get(TIME_PERIOD);
        task.priority = Optional.of(matchesPriority());
    }
    
    public static String getMessageRepeatedStartTime() {
        return MESSAGE_REPEATED_START_TIME;
    }
    
    public static String getMessageRepeatedEndTime() {
        return MESSAGE_REPEATED_END_TIME;
    }

    //@@author A0139552B
    private void assignTaskParametersEdit(OptionalStringTask task) throws IllegalValueException {
        task.taskName = Optional.of(matcher.group(TASK_NAME).trim());
        HashMap<String, Optional<String>> recurrenceRateMap = matchesRateAndTimePeriod();
        task.rate = recurrenceRateMap.get(RATE);
        task.timePeriod = recurrenceRateMap.get(TIME_PERIOD);
        task.priority = Optional.of(generatePriorityEdit(matcher));
    }
    
    //@@author A0139655U
    /*
     * A simplified version of Task class, having all parameters stored as String objects.
     */
    private class OptionalStringTask {
        public Optional<String> taskName;
        public Optional<String> startDate;
        public Optional<String> endDate;
        public Optional<String> rate;
        public Optional<String> timePeriod;
        public Optional<String> priority;
        
        public OptionalStringTask() {
            taskName = startDate = endDate = rate = timePeriod = priority = Optional.empty();
        }
    }
}
