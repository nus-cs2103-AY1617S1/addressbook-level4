package tars.model.task.rsv;

import java.util.ArrayList;
import java.util.Objects;
import tars.commons.util.CollectionUtil;
import tars.model.task.DateTime;
import tars.model.task.Name;

/**
 * A task that has unconfirmed, reserved dates.
 * 
 * @@author A0124333U
 */

public class RsvTask {

    private Name name;
    private ArrayList<DateTime> dateTimeList;

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
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, dateTimeList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" DateTime: ").append(getDateTimeList().toString());

        return builder.toString();
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RsvTask // instanceof handles nulls
                        && ((RsvTask) other).getName().equals(this.getName())
                        && ((RsvTask) other).getDateTimeList().equals(this.getDateTimeList()));
    }

}