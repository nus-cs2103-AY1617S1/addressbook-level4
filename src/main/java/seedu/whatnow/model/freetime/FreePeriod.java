//@@author A0139772U
package seedu.whatnow.model.freetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import java.util.logging.Logger;

import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.model.task.Task;

/**
 * Stores the freeslots(time period with no time-based tasks) of a given date.
 */
public class FreePeriod {
    
    private static final Logger logger = LogsCenter.getLogger(FreePeriod.class);
    
    private ArrayList<Period> freePeriod;

    private static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mma";

    public FreePeriod() {
        freePeriod = new ArrayList<Period>();
        freePeriod.add(new Period("12:00am", "11:59pm"));
    }

    public ArrayList<Period> getList() {
        return freePeriod;
    }

    public void block(String start, String end) {
        DateFormat df = new SimpleDateFormat(TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT);
        df.setLenient(false);
        try {
            Date reqStartTime = df.parse(start);
            Date reqEndTime = df.parse(end);
            Date freeSlotStartTime;
            Date freeSlotEndTime;
            for (int i = 0; i < freePeriod.size(); i++) {
                Period curr = freePeriod.get(i);
                freeSlotStartTime = df.parse(curr.getStart());
                freeSlotEndTime = df.parse(curr.getEnd());
                if (isWithinThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    freePeriod.remove(i);
                    freePeriod.add(new Period(curr.getStart(), start));
                    freePeriod.add(new Period(end, curr.getEnd()));
                } else if (isExactlyThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    freePeriod.remove(i);
                } else if (isHeadOfThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    curr.setStart(df.format(reqEndTime));
                } else if (isTailOfThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    curr.setEnd(df.format(reqStartTime));
                } else if (isPartlyBeforeThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    curr.setStart(df.format(reqEndTime));
                } else if (isPartlyAfterThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    curr.setEnd(df.format(reqStartTime));
                } else if (isBiggerThanThisPeriod(reqStartTime, reqEndTime, freeSlotStartTime, freeSlotEndTime)) {
                    freePeriod.remove(i);
                } else {
                }
            }
        } catch (ParseException e) {
            logger.warning("ParseException at FreePeriod: \n" + e.getMessage());
        }
    }

    /**
     * 
     * @param reqStartTime:
     *            The start time of the task
     * @param reqEndTime:
     *            The end time of the task
     * @param freeSlotStartTime:
     *            The start time of a free time block
     * @param freeSlotEndTime:
     *            The end time of a free time block
     * @return true if time period of task is within the time period of a free
     *         block
     */
    private boolean isWithinThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime,
            Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) > 0 && reqEndTime.compareTo(freeSlotEndTime) < 0;
    }

    /**
     * 
     * @param reqStartTime:
     *            The start time of the task
     * @param reqEndTime:
     *            The end time of the task
     * @param freeSlotStartTime:
     *            The start time of a free time block
     * @param freeSlotEndTime:
     *            The end time of a free time block
     * @return true if time period of task coincides with the start of the free
     *         time block
     */
    private boolean isHeadOfThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime,
            Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) == 0 && reqEndTime.compareTo(freeSlotEndTime) < 0;
    }

    /**
     * 
     * @param reqStartTime:
     *            The start time of the task
     * @param reqEndTime:
     *            The end time of the task
     * @param freeSlotStartTime:
     *            The start time of a free time block
     * @param freeSlotEndTime:
     *            The end time of a free time block
     * @return true if time period of task coincides with the end of the free
     *         time block
     */
    private boolean isTailOfThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime,
            Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) > 0 && reqEndTime.compareTo(freeSlotEndTime) == 0;
    }

    /**
     * 
     * @param reqStartTime:
     *            The start time of the task
     * @param reqEndTime:
     *            The end time of the task
     * @param freeSlotStartTime:
     *            The start time of a free time block
     * @param freeSlotEndTime:
     *            The end time of a free time block
     * @return true if time period of task overlaps with the free time block and
     *         earlier
     */
    private boolean isPartlyBeforeThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime,
            Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) < 0 && reqEndTime.compareTo(freeSlotStartTime) > 0
                && reqEndTime.compareTo(freeSlotEndTime) < 0;
    }

    /**
     * 
     * @param reqStartTime:
     *            The start time of the task
     * @param reqEndTime:
     *            The end time of the task
     * @param freeSlotStartTime:
     *            The start time of a free time block
     * @param freeSlotEndTime:
     *            The end time of a free time block
     * @return true if time period of task overlaps with the free tiem block and
     *         later
     */
    private boolean isPartlyAfterThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime,
            Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) > 0 && reqStartTime.compareTo(freeSlotEndTime) < 0
                && reqEndTime.compareTo(freeSlotEndTime) > 0;
    }

    /**
     * 
     * @param reqStartTime:
     *            The start time of the task
     * @param reqEndTime:
     *            The end time of the task
     * @param freeSlotStartTime:
     *            The start time of a free time block
     * @param freeSlotEndTime:
     *            The end time of a free time block
     * @return true if time period of task is within the time period of a free
     *         block
     */
    private boolean isBiggerThanThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime,
            Date freeSlotEndTime) {
        return (reqStartTime.compareTo(freeSlotStartTime) < 0 && reqEndTime.compareTo(freeSlotEndTime) >= 0)
                || (reqStartTime.compareTo(freeSlotStartTime) <= 0 && reqEndTime.compareTo(freeSlotEndTime) >= 0);
    }

    /**
     * 
     * @param reqStartTime:
     *            The start time of the task
     * @param reqEndTime:
     *            The end time of the task
     * @param freeSlotStartTime:
     *            The start time of a free time block
     * @param freeSlotEndTime:
     *            The end time of a free time block
     * @return true if time period of task is exactly the time period of a free
     *         block
     */
    private boolean isExactlyThisPeriod(Date reqStartTime, Date reqEndTime, Date freeSlotStartTime,
            Date freeSlotEndTime) {
        return reqStartTime.compareTo(freeSlotStartTime) == 0 && reqEndTime.compareTo(freeSlotEndTime) == 0;
    }

    @Override
    public String toString() {
        return freePeriod.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(freePeriod);
    }
}
