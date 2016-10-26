package seedu.whatnow.testutil;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.WhatNow;
import seedu.whatnow.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v;

    public TypicalTestTasks() {
        try {
            a = new TaskBuilder().withName("Apricot & Vanilla Shake").withDate("23/2/2017").withTaskType("schedule").withTags("low").build();
            b = new TaskBuilder().withName("Business meeting").withDate("24/12/2016").withTaskType("schedule").withTags("high").build();
            c = new TaskBuilder().withName("Chocolate cake mix").withDate("11/10/2017").withTaskType("schedule").withTags("").build();
            d = new TaskBuilder().withName("Doggie food").withDate("18/12/2016").withTaskType("schedule").withTags("medium").build();
            e = new TaskBuilder().withName("Economics Class").withDate("20/10/2018").withTaskType("schedule").withTags("").build();
            f = new TaskBuilder().withName("Family Day Outing").withDate("10/08/2017").withTaskType("schedule").withTags("medium").build();
            g = new TaskBuilder().withName("Grape juice").withDate("24/2/2017").withTime("4.45pm").withTaskType("schedule").withTags("low").build();
            j = new TaskBuilder().withName("Jam sanwich").withDate("today").withTime("3pm").withTaskType("schedule").withTags("low").build();
            k = new TaskBuilder().withName("Kimono Party").withDate("tommorrow").withStartTime("1:00pm").withEndTime("6:00pm").withTaskType("schedule").withTags("high").build();
            l = new TaskBuilder().withName("Lemon Meringue Pie Baking Contest").withStartDate("02/01/2017").withEndDate("05/01/2017").withStartTime("9am").withEndTime("12pm").withTaskType("schedule").withTags("high").build();
            m = new TaskBuilder().withName("Mardi Gras").withStartDate("28/02/2017").withEndTime("01/03/2017").withTaskType("schedule").withTags("medium").build();
            n = new TaskBuilder().withName("National Young Mathletes Competition").withStartDate("22/11/2016").withEndDate("30/11/2016").withTime("4.15pm").withTaskType("schedule").withTags("").build();
            o = new TaskBuilder().withName("Overseas School Trip").withTaskType("schedule").withStartDate("02/01/2020").withEndDate("05/01/2020").withTaskType("schedule").withTags("").build();
            p = new TaskBuilder().withName("Pool Party").withDate("23/2/2017").withStartTime("5.30pm").withEndTime("6.45pm").withTaskType("schedule").withTags("low").build();
            q = new TaskBuilder().withName("Quartet Performance at Esplanade").withStartDate("12/02/2017").withEndDate("24/02/2017").withStartTime("8am").withTaskType("schedule").withTags("low").build();
            r = new TaskBuilder().withName("Rationing Exercise").withStartDate("25/04/2018").withEndDate("31/05/2018").withStartTime("7.30pm").withEndTime("9.30pm").withTaskType("schedule").withTags("high").build();
            
            
            //Manually added
            h = new TaskBuilder().withName("Homemade avacado toast").withDate("24/2/2017").withTaskType("schedule").build();
            i = new TaskBuilder().withName("International Finance Forum").withDate("13/12/2016").withTime("10am").withTaskType("schedule").withTags("high").build();
            s = new TaskBuilder().withName("Strawberry Crumble Baking Showdown").withDate("today").withTaskType("schedule").build();
            t = new TaskBuilder().withName("Theatre Arts Major Camp").withStartDate("17/06/2019").withEndDate("29/07/2019").withTaskType("schedule").withTags("medium").build();
            u = new TaskBuilder().withName("University of Nevada, Las Vegas Open Day").withStartDate("tomorrow").withEndDate("13/12/2016").withStartTime("6pm").withEndTime("10pm").withTaskType("schedule").withTags("low").build();
            v = new TaskBuilder().withName("Victorian Museum Opening").withDate("today").withStartTime("8am").withEndTime("10.45am").withTaskType("schedule").build();
            
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
            assert false : "not possible";
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
            ab.addTask(new Task(j));
            ab.addTask(new Task(k));
            ab.addTask(new Task(l));
            ab.addTask(new Task(m));
            ab.addTask(new Task(n));
            ab.addTask(new Task(o));
            ab.addTask(new Task(p));
            ab.addTask(new Task(q));
            ab.addTask(new Task(r));
            
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{a, b, c, d, e, f, g, j, k, l, m, n, o, p, q, r};
    }

    public WhatNow getTypicalWhatNow(){
        WhatNow ab = new WhatNow();
        loadWhatNowWithSampleData(ab);
        return ab;
    }
}
