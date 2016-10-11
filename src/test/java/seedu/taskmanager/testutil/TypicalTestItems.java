package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.AddressBook;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.UniquePersonList;

/**
 *
 */
public class TypicalTestItems {

    public static TestItem event1, deadline1, task1, event2, deadline2, task2, event3, deadline3, task3;

    public TypicalTestItems() {
        try {
            event1 =  new ItemBuilder().withItemType("event").withStartDate("2016-06-06").withStartTime("05:00").withEndTime("12:01")
                    .withEndDate("2016-08-08").withName("Game of Life")
                    .withTags("friends").build();
            deadline1 = new ItemBuilder().withItemType("deadline").withStartDate("").withStartTime("").withEndTime("01:01")
                    .withEndDate("2016-12-06").withName("This is a deadline")
                    .withTags("work", "important").build();
            task1 = new ItemBuilder().withItemType("task").withName("Win at Life").withStartDate("").withStartTime("").withEndDate("").withEndTime("").build();
            event2 = new ItemBuilder().withItemType("event").withName("This is an event").withStartDate("2015-01-01").withStartTime("00:00").withEndDate("2015-01-01").withEndTime("23:59").build();
            deadline2 = new ItemBuilder().withItemType("deadline").withName("Pay my bills").withStartDate("").withStartTime("").withEndDate("2019-09-09").withEndTime("10:30").build();
            task2 = new ItemBuilder().withItemType("task").withName("This is a task").withStartDate("").withStartTime("").withEndDate("").withEndTime("").build();
            event3 = new ItemBuilder().withItemType("event").withName("2103 exam").withStartDate("2016-01-01").withStartTime("13:59").withEndDate("2017-01-03").withEndTime("15:00").build();

            //Manually added
            deadline3 = new ItemBuilder().withItemType("deadline").withName("Submit report").withStartDate("").withStartTime("").withEndDate("2016-09-30").withEndTime("21:14").build();
            task3 = new ItemBuilder().withItemType("task").withName("Buy a dozen cartons of milk").withStartDate("").withStartTime("").withEndDate("").withEndTime("").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addItem(new Item(event1));
            ab.addItem(new Item(deadline1));
            ab.addItem(new Item(task1));
            ab.addItem(new Item(event2));
            ab.addItem(new Item(deadline2));
            ab.addItem(new Item(task2));
            ab.addItem(new Item(event3));
        } catch (UniquePersonList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestItem[] getTypicalPersons() {
        return new TestItem[]{event1, deadline1, task1, event2, deadline2, task2, event3};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
