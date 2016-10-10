package seedu.menion.model.activity;

import java.util.ArrayList;

public class EventStub {
    public String type;
    public String name;
    public String startDate;
    public String startTime;
    public String endDate;
    public String endTime;
    public String notes;

    public EventStub(ArrayList<String> details) {
        this.type = details.get(0);
        this.name = details.get(1);
        this.notes = details.get(2);
        this.startDate = details.get(3);
        this.startTime = details.get(4);
        this.endDate = details.get(5);
        this.endTime = details.get(6);
    }
}
