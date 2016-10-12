package seedu.address.model.item;
import seedu.address.model.tag.UniqueTagList;

public class FloatingTask extends Item {

	public FloatingTask(Description desc) {
		super(desc);
	}
	
    /**
     * Copy constructor.
     */
    public FloatingTask(ReadOnlyItem source) {
        this(source.getDescription());
    }


	@Override
	public UniqueTagList getTags() {
		// TODO Auto-generated method stub
		return null;
	}

}
