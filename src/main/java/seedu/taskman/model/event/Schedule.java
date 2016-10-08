package seedu.taskman.model.event;

import com.google.common.base.Objects;

public class Schedule {

    public final String value;
    // TODO: differ for now, design a schedule obj
    public Schedule(String schedule) {
        value = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // todo: change to instanceof? standardize with all other objs
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equal(value, schedule.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }


    @Override
    public String toString() {
        return value;
    }
}
