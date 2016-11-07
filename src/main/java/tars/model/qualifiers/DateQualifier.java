package tars.model.qualifiers;

// @@author A0140022H
import java.time.LocalDateTime;

import tars.commons.util.DateTimeUtil;
import tars.model.task.DateTime;
import tars.model.task.ReadOnlyTask;

public class DateQualifier implements Qualifier {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final DateTime dateTimeQuery;

    public DateQualifier(DateTime dateTime) {
        if (dateTime.getStartDate() != null) {
            startDateTime = DateTimeUtil.setLocalTime(dateTime.getStartDate(),
                    DateTimeUtil.DATETIME_FIRST_HOUR_OF_DAY,
                    DateTimeUtil.DATETIME_FIRST_MINUTE_OF_DAY,
                    DateTimeUtil.DATETIME_FIRST_SECOND_OF_DAY);
            endDateTime = DateTimeUtil.setLocalTime(dateTime.getEndDate(),
                    DateTimeUtil.DATETIME_LAST_HOUR_OF_DAY,
                    DateTimeUtil.DATETIME_LAST_MINUTE_OF_DAY,
                    DateTimeUtil.DATETIME_LAST_SECOND_OF_DAY);
        } else {
            startDateTime = DateTimeUtil.setLocalTime(dateTime.getEndDate(),
                    DateTimeUtil.DATETIME_FIRST_HOUR_OF_DAY,
                    DateTimeUtil.DATETIME_FIRST_HOUR_OF_DAY,
                    DateTimeUtil.DATETIME_FIRST_HOUR_OF_DAY);
            endDateTime = DateTimeUtil.setLocalTime(dateTime.getEndDate(),
                    DateTimeUtil.DATETIME_LAST_HOUR_OF_DAY,
                    DateTimeUtil.DATETIME_LAST_MINUTE_OF_DAY,
                    DateTimeUtil.DATETIME_LAST_SECOND_OF_DAY);
        }

        dateTimeQuery = new DateTime();
        dateTimeQuery.setStartDateTime(startDateTime);
        dateTimeQuery.setEndDateTime(endDateTime);
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return DateTimeUtil.isDateTimeWithinRange(task.getDateTime(),
                dateTimeQuery);
    }
}
