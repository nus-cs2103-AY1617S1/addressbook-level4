package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.item.DateTime;

public class CommandParserHelper {
    
    private final Logger logger = LogsCenter.getLogger(CommandParserHelper.class);
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final Pattern RECURRENCE_RATE_ARGS_FORMAT = Pattern.compile("(?<rate>\\d+)?(?<timePeriod>.*?)");

    private static final String REGEX_OPEN_BRACE = "(";
    private static final String REGEX_CASE_IGNORE = "?i:";
    private static final String REGEX_CLOSE_BRACE = ")";
    private static final String REGEX_GREEDY_SELECT = ".*?";

    private static final String REGEX_NAME = "?<taskName>.*?";
    private static final String REGEX_ADDITIONAL_KEYWORD = "(?:" + "(?: from )" + "|(?: at )" + "|(?: start )"
            + "|(?: by )" + "|(?: to )" + "|(?: end )" + ")";
    private static final String REGEX_FIRST_DATE = "(?:" + "(?: from (?<startDateFormatOne>.*?))"
            + "|(?: at (?<startDateFormatTwo>.*?))" + "|(?: start (?<startDateFormatThree>.*?))"
            + "|(?: by (?<endDateFormatOne>.*?))" + "|(?: to (?<endDateFormatTwo>.*?))"
            + "|(?: end (?<endDateFormatThree>.*?))" + ")";
    private static final String REGEX_SECOND_DATE = "(?:" + "(?: from (?<startDateFormatFour>.*?))"
            + "|(?: at (?<startDateFormatFive>.*?))" + "|(?: start (?<startDateFormatSix>.*?))"
            + "|(?: by (?<endDateFormatFour>.*?))" + "|(?: to (?<endDateFormatFive>.*?))"
            + "|(?: end (?<endDateFormatSix>.*?))" + ")";
    private static final String REGEX_RECURRENCE_AND_PRIORITY = "(?: repeat every (?<recurrenceRate>.*?))?"
            + "(?: -(?<priority>.*?))?";

    private static final String REGEX_OPEN_BRACE_CASE_IGNORE_NAME = REGEX_OPEN_BRACE + REGEX_CASE_IGNORE
            + REGEX_OPEN_BRACE + REGEX_NAME;
    private static final String REGEX_KEYWORD_GREEDY_SELECT = REGEX_ADDITIONAL_KEYWORD + REGEX_GREEDY_SELECT;
    private static final String REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE = REGEX_RECURRENCE_AND_PRIORITY
            + REGEX_CLOSE_BRACE;

    private Pattern pattern;
    private Matcher matcher;

    public HashMap<String, Optional<String>> prepareAdd(String args) {
        assert args != null;
        
        OptionalStringTask task = new OptionalStringTask();
        String startOfRegex;
        String startOfRegexCopy;

        int numberOfKeywords = generateNumberOfKeywords(args);

        logger.log(Level.FINEST, "Number of keywords in \"" + args + "\" = " + numberOfKeywords);

        try {
            startOfRegex = generateStartOfRegex(numberOfKeywords);

            if (numberOfKeywords == ZERO) {
                matcher = generateMatcherForNoKeyword(startOfRegex, args);
            } else if (numberOfKeywords == ONE) {
                startOfRegexCopy = startOfRegex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE
                        + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;

                matcher = generateMatcher(startOfRegexCopy, args);

                HashMap<String, Optional<String>> map = setStartOrEndDate(matcher);
                task.startDate = map.get("startDate");
                task.endDate = map.get("endDate");

                if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
                    startOfRegex += REGEX_KEYWORD_GREEDY_SELECT;
                    matcher = generateMatcherForNoKeyword(startOfRegex, args);
                }
            } else if (numberOfKeywords >= TWO) {
                startOfRegexCopy = startOfRegex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE + REGEX_SECOND_DATE
                        + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;

                matcher = generateMatcher(startOfRegexCopy, args);

                HashMap<String, Optional<String>> map = setStartOrEndDate(matcher);
                task.startDate = map.get("startDate");
                task.endDate = map.get("endDate");

                boolean isValidEndDate = true;

                if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
                    isValidEndDate = false;
                    task.startDate = Optional.empty();
                    task.endDate = Optional.empty();
                    startOfRegex += REGEX_KEYWORD_GREEDY_SELECT;
                    startOfRegexCopy = startOfRegex + REGEX_CLOSE_BRACE + REGEX_FIRST_DATE
                            + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
                    matcher = generateMatcher(startOfRegexCopy, args);

                    map = setStartOrEndDate(matcher);
                    task.startDate = map.get("startDate");
                    task.endDate = map.get("endDate");
                    
                    if (startOrEndDateIsInvalid(task.startDate, task.endDate)) {
                        startOfRegex += REGEX_KEYWORD_GREEDY_SELECT;
                        matcher = generateMatcherForNoKeyword(startOfRegex, args);
                    }
                }



                if (isValidEndDate) {
                    task.endDate = validateEndDateFormatsFourToSix(matcher);
                }
            }

            // assign everything
            assert matcher != null;
            validateMatcherMatches(matcher);

            assert matcher.group("taskName") != null;
            task.taskName = Optional.of(matcher.group("taskName").trim());

            // TODO: Works but looks sloppy
            if (!matcher.toString().contains(REGEX_FIRST_DATE)) {
                task.startDate = Optional.empty();
            }

            if (!matcher.toString().contains(REGEX_FIRST_DATE) && !matcher.toString().contains(REGEX_SECOND_DATE)) {
                task.endDate = Optional.empty();
            }

            HashMap<String, Optional<String>> recurrenceRateMap = generateRateAndTimePeriod(matcher);
            task.rate = recurrenceRateMap.get("rate");
            task.timePeriod = recurrenceRateMap.get("timePeriod");

            task.priority = Optional.of(assignPriority(matcher));
            
            return putVariablesInMap(task);
        } catch (IllegalValueException ive) {
            logger.finer("IllegalValueException caught in CommandParser, prepareAdd()");
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE + "\n" + ive.getMessage()));
        }
    }

    public HashMap<String, Optional<String>> putVariablesInMap(OptionalStringTask task) {
        HashMap<String, Optional<String>> map = new HashMap<String, Optional<String>>();
        
        map.put("taskName", task.taskName);
        map.put("startDate", task.startDate);
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

    private Matcher generateFloatingTaskMatcherIfInvalidDates(String regex, String args) throws IllegalValueException {
        regex += REGEX_KEYWORD_GREEDY_SELECT;
        Matcher matcher = generateMatcherForNoKeyword(regex, args);
        return matcher;
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

    private HashMap<String, Optional<String>> setStartOrEndDate(Matcher matcher) {
        HashMap<String, Optional<String>> map = new HashMap<String, Optional<String>>();

        Optional<String> startDate = validateStartDateFormatsOneToThree(matcher);
        Optional<String> endDate = validateEndDateFormatsOneToThree(matcher);

        assert startDate.isPresent() ^ endDate.isPresent();

        map.put("startDate", startDate);
        map.put("endDate", endDate);

        return map;
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

    private Matcher generateMatcherForNoKeyword(String regex, String args) throws IllegalValueException {
        regex += REGEX_CLOSE_BRACE + REGEX_RECURRENCE_PRIORITY_CLOSE_BRACE;
        return generateMatcher(regex, args);
    }

    private Matcher generateMatcher(String regex, String args) throws IllegalValueException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(args);
        validateMatcherMatches(matcher);
        return matcher;
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
