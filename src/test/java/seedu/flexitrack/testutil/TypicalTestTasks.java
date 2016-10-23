package seedu.flexitrack.testutil;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask homework1, homework2, homework3, event, soccer, dinner, exam, midterm, basketball, lecture,
            job, homework1EditName, homework1EditDueDate, homework3EditName, homework3EditToTask, soccerEditName,
            soccerEditStartTime, soccerEditEndTime, eventEditToEvent;

    // TODO: change the test case soccer, homework, exam, dinner, homework2,
    // homework3, lecture, basketball, midterm
    public TypicalTestTasks() {
        try {
            homework1 = new TaskBuilder().withName("Homework cs 2103").withDueDate("Jan 11 2017 17:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();
            homework2 = new TaskBuilder().withName("Homework cs 2101").withDueDate("Sep 01 2016 13:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();
            homework3 = new TaskBuilder().withName("Homework ma 1505").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("Feb 29 2000 00:00").build();
            soccer = new TaskBuilder().withName("Soccer training").withStartTime("Jun 30 2016 21:00")
                    .withEndTime("Jun 30 2016 23:00").withDueDate("Feb 29 2000 00:00").build();
            dinner = new TaskBuilder().withName("Dinner with parents").withStartTime("Nov 16 2017 19:00")
                    .withEndTime("Nov 16 2017 20:15").withDueDate("Feb 29 2000 00:00").build();
            exam = new TaskBuilder().withName("MA 1505 Exams").withStartTime("May 12 2016 10:00")
                    .withEndTime("May 12 2016 11:30").withDueDate("Feb 29 2000 00:00").build();
            midterm = new TaskBuilder().withName("Midter cs 2101").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("Mar 23 2017 09:00").build();
            event = new TaskBuilder().withName("Event lol").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("Feb 29 2000 00:00").build();
            // Manually added
            basketball = new TaskBuilder().withName("Basketball training").withStartTime("Dec 25 2016 18:00")
                    .withEndTime("Dec 25 2016 21:00").withDueDate("Feb 29 2000 00:00").build();
            lecture = new TaskBuilder().withName("Lecture CS2103").withStartTime("Oct 10 2016 14:00")
                    .withEndTime("Oct 10 2016 16:00").withDueDate("Feb 29 2000 00:00").build();
            job = new TaskBuilder().withName("Apply Job in Starbucks").withDueDate("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();

            // After edit
            homework1EditName = new TaskBuilder().withName("Name Edited").withDueDate("Jan 11 2016 17:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();
            homework1EditDueDate = new TaskBuilder().withName("Name Edited").withDueDate("Jan 14 2016 10:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();

            soccerEditName = new TaskBuilder().withName("Name Edited").withStartTime("Jun 30 2016 21:00")
                    .withEndTime("Jun 30 2016 23:00").withDueDate("Feb 29 2000 00:00").build();
            soccerEditStartTime = new TaskBuilder().withName("Name Edited").withStartTime("Jun 10 2016 21:00")
                    .withEndTime("Jun 30 2016 23:00").withDueDate("Feb 29 2000 00:00").build();
            soccerEditEndTime = new TaskBuilder().withName("Name Edited").withStartTime("Jun 10 2016 21:00")
                    .withEndTime("Jun 30 2020 6:00").withDueDate("Feb 29 2000 00:00").build();

            homework3EditName = new TaskBuilder().withName("Name Edited").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("Feb 29 2000 00:00").build();
            homework3EditToTask = new TaskBuilder().withName("Name Edited").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("Jun 10 2016 21:00").build();

            eventEditToEvent = new TaskBuilder().withName("Event lol").withStartTime("Jun 10 2016 21:00")
                    .withEndTime("Jun 30 2016 23:00").withDueDate("Feb 29 2000 00:00").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadFlexiTrackWithSampleData(FlexiTrack ab) {
        // TODO: change the add Task cases
        try {
            ab.addTask(new Task(homework1));
            ab.addTask(new Task(homework2));
            ab.addTask(new Task(homework3));
            ab.addTask(new Task(soccer));
            ab.addTask(new Task(dinner));
            ab.addTask(new Task(exam));
            ab.addTask(new Task(midterm));
            ab.addTask(new Task(event));

        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { homework1, homework2, homework3, soccer, dinner, exam, midterm, event };
    }
    
    public TestTask[] getExpectedTypicalFutureTasks() {
        return new TestTask[] { homework1, homework3, dinner, midterm, event };
    }
    
    public TestTask[] getExpectedTypicalPastTasks() {
        return new TestTask[] { homework2, homework3, soccer, exam, event };
    }

    public TestTask[] getExpectedTypicalMarkTasks() {
        try {
            homework1.setName(new Name ("(Done) " + homework1.getName().fullName));
            soccer.setName(new Name ("(Done) " + soccer.getName().fullName));
            exam.setName(new Name ("(Done) " + exam.getName().fullName));
            event.setName(new Name ("(Done) " + event.getName().fullName));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return new TestTask[] { homework1, soccer, exam, event };
    }

    public TestTask[] getExpectedTypicalUnMarkTasks() {
        return new TestTask[] { homework2, homework3, dinner, midterm };
    }

    public TestTask[] getExpectedTypicalFutureMarkTasks() {
        try {
            homework1.setName(new Name ("(Done) " + homework1.getName().fullName));
            soccer.setName(new Name ("(Done) " + soccer.getName().fullName));
            exam.setName(new Name ("(Done) " + exam.getName().fullName));
            event.setName(new Name ("(Done) " + event.getName().fullName));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return new TestTask[] { homework1 };
    }
    
    public FlexiTrack getTypicalFlexiTrack() {
        FlexiTrack ab = new FlexiTrack();
        loadFlexiTrackWithSampleData(ab);
        return ab;
    }
}
