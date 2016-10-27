package jym.manager.testutil;

import java.time.LocalDateTime;

import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Location address;
    private Deadline deadline;
    private Priority pri;
//    private Phone phone;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public void setDate(LocalDateTime date) {
        this.deadline = new Deadline(date);
    }
//
//    public void setPhone(Phone phone) {
//        this.phone = phone;
//    }

    @Override
    public Description getDescription() {
        return description;
    }

//    @Override
//    public Phone getPhone() {
//        return phone;
//    }
//
    @Override
    public Deadline getDate() {
        return this.deadline;
    }

    @Override
    public Location getLocation() {
        return address;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().toString());
        if(this.hasDeadline()){
        	sb.append(" by " + this.getDate().toString());
        }
        if(this.getLocation() != null){
        	sb.append(" at " + this.getLocation().toString());
        }
     //   this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    public boolean hasDeadline(){
    	return (this.deadline != null && this.deadline.hasDeadline());
    }
	@Override
	public Priority getPriority() {
		return pri;
	}

	@Override
	public Complete getComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDateString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deadline getEndTime() {
		// TODO Auto-generated method stub
		return null;
	}
}
