package seedu.address.model;


import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashMap;
import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAliasManager {

	public HashMap<String,String> getAlias();
	public String getValueOf(String key);
	
}
