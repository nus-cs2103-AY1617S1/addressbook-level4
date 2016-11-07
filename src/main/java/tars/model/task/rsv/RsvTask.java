package tars.model.task.rsv;

import java.util.ArrayList;
import java.util.Objects;
import tars.commons.util.CollectionUtil;
import tars.model.task.DateTime;
import tars.model.task.Name;

// @@author A0124333U
/**
 * A task that has unconfirmed, reserved dates.
 */
public class RsvTask {

    protected Name name;
    protected ArrayList<DateTime> dateTimeList = new ArrayList<DateTime>();

    private static String RSV_TASK_STRING = "%1$s DateTime: %2$s";

    public RsvTask() {

    }

    public RsvTask(Name name, ArrayList<DateTime> dateTimeList) {
        assert !CollectionUtil.isAnyNull(name, dateTimeList);

        this.name = name;
        this.dateTimeList = dateTimeList;

    }

    /**
     * Copy constructor.
     */
    public RsvTask(RsvTask source) {
        this(source.getName(), source.getDateTimeList());
    }

    /*
     * Accessors
     */

    public Name getName() {
        return name;
    }

    public ArrayList<DateTime> getDateTimeList() {
        return dateTimeList;
    }

    /*
     * Mutators
     */
    public void setName(Name name) {
        this.name = name;
    }

    public void setDateTimeList(ArrayList<DateTime> dateTimeList) {
        this.dateTimeList = dateTimeList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RsvTask // instanceof handles nulls
                        && this.isSameStateAs((RsvTask) other));
    }

    public boolean isSameStateAs(RsvTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                        && other.getName().equals(this.getName())
                        && other.getDateTimeList()
                                .equals(this.getDateTimeList()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, dateTimeList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format(RSV_TASK_STRING, getName(),
                getDateTimeList().toString()));

        return builder.toString();
    }

}
