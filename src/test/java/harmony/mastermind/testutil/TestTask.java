package harmony.mastermind.testutil;

import java.util.Date;

import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }


    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getStartDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getEndDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isArchived() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFloating() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDeadline() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEvent() {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
