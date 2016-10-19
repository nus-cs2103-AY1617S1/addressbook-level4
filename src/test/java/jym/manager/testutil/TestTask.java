package jym.manager.testutil;

import java.time.LocalDateTime;

import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
//    private Address address;
//    private Email email;
//    private Phone phone;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    public void setEmail(Email email) {
//        this.email = email;
//    }
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
//    @Override
//    public Email getEmail() {
//        return email;
//    }
//
//    @Override
//    public Address getAddress() {
//        return address;
//    }

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
        sb.append("add " + this.getDescription().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }


	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Priority getPriority() {
		// TODO Auto-generated method stub
		return null;
	}
}
