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

public class CommandParserHelper {
    
    private final Logger logger = LogsCenter.getLogger(CommandParserHelper.class);
    
    private static final String MESSAGE_REPEATED_START_TIME = "Repeated start times are not allowed.";
    private static final String MESSAGE_REPEATED_END_TIME = "Repeated end times are not allowed.";
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final Pattern RECURRENCE_RATE_ARGS_FORMAT = Pattern.compile("(?<rate>\\d+)?(?<timePeriod>.*?)");

    private static final String REGEX_OPEN_BRACE = "(";
    private static final String REGEX_CASE_IGNORE = "?i:";
    private static final String REGEX_CLOSE_BRACE = ")";
    private static final String REGEX_GREEDY_SELECT = ".*?";
    private static final String REGEX_ESCAPE = "\"";

    private static final String REGEX_NAME = "?<taskName>.*?";
    private static final String REGEX_ADDITIONAL_KEYWORD = "(?:" + "(?: from )" + "|(?: at )" + "|(?: start )"
            + "|(?: by )" + "|(?: to )" + "|(?: end )" + ")";
    private static final String REGEX_FIRST_DATE = "(?:" + "(?: from (?<startDateFormatOne>.*?))"
            + "|(?: at (?<startDateFormatTwo>.*?))" + "|(?: start (?<startDateFormatThree>.*?))"
            + "|(?: by (?<endDateFormatOne>.*?))" + "|(?: to (?<endDateFormatTwo>.*?))"
            + "|(?: end (?<endDateFormatThree>.*?))" + ")";
    private static final String REGEX_SECOND_DATE = "(?:" + "(?: from (?:.*?))"
            + "|(?: at (?:.*?))" + "|(?: start (?:.*?))"
            + "|(?: by (?<endDateFormatFour>.*?))" + "|(?: to (?<endDateFormatFive>.*?))"
            + "|(?: end (?<endDateFormatSix>.*?))" + ")";
    private static final String REGEX_RECURRENCE_AND_PRIORITY = "(?: repeat every (?<recurrenceRate>.*?))?"
            + "(?: -(?<priority>.*?))?";

    private static final String REGEX_OPEN_BRACE_CASE_IGNORE_NAME_ESCAPE = REGEX_OPEN_BRACE + REGEX_CASE_IGNORE
            + REGEX_ESCAPE + REGEX_OPEN_BRACE + REGEX_NAME + REGEX_CLOSE_BRACE + REGEX_ESCAPE;
    private static final String REGEX_OPEN_BRACE_CASE_IGNORE_NAME = REGEX_OPEN_BRACE + REGEX_CASE_IGNORE
            + REGEX_OPEN_BRACE + REGEX_NAME;
    private static final String REGEX_KEYWORD_GREEDY_SELECT = REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT;
    private static final String REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE = REGEX_RECURRENCE_AND_PRIORITY
            + REGEX_CLOSE_BRACE;

    private Pattern pattern;
    private Matcher matcher;

    public HashMap<String, Optional<String>> prepareAdd(String args) throws IllegalValueException {
        assert args != null;
        OptionalStringTask task = new OptionalStringTask();
        
        if (args.contains(REGEX_ESCAPE)) {
            prepareAddForEscapeInput(args, task);
        } else {
            prepareAddForNonEscapeInput(args, task);
        }
        
        assignTaskParameters(task);
        return putVariablesInMap(task);
    }

    private void prepareAddForNonEscapeInput(String args, OptionalStringTask task) throws IllegalValueException {
        int numberOfKeywords = generateNumberOfKeywords(args);
        logger.log(Level.FINEST, "Number of keywords in \"" + args + "\" = " + numberOfKeywords);
        String regex = generateStartOfRegex(numberOfKeywords);
        generateCorrectMatcher(args, task, regex, numberOfKeywords);
    }

    private void prepareAddForEscapeInput(String args, OptionalStringTask task) throws IllegalValueException {
        String argsMinusTaskName = generateArgsMinusTaskName(args);
        int numberOfKeywords = generateNumberOfKeywords(argsMinusTaskName);
        logger.log(Level.FINEST, "Number of keywords in \"" + args + "\" = " + numberOfKeywords);
        String regex = REGEX_OPEN_BRACE_CASE_IGNORE_NAME_ESCAPE;
        generateCorrectMatcherEscape(args, task, regex, numberOfKeywords);
    }

    private String generateArgsMinusTaskName(String args) {
        int indexOfEndOfTaskName = args.lastIndexOf(REGEX_ESCAPE) + ONE;
        return args.substring(indexOfEndOfTaskName);
    }

    public HashMap<String, Optional<String>> prepareEdit(String args) throws IllegalValueException {
        assert args != null;
        OptionalStringTask task = new OptionalStringTask();
        int numberOfKeywords;
        String regex;
        if (args.contains("\"")) {
            String nonName = args.substring(args.lastIndexOf("\"") + 1);
            numberOfKeywords = generateNumberOfKeywords(nonName);
            regex = REGEX_OPEN_BRACE_CASE_IGNORE_NAME_ESCAPE;
            generateCorrectMatcherEscape(args, task, regex, numberOfKeywords);
        } else {
            prepareAddForNonEscapeInput(args, task);
        }
        
        assignTaskParametersEdit(task);
        return putVariablesInMap(task);
    }

    public void generateCorrectMatcherEscape(String args, OptionalStringTask task, String regex, int numberOfKeywords)
            throws IllegalValueException {
        if (numberOfKeywords == ZERO) {
            validateMatcherForNoKeywordEscape(args, regex);
        } else if (numberOfKeywords == ONE) {
            validateMatcherForOneKeywordEscape(args, task, regex);
        } else if (numberOfKeywords >= TWO) {
            validateMatcherForTwoKeywordsEscape(args, task, regex);
        }
    }

    private void assignTaskParameters(OptionalStringTask task) throws IllegalValueException {
        assert matcher.group("taskName") != null;
        task.taskName = Optional.of(matcher.group("taskName").trim());
        HashMap<String, Optional<String>> recurrenceRateMap = generateRateAndTimePeriod(matcher);
        task.rate = recurrenceRateMap.get("rate");
        task.timePeriod = recurrenceRateMap.get("timePeriod");
        task.priority = Optional.of(assignPriority(matcher));
    }

    private void assignTaskParametersEdit(OptionalStringTask task) throws IllegalValueException {
        task.taskName = Optional.of(matcher.group("taskName").trim());
        HashMap<String, Optional<String>> recurrenceRateMap = generateRateAndTimePeriod(matcher);
        task.rate = recurrenceRateMap.get("rate");
        task.timePeriod = recurrenceRateMap.get("timePeriod");
        task.priority = Optional.of(assignPriorityEdit(matcher));
    }

    private void generateCorrectMatcher(String args, OptionalStringTask task, String regex, int numberOfKeywords)
            throws IllegalValueException {
        if (numberOfKeywords == ZERO) {
            validateMatcherForNoKeyword(args, regex);
        } else if (numberOfKeywords == ONE) {
            validateMatcherForOneKeyword(args, task, regex);
        } else if (numberOfKeywords >= TWO) {
            validateMatcherForTwoKeywords(args, task, regex);
        }
    }
    
 
    private void validateMatcherForTwoKeywordsEscape(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        generateMatcherForTwoKeywordsEscape(args, regex);
        setStartOrEndDate(task, matcher);
        validateStartAndEndDates(task);
    }

    private void validateMatcherForTwoKeywords(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
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

    private void validateStartAndEndDates(OptionalStringTask task) throws IllegalValueException {
        if (task.endDate.isPresent()) { // i.e does not allow "by 1030pm by 1050pm"
            throw new IllegalValueException(MESSAGE_REPEATED_END_TIME);
        } else {
            task.endDate = validateEndDateFormatsFourToSix(matcher);
        }
        
        if (!task.endDate.isPresent()) {
            throw new IllegalValueException(MESSAGE_REPEATED_START_TIME);
        }
    }

    private void reinitialiseStartAndEndDatesToEmpty(OptionalStringTask task) {
        task.startDate = Optional.empty();
        task.endDate = Optional.empty();
    }
    
    private void validateMatcherForOneKeywordEscape(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        generateMatcherForOneKeywordEscape(args, regex);
        setStartOrEndDate(task, matcher);
    }

    private void validateMatcherForOneKeyword(String args, OptionalStringTask task, String regex)
            throws IllegalValueException {
        generateMatcherForOneKeyword(args, regex);
        setStartOrEndDate(task, matcher);
        if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
            reinitialiseStartAndEndDatesToEmpty(task);
            regex += REGEX_KEYWORD_GREEDY_SELECT;
            validateMatcherForNoKeyword(args, regex);
        }
    }
    
    private void generateMatcherForOneKeywordEscape(String args, String regex) throws IllegalValueException {
        String regexCopy = generateRegexForOneKeywordEscape(regex);
        generateMatcher(regexCopy, args);
    }

    private void generateMatcherForOneKeyword(String args, String regex) throws IllegalValueException {
        String regexCopy = generateRegexForOneKeyword(regex);
        generateMatcher(regexCopy, args);
    }
    
    private void generateMatcherForTwoKeywordsEscape(String args, String regex) throws IllegalValueException {
        String regexCopy = generateRegexForTwoKeywordsEscape(regex);
        generateMatcher(regexCopy, args);
    }

    private void generateMatcherForTwoKeywords(String args, String regex) throws IllegalValueException {
        String regexCopy = generateRegexForTwoKeywords(regex);
        generateMatcher(regexCopy, args);
    }
    
    private String generateRegexForTwoKeywordsEscape(String regex) {
        return regex + REGEX_FIRST_DATE + REGEX_SECOND_DATE
                + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }

    private String generateRegexForTwoKeywords(String regex) {
        return regex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_SECOND_DATE
                + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }
    
    private String generateRegexForOneKeywordEscape(String regex) {
        return regex + REGEX_FIRST_DATE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
    }

    private String generateRegexForOneKeyword(String regex) {
        return regex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
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
    
    private int generateNumberOfKeywords(String args) {    
        int numberOfKeywords = ZERO;
        pattern = Pattern.compile(REGEX_ADDITIONAL_KEYWORD);
        matcher = pattern.matcher(args);
        while (matcher.find()) {
            numberOfKeywords++;
        }
        return numberOfKeywords;
    }

    private boolean startOrEndDateIsInvalid(Optional<String> startDate, Optional<String> endDate) {
        return startDate.isPresent() && !DateTime.isValidDate(startDate.get())
                || endDate.isPresent() && !DateTime.isValidDate(endDate.get());
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

    private void setStartOrEndDate(OptionalStringTask task, Matcher matcher) {
        task.startDate = validateStartDateFormatsOneToThree(matcher);
        task.endDate = validateEndDateFormatsOneToThree(matcher);
        assert task.startDate.isPresent() ^ task.endDate.isPresent();
    }

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
    
    private void validateMatcherForNoKeywordEscape(String args, String regex) throws IllegalValueException {
        regex += REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
        generateMatcher(regex, args);
    }

    private void validateMatcherForNoKeyword(String args, String regex) throws IllegalValueException {
        regex += REGEX_CLOSE_BRACE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
        generateMatcher(regex, args);
    }

    private void generateMatcher(String regex, String args) throws IllegalValueException {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(args);
        validateMatcherMatches(matcher);
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

    // TODO: Update this
    private void validateMatcherMatches(Matcher matcher) throws IllegalValueException {
        if (!matcher.matches()) {
            throw new IllegalValueException("");
        }
    }

    private String assignPriority(Matcher matcher) {
        String priority;
        if (matcher.group("priority") != null) {
            priority = matcher.group("priority").trim();
        } else {
            priority = "medium";
        }
        return priority;
    }

    private String assignPriorityEdit(Matcher matcher) {
        String priority;
        if (matcher.group("priority") != null) {
            priority = matcher.group("priority").trim();
        } else {
            priority = "null";
        }
        return priority;
    }
    
    // TODO: To update this
    private Matcher validateRecurrenceMatcher(Matcher matcher) throws IllegalValueException {
        String recurrenceString = matcher.group("recurrenceRate");
        final Matcher recurrenceMatcher = RECURRENCE_RATE_ARGS_FORMAT.matcher(recurrenceString);

        if (!recurrenceMatcher.matches()) {
            throw new IllegalValueException("");
        }

        return recurrenceMatcher;
    }
    
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
