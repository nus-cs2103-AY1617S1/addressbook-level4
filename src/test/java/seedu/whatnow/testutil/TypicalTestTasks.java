package seedu.whatnow.testutil;

import java.text.ParseException;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.WhatNow;
import seedu.whatnow.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask a, b, c, d, e, f, g, h, i;

    public TypicalTestTasks() {
        try {
            a = new TaskBuilder().withName("Buy apricots").withStartDate("").withTaskType("todo").withTags("low").withStatus("incomplete").build();
            b = new TaskBuilder().withName("Business meeting").withStartDate("24/12/2016").withTaskType("schedule").withTags("high").withStatus("incomplete").build();
            c = new TaskBuilder().withName("Buy cake").withStartDate("11/10/2017").withTaskType("todo").withTags("").withStatus("incomplete").build();
            d = new TaskBuilder().withName("Buy a doggie").withStartDate("18/10/2016").withTaskType("todo").withTags("medium").withStatus("incomplete").build();
            e = new TaskBuilder().withName("Economics Class").withStartDate("20/10/2016").withTaskType("schedule").withTags("").withStatus("incomplete").build();
            f = new TaskBuilder().withName("Family Day Outing").withStartDate("10/08/2017").withTaskType("schedule").withTags("medium").withStatus("incomplete").build();
            g = new TaskBuilder().withName("Buy grapes").withStartDate("").withTaskType("todo").withTags("low").withStatus("incomplete").build();
            
            
            //Manually added
            h = new TaskBuilder().withName("homemade avacado toast").withDate("").withTaskType("todo").withTags("").withStatus("incomplete").build();
            i = new TaskBuilder().withName("International Finance Forum").withDate("").withTaskType("todo").withTags("high").withStatus("incomplete").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public static void loadWhatNowWithSampleData(WhatNow ab) {

        try {
            ab.addTask(new Task(a));
            ab.addTask(new Task(b));
            ab.addTask(new Task(c));
            ab.addTask(new Task(d));
            ab.addTask(new Task(e));
            ab.addTask(new Task(f));
            ab.addTask(new Task(g));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{a, b, c, d, e, f, g};
    }

    public WhatNow getTypicalWhatNow(){
        WhatNow ab = new WhatNow();
        loadWhatNowWithSampleData(ab);
        return ab;
    }
}
