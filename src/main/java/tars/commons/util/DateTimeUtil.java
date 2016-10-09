package tars.commons.util;

public class DateTimeUtil {
    /**
     * Extracts the new task's dateTime from the add command's task arguments string.
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
