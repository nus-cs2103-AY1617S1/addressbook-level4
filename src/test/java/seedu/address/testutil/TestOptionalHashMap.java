package seedu.address.testutil;

import java.util.HashMap;
import java.util.Optional;

//@@author A0139655U
public class TestOptionalHashMap {
    public HashMap<String, Optional<String>> map;
        
    public TestOptionalHashMap() {
        map = new HashMap<String, Optional<String>>();
        
        map.put("taskName", Optional.empty());
        map.put("startDate", Optional.empty());
        map.put("endDate", Optional.empty());
        map.put("rate", Optional.empty());
        map.put("timePeriod", Optional.empty());
        map.put("priority", Optional.empty());
    }
    
    public TestOptionalHashMap(String taskNameString, String startDateString, String endDateString, 
            String rateString, String timePeriodString, String priorityString) {
        map = new HashMap<String, Optional<String>>();
        
        map.put("taskName", Optional.ofNullable(taskNameString));
        map.put("startDate", Optional.ofNullable(startDateString));
        map.put("endDate", Optional.ofNullable(endDateString));
        map.put("rate", Optional.ofNullable(rateString));
        map.put("timePeriod", Optional.ofNullable(timePeriodString));
        map.put("priority", Optional.ofNullable(priorityString));
    }
}
