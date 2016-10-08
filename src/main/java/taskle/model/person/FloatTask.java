package taskle.model.person;

import taskle.model.tag.UniqueTagList;

public class FloatTask extends Task {

    public FloatTask(Name name, UniqueTagList tags) {
        super(name, tags);
    }
    
    /**
     * Copy constructor.
     */
    public FloatTask(ReadOnlyTask source) {
        super(source);
    }
    
    /**
     * Copy constructor.
     */
    public FloatTask(ModifiableTask source) {
        super(source);
    }
    
    @Override
    public DateTime getDateTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDateTimeString() {
        // TODO Auto-generated method stub
        return null;
    }

}
