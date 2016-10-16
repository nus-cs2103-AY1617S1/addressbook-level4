package seedu.address.testutil;

import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask application, party, book, food, haircut, fines, tutorial, homework, groceries;

    public TypicalTestTasks() {
        try {
            application =  new TaskBuilder().withDescription("Complete application form for SEP").withDateTime("22/01/2014 12:01")
                    .withTags("urgent").build();
            party = new TaskBuilder().withDescription("Fred birthday party").withDateTime("21/03/2015 11:00")
                    .withTags("buyGift", "friends").build();
            book = new TaskBuilder().withDescription("Return book to library").withDateTime("01/05/2015").build();
            food = new TaskBuilder().withDescription("Bring food for party").withDateTime("01/06/2015").build();
            haircut = new TaskBuilder().withDescription("Go for a haircut").withDateTime("30/11/2016").build();
            fines = new TaskBuilder().withDescription("Pay for parking fines").withDateTime("30/01/2017").build();
            tutorial = new TaskBuilder().withDescription("Complete tutorial for EE2020").withDateTime("30/05/2017").build();

            //Manually added
            homework = new TaskBuilder().withDescription("Do Homework").withDateTime("20/11/2016").build();
            groceries = new TaskBuilder().withDescription("Buy groceries for mum").withDateTime("20/12/2016").build();
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
