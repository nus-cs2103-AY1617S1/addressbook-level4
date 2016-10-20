package seedu.address.testutil;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestActivity implements ReadOnlyActivity {

    protected Name name;
    protected Reminder reminder;
    private boolean isCompleted;
    protected UniqueTagList tags;
    
    /**
     * Every field must be present and not null.
     */
    public TestActivity(Name name, Reminder reminder, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, reminder, tags);
        this.name = name;
        this.reminder = reminder;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    /**
     * Copy constructor.
     */
    public TestActivity(ReadOnlyActivity source) {
        this(source.getName(), source.getReminder(), source.getTags());
    }
    
    @Override
    public Name getName() {
        return name;
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder address) {
        this.reminder = address;
    }
    
	@Override
	public boolean getCompletionStatus() {
		return isCompleted;
	}

    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass()) {
            return false;
        } else {
            return other == this // short circuit if same object
                    || (other instanceof ReadOnlyActivity // instanceof handles nulls
                    && this.isSameStateAs((ReadOnlyActivity) other));
        }
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, reminder, tags);
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    public void setCompletionStatus(boolean isComplete) {
        isCompleted = isComplete;
        
    }
    
    @Override
	public String toStringCompletionStatus() {
        if(isCompleted) {
        	return "Completed";
        } 
        
        	return "";	
    }

    @Override
    public boolean passedDueDate() {
        // TODO Auto-generated method stub
        return false;
    }
    
    public static Activity create (ReadOnlyActivity act) {
		
    	String actType = act.getClass().getSimpleName().toLowerCase();
    	
    			switch (actType) {
                
    			case "activity":
                    return new Activity(act);
                case "task":
                	 return new Task((ReadOnlyTask) act);
                case "event":
                	return new Event((ReadOnlyEvent) act);
    }
				return null;
    	
    }
    
    //TestActivity specific commands
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("add " + this.getName().fullName + " ");
        
        if (getReminder().value != null && !getReminder().value.equals("")) {
        sb.append("a/" + this.getReminder().value + " ");
        }
        
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
