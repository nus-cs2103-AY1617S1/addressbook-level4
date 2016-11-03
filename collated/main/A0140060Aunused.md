# A0140060Aunused
###### /java/seedu/taskmanager/logic/parser/Parser.java
``` java
    //Used in earlier version, functionality replaced by ArgumentTokenizer
    private static final Pattern NAME_ARG_FORMAT = Pattern.compile("(n/(?<name>[^/#]+))");
    private static final Pattern START_DATE_ARG_FORMAT = Pattern.compile("(sd/(?<startDate>[^/#]+))");    
    private static final Pattern START_TIME_ARG_FORMAT = Pattern.compile("(st/(?<startTime>[^/#]+))");
    private static final Pattern START_DATETIME_ARG_FORMAT = Pattern.compile("sdt/(?<startDateTime>[^/#]+)");
    private static final Pattern END_DATE_ARG_FORMAT = Pattern.compile("(ed/(?<endDate>[^/#]+))");
    private static final Pattern END_TIME_ARG_FORMAT = Pattern.compile("(et/(?<endTime>[^/#]+))");
    private static final Pattern END_DATETIME_ARG_FORMAT = Pattern.compile("edt/(?<endDateTime>[^/#]+)");
    
```
###### /java/seedu/taskmanager/logic/parser/Parser.java
``` java
    //Used in earlier version, functionality replaced due to ArgumentTokenizer
    /**
     * Extracts argument from a string containing command arguments
     * @param argumentPattern the pattern used to extract the argument from commandArgs
     * @param argumentGroupName the matcher group name of the argument used in argumentPattern
     * @param commandArgs string containing command arguments
     * @return parsed argument as string or null if argument not parsed 
     */
    private String parseArgument(Pattern argumentPattern, String argumentGroupName, String commandArgs) {
        String argument = null;
        final Matcher argumentMatcher = argumentPattern.matcher(commandArgs);
        if (argumentMatcher.find()) {
            argument = argumentMatcher.group(argumentGroupName);
            argument = removeTrailingCommandChars(argument, commandArgs);
        }
        return argument;
    }

    /**
     * Removes unwanted trailing command characters from argument
     * @param argument
     * @param commandArgs
     * @return cleaned argument string
     */
    private String removeTrailingCommandChars(String argument, String commandArgs) {
        //maximum size of trailing command characters is 3, including the space before them
        if (argument.length() >= 3 && argument.length() < commandArgs.trim().length()-3) {
            //size of trailing name command characters is 2, including the space before it
            if (argument.substring(argument.length()-2, argument.length()).matches(" n")) {
                argument = argument.substring(0, argument.length()-2);
            } else if (argument.substring(argument.length()-3, argument.length()).matches(" (sd|st|ed|et)")) {
            //size of trailing command characters is 3, including the space before them
                argument = argument.substring(0, argument.length()-3);
            }
        }
        return argument;
    }
    
```
###### /java/seedu/taskmanager/model/item/Priority.java
``` java
//Functionality scrapped
/**
 * Represents a Item's priority in the task manager.
 */
public class Priority {

	public static final String HIGH_PRIORITY_WORD = "event";
	public static final String MEDIUM_PRIORITY_WORD = "deadline";
	public static final String LOW_PRIORITY_WORD = "task";
	
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Item types should only be 'task', 'deadline' or 'event'.";
    public static final String PRIORITY_VALIDATION_REGEX = HIGH_PRIORITY_WORD 
                                                           + "|" + MEDIUM_PRIORITY_WORD 
                                                           + "|" + LOW_PRIORITY_WORD;

    public final String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        priority = priority.trim();
        if (!isValidPriority(priority.toLowerCase())) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
