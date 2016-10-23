package seedu.emeraldo.model.task;

import java.util.regex.Pattern;

public class DateTimeParser {
    private static final String OPTIONAL_TIME_REGEX = "( (?<hour>([01][0-9]|[2][0-3])))?"
            + "(:(?<minute>([0-5][0-9])))?";

    private static final String OPTIONAL_END_DATE_TIME_REGEX = "( (?<aftKeyword>(to )))?"
            + "(?<dayEnd>(0?[1-9]|[12][0-9]|3[01]))?"
            + "(/(?<monthEnd>(0?[1-9]|[1][0-2]))/)?"
            + "(?<yearEnd>(([0-9][0-9])?[0-9][0-9]))?"
            + "( (?<hourEnd>([01][0-9]|[2][0-3])))?"
            + "(:(?<minuteEnd>([0-5][0-9])))?";

    private static final String END_DATE_TIME_REGEX = "( (?<aftKeyword>(to )))"
            + "(?<dayEnd>(0?[1-9]|[12][0-9]|3[01]))"
            + "(/(?<monthEnd>(0?[1-9]|[1][0-2]))/)"
            + "(?<yearEnd>(([0-9][0-9])?[0-9][0-9]))"
            + "( (?<hourEnd>([01][0-9]|[2][0-3])))"
            + "(:(?<minuteEnd>([0-5][0-9])))";

    private static final String TIME_VALIDATION_REGEX = "( (?<hour>([01][0-9]|[2][0-3])))"
        + "(:(?<minute>([0-5][0-9])))";

    private static final String DATE_VALIDATION_REGEX = "(?<day>(0?[1-9]|[12][0-9]|3[01]))"
        + "/(?<month>(0?[1-9]|[1][0-2]))/"
        + "(?<year>(([0-9][0-9])?[0-9][0-9]))";

    public static final String ON_KEYWORD_VALIDATION_REGEX = "on " + DATE_VALIDATION_REGEX;

    public static final String BY_KEYWORD_VALIDATION_REGEX = "by " + DATE_VALIDATION_REGEX
        + TIME_VALIDATION_REGEX;

    public static final String FROM_KEYWORD_VALIDATION_REGEX = "from " + DATE_VALIDATION_REGEX
        + TIME_VALIDATION_REGEX
        + END_DATE_TIME_REGEX;

    public static final Pattern DATETIME_VALIDATION_REGEX =
        Pattern.compile("(?<preKeyword>((by )|(on )|(from )))"      //Preceding keyword regex
        + DATE_VALIDATION_REGEX
        + OPTIONAL_TIME_REGEX
        + OPTIONAL_END_DATE_TIME_REGEX);
}
