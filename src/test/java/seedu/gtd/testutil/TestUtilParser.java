package seedu.gtd.testutil;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.logic.parser.DateNaturalLanguageProcessor;
import seedu.gtd.logic.parser.NaturalLanguageProcessor;
import seedu.gtd.model.task.Address;
import seedu.gtd.model.task.DueDate;
import seedu.gtd.model.task.Name;
import seedu.gtd.model.task.Priority;

/**
 * A utility class that parses tasks for test cases.
 */
public class TestUtilParser {
	
	public static TestTask editTask(TestTask task, String change) throws IllegalValueException {
		
		TestTask newTask = task;
		
		switch(change.substring(0, 2)) {
    	case "d/": newTask = new TestTask(task.getName(), new DueDate(parseDueDate(change)), task.getAddress(), task.getPriority(), task.getTags());
    	case "a/": newTask = new TestTask(task.getName(), task.getDueDate(), new Address(change), task.getPriority(), task.getTags());
    	case "p/": newTask = new TestTask(task.getName(), task.getDueDate(), task.getAddress(), new Priority(change), task.getTags());
    	default: newTask = new TestTask(new Name(change), task.getDueDate(), task.getAddress(), task.getPriority(), task.getTags());
		}
		return newTask;
	}
	
	 public static String parseDueDate(String dueDateRaw) {
    	NaturalLanguageProcessor nlp = new DateNaturalLanguageProcessor();
    	return nlp.formatString(dueDateRaw);
    }
}