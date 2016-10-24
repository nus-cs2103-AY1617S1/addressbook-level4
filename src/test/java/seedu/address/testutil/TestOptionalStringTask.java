package seedu.address.testutil;

import java.util.Optional;

public class TestOptionalStringTask {
    public Optional<String> taskName;
    public Optional<String> startDate;
    public Optional<String> endDate;
    public Optional<String> rate;
    public Optional<String> timePeriod;
    public Optional<String> priority;
        
    public TestOptionalStringTask() {
        taskName = startDate = endDate = rate = timePeriod = priority = Optional.empty();
    }
    
    public TestOptionalStringTask(String taskNameString, String startDateString, String endDateString, 
            String rateString, String timePeriodString, String priorityString) {
        taskName = Optional.of(taskNameString);
        startDate = Optional.of(startDateString);
        endDate = Optional.of(endDateString);
        rate = Optional.of(rateString);
        timePeriod = Optional.of(timePeriodString);
        priority = Optional.of(priorityString);
    }
}
