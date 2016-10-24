package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.UniqueItemList;

/**
 *
 */
public class TypicalTestItems {

    public static TestItem event1, deadline1, task1, event2, deadline2, task2, event3, deadline3, task3, event4, deadline4, task4, event5, deadline5, task5, event6, deadline6, task6;

    //@@author A0140060A
    public TypicalTestItems() {
        try {
            event1 =  new ItemBuilder().withItemType("event").withStartDate("2016-06-06").withStartTime("05:00").withEndTime("12:01")
                    .withEndDate("2016-08-08").withName("Game of Life").withTags("play", "forever").build();
            deadline1 = new ItemBuilder().withItemType("deadline").withStartDate("").withStartTime("").withEndTime("01:01")
                    .withEndDate("2016-12-06").withName("This is a deadline")
                    .withTags("work", "important").build();
            task1 = new ItemBuilder().withItemType("task").withName("Win at Life").withStartDate("").withStartTime("").withEndDate("").withEndTime("").build();
            event2 = new ItemBuilder().withItemType("event").withName("This is an event").withStartDate("2015-01-01").withStartTime("00:00").withEndDate("2015-01-01").withEndTime("23:59").build();
            deadline2 = new ItemBuilder().withItemType("deadline").withName("Pay my bills").withStartDate("").withStartTime("").withEndDate("2019-09-09").withEndTime("10:30").build();
            task2 = new ItemBuilder().withItemType("task").withName("This is a task").withStartDate("").withStartTime("").withEndDate("").withEndTime("").build();
            event3 = new ItemBuilder().withItemType("event").withName("2103 exam").withStartDate("2016-01-01").withStartTime("13:59").withEndDate("2017-01-03").withEndTime("15:00").build();
            deadline3 = new ItemBuilder().withItemType("deadline").withName("Submit report").withStartDate("").withStartTime("").withEndDate("2016-09-30").withEndTime("21:14").build();
            task3 = new ItemBuilder().withItemType("task").withName("Buy one dozen cartons of milk").withStartDate("").withStartTime("").withEndDate("").withEndTime("").build();
            event4 = new ItemBuilder().withItemType("event").withName("Java Workshop").withStartDate("2017-01-02").withStartTime("08:00").withEndDate("2017-01-02").withEndTime("12:00").withTags("important").build();

            //Manually added
            deadline4 = new ItemBuilder().withItemType("deadline").withName("Submit essay assignment").withStartDate("").withStartTime("").withEndDate("2016-11-28").withEndTime("21:29").build();
            task4 = new ItemBuilder().withItemType("task").withName("Call for the electrician").withStartDate("").withStartTime("").withEndDate("").withEndTime("").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    //@@author A0140060A-reused
    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addItem(new Item(event1));
            ab.addItem(new Item(deadline1));
            ab.addItem(new Item(task1));
            ab.addItem(new Item(event2));
            ab.addItem(new Item(deadline2));
            ab.addItem(new Item(task2));
            ab.addItem(new Item(event3));
            ab.addItem(new Item(deadline3));
            ab.addItem(new Item(task3));
            ab.addItem(new Item(event4));
//            ab.addItem(new Item(deadline4));
//            ab.addItem(new Item(task4));
        } catch (UniqueItemList.DuplicateItemException e) {
            assert false : "not possible";
        }
    }

    public TestItem[] getTypicalItems() {
        return new TestItem[]{event1, deadline1, task1, event2, deadline2, task2, event3, deadline3, task3,
                event4}; //deadline4, task4};
    }
    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
