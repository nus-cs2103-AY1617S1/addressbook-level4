package seedu.address.model.activity;

public class Activity implements ReadOnlyActivity {
	public String name;
	
	public Activity(String name) {
		this.name = name;
	}

	/**
	 * Copy constructor
	 */
	public Activity(ReadOnlyActivity source) {
	    this(source.getName());
	}
	
	@Override
	public String getName() {
	    return name;
	}
}
