package seedu.testplanner.testutil;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.logic.parser.nattyParser;
import seedu.dailyplanner.model.category.Category;
import seedu.dailyplanner.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.dailyplanner.model.task.*;
//@@author A0146749N
/**
 *
 */
public class TaskBuilder {

	private TestTask task;
	nattyParser np;

	public TaskBuilder() {
		this.task = new TestTask();
		np = new nattyParser();
	}

	public TaskBuilder withName(String name) {
		this.task.setName(name);
		return this;
	}

	public TaskBuilder withStartDateAndTime(String st) {
		String convertedSt = np.parse(st);
		String[] dateTimeArray = convertedSt.split(" ");
		this.task.setStart(new DateTime(new Date(dateTimeArray[0]), new Time(dateTimeArray[1])));
		return this;
	}

	public TaskBuilder withStartDate(String st) {
		String convertedSt = np.parseDate(st);
		this.task.setStart(new DateTime(new Date(convertedSt), new Time("")));
		return this;
	}
	
	public TaskBuilder withoutStart() {
		this.task.setStart(new DateTime(new Date(""), new Time("")));
		return this;
	}

	public TaskBuilder withEndDateAndTime(String et) {
		String convertedEt = np.parse(et);
		String[] dateTimeArray = convertedEt.split(" ");
		this.task.setEnd(new DateTime(new Date(dateTimeArray[0]), new Time(dateTimeArray[1])));
		return this;
	}
	
	public TaskBuilder withEndDate(String et) {
		String convertedEt = np.parseDate(et);
		this.task.setEnd(new DateTime(new Date(convertedEt), new Time("")));
		return this;
	}
	
	public TaskBuilder withoutEnd() {
		this.task.setEnd(new DateTime(new Date(""), new Time("")));
		return this;
	}
	//@@author A0139102U
	public TaskBuilder withCompletion(boolean completion) {
		this.task.setCompletion(completion);
		return this;
	}
	
	public TaskBuilder withPin(boolean pinStatus){
		this.task.setPin(pinStatus);
		return this;
	}

	public TaskBuilder withCategories(String... cats) throws IllegalValueException {
		for (String cat : cats) {
			task.getCats().add(new Category(cat));
		}
		return this;
	}

	public TestTask build() {
		return this.task;
	}
	
	public Task buildAsTask() {
	    Task toReturn = this.task.asTask();
	    
	    return toReturn;
	}

}
