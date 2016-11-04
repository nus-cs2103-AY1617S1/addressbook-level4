package seedu.simply.testutil;

import seedu.simply.commons.exceptions.IllegalValueException;
import seedu.simply.model.TaskBook;
import seedu.simply.model.task.*;
import seedu.simply.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * @@author A0138993L
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;
    public static TestDeadline deadline1, deadline2, deadline3, deadline4, deadline5, deadline6, deadline7, deadline8, deadline9;
    public static TestTodo todo1, todo2, todo3, todo4, todo5, todo6, todo7, todo8, todo9;

    public TypicalTestTasks() {
        try {
        	//Events
        	alice =  new TaskBuilder().withName("Alice Pauline").withEnd("2359")
        			.withStart("1100").withDate("12.12.23").withTaskCat(1).withIsCompleted(false)
        			.withOverdue(0).withTags("friends").build();
        	benson = new TaskBuilder().withName("Benson Meier").withEnd("6pm")
        			.withStart("2am").withDate("13.12.23").withTaskCat(1).withIsCompleted(false)
        			.withOverdue(0).withTags("owesMoney", "friends").build();
        	carl = new TaskBuilder().withName("Carl Kurz").withDate("14/12/23").withStart("10am").withEnd("2350")
        			.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
        	daniel = new TaskBuilder().withName("Daniel Meier").withDate("15.12.23").withStart("7am").withEnd("11pm")
        			.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
        	elle = new TaskBuilder().withName("Elle Meyer").withDate("16.12.23").withStart("3am").withEnd("4pm")
        			.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
        	fiona = new TaskBuilder().withName("Fiona Kunz").withDate("17/12/23").withStart("1000").withEnd("1300")
        			.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
        	george = new TaskBuilder().withName("George Best").withDate("181223").withStart("1111").withEnd("1212")
        			.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
        	//deadlines
        	deadline1 = new DeadlineBuilder().withName("Ali Pauline").withEnd("2359")
        			.withDate("12.12.23").withStart("no start").withTaskCat(2).withIsCompleted(false)
        			.withOverdue(0).withTags("friends").build();
        	deadline2 = new DeadlineBuilder().withName("Ben Meier").withEnd("6pm")
        			.withDate("13.12.23").withStart("no start").withTaskCat(2).withIsCompleted(false)
        			.withOverdue(0).withTags("owesMoney", "friends").build();
        	deadline3 = new DeadlineBuilder().withName("Car Kurz").withDate("14/12/23").withStart("no start").withEnd("2350")
        			.withTaskCat(2).withIsCompleted(false).withOverdue(0).build();
        	deadline4 = new DeadlineBuilder().withName("Dan Meier").withDate("15.12.23").withStart("no start").withEnd("11pm")
        			.withTaskCat(2).withIsCompleted(false).withOverdue(0).build();
        	deadline5 = new DeadlineBuilder().withName("Ellie Meyer").withDate("16.12.23").withStart("no start").withEnd("4pm")
        			.withTaskCat(2).withIsCompleted(false).withOverdue(0).build();
        	deadline6 = new DeadlineBuilder().withName("Fon Kunz").withDate("17/12/23").withStart("no start").withEnd("1300")
        			.withTaskCat(2).withIsCompleted(false).withOverdue(0).build();
        	deadline7 = new DeadlineBuilder().withName("Gorgc Best").withDate("181223").withStart("no start").withEnd("1212")
        			.withTaskCat(2).withIsCompleted(false).withOverdue(0).build();
        	//todos
        	todo1 = new TodoBuilder().withName("Alsi Pauline").withEnd("no end")
        			.withDate("no date").withStart("no start").withTaskCat(3).withIsCompleted(false)
        			.withOverdue(0).withTags("friends").build();
        	todo2 = new TodoBuilder().withName("Beny Meier").withEnd("no end")
        			.withDate("no date").withStart("no start").withTaskCat(3).withIsCompleted(false)
        			.withOverdue(0).withTags("owesMoney", "friends").build();
        	todo3 = new TodoBuilder().withName("CarC Kurz").withDate("no date").withStart("no start").withEnd("no end")
        			.withTaskCat(3).withIsCompleted(false).withOverdue(0).build();
        	todo4 = new TodoBuilder().withName("an Meier").withDate("no date").withStart("no start").withEnd("no end")
        			.withTaskCat(3).withIsCompleted(false).withOverdue(0).build();
        	todo5 = new TodoBuilder().withName("Elie Meyer").withDate("no date").withStart("no start").withEnd("no end")
        			.withTaskCat(3).withIsCompleted(false).withOverdue(0).build();
        	todo6 = new TodoBuilder().withName("Sin Kunz").withDate("no date").withStart("no start").withEnd("no end")
        			.withTaskCat(3).withIsCompleted(false).withOverdue(0).build();
        	todo7 = new TodoBuilder().withName("Gorgc Best").withDate("no date").withStart("no start").withEnd("no end")
        			.withTaskCat(3).withIsCompleted(false).withOverdue(0).build();

        	//Manually added
        	hoon = new TaskBuilder().withName("Hoon Meier").withDate("191223").withStart("10am").withEnd("2pm")
        			.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
        	ida = new TaskBuilder().withName("Ida Mueller").withDate("201223").withStart("2am").withEnd("4pm")
        			.withTaskCat(1).withIsCompleted(false).withOverdue(0).build();
        	deadline8 = new DeadlineBuilder().withName("Hoon Meier").withDate("191223").withStart("no start").withEnd("2pm")
        			.withTaskCat(2).withIsCompleted(false).withOverdue(0).build();
        	deadline9 = new DeadlineBuilder().withName("Id Mueller").withDate("201223").withStart("no start").withEnd("4pm")
            		.withTaskCat(2).withIsCompleted(false).withOverdue(0).build();
        	todo8 = new TodoBuilder().withName("on Meier").withDate("no date").withStart("no start").withEnd("no end")
        			.withTaskCat(3).withIsCompleted(false).withOverdue(0).build();
        	todo9 = new TodoBuilder().withName("Ijd Mueller").withDate("no date").withStart("no start").withEnd("no end")
            		.withTaskCat(3).withIsCompleted(false).withOverdue(0).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
            ab.addTask(new Task(deadline1));
            ab.addTask(new Task(deadline2));
            ab.addTask(new Task(deadline3));
            ab.addTask(new Task(deadline4));
            ab.addTask(new Task(deadline5));
            ab.addTask(new Task(deadline6));
            ab.addTask(new Task(deadline7));
            ab.addTask(new Task(todo1));
            ab.addTask(new Task(todo2));
            ab.addTask(new Task(todo3));
            ab.addTask(new Task(todo4));
            ab.addTask(new Task(todo5));
            ab.addTask(new Task(todo6));
            ab.addTask(new Task(todo7));
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }


    public TestDeadline[] getTypicalDeadline() {
        return new TestDeadline[]{deadline1, deadline2, deadline3, deadline4, deadline5, deadline6, deadline7};
    }
    
    public TestTodo[] getTypicalTodo() {
    	return new TestTodo[]{todo1, todo2, todo3, todo4, todo5, todo6, todo7};
    }

    public TestTask getSelectedPerson(int index) {
    	TestTask[] tasks = new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    	return tasks[index];
    }

    public TestDeadline getSelectedDeadline(int index) {
        TestDeadline[] deadlines = new TestDeadline[]{deadline1, deadline2, deadline3, deadline4, deadline5, deadline6, deadline7};
        return deadlines[index];
    }

    public TestTodo getSelectedTodo(int index) {
    	TestTodo[] todos = new TestTodo[]{todo1, todo2, todo3, todo4, todo5, todo6, todo7};
    	return todos[index];
    }
    
    public TaskBook getTypicalTaskBook(){
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
