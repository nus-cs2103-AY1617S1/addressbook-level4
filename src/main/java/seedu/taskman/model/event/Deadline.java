package seedu.taskman.model.event;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.logic.parser.DateTimeParser;

import java.util.Objects;

public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should only contain dates and times in the format: " +
                    DateTimeParser.DESCRIPTION_DATE_TIME_FULL;

    public final long epochSecond;

    public Deadline(String deadline) throws IllegalValueException {
        deadline = deadline.trim();
        epochSecond = DateTimeParser.getUnixTime(deadline);
    }

    public Deadline(long epochSecond) throws IllegalValueException {
        if (epochSecond < 0) {
            throw new IllegalValueException("Too far in the past.");
        }

        this.epochSecond = epochSecond;
    }

    public static boolean isValidDeadline(String test) {
        try {
            new Deadline(test);
            return true;
        } catch (IllegalValueException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return DateTimeParser.epochSecondToDetailedDateTime(epochSecond);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deadline deadline = (Deadline) o;
        return epochSecond == deadline.epochSecond;
    }

    @Override
    public int hashCode() {
        return Objects.hash(epochSecond);
    }
}
