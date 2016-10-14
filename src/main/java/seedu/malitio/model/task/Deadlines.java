package seedu.malitio.model.task;

import seedu.malitio.model.tag.UniqueTagList;

public class Deadlines extends Task {

	private DateTime due = null;
	
	public Deadlines(Name name, DateTime due, UniqueTagList tags) {
		super(name, tags);
		this.due = due;
		
	}
	
	/**
     * Copy constructor.
     */
    public Deadlines(ReadOnlyTask source) {
        super(source);
    }

}
