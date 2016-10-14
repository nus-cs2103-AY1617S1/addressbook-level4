/**
 * 
 */
package seedu.malitio.model.task;

import seedu.malitio.model.tag.UniqueTagList;

public class Events extends Task {
	
	private DateTime start = null;
    private DateTime end = null;

	public Events(Name name, DateTime start, DateTime end, UniqueTagList tags) {
		super(name, tags);
		this.start = start;
        this.end = end;
	}
	
	/**
     * Copy constructor.
     */
    public Events(ReadOnlyTask source) {
        super(source);
    }

}
