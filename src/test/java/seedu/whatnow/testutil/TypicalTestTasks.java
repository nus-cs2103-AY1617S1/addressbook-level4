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
            a = new TaskBuilder().withName("Apricot & Vanilla Shake").withDate("23/02/2017").withTaskType("schedule")
                    .withTags("low").build();
            b = new TaskBuilder().withName("Business meeting").withDate("24/12/2016").withTaskType("schedule")
                    .withTags("high").build();
            c = new TaskBuilder().withName("Chocolate cake mix").withDate("11/10/2017").withTaskType("schedule")
                    .withTags("").build();
            d = new TaskBuilder().withName("Doggie food").withDate("18/12/2016").withTaskType("schedule")
                    .withTags("medium").build();
            e = new TaskBuilder().withName("Economics Class").withDate("20/10/2018").withTaskType("schedule")
                    .withTags("").build();
            f = new TaskBuilder().withName("Family Day Outing").withDate("10/08/2017").withTaskType("schedule")
                    .withTags("medium").build();
            g = new TaskBuilder().withName("Grape juice").withDate("24/02/2017").withTime("4:45pm")
                    .withTaskType("schedule").withTags("low").build();
            j = new TaskBuilder().withName("Jam sanwich").withDate("25/02/2017").withTime("3:00pm").withTaskType("schedule")
                    .withTags("low").build();
            k = new TaskBuilder().withName("Kimono Party").withDate("12/02/2017").withStartTime("1:00pm")
                    .withEndTime("6:00pm").withTaskType("schedule").withTags("high").build();
            l = new TaskBuilder().withName("Lemon Meringue Pie Baking Contest").withStartDate("02/01/2017")
                    .withEndDate("05/01/2017").withStartTime("9:00am").withEndTime("12:00pm").withTaskType("schedule")
                    .withTags("high").build();
            m = new TaskBuilder().withName("Mardi Gras").withStartDate("28/02/2017").withEndDate("01/03/2017")
                    .withTaskType("schedule").withTags("medium").build();
            n = new TaskBuilder().withName("National Young Mathletes Competition").withStartDate("22/11/2016")
                    .withEndDate("30/11/2016").withStartTime("4:15pm").withEndTime("9:15pm").withTaskType("schedule").withTags("").build();
            o = new TaskBuilder().withName("Overseas School Trip").withTaskType("schedule").withStartDate("02/01/2020")
                    .withEndDate("05/01/2020").withTaskType("schedule").withTags("").build();
            p = new TaskBuilder().withName("Pool Party").withDate("23/02/2017").withStartTime("5:30pm")
                    .withEndTime("6:45pm").withTaskType("schedule").withTags("low").build();
            q = new TaskBuilder().withName("Quartet Performance at Esplanade").withStartDate("12/02/2017")
                    .withEndDate("24/02/2017").withStartTime("8:00am").withEndTime("8:00am").withTaskType("schedule").withTags("low").build();
            r = new TaskBuilder().withName("Rationing Exercise").withStartDate("25/04/2018").withEndDate("31/05/2018")
                    .withStartTime("7:30pm").withEndTime("9:30pm").withTaskType("schedule").withTags("high").build();

            // Manually added
            h = new TaskBuilder().withName("Homemade avacado toast").withDate("24/2/2017").withTaskType("schedule")
                    .build();
            i = new TaskBuilder().withName("International Finance Forum").withDate("13/12/2017").withTime("10:00am")
                    .withTaskType("schedule").withTags("high").build();
            s = new TaskBuilder().withName("Strawberry Crumble Baking Showdown").withDate("14/02/2017")
                    .withTaskType("schedule").build();
            t = new TaskBuilder().withName("Theatre Arts Major Camp").withStartDate("17/06/2019")
                    .withEndDate("29/07/2019").withTaskType("schedule").withTags("medium").build();
            u = new TaskBuilder().withName("University of Nevada, Las Vegas Open Day").withStartDate("24/02/2018")
                    .withEndDate("13/12/2018").withStartTime("6:00pm").withEndTime("10:00pm").withTaskType("schedule")
                    .withTags("low").build();
            v = new TaskBuilder().withName("Victorian Museum Opening").withDate("04/12/2017").withStartTime("8:00am")
                    .withEndTime("10:45am").withTaskType("schedule").build();

        } catch (IllegalValueException ive) {
            ive.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadWhatNowWithSampleData(WhatNow wn) {

        try {
            wn.addTask(new Task(a));
            wn.addTask(new Task(b));
            wn.addTask(new Task(c));
            wn.addTask(new Task(d));
            wn.addTask(new Task(e));
            wn.addTask(new Task(f));
            wn.addTask(new Task(g));
            wn.addTask(new Task(j));
            wn.addTask(new Task(k));
            wn.addTask(new Task(l));
            wn.addTask(new Task(m));
            wn.addTask(new Task(n));
            wn.addTask(new Task(o));
            wn.addTask(new Task(p));
            wn.addTask(new Task(q));
            wn.addTask(new Task(r));

        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { a, b, c, d, e, f, g, j, k, l, m, n, o, p, q, r };
    }

    public WhatNow getTypicalWhatNow() {
        WhatNow wn = new WhatNow();
        loadWhatNowWithSampleData(wn);
        return wn;
    }
}
