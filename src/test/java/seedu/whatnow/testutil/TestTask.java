package seedu.whatnow.testutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<TestTask> {
    private static final Logger logger = LogsCenter.getLogger(TestTask.class);

    private Name name;
    private String taskDate;
    private String startDate;
    private String endDate;
    private String taskTime;
    private String startTime;
    private String endTime;
    private String period;
    private String endPeriod;
    private UniqueTagList tags;
    private String status;
    private String taskType; // todo or schedule
    
    private static final String FLOATING = "floating";
    private static final String NOT_FLOATING = "not_floating";
    private static final int COMPARE_TO_IS_EQUAL = 0;
    private static final int COMPARE_TO_SMALLER = -1;
    private static final int COMPARE_TO_BIGGER = 1;
    private static final String DEFAULT_DATE = "01/01/2001";
    private static final String DEFAULT_START_TIME = "12:00am";
    private static final String DEFAULT_END_TIME = "11:59pm";
    public static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mma";
    private static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";

    public TestTask() throws IllegalValueException {
        setTaskDate(null);
        setStartDate(null);
        setEndDate(null);
        setTaskTime(null);
        setStartTime(null);
        setEndTime(null);
        setTaskType(null);
        setStatus("incomplete");
        tags = new UniqueTagList();
    }

    @Override
    public Name getName() {
        return this.name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    public String setStatus(String status) {
        return this.status = status;
    }

    @Override
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Override
    public String getTaskDate() {
        return taskDate;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public String getTaskTime() {
        return taskTime;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }
    
    @Override
    public String getPeriod() {
        return period;
    }
    
    @Override
    public String getEndPeriod() {
        return endPeriod;
    }

    public void setTaskDate(String date) {
        this.taskDate = date;
    }

    public void setStartDate(String date) {
        this.startDate = date;
    }

    public void setEndDate(String date) {
        this.endDate = date;
    }

    public void setTaskTime(String time) {
        this.taskTime = time;
    }

    public void setStartTime(String time) {
        this.startTime = time;
    }

    public void setEndTime(String time) {
        this.endTime = time;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, status, tags, taskType, startDate, endDate, startTime, endTime);
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add \"" + this.getName().fullName + "\" ");
        if (this.getStartDate() != null && !this.getStartDate().equals("")) {
            sb.append("on" + " " + this.getStartDate());
        } else if (this.getTaskDate() != null && !this.getTaskDate().equals("")) {
            sb.append("on" + " " + this.getTaskDate());
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append(" t/" + s.tagName + " "));
        return sb.toString();
    }
    
    private String getStartingDate() {
        String thisStartingDate;

        if (this.getTaskDate() == null) {
            thisStartingDate = this.getStartDate();
        } else {
            thisStartingDate = this.getTaskDate();
        }

        if (thisStartingDate == null) {
            thisStartingDate = DEFAULT_DATE;
        }

        return thisStartingDate;
    }

    private String getStartingDate(TestTask task) {
        String otherStartingDate;

        if (task.getTaskDate() == null) {
            otherStartingDate = task.getStartDate();
        } else {
            otherStartingDate = task.getTaskDate();
        }

        if (otherStartingDate == null) {
            otherStartingDate = DEFAULT_DATE;
        }

        return otherStartingDate;
    }

    private String getEndingDate() {
        String thisEndingDate = this.getEndDate();

        if (thisEndingDate == null) {
            thisEndingDate = DEFAULT_DATE;
        }

        return thisEndingDate;
    }

    private String getEndingDate(TestTask task) {
        String otherEndingDate = task.getEndDate();

        if (otherEndingDate == null) {
            otherEndingDate = DEFAULT_DATE;
        }

        return otherEndingDate;
    }

    private String getStartingTime() {
        String thisStartingTime;

        if (this.getTaskTime() == null) {
            thisStartingTime = this.getStartTime();
        } else {
            thisStartingTime = this.getTaskTime();
        }

        if (thisStartingTime == null) {
            thisStartingTime = DEFAULT_START_TIME;
        }

        return thisStartingTime;
    }

    private String getStartingTime(TestTask task) {
        String otherStartingTime;

        if (task.getTaskTime() == null) {
            otherStartingTime = task.getStartTime();
        } else {
            otherStartingTime = task.getTaskTime();
        }

        if (otherStartingTime == null) {
            otherStartingTime = DEFAULT_START_TIME;
        }

        return otherStartingTime;

    }

    private String getEndingTime() {
        String thisEndingTime = this.getEndTime();

        if (thisEndingTime == null) {
            thisEndingTime = DEFAULT_END_TIME;
        }

        return thisEndingTime;
    }

    private String getEndingTime(TestTask task) {
        String otherEndingTime = task.getEndTime();

        if (otherEndingTime == null) {
            otherEndingTime = DEFAULT_END_TIME;
        }

        return otherEndingTime;
    }
    
    //@@author A0139772U
    public int compareTo(TestTask task) {
        int compareToResult = 0;
        DateFormat df = new SimpleDateFormat(DATE_NUM_SLASH_WITH_YEAR_FORMAT);
        DateFormat tf = new SimpleDateFormat(TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT);
        try {
            Date thisStartDate = df.parse(getStartingDate());
            Date otherStartDate = df.parse(getStartingDate(task));
            Date thisEndDate = df.parse(getEndingDate());
            Date otherEndDate = df.parse(getEndingDate(task));
            Date thisStartTime = tf.parse(getStartingTime());
            Date otherStartTime = tf.parse(getStartingTime(task));
            Date thisEndTime = tf.parse(getEndingTime());
            Date otherEndTime = tf.parse(getEndingTime(task));

            if (thisStartDate.compareTo(otherStartDate) < 0) {
                compareToResult = COMPARE_TO_SMALLER;
            } else if (thisStartDate.compareTo(otherStartDate) > 0) {
                compareToResult = COMPARE_TO_BIGGER;
            } else if (thisEndDate.compareTo(otherEndDate) < 0) {
                compareToResult = COMPARE_TO_SMALLER;
            } else if (thisEndDate.compareTo(otherEndDate) > 0) {
                compareToResult = COMPARE_TO_BIGGER;
            } else if (thisStartTime.compareTo(otherStartTime) < 0) {
                compareToResult = COMPARE_TO_SMALLER;
            } else if (thisStartTime.compareTo(otherStartTime) > 0) {
                compareToResult = COMPARE_TO_BIGGER;
            } else if (thisEndTime.compareTo(otherEndTime) < 0) {
                compareToResult = COMPARE_TO_SMALLER;
            } else if (thisEndTime.compareTo(otherEndTime) > 0) {
                compareToResult = COMPARE_TO_BIGGER;
            } else {
                compareToResult = COMPARE_TO_IS_EQUAL;
            }
        } catch (ParseException e) {
            logger.warning("ParseException at Task: \n" + e.getMessage());
        }
        return compareToResult;
    }
}
