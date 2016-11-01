//@@author A0139772U
package seedu.whatnow.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.util.CollectionUtil;
import seedu.whatnow.model.tag.UniqueTagList;

/**
 * Represents a Task in WhatNow. Guarantees: details are present and not null,
 * field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {
    private static final Logger logger = LogsCenter.getLogger(Task.class);

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
    private String taskType;

    private static final String FLOATING = "floating";
    private static final String NOT_FLOATING = "not_floating";
    private static final int COMPARE_TO_IS_EQUAL = 0;
    private static final int COMPARE_TO_IS_NOT_EQUAL = 0;
    private static final int COMPARE_TO_SMALLER = -1;
    private static final int COMPARE_TO_BIGGER = 1;
    private static final String DEFAULT_DATE = "01/01/2001";
    private static final String DEFAULT_START_TIME = "12:00am";
    private static final String DEFAULT_END_TIME = "11:59pm";
    public static final String TWELVE_HOUR_WITH_MINUTES_COLON_FORMAT = "h:mma";
    private static final String DATE_NUM_SLASH_WITH_YEAR_FORMAT = "dd/MM/yyyy";

    public Task() {

    }

    // @@author A0126240W
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, String taskDate, String startDate, String endDate, String taskTime, String startTime, String endTime, String period, String endPeriod, UniqueTagList tags, String status, String taskType) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.period = period;
        this.endPeriod = endPeriod;
        this.tags = new UniqueTagList(tags);
        this.status = status;
        this.taskType = FLOATING;
        if (taskDate != null) {
            this.taskDate = taskDate;
            this.taskType = NOT_FLOATING;
        }

        if (startDate != null) {
            this.startDate = startDate;
            this.taskType = NOT_FLOATING;
        }

        if (endDate != null) {
            this.endDate = endDate;
            this.taskType = NOT_FLOATING;
        }

        if (taskTime != null) {
            this.taskTime = taskTime;
            this.taskType = NOT_FLOATING;
        }

        if (startTime != null) {
            this.startTime = startTime;
            this.taskType = NOT_FLOATING;
        }

        if (endTime != null) {
            this.endTime = endTime;
            this.taskType = NOT_FLOATING;
        }
        
        if (taskType != null) {
            this.taskType = taskType;
        }
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTaskDate(), source.getStartDate(), source.getEndDate(), source.getTaskTime(), source.getStartTime(), source.getEndTime(), source.getPeriod(), source.getEndPeriod(), source.getTags(), source.getStatus(), source.getTaskType());
    }

    @Override
    public Name getName() {
        return name;
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

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getTaskType() {
        return taskType;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setTaskDate(String date) {
        this.taskDate = date;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTaskTime(String time) {
        this.taskTime = time;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }
    
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    // @@author A0139772U
    public int compareTo(Task task) {
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

    private String getStartingDate(Task task) {
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

    private String getEndingDate(Task task) {
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

    private String getStartingTime(Task task) {
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

    private String getEndingTime(Task task) {
        String otherEndingTime = task.getEndTime();

        if (otherEndingTime == null) {
            otherEndingTime = DEFAULT_END_TIME;
        }

        return otherEndingTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, taskDate, startDate, endDate, taskTime, startTime, endTime, tags, status, taskType);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
