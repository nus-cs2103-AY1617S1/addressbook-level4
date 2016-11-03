package seedu.flexitrack.testutil;

import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.task.Name;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList;

/**
 * @param <lecture1>
 *
 */
public class TypicalTestTasks<lecture1> {

    public static TestTask homework1, homework2, homework3, event, soccer, dinner, exam, midterm, basketball, lecture,job, 
            homework1EditName, homework1EditDueDate, homework3EditName, homework3EditToTask, soccerEditName,
            soccerEditStartTime, soccerEditEndTime, eventEditToEvent, tutorial1,tutorial2,tutorial3,
            lecture1, lecture2, lecture3, lecture4, lecture5, exam1, exam2, exam3, study1, study2, past1, past2;

    public TypicalTestTasks() {
        try {
            // @@author A0127686R
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
            tutorial2 = new TaskBuilder().withName("(Blocked) for CS 2103 tutorial 2").withStartTime("Nov 21 2017 19:00")
                    .withEndTime("Nov 21 2017 20:15").withDueDate("Feb 29 2000 00:00").build();
            // Manually added
            basketball = new TaskBuilder().withName("Basketball training").withStartTime("Dec 25 2016 18:00")
                    .withEndTime("Dec 25 2016 21:00").withDueDate("Feb 29 2000 00:00").build();
            lecture = new TaskBuilder().withName("Lecture CS2103").withStartTime("Oct 10 2016 14:00")
                    .withEndTime("Oct 10 2016 16:00").withDueDate("Feb 29 2000 00:00").build();
            job = new TaskBuilder().withName("Apply Job in Starbucks").withDueDate("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();
            tutorial1 = new TaskBuilder().withName("CS2103 tutorial 1").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("11 Nov 23:59").build(); 
            tutorial3 = new TaskBuilder().withName("(Blocked) for CS 2103 tutorial 3").withStartTime("Nov 21 2017 19:00")
                    .withEndTime("Nov 21 2017 20:15").withDueDate("Feb 29 2000 00:00").build();
            
            // @@author A0127855W
            // After edit
            homework1EditName = new TaskBuilder().withName("Name Edited").withDueDate("Jan 11 2017 17:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();
            homework1EditDueDate = new TaskBuilder().withName("Name Edited").withDueDate("Jan 14 2016 10:00")
                    .withEndTime("Feb 29 2000 00:00").withStartTime("Feb 29 2000 00:00").build();

            soccerEditName = new TaskBuilder().withName("Name Edited 2").withStartTime("Jun 30 2016 21:00")
                    .withEndTime("Jun 30 2016 23:00").withDueDate("Feb 29 2000 00:00").build();
            soccerEditStartTime = new TaskBuilder().withName("Name Edited 2").withStartTime("Jun 10 2016 21:00")
                    .withEndTime("Jun 30 2016 23:00").withDueDate("Feb 29 2000 00:00").build();
            soccerEditEndTime = new TaskBuilder().withName("Name Edited 2").withStartTime("Jun 10 2016 21:00")
                    .withEndTime("Jun 30 2020 6:00").withDueDate("Feb 29 2000 00:00").build();

            homework3EditName = new TaskBuilder().withName("Name Edited 3").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("Feb 29 2000 00:00").build();
            homework3EditToTask = new TaskBuilder().withName("Name Edited 3").withStartTime("Feb 29 2000 00:00")
                    .withEndTime("Feb 29 2000 00:00").withDueDate("Jun 10 2016 21:00").build();

            eventEditToEvent = new TaskBuilder().withName("Event lol").withStartTime("Jun 10 2016 21:00")
                    .withEndTime("Jun 30 2016 23:00").withDueDate("Feb 29 2000 00:00").build();

            // @@author A0127686R
            // Additional Event Task 
            lecture1 = new TaskBuilder().withName("lecture 1").withStartTime("Nov 08 2016 09:00")
                    .withEndTime("Nov 08 2016 11:00").withDueDate("Feb 29 2000 00:00").build();
            lecture2 = new TaskBuilder().withName("lecture 2").withStartTime("Nov 08 2016 15:00")
                    .withEndTime("Nov 08 2016 16:00").withDueDate("Feb 29 2000 00:00").build();
            lecture3 = new TaskBuilder().withName("lecture 3").withStartTime("Nov 09 2016 14:00")
                    .withEndTime("Nov 09 2016 16:00").withDueDate("Feb 29 2000 00:00").build();
            lecture4 = new TaskBuilder().withName("lecture 4").withStartTime("Nov 12 2016 10:00")
                    .withEndTime("Nov 12 2016 12:00").withDueDate("Feb 29 2000 00:00").build();
            lecture5 = new TaskBuilder().withName("lecture 5").withStartTime("Nov 12 2016 13:00")
                    .withEndTime("Nov 12 2016 14:00").withDueDate("Feb 29 2000 00:00").build();
            exam1 = new TaskBuilder().withName("exam 1").withStartTime("Nov 20 2016 09:00")
                    .withEndTime("Nov 20 2016 10:30").withDueDate("Feb 29 2000 00:00").build();
            exam2 = new TaskBuilder().withName("exam 2").withStartTime("Nov 20 2016 12:00")
                    .withEndTime("Nov 20 2016 14:00").withDueDate("Feb 29 2000 00:00").build();
            exam3 = new TaskBuilder().withName("exam 3").withStartTime("Nov 22 2016 19:00")
                    .withEndTime("Nov 22 2016 21:00").withDueDate("Feb 29 2000 00:00").build();
            study1 = new TaskBuilder().withName("study 1").withStartTime("Nov 18 2016 09:00")
                    .withEndTime("Nov 18 2016 23:00").withDueDate("Feb 29 2000 00:00").build();
            study2 = new TaskBuilder().withName("study 2").withStartTime("Nov 19 2016 06:00")
                    .withEndTime("Nov 19 2016 10:00").withDueDate("Feb 29 2000 00:00").build();     
            past1 = new TaskBuilder().withName("past 1").withStartTime("Nov 01 2016 09:00")
                    .withEndTime("Nov 01 2016 11:00").withDueDate("Feb 29 2000 00:00").build();
            past2 = new TaskBuilder().withName("past 2").withStartTime("Oct 20 2016 15:00")
                    .withEndTime("Oct 20 2016 16:00").withDueDate("Feb 29 2000 00:00").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
    
    // @@author 
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
            ab.sort();

        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    // @@author A0127686R
    public TestTask[] getTypicalSortedTasks() {
        TestTask[] testTask = new TestTask[] { event, homework3, exam, soccer, homework2, homework1, midterm, dinner};
        return testTask;
    }
    
    public TestTask[] getTypicalUnsortedTasks() {
        TestTask[] testTask = new TestTask[] { homework1, homework2, homework3, soccer, dinner, exam, midterm, event };
        return testTask;
    }
    
    public TestTask[] getExpectedTypicalFutureTasks() {
        return new TestTask[] { homework1, homework3, dinner, midterm, event };
    }
    
    public TestTask[] getExpectedTypicalPastTasks() {
        return new TestTask[] { homework2, homework3, soccer, exam, event };
    }
    
    public TestTask[] getTypicalEventTasks() {
        TestTask[] testTask = new TestTask[] { lecture1, lecture2, lecture3, lecture4, lecture5, exam1, exam2, exam3, study1, study2};
        return testTask;
    }

    public TestTask[] getExpectedTypicalMarkTasks() {
        try {
            homework1.setName(new Name(homework1.getName().toString()));
            soccer.setName(new Name(soccer.getName().toString()));
            exam.setName(new Name(exam.getName().toString()));
            event.setName(new Name(event.getName().toString()));
        } catch (IllegalValueException e) {
            assert false;
        }
        homework1.getName().setAsMark();
        soccer.getName().setAsMark();
        exam.getName().setAsMark();
        event.getName().setAsMark();
        return new TestTask[] { homework1, soccer, exam, event };
    }

    public TestTask[] getExpectedTypicalUnMarkTasks() {
        return new TestTask[] { homework2, homework3, dinner, midterm };
    }

    public TestTask[] getExpectedTypicalFutureMarkTasks() {
        try {
            homework1.setName(new Name (homework1.getName().toString()));
            soccer.setName(new Name (soccer.getName().toString()));
            exam.setName(new Name (exam.getName().toString()));
            event.setName(new Name (event.getName().toString()));
        } catch (IllegalValueException e) {
            assert false;
        }
        homework1.getName().setAsMark();
        soccer.getName().setAsMark();
        exam.getName().setAsMark();
        event.getName().setAsMark();
        return new TestTask[] { homework1 };
    }
    
    // @@author 
    public FlexiTrack getTypicalFlexiTrack() {
        FlexiTrack ab = new FlexiTrack();
        loadFlexiTrackWithSampleData(ab);
        return ab;
    }

    public TestTask[] getExpectedTypicalNextWeekTasks() {
        return new TestTask[] { lecture1 };
    }

    public TestTask[] getExpectedTypicalLastWeekTasks() {
        return new TestTask[] { past1 };
    }

    public TestTask[] getExpectedTypicalNextMonthTasks() {
        return new TestTask[] { lecture1, exam1 };
    }

    public TestTask[] getExpectedTypicalLastMonthTasks() {
        return new TestTask[] { past2, past1 };
    }
    
}
