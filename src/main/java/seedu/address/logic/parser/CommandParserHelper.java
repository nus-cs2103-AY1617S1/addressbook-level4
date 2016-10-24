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


//TODO: Implement interface to access DateTime.isValidDate?
//@@author A0139655U
public class CommandParserHelper {
    
    private final Logger logger = LogsCenter.getLogger(CommandParserHelper.class);
    
    private static final String MESSAGE_REPEATED_START_TIME = "Repeated start times are not allowed.";
    private static final String MESSAGE_REPEATED_END_TIME = "Repeated end times are not allowed.";
    private static final String MESSAGE_INVALID_MATCHER = "Matcher is unable to find a match.";
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final String REGEX_OPEN_BRACE = "(";
    private static final String REGEX_CASE_IGNORE = "?i:";
    private static final String REGEX_CLOSE_BRACE = ")";
    private static final String REGEX_GREEDY_SELECT = ".*?";
    private static final String REGEX_ESCAPE = "\"";
    
    // greedily captures the taskName until it reaches the following keyword
    private static final String REGEX_NAME = "?<taskName>.*?";
    
    // used for concatenating keyword to REGEX_NAME
    private static final String REGEX_ADDITIONAL_KEYWORD = "(?:" + "(?: from )" + "|(?: at )" + "|(?: start )"
            + "|(?: by )" + "|(?: to )" + "|(?: end )" + ")";
    
    // greedily captures everything after the first keyword, until it reaches the following keyword
    private static final String REGEX_FIRST_DATE = "(?:" + "(?: from (?<startDateFormatOne>.*?))"
            + "|(?: at (?<startDateFormatTwo>.*?))" + "|(?: start (?<startDateFormatThree>.*?))"
            + "|(?: by (?<endDateFormatOne>.*?))" + "|(?: to (?<endDateFormatTwo>.*?))"
            + "|(?: end (?<endDateFormatThree>.*?))" + ")";
    
    // greedily captures everything after the first keyword, until it reaches the following keyword
    private static final String REGEX_SECOND_DATE = "(?:" + "(?: from (?:.*?))"
            + "|(?: at (?:.*?))" + "|(?: start (?:.*?))"
            + "|(?: by (?<endDateFormatFour>.*?))" + "|(?: to (?<endDateFormatFive>.*?))"
            + "|(?: end (?<endDateFormatSix>.*?))" + ")";
    
    // greedily captures everything after the first keyword, until it reaches the following keyword
    private static final String REGEX_RECURRENCE_AND_PRIORITY = "(?: repeat every (?<recurrenceRate>.*?))?"
            + "(?: -(?<priority>.*?))?";

    // beginning of regex when input is escaped
    private static final String REGEX_OPEN_BRACE_CASE_IGNORE_NAME_ESCAPE = REGEX_OPEN_BRACE + REGEX_CASE_IGNORE
            + REGEX_ESCAPE + REGEX_OPEN_BRACE + REGEX_NAME + REGEX_CLOSE_BRACE + REGEX_ESCAPE;
    
    // beginning of regex when input is not escaped
    private static final String REGEX_OPEN_BRACE_CASE_IGNORE_NAME = REGEX_OPEN_BRACE + REGEX_CASE_IGNORE
            + REGEX_OPEN_BRACE + REGEX_NAME;
    
    // used for concatenating keyword to REGEX_NAME
    private static final String REGEX_KEYWORD_GREEDY_SELECT = REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT;
    
    // end of regex; only concatenated at the end of the regex
    private static final String REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE = REGEX_RECURRENCE_AND_PRIORITY
            + REGEX_CLOSE_BRACE;
    
    // seperates the recurrence rate captured by the previous regex into rate and timePeriod.
    private static final Pattern RECURRENCE_RATE_ARGS_FORMAT = Pattern.compile("(?<rate>\\d+)?(?<timePeriod>.*?)");


    private Pattern pattern;
    private Matcher matcher;

    /**
     * Returns a HashMap containing Optional<String> values of
     * taskName, startDate, endDate, rate, timePeriod and priority.
     *
     * @param args  user input of task to add.
     * @return     Values of taskName, startDate, endDate, rate, timePeriod and priority.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    public HashMap<String, Optional<String>> prepareAdd(String args) throws IllegalValueException {
        // how should i assert sia: assert args != null;
        OptionalStringTask task = new OptionalStringTask();
        
        if (args.contains(REGEX_ESCAPE)) {
            prepareAddForEscapeInput(args, task);
        } else {
            prepareAddForNonEscapeInput(args, task);
        }
        
        assignTaskParameters(task);
        return putVariablesInMap(task);
    }
    
    //@@author 
    public HashMap<String, Optional<String>> prepareEdit(String args) throws IllegalValueException {
        assert args != null;
        OptionalStringTask task = new OptionalStringTask();
        
        if (args.contains(REGEX_ESCAPE)) {
            prepareAddForEscapeInput(args, task);
        } else {
            prepareAddForNonEscapeInput(args, task);
        }
        
        assignTaskParametersEdit(task);
        return putVariablesInMap(task);
    }
    
    //@@author A0139655U
    /**
     * Generates the right matcher for the escaped input args.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void prepareAddForEscapeInput(String args, OptionalStringTask task) throws IllegalValueException {
        assert args != null && task != null;
        
        String argsMinusTaskName = generateArgsMinusTaskName(args);
        int numberOfKeywords = generateNumberOfKeywords(argsMinusTaskName);
        logger.log(Level.FINEST, "Number of keywords in \"" + args + "\" = " + numberOfKeywords);
        String regex = REGEX_OPEN_BRACE_CASE_IGNORE_NAME_ESCAPE;
        generateCorrectMatcherEscape(args, task, regex, numberOfKeywords);
    }

    /**
     * Generates the right matcher for the input args.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void prepareAddForNonEscapeInput(String args, OptionalStringTask task) throws IllegalValueException {
        assert args != null && task != null;
        
        int numberOfKeywords = generateNumberOfKeywords(args);
        logger.log(Level.FINEST, "Number of keywords in \"" + args + "\" = " + numberOfKeywords);
        String regex = generateStartOfRegex(numberOfKeywords);
        generateCorrectMatcher(args, task, regex, numberOfKeywords);
    }

    /**
     * Returns a string containing args minus the escaped string.
     *
     * @param args  user input of task to add.
     * @return      args minus escaped string.
     */
    private String generateArgsMinusTaskName(String args) {
        assert args != null;
        
        int indexOfEndOfTaskName = args.lastIndexOf(REGEX_ESCAPE) + ONE;
        return args.substring(indexOfEndOfTaskName);
    }
    
    /**
     * Returns the number of occurrences of "from", "at", "start", "by", "to", "end" in args.
     * The value returned will be >= 0.
     *
     * @param args  user input of task to add.
     * @return      number of occurrences of "from", "at", "start", "by", "to", "end".
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
    
    //TODO: HELP
    /**
     * Generates the beginning of the regex.
     *
     * @param numberOfKeywords  number of occurrences of "from", "at", "start", "by", "to", "end".
     * @return      the beginning of the regex.
     */
    private String generateStartOfRegex(int numberOfKeywords) {
        assert numberOfKeywords >= 0;
        
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
     * Generates the matcher for the given escaped args.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @param regex regex generated by generateStartOfRegex().
     * @param numberOfKeywords  number of occurrences of "from", "at", "start", "by", "to", "end".
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateCorrectMatcherEscape(String args, OptionalStringTask task, String regex, int numberOfKeywords)
            throws IllegalValueException {
        assert args != null && task != null && regex != null && numberOfKeywords >= 0;
        if (numberOfKeywords == ZERO) {
            validateMatcherForNoKeywordEscape(args, regex);
        } else if (numberOfKeywords == ONE) {
            validateMatcherForOneKeywordEscape(args, task, regex);
        } else if (numberOfKeywords >= TWO) {
            validateMatcherForTwoKeywordsEscape(args, task, regex);
        }
    }
    
    /**
     * Generates the matcher for the given args.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @param regex regex generated by generateStartOfRegex().
     * @param numberOfKeywords  number of occurrences of "from", "at", "start", "by", "to", "end".
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateCorrectMatcher(String args, OptionalStringTask task, String regex, int numberOfKeywords)
            throws IllegalValueException {
        assert args != null && task != null && regex != null && numberOfKeywords >= 0;
        if (numberOfKeywords == ZERO) {
            validateMatcherForNoKeyword(args, regex);
        } else if (numberOfKeywords == ONE) {
            validateMatcherForOneKeyword(args, task, regex);
        } else if (numberOfKeywords >= TWO) {
            validateMatcherForTwoKeywords(args, task, regex);
        }
    }

    /**
     * Validates the matcher for the given escaped args, where args has no keywords.
     *
     * @param args  user input of task to add.
     * @param regex regex generated by generateStartOfRegex().
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void validateMatcherForNoKeywordEscape(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        regex += REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
        generateAndValidateMatcher(args, regex);
    }

    /**
     * Validates the matcher for the given args, where args has no keywords.
     *
     * @param args  user input of task to add.
     * @param regex regex generated by generateStartOfRegex().
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void validateMatcherForNoKeyword(String args, String regex) throws IllegalValueException {
        assert args != null && regex != null;
        regex += REGEX_CLOSE_BRACE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
        generateAndValidateMatcher(args, regex);
    }
    
    /**
     * Generates and validates a matcher from the given args and regex.
     *
     * @param args  user input of task to add.
     * @param regex
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void generateAndValidateMatcher(String args, String regex) throws IllegalValueException {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(args);
        if (!matcher.matches()) {
            throw new IllegalValueException(MESSAGE_INVALID_MATCHER);
        }
    }
    
    /**
     * Validates the matcher for the given escaped args, where args has one keyword.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @param regex regex generated by generateStartOfRegex().
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void validateMatcherForOneKeywordEscape(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        assert args != null && task != null && regex != null;
        generateMatcherForOneKeywordEscape(args, regex);
        setStartOrEndDate(task, matcher);
    }

    /**
     * Validates the matcher for the given args, where args has one keyword.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @param regex regex generated by generateStartOfRegex().
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void validateMatcherForOneKeyword(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        assert args != null && task != null && regex != null;
        generateMatcherForOneKeyword(args, regex);
        setStartOrEndDate(task, matcher);
        if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
            reinitialiseStartAndEndDatesToEmpty(task);
            regex += REGEX_KEYWORD_GREEDY_SELECT;
            validateMatcherForNoKeyword(args, regex);
        }
    }
    
    /**
     * Generates the matcher for the given escaped args, where args has one keyword.
     *
     * @param args  user input of task to add.
     * @param regex regex generated by generateStartOfRegex().
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
     * @param args  user input of task to add.
     * @param regex regex generated by generateStartOfRegex().
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
     * @param regex regex generated by generateStartOfRegex().
     * @return regex that matches an escaped string with one keyword.
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
     * @param regex regex generated by generateStartOfRegex().
     * @return regex that matches a string with one keyword.
     */
    private String generateRegexForOneKeyword(String regex) {
        assert regex != null;
        return regex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }
    
    /**
     * Sets start date or end date depending on which matcher group was matched
     *
     * @param regex regex generated by generateStartOfRegex().
     */
    private void setStartOrEndDate(OptionalStringTask task, Matcher matcher) {
        assert task != null && matcher != null;
        task.startDate = validateStartDateFormatsOneToThree(matcher);
        task.endDate = validateEndDateFormatsOneToThree(matcher);
        assert task.startDate.isPresent() ^ task.endDate.isPresent();
    }
    
    /**
     * Reinitialises both start date and end date to be empty.
     *
     * @param task  object to store values for startDate and endDate.
     */
    private void reinitialiseStartAndEndDatesToEmpty(OptionalStringTask task) {
        assert task != null;
        task.startDate = Optional.empty();
        task.endDate = Optional.empty();
    }
    
    //TODO: HALP
    /**
     * Verifies if either startDate or endDate is present, and verifies whether 
     * the date that is present is valid.
     *
     * @param startDate 
     * @param endDate   
     * @return true if the date that is present is valid.
     */
    private boolean startOrEndDateIsInvalid(Optional<String> startDate, Optional<String> endDate) {
        return startDate.isPresent() && !DateTime.isValidDate(startDate.get())
                || endDate.isPresent() && !DateTime.isValidDate(endDate.get());
    }
    
    //TODO: HALP
    /**
     * Checks whether any of the matcher groups for start date are present. 
     * Either zero matcher groups are present or only one matcher group is present. 
     * Return the value of the matcher group if it is present, else return Optional.empty().
     *
     * @param matcher   
     * @return the value of the matcher group if it is present, else return Optional.empty().
     */
    private Optional<String> validateStartDateFormatsOneToThree(Matcher matcher) {
        assert matcher != null;

        Optional<String> startDate = Optional.empty();

        if (matcher.group("startDateFormatOne") != null) {
            startDate = Optional.of(matcher.group("startDateFormatOne").trim());
        } else if (matcher.group("startDateFormatTwo") != null) {
            startDate = Optional.of(matcher.group("startDateFormatTwo").trim());
        } else if (matcher.group("startDateFormatThree") != null) {
            startDate = Optional.of(matcher.group("startDateFormatThree").trim());
        }

        return startDate;
    }
    
    //TODO: HALP
    /**
     * Checks whether any of the matcher groups for end date are present. 
     * Either zero matcher groups are present or only one matcher group is present. 
     * Return the value of the matcher group if it is present, else return Optional.empty().
     *
     * @param matcher   
     * @return the value of the matcher group if it is present, else return Optional.empty().
     */
    private Optional<String> validateEndDateFormatsOneToThree(Matcher matcher) {
        assert matcher != null;

        Optional<String> endDate = Optional.empty();

        if (matcher.group("endDateFormatOne") != null) {
            endDate = Optional.of(matcher.group("endDateFormatOne").trim());
        } else if (matcher.group("endDateFormatTwo") != null) {
            endDate = Optional.of(matcher.group("endDateFormatTwo").trim());
        } else if (matcher.group("endDateFormatThree") != null) {
            endDate = Optional.of(matcher.group("endDateFormatThree").trim());
        }

        return endDate;
    }

    /**
     * Validates the matcher for the given escaped args, where args has two keywords.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @param regex regex generated by generateStartOfRegex().
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void validateMatcherForTwoKeywordsEscape(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        assert args != null && task != null && regex != null;
        generateMatcherForTwoKeywordsEscape(args, regex);
        setStartOrEndDate(task, matcher);
        validateStartAndEndDates(task);
    }
    
    /**
     * Validates the matcher for the given args, where args has two keywords.
     *
     * @param args  user input of task to add.
     * @param task  object to store values for startDate and endDate.
     * @param regex regex generated by generateStartOfRegex().
     * @throws IllegalValueException  If args does not match the matcher.
     */
    private void validateMatcherForTwoKeywords(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        assert args != null && task != null && regex != null;
        generateMatcherForTwoKeywords(args, regex);
        setStartOrEndDate(task, matcher);
        if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
            reinitialiseStartAndEndDatesToEmpty(task);
            regex += REGEX_KEYWORD_GREEDY_SELECT;
            validateMatcherForOneKeyword(args, task, regex);
        } else { 
            validateStartAndEndDates(task);
        }
    }
    
    /**
     * Generates the matcher for the given escaped args, where args has two keywords.
     *
     * @param args  user input of task to add.
     * @param regex regex generated by generateStartOfRegex().
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
     * @param args  user input of task to add.
     * @param regex regex generated by generateStartOfRegex().
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
     * @param regex regex generated by generateStartOfRegex().
     * @return regex that matches an escaped string with two keywords.
     */
    private String generateRegexForTwoKeywordsEscape(String regex) {
        assert regex != null;
        return regex + REGEX_FIRST_DATE + REGEX_SECOND_DATE
                + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }

    /**
     * Generates the regex to match an string with two keywords 
     * by concatenating the given regex generated by generateStartOfRegex() 
     * with the regex for matching two keywords.
     *
     * @param regex regex generated by generateStartOfRegex().
     * @return regex that matches a string with two keywords.
     */
    private String generateRegexForTwoKeywords(String regex) {
        assert regex != null;
        return regex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_SECOND_DATE
                + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }

    //TODO: HALP I'M HERE
    /**
     * Generates the regex to match an string with two keywords 
     * by concatenating the given regex generated by generateStartOfRegex() 
     * with the regex for matching two keywords.
     *
     * @param task  object to store values for startDate and endDate.
     */
    private void validateStartAndEndDates(OptionalStringTask task) throws IllegalValueException {
        assert task != null;
        if (task.endDate.isPresent()) { // i.e does not allow "by 1030pm by 1050pm"
            throw new IllegalValueException(MESSAGE_REPEATED_END_TIME);
        } else {
            task.endDate = validateEndDateFormatsFourToSix(matcher);
        }
        
        if (!task.endDate.isPresent()) {
            throw new IllegalValueException(MESSAGE_REPEATED_START_TIME);
        }
    }

    
    private HashMap<String, Optional<String>> putVariablesInMap(OptionalStringTask task) {
        HashMap<String, Optional<String>> map = new HashMap<String, Optional<String>>();
        
        map.put("taskName", task.taskName);
        map.put("startDate", task.startDate);
        map.put("endDate", task.endDate);
        map.put("rate", task.rate);
        map.put("timePeriod", task.timePeriod);
        map.put("priority", task.priority);
        
        return map;
    }
    
    private HashMap<String, Optional<String>> generateRateAndTimePeriod(Matcher matcher) throws IllegalValueException {
        HashMap<String, Optional<String>> map = new HashMap<String, Optional<String>>();

        Optional<String> rate = Optional.empty();
        Optional<String> timePeriod = Optional.empty();

        if (matcher.group("recurrenceRate") != null) {
            final Matcher recurrenceMatcher = validateRecurrenceMatcher(matcher);

            if (recurrenceMatcher.group("rate") != null) {
                rate = Optional.of(recurrenceMatcher.group("rate").trim());
            }

            assert recurrenceMatcher.group("timePeriod") != null;

            timePeriod = Optional.of(recurrenceMatcher.group("timePeriod").trim());
        }

        map.put("rate", rate);
        map.put("timePeriod", timePeriod);

        return map;
    }

    private Optional<String> validateEndDateFormatsFourToSix(Matcher matcher) {
        assert matcher != null;

        Optional<String> endDate = Optional.empty();

        if (matcher.group("endDateFormatFour") != null) {
            endDate = Optional.of(matcher.group("endDateFormatFour").trim());
        } else if (matcher.group("endDateFormatFive") != null) {
            endDate = Optional.of(matcher.group("endDateFormatFive").trim());
        } else if (matcher.group("endDateFormatSix") != null) {
            endDate = Optional.of(matcher.group("endDateFormatSix").trim());
        }

        return endDate;
    }

    private String generatePriority(Matcher matcher) {
        String priority;
        if (matcher.group("priority") != null) {
            priority = matcher.group("priority").trim();
        } else {
            priority = "medium";
        }
        return priority;
    }

    //@@author
    private String generatePriorityEdit(Matcher matcher) {
        String priority;
        if (matcher.group("priority") != null) {
            priority = matcher.group("priority").trim();
        } else {
            priority = "null";
        }
        return priority;
    }
    
    //@@author A0139655U
    // TODO: To update this
    private Matcher validateRecurrenceMatcher(Matcher matcher) throws IllegalValueException {
        String recurrenceString = matcher.group("recurrenceRate");
        final Matcher recurrenceMatcher = RECURRENCE_RATE_ARGS_FORMAT.matcher(recurrenceString);

        if (!recurrenceMatcher.matches()) {
            throw new IllegalValueException(MESSAGE_INVALID_MATCHER);
        }

        return recurrenceMatcher;
    }
    
    private void assignTaskParameters(OptionalStringTask task) throws IllegalValueException {
        assert matcher.group("taskName") != null;
        task.taskName = Optional.of(matcher.group("taskName").trim());
        HashMap<String, Optional<String>> recurrenceRateMap = generateRateAndTimePeriod(matcher);
        task.rate = recurrenceRateMap.get("rate");
        task.timePeriod = recurrenceRateMap.get("timePeriod");
        task.priority = Optional.of(generatePriority(matcher));
    }

    //@@author
    private void assignTaskParametersEdit(OptionalStringTask task) throws IllegalValueException {
        task.taskName = Optional.of(matcher.group("taskName").trim());
        HashMap<String, Optional<String>> recurrenceRateMap = generateRateAndTimePeriod(matcher);
        task.rate = recurrenceRateMap.get("rate");
        task.timePeriod = recurrenceRateMap.get("timePeriod");
        task.priority = Optional.of(generatePriorityEdit(matcher));
    }
    
    //@@author A0139655U
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
