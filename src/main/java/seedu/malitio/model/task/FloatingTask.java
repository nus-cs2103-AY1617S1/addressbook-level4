package seedu.malitio.model.task;

import seedu.malitio.model.tag.UniqueTagList;

public class FloatingTask extends Task implements ReadOnlyTask {

	public FloatingTask(Name name, UniqueTagList tags) {
		super(name, tags);
		// TODO Auto-generated constructor stub
	}
	
	public FloatingTask(ReadOnlyTask source) {
        this(source.getName(), source.getTags());
    }

}
