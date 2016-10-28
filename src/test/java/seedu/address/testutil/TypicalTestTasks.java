package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.model.task.*;

/**
 *
 */
//@@author A0139749L
public class TypicalTestTasks {

    public static TestTask application, party, book, food, haircut, fines, tutorial, homework, groceries;
    
    public TypicalTestTasks() {
        try {
            application =  new TaskBuilder().withDescription("Complete application form for SEP").withDateTime("by 22 Jan 2014, 12:01")
                    .withTags("urgent").build();
            party = new TaskBuilder().withDescription("Fred birthday party").withDateTime("by 21/11/2015, 11:00")
                    .withTags("buyGift", "friends").build();
            book = new TaskBuilder().withDescription("Return book to library").withDateTime("on 01-05-2015").build();
            food = new TaskBuilder().withDescription("Bring food for party").withDateTime("on 01 06 2015").build();
            haircut = new TaskBuilder().withDescription("Go for a haircut").withDateTime("on 30 Nov").build();
            fines = new TaskBuilder().withDescription("Pay for parking fines")
                    .withDateTime("from 30/01/2017, 11:00 to 28/02/2018, 12:00").build();
            tutorial = new TaskBuilder().withDescription("Complete tutorial for EE2020")
                    .withDateTime("from 30 May, 13:00 to 06 Jun, 14:00").build();

            //Manually added
            homework = new TaskBuilder().withDescription("Do Homework").withDateTime("by 20/03/2016, 14:01").build();
            groceries = new TaskBuilder().withDescription("Buy groceries for mum").withDateTime("on 20 Dec 2016").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadEmeraldoWithSampleData(Emeraldo ab) {

        try {
            ab.addTask(new Task(application));
            ab.addTask(new Task(party));
            ab.addTask(new Task(book));
            ab.addTask(new Task(food));
            ab.addTask(new Task(haircut));
            ab.addTask(new Task(fines));
            ab.addTask(new Task(tutorial));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{application, party, book, food, haircut, fines, tutorial};
    }

    public Emeraldo getTypicalEmeraldo(){
        Emeraldo ab = new Emeraldo();
        loadEmeraldoWithSampleData(ab);
        return ab;
    }
}
