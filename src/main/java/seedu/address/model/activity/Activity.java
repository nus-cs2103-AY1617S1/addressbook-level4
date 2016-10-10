package seedu.address.model.activity;

import java.util.Objects;

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
	
	@Override
	public void setName(String newName) {
		this.name = newName;
	}
	
	// TODO: Re-implement equality if necessary when more details are added
	@Override
	public boolean equals(Object o) {
	    // Check for name equality
	    return o == this ||
	           (o instanceof Activity &&
	            this.name.equals(((Activity)o).name));
	}
}
