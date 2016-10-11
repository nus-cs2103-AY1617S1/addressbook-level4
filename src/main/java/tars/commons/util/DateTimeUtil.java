package tars.commons.util;

/**
 * Date Time Utility package
 * @@author Joel Foo
 *
 */
public class DateTimeUtil {
    /**
     * Extracts the new task's dateTime from the string arguments.
     * @return String[] with first index being the startDate time 
     * and second index being the end date time
     */
    public static String[] getDateTimeFromArgs(String taskArguments) {
        if (taskArguments.equals("")) {
            return new String[] {"", ""};
        } else if (taskArguments.contains("to")) {
            int toIndex = taskArguments.indexOf("to");
            String startDateTime = taskArguments.substring(0, toIndex).trim();
            String endDateTime = taskArguments.substring(toIndex + 2).trim();
            return new String[] {startDateTime, endDateTime};
        } else {
            return new String[] {"", taskArguments.trim()};
        }
    }
}
