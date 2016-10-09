package seedu.taskman.model;

public class Regex {
    // Examples: tdy 2359, tmr 0000, mon 0400, this tue 1600, next thu 2200
    public static final String DATE_TIME_TYPIST_FRIENDLY =
            "((tdy|tmr)|(((this)|(next))? (mon|tue|wed|thu|fri|sat|sun))) (([01][0-9])|)(2[0-3])[0-5][0-9]";
    public static final String DESCRIPTION_DATE_TIME_TYPIST_FRIENDLY =
            "[this/next] tdy/tmr/mon/tue/wed/thu/fri/sat/sun HHMM.";

    public static final String DURATION_TYPIST_FRIENDLY = "([1-9]+[0-9]*)(min|hr|d|wk|mth|yr)";
    public static final String DESCRIPTION_DURATION_TYPIST_FRIENDLY = "<number><min/hr/d/wk/mth/yr>";
}
