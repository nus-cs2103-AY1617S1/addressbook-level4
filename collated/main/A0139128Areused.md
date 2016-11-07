# A0139128Areused
###### \java\seedu\whatnow\commons\exceptions\IllegalValueException.java
``` java
package seedu.whatnow.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends Exception {
    /**
     * @param message
     *            should contain relevant information on the failed
     *            constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }
}
```
###### \java\seedu\whatnow\logic\commands\RedoCommand.java
``` java
    public final String ADD_COMMAND = "add";
    public final String DELETE_COMMAND = "delete";
    public final String LIST_COMMAND = "list";
    public final String MARKDONE_COMMAND = "done";
    public final String MARKUNDONE_COMMAND = "undone";
    public final String UPDATE_COMMAND = "update";
    public final String CLEAR_COMMAND = "clear";
    public final String CHANGE_COMMAND = "change";

```
###### \java\seedu\whatnow\logic\commands\RedoCommand.java
``` java
    private CommandResult performReqRedo(String reqCommand) throws TaskNotFoundException {
        if (reqCommand.equals(ADD_COMMAND)) {
            return performRedoAdd();
        } else if (reqCommand.equals(DELETE_COMMAND)) {
            return performRedoDelete();
        } else if (reqCommand.equals(LIST_COMMAND)) {
            return performRedoList();
        } else if (reqCommand.equals(MARKDONE_COMMAND)) {
            return performRedoMarkDone();
        } else if (reqCommand.equals(MARKUNDONE_COMMAND)) {
            return performRedoMarkUnDone();
        } else if (reqCommand.equals(UPDATE_COMMAND)) {
            return performRedoUpdate();
        } else if (reqCommand.equals(CLEAR_COMMAND)) {
            return performRedoClear();
        } else if(reqCommand.equals(CHANGE_COMMAND)) {
            return performRedoChange();
        } else
            return new CommandResult(UNKNOWN_COMMAND_FOUND);
    }
    
```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    @Override
    public ReadOnlyWhatNow getWhatNow() {
        return whatNow;
    }

```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    @Override
    public synchronized int deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        int indexRemoved = whatNow.removeTask(target);
        unblockFreeTime();
        indicateWhatNowChanged();
        return indexRemoved;
    }

```
###### \java\seedu\whatnow\model\task\TaskTime.java
``` java
    private static final Pattern TODAY = Pattern.compile("((?:today|tdy))", Pattern.CASE_INSENSITIVE);
    private static final Pattern TOMORROW = Pattern.compile("((?:tomorrow|tmr))", Pattern.CASE_INSENSITIVE);

    private static final Pattern DAYS_IN_FULL = Pattern
            .compile("^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern DAYS_IN_SHORT = Pattern.compile("^(mon|tue|tues|wed|thu|thur|fri|sat|sun)$",
            Pattern.CASE_INSENSITIVE);

```
###### \java\seedu\whatnow\model\task\TaskTime.java
``` java
    private boolean isValidNumDate(String reqDate) {
        /** First check: whether if this date is of a valid format */
        if (TaskDate.isDay(reqDate)) {
            reqDate = TaskDate.formatDayToDate(reqDate);
        }
        Date inputDate = null;
        try {
            DateFormat df = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
            df.setLenient(false);

            inputDate = df.parse(reqDate);
        } catch (ParseException ex) {
            logger.warning("TaskTime.java:\n" + ex.getMessage());
            return false;
        }
        Calendar input = new GregorianCalendar();
        input = setGregorian(input, inputDate);
        inputDate = input.getTime();

        /**
         * Following checks if the user input date is invalid i.e before today's
         * date
         */
        Calendar current = new GregorianCalendar();
        current = setGregorianCurrent(current);
        Date currDate = current.getTime();
        if (currDate.compareTo(inputDate) > 0) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a particular Date range is valid i.e. startDate is before
     * endDate
     * 
     * @return true if range is valid, false if range is invalid
     */
```
###### \java\seedu\whatnow\model\task\TaskTime.java
``` java
    private boolean isValidDateRange(String beforeDate, String afterDate) {
        if (beforeDate == null && afterDate == null) {
            return true;
        }
        boolean convertedFromDay = false;
        if (TaskDate.isDay(beforeDate) && TaskDate.isDay(afterDate)) {
            beforeDate = TaskDate.formatDayToDate(beforeDate);
            afterDate = TaskDate.formatDayToDate(afterDate);
            convertedFromDay = true;
        } else if (TaskDate.isDay(beforeDate)) {
            beforeDate = TaskDate.formatDayToDate(beforeDate);
            convertedFromDay = true;
        } else if (TaskDate.isDay(afterDate)) {
            afterDate = TaskDate.formatDayToDate(afterDate);
            convertedFromDay = true;
        }
        boolean validDateRange = false;
        boolean sameDate = false;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        Date beginDate = null;
        Date finishDate = null;
        try {
            beginDate = sdf.parse(beforeDate);
            finishDate = sdf.parse(afterDate);
            if (beginDate.before(finishDate)) {
                validDateRange = true;
            } else {
                if (convertedFromDay) {
                    afterDate = TaskDate.isBeforeEarlierThanAfter(finishDate);
                    finishDate = sdf.parse(afterDate);
                    if (beginDate.before(finishDate)) {
                        validDateRange = true;
                    }
                }
            }
            if (beginDate.equals(finishDate)) {
                sameDate = true;
            }
        } catch (ParseException e) {
            return false;
        }
        Calendar before = new GregorianCalendar();
        before = setGregorian(before, beginDate);
        beginDate = before.getTime();

        Calendar after = new GregorianCalendar();
        after = setGregorian(after, finishDate);
        finishDate = after.getTime();

        /**
         * Following checks if the user input date is invalid i.e before today's
         * date
         */
        Calendar current = new GregorianCalendar();
        current = setGregorianCurrent(current);
        Date currDate = current.getTime();

        if (currDate.compareTo(beginDate) > 0 || currDate.compareTo(finishDate) > 0) {
            return false;
        }
        if (!validDateRange && !sameDate) {
            return false;
        } else {
            startDate = beforeDate;
            endDate = afterDate;
            return true;
        }
    }

    /**
     * This method sets the date to be of the latest time as a date always comes
     * attached with a default time and there is a need to overwrite this timing
     * to the latest so that it can be compared with the current date
     */
    private Calendar setGregorian(Calendar cal, Date reqDate) {
        cal.setTime(reqDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal;
    }

    /** Gets the current Date and set it to earliest */
    private Calendar setGregorianCurrent(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        return cal;
    }
}
```
