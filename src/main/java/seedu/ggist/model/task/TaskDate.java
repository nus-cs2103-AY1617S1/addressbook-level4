package seedu.ggist.model.task;


import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

/**
 * Represents a Task's taskDate in the GGist.
 * Guarantees: immutable; is valid as declared in right format
 */
public class TaskDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date is unreadable and should only contains letters and digits. ";
    public static final String DATE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public String value;

    /**
     * Validates given taskDate.
     *
     * @throws IllegalValueException if given taskDate string is invalid.
     */
    public TaskDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if(date.equals(Messages.MESSAGE_NO_DATE_SPECIFIED)) {
            this.value = date;
        } else if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
            this.value = sdf.format(new PrettyTimeParser().parse(date).get(0)).toString();
        }
    }

    /**
     * Returns if a given string is a valid taskDate.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX) && new PrettyTimeParser().parse(test).size() != 0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instance of handles nulls
                && this.value.equals(((TaskDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}