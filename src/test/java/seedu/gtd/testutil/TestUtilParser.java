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
		String changeWithoutPrefix = change.substring(2);
		String changePrefix = change.substring(0, 2);
		System.out.println("From TestUtil Parser: " + changePrefix + " " + changeWithoutPrefix);
		
		switch(change.substring(0, 2)) {
    	case "d/": newTask = new TestTask(task.getName(), new DueDate(parseDueDate(changeWithoutPrefix)), task.getAddress(), task.getPriority(), task.getTags()); break;
    	case "a/": newTask = new TestTask(task.getName(), task.getDueDate(), new Address(changeWithoutPrefix), task.getPriority(), task.getTags()); break;
    	case "p/": newTask = new TestTask(task.getName(), task.getDueDate(), task.getAddress(), new Priority(changeWithoutPrefix), task.getTags()); break;
    	default: newTask = new TestTask(new Name(change), task.getDueDate(), task.getAddress(), task.getPriority(), task.getTags());
		}
		return newTask;
	}
	
	 public static String parseDueDate(String dueDateRaw) {
    	NaturalLanguageProcessor nlp = new DateNaturalLanguageProcessor();
    	return nlp.formatString(dueDateRaw);
    }
}
