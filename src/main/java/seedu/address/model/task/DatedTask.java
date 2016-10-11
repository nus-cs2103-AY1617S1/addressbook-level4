package seedu.address.model.task;

import java.text.SimpleDateFormat;

/**
 * Tasks that implement this interface have dates attached to it
 */
public interface DatedTask {
	// Format for displaying dates
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
}
