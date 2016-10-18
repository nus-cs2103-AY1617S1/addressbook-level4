package teamfour.tasc.ui.calendar;

import java.time.LocalDateTime;

import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;

public class CalendarReadOnlyRecurredAppointment extends CalendarReadOnlyAppointment {

    private final Deadline deadlineForOccurrence;
    private final Period periodForOccurence;
    
    public CalendarReadOnlyRecurredAppointment(ReadOnlyTask associatedTask,
            Deadline deadlineForOccurrence, Period periodForOccurence) {
        super(associatedTask);

        this.deadlineForOccurrence = deadlineForOccurrence;
        this.periodForOccurence = periodForOccurence;
    }

    @Override
    public LocalDateTime getStartLocalDateTime() {
        if (deadlineForOccurrence.hasDeadline()) {
            return convertToLocalDateTime(deadlineForOccurrence.getDeadline());
        }
        
        if (periodForOccurence.hasPeriod()) {
            return convertToLocalDateTime(periodForOccurence.getStartTime());
        }

        return null;
    }

    @Override
    public LocalDateTime getEndLocalDateTime() {
        if (deadlineForOccurrence.hasDeadline()) {
            return convertToLocalDateTime(deadlineForOccurrence.getDeadline()).plusHours(1);
        }
        
        if (periodForOccurence.hasPeriod()) {
            return convertToLocalDateTime(periodForOccurence.getEndTime());
        }

        return null;
    }
}
