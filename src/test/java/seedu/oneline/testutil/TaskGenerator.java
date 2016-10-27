package seedu.oneline.testutil;

import java.util.Random;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.TaskRecurrence;
import seedu.oneline.model.task.TaskTime;

public class TaskGenerator {

    public static Task generateTask() {
        try {
            int type = rand.nextInt(2);
            switch (type) {
            case 0: // Floating task
                return new Task(
                            new TaskName(getNameString()),
                            TaskTime.getDefault(),
                            TaskTime.getDefault(),
                            TaskTime.getDefault(),
                            TaskRecurrence.getDefault(),
                            getTag()
                        );
            case 1: // Event
                TaskTime start = null;
                TaskTime end = null;
                do {
                start = new TaskTime(getTimeString());
                end = new TaskTime(getTimeString());
                } while (start.compareTo(end) >= 0);
                return new Task(
                        new TaskName(getNameString()),
                        start,
                        end,
                        TaskTime.getDefault(),
                        TaskRecurrence.getDefault(),
                        getTag()
                    );
            case 2: // Deadline
                return new Task(
                        new TaskName(getNameString()),
                        TaskTime.getDefault(),
                        TaskTime.getDefault(),
                        new TaskTime(getTimeString()),
                        TaskRecurrence.getDefault(),
                        getTag()
                    );
            }
        } catch (IllegalValueException e) {
            System.out.println("Invalid generation: " + e.getMessage());
        }
        return null;
    }
    
    private static Random rand = new Random();
    
    private static final String[] peopleNames = new String[] { "Alice", "Albert", "Aaron", "Benedict", "Bob", "Cathy", "Conan",
            "Dennis", "Deborah", "Edward", "Fabian", "Faye", "Gordon", "Gregory", "Henry", "Harry", "Judy",
            "Jane", "Jasper", "Ken", "Kaylyn", "Lenny", "Louis"
        };
    
    private static final String[] eventNames = new String[] { "Gym", "Meeting", "Business meeting", "Breakfast", "Lunch",
            "Dinner", "Supper", "Cycling", "Tennis", "Consultation", "Birthday celebration", "Shopping",
            "Movie", "Network"
            };
    
    private static final String[] soloEventNames = new String[] { "Check email", "Update report", "Prepare briefing", "Clear paperwork",
            "Send cheque to bank", "Arrange doctor appointment", "Schedule medical checkup", "Look for new laptop", "Order iPhone 7",
            "Update Windows", "Pay bills", "Pay insurance premium"
            };
            
    private static String getNameString() {
        int type = rand.nextInt(3);
        switch (type) {
        case 0:
            return chooseRand(eventNames) + " with " + chooseRand(peopleNames);
        case 1:
            return chooseRand(eventNames);
        case 2:
            return chooseRand(soloEventNames);
        default:
            return null;
        }
    }
    
    private static <T> T chooseRand(T[] arr) {
        return arr[rand.nextInt(arr.length)];
    }

    private static final String[] daysOfWeek = new String[] { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private static final String[] monthsOfYear = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                                                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    private static String getTimeString() {
        int type = rand.nextInt(2);
        switch (type) {
        case 0:
            return chooseRand(new String[] {"", "Next ", "Following "}) + chooseRand(daysOfWeek);
        case 1:
            return rand.nextInt(32) + " " +
                    chooseRand(monthsOfYear);
        default:
            return null;
        }
    }
    
    private static final String[] tags = new String[] { "OTOT", "Urgent", "Enjoyment", "Project", "Work", "Meeting",
            "Important", "Priority", "Buy", "Shopping", "Todo", "Revised", "Updated", "KIV", "ASAP"};
    
    private static Tag getTag() {
        if (rand.nextDouble() < 0.1) {
            return Tag.EMPTY_TAG;
        }
        String tag = chooseRand(tags);
        try {
            return Tag.getTag(tag);
        } catch (IllegalValueException e) {
            System.out.println("Invalid generation for tag [" + tag + "]: " + e.getMessage());
        }
        return Tag.EMPTY_TAG;
    }
    
}
