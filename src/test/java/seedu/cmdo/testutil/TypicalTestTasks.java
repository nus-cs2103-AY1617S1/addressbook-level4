package seedu.cmdo.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.model.ToDoList;
import seedu.cmdo.model.task.*;

//@@author A0139661Y
public class TypicalTestTasks {

    public static TestTask grocery, house, family, car, dog, zika, vacation,eat,meeting,businessDeal,deal,editedGrocery,editedZika,floatingGrocery,taggedZika,noPriorityGrocery,editedHouse2,editedHouse1,editedCar;

    public TypicalTestTasks() {
        try {
            grocery = new TaskBuilder().withDetail("Buy more milk").withDueByDate(LocalDate.of(2012, 07, 13)).withDueByTime(LocalTime.of(13, 20)).withPriority("high").build();
            editedGrocery = new TaskBuilder().withDetail("Eat Buffet").withDueByDate(LocalDate.of(2012, 07, 13)).withDueByTime(LocalTime.of(13, 20)).withPriority("high").build();
            //make floating grocery
            floatingGrocery = new TaskBuilder().withDetail("Eat Buffet").withDueByDate(LocalDate.MIN).withDueByTime(LocalTime.MAX).withPriority("high").build();
            noPriorityGrocery = new TaskBuilder().withDetail("Eat Buffet").withDueByDate(LocalDate.MIN).withDueByTime(LocalTime.MAX).withPriority("").build();
            house = new TaskBuilder().withDetail("Paint the house").withDueByDate(LocalDate.of(2010, 06, 20)).withDueByTime(LocalTime.of(10, 20)).withPriority("high").build();
            editedHouse1 = new TaskBuilder().withDetail("Paint the house").withDueByDate(LocalDate.of(2010, 06, 20)).withDueByTime(LocalTime.of(11, 20)).withPriority("high").build();
            editedHouse2 = new TaskBuilder().withDetail("Paint the house").withDueByDate(LocalDate.of(2016, 10, 20)).withDueByTime(LocalTime.of(11, 20)).withPriority("high").build();
            family = new TaskBuilder().withDetail("Give Kelly a bath").withDueByDate(LocalDate.of(2012,11,20)).withDueByTime(LocalTime.of(11, 20)).withPriority("low").build();
            car = new TaskBuilder().withDetail("Add gas").withDueByDate(LocalDate.of(2014,11,20)).withDueByTime(LocalTime.of(9, 20)).withPriority("high").build();
            editedCar = new TaskBuilder().withDetail("Add gas")
					.withDueByDateRange(LocalDate.of(2016, 11, 12), LocalDate.of(2016, 12, 12))
					.withDueByTimeRange(LocalTime.of(13, 00), LocalTime.of(15, 00))
					.withPriority("high")
					.build();
            zika = new TaskBuilder().withDetail("Gas the zika mosquitoes").withDueByDate(LocalDate.of(2014,11,20)).withDueByTime(LocalTime.of(9, 20)).withPriority("high").build();
            editedZika = new TaskBuilder().withDetail("Gas the zika mosquitoes").withDueByDate(LocalDate.of(2014,11,20)).withDueByTime(LocalTime.of(9, 20)).withPriority("low").build();
            taggedZika = new TaskBuilder().withDetail("Gas the zika mosquitoes").withDueByDate(LocalDate.of(2014,11,20)).withDueByTime(LocalTime.of(9, 20)).withPriority("low").withTags("dangerous").build();
            dog = new TaskBuilder().withDetail("Invent automatic dog toilet").withDueByDate(LocalDate.of(2016,10,10)).withDueByTime(LocalTime.of(16, 10)).withPriority("low").withTags("dog").build();
            vacation = new TaskBuilder().withDetail("Take grandma on a cruise")
            							.withDueByDateRange(LocalDate.of(2016, 11, 11), LocalDate.of(2016, 12, 12))
            							.withDueByTimeRange(LocalTime.of(1, 0), LocalTime.of(23, 59))
            							.withPriority("")
            							.build();
            eat = new TaskBuilder().withDetail("Eat bagel").withDueByDate(LocalDate.of(2016, 12, 12)).withDueByTime(LocalTime.of(11, 20)).withPriority("high").build();
            meeting = new TaskBuilder().withDetail("Unconfirmed meeting with boss")
					.withDueByDateRange(LocalDate.of(2016, 11, 11),LocalDate.of(2016, 11, 11))
					.withDueByTimeRange(LocalTime.of(1, 0), LocalTime.of(2, 0))
					.withPriority("")
					.build();
            businessDeal = new TaskBuilder().withDetail("Unconfirmed meeting with carousell")
					.withDueByDateRange(LocalDate.of(2016, 12, 12), LocalDate.of(2016, 12, 12))
					.withDueByTimeRange(LocalTime.of(11, 0), LocalTime.of(15, 00))
					.withPriority("")
					.build();
            deal = new TaskBuilder().withDetail("Unconfirmed meeting with Rakuten Ventures")
					.withDueByDateRange(LocalDate.of(2016, 12, 12), LocalDate.of(2016, 12, 12))
					.withDueByTimeRange(LocalTime.of(11, 0), LocalTime.of(15, 00))
					.withPriority("")
					.build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList td) {
    	td.addTask(new Task(grocery));
        td.addTask(new Task(house));
        td.addTask(new Task(car));
        td.addTask(new Task(zika));
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{grocery, house, car, zika};
    }
    
    //@@author A0139661Y
    public TestTask[] getEmptyTasks() {
    	return new TestTask[]{};
    }
    
    public ToDoList getTypicalToDoList(){
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
